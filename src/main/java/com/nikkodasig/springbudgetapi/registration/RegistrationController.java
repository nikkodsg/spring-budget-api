package com.nikkodasig.springbudgetapi.registration;

import com.nikkodasig.springbudgetapi.dto.MessageResponse;
import com.nikkodasig.springbudgetapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/registration")
public class RegistrationController {

    private UserRepository userRepository;
    private RegistrationService registrationService;

    public RegistrationController(UserRepository userRepository, RegistrationService registrationService) {
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already exists!"));
        }

        String token = registrationService.register(registrationRequest);
//        Map<String, String> responseBody = new HashMap<>();
//        responseBody.put("confirmationToken", token);
        return ResponseEntity.ok().body(new RegistrationResponse(token));
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam String token) {
        registrationService.confirmRegistration(token);
        return ResponseEntity.ok(new MessageResponse("Email confirmed!"));
    }
}
