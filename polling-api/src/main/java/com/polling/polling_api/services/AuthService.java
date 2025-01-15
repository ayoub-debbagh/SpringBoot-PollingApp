
package com.polling.polling_api.services;



import com.polling.polling_api.models.User;
import com.polling.polling_api.repositories.UserRepository;
import com.polling.polling_api.security.JwtUtil;
import com.polling.polling_api.dtos.AuthRequestDTO;
import com.polling.polling_api.dtos.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.polling.polling_api.dtos.RegisterRequestDTO;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtTokenUtil;


    public void register (RegisterRequestDTO registerRequest) {
        String username = registerRequest.getUsername();
        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already in use");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email is taken");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));

        userRepository.save(user);
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authCredentials) {
        User user = userRepository.findByUsername(authCredentials.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Username or Password"));

        if (!passwordEncoder.matches(authCredentials.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtTokenUtil.generateToken(user.getUsername());
        return new AuthResponseDTO(token);

    }
}

