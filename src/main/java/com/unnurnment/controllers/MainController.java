package com.unnurnment.controllers;

import com.unnurnment.model.User;
import com.unnurnment.repository.UserRepo;
import javassist.NotFoundException;
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
       if (user.getUsername().isEmpty() && user.getEmail().isEmpty()){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }else {
           userRepo.save(user);
           return new ResponseEntity<User>(HttpStatus.OK);
       }
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Iterable<User>> find(){
        final Iterable<User> users = userRepo.findAll();
        if (users.iterator().hasNext()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody User user){
        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            user.setId(id);
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id){
        final Optional<User> users = userRepo.findById(id);
        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            userRepo.deleteById(id);
            return new ResponseEntity<User>(HttpStatus.OK);
        }
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> readById (@PathVariable(name = "id") long id) throws NotFoundException {
        final Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isEmpty()){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(userOptional,HttpStatus.OK);
        }
    }
}
