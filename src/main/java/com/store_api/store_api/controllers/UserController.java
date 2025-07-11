package com.store_api.store_api.controllers;

import com.store_api.store_api.dtos.RegisterUserRequest;
import com.store_api.store_api.dtos.UpdateUserPassword;
import com.store_api.store_api.dtos.UpdateUserRequest;
import com.store_api.store_api.dtos.UserDto;
import com.store_api.store_api.entities.Role;
import com.store_api.store_api.entities.User;
import com.store_api.store_api.mappers.UserMapper;
import com.store_api.store_api.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "", name = "sort") String sort) {

        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }
        return userRepository.findAll(Sort.by(sort).descending())
                .stream()
                .map(userMapper::userToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.userToDto(user));
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody RegisterUserRequest registerUserRequest,
            UriComponentsBuilder uriComponentsBuilder) {

        //Vérifier le doublon sur les adresses mails
        if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email", "L'email " + registerUserRequest.getEmail() + " existe déjà"));
        }

        //Transformer le dto en entité
        User user = userMapper.userDtoToUser(registerUserRequest);
        //Crypter le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER); //Définir manuellement pour des tents
        User saveUser = userRepository.save(user);

        UserDto userDto = userMapper.userToDto(saveUser);

        //Créer le lien de l'objet nouvellement crée
        URI url = uriComponentsBuilder
                .path("/users/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();

        return ResponseEntity.created(url).body(userDto);
    }

    //Modifier un user sans son mot de passe
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id,
                                              @RequestBody UpdateUserRequest userRequest) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        User user1 = userMapper.updateUser(userRequest, user);

        User SaveUser = userRepository.save(user1);

        return ResponseEntity.ok(userMapper.userToDto(SaveUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/change-password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @RequestBody UpdateUserPassword updateUserPassword) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        //Vérifier si l'ancien mot est réellement saisi par l'utilisateur propriétaire
        if (!user.getPassword().equals(updateUserPassword.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        //Modifier
        user.setPassword(updateUserPassword.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }


//    @PostMapping
//    public ResponseEntity<UserDto> createUser(@RequestBody RegisterUserRequest registerUserRequest) {
//        //Transformer le dto en entité
//        User user = userMapper.userDtoToUser(registerUserRequest);
//        User user1 = userRepository.save(user);
//
//        return ResponseEntity.ok(userMapper.userToDto(user1));
//    }


}
