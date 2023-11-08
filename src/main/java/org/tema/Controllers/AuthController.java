package org.tema.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tema.Repositories.UserRepository;
import org.tema.domain.dto.UserDTO;
import org.tema.jwt.JwtCore;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager manager;
    private JwtCore jwtCore;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setManager(AuthenticationManager manager) {
        this.manager = manager;
    }
    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @PostMapping("/signin")
    ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        if(userRepository.existsByUsername(userDTO.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This username already in use!");
        }
    }

    @PostMapping("/signup")
    ResponseEntity<?> signin(){

    }

}
