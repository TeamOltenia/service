package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.UserService;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(userService.getUserById(id));
    }

    @PutMapping(value = "/updateById/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUserById(@PathVariable("id") String id, @RequestBody User user) {

        String objId = userService.updateUserById(id, user);

        return ResponseEntity.ok()
                .body(userService.getUserById(objId));
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception{
        String id= userService.saveUser(user);
        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id).toUriString();
        return ResponseEntity.created(new URI(uri))
                .body(userService.getUserById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") String id) {

        userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}
