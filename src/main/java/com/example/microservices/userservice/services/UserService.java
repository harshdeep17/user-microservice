package com.example.microservices.userservice.services;

import com.example.microservices.userservice.constants.MessageConstants;
import com.example.microservices.userservice.exceptions.InvalidCredentialsException;
import com.example.microservices.userservice.exceptions.TokenNotFoundException;
import com.example.microservices.userservice.exceptions.UserNotFoundException;
import com.example.microservices.userservice.models.Token;
import com.example.microservices.userservice.models.User;
import com.example.microservices.userservice.repositories.TokenRepository;
import com.example.microservices.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Signs up a new user with the provided details.
     *
     * @param name     The name of the user.
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The created User entity.
     */
    public User signup(String name, String email, String password) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setHashedPassword(bCryptPasswordEncoder.encode(password));
        newUser.setEmailVerified(true);

        return userRepository.save(newUser);
    }

    /**
     * Logs in a user by validating their credentials.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return A token string if the login is successful.
     * @throws UserNotFoundException        If the user is not found.
     * @throws InvalidCredentialsException If the credentials are invalid.
     */
    public String login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("USER_NOT_FOUND",
                    MessageFormat.format(MessageConstants.USER_NOT_FOUND, email));
        }

        boolean isPasswordValid = bCryptPasswordEncoder.matches(password, optionalUser.get().getHashedPassword());
        if (!isPasswordValid) {
            throw new InvalidCredentialsException("INVALID_CREDENTIALS", MessageConstants.INVALID_CREDENTIALS);
        }

        Token token = generateToken(optionalUser.get());
        Token savedToken = tokenRepository.save(token);

        return savedToken.getValue();
    }

    /**
     * Generates a new token for the provided user.
     *
     * @param user The user for whom the token is generated.
     * @return A new Token entity.
     */
    private Token generateToken(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpireAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);

        return token;
    }
    public void logout(String tokenValue){
        Optional<Token> optionalToken =
                tokenRepository.findByValueAndIsDeleted(tokenValue,false);
        if(optionalToken.isEmpty()){
            throw new TokenNotFoundException("INVALID_TOKEN",MessageConstants.INVALID_TOKEN);
        }
        Token token=optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }
    public User validateToken(String tokenValue){
        Optional<Token> optionalToken =
                tokenRepository.findByValueAndIsDeletedAndExpireAtGreaterThan
                        (tokenValue,false,new Date());
        if(optionalToken.isEmpty()){
            throw new TokenNotFoundException("INVALID_TOKEN",MessageConstants.INVALID_TOKEN);
        }
        return optionalToken.get().getUser();
    }
}

