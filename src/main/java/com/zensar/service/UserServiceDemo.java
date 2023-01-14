package com.zensar.service;
import com.zensar.dto.AuthenticationRequest;
import com.zensar.dto.AuthenticationResponse;
import com.zensar.dto.RegisterRequest;
import com.zensar.entity.Role;
import com.zensar.entity.User;
import com.zensar.repository.UserRepository;
import com.zensar.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceDemo {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    private JwtService service;

    private AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        String authToken = service.generateToken(user);
        var authResponse = AuthenticationResponse.builder()
                .token(authToken)
                .build();

        return authResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail() ,
                        registerRequest.getPassword()
                )
        );
     User user =  repository.findByEmail(registerRequest.getEmail()).orElseThrow( ()-> new UsernameNotFoundException(""));
        final String jwtToken = service.generateToken(user);

        return new AuthenticationResponse().builder().token(jwtToken).build();
    }
}
