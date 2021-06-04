package com.unnurnment.controllers;

import com.unnurnment.model.User;
import com.unnurnment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class MainController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody User user){
        userRepo.save(user);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Iterable<User>> find(){
        final Iterable<User> users = userRepo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody User user){
        Optional<User> userOptional = userRepo.findById(id);
        user.setId(id);
        userRepo.save(user);

        if (userOptional.isEmpty()){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<User>(HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id){
        userRepo.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> readById (@PathVariable(name = "id") long id){
        final User user = userRepo.findById(id).orElseThrow();
        return user != null
                ? new ResponseEntity<>(user,HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
}
