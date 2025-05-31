package vn.ltdt.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ltdt.SocialNetwork.dtos.user.UserDTO;
import vn.ltdt.SocialNetwork.dtos.user.UserListDTO;
import vn.ltdt.SocialNetwork.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserListDTO>> getUserByUsername(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String searchText
    ) {
        return ResponseEntity.ok(userService.findUser(pageNum, pageSize, Optional.ofNullable(searchText), Optional.ofNullable(sortField)));
    }

    @GetMapping("{userEmail}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String userEmail) {
        return ResponseEntity.ok(userService.findByEmail(userEmail));
    }

    @PatchMapping("{userEmail}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userEmail, @RequestBody UserDTO userDTO) {
        return null;
    }

    @PatchMapping("avatar/{userEmail}")
    public ResponseEntity<UserDTO> updateUserAvatar(@PathVariable String userEmail, @RequestPart MultipartFile file) {
        return null;
    }

}
