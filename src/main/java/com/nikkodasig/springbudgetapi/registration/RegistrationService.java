package com.nikkodasig.springbudgetapi.registration;

import com.nikkodasig.springbudgetapi.email.EmailSender;
import com.nikkodasig.springbudgetapi.exception.NotFoundException;
import com.nikkodasig.springbudgetapi.model.User;
import com.nikkodasig.springbudgetapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ConfirmationTokenService confirmationTokenService;
    private EmailSender emailSender;
    private String clientUrl;

    public RegistrationService(UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               ConfirmationTokenService confirmationTokenService,
                               EmailSender emailSender) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    public String register(RegistrationRequest request) {
        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        // generate registration token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, user);

        confirmationTokenService.saveToken(confirmationToken);

        // send email to confirm registration
        String link = "http://localhost:8080/api/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link));

        return token;
    }

    @Transactional
    public void confirmRegistration(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token)
                .orElseThrow(() -> new NotFoundException("Token not found."));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed.");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableUser(confirmationToken.getUser().getEmail());
    }

    // TODO: Move to a User service
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    private String buildEmail(String name, String link) {
        return "Hi " + name + "! Thank you for registering. Please click this" + "<a href='" + link + "'> link </a>" +
                "to verify your email and activate your account.";
    }
}
