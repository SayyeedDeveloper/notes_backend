package sayyeed.com.notesbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sayyeed.com.notesbackend.dto.user.UserResponseDTO;
import sayyeed.com.notesbackend.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUserInfo() {
        return ResponseEntity.ok(service.getUserInfo());
    }
}
