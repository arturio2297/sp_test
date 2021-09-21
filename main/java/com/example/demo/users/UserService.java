package com.example.demo.users;

import com.example.demo.registration.email.EmailService;
import com.example.demo.registration.token.TokenEntity;
import com.example.demo.registration.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final static String U_N_F_M =
            "User with email: %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder encoder,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(U_N_F_M, email)));
    }

    public String singUpUser(UserEntity user) {
        boolean exist = userRepository.findUserByEmail(user.getUsername())
                .isPresent();
        if (exist) {
            throw new IllegalStateException("email already registered");
        }
        var encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
        var token = new TokenEntity(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        tokenService.saveConfirmationToken(token);
        return token.getToken();
    }

    public void enableUserAccount(String email) {
        var user = userRepository.findUserByEmail(email)
                .orElseThrow(()-> new IllegalStateException("user not found"));
        user.setEnabled(true);
        user.setLocked(true);
        userRepository.save(user);
    }
}
