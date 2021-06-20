package com.unnurnment.controllers;

import com.unnurnment.model.Massage;
import com.unnurnment.model.User;
import com.unnurnment.repository.MassageRepo;
import com.unnurnment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class MainController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MassageRepo massageRepo;

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody User user){
       if (user.getUsername().isEmpty() && user.getEmail().isEmpty()){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }else {
           userRepo.save(user);
           return new ResponseEntity<User>(HttpStatus.CREATED);
       }
    }

    @PostMapping(value = "users/{id}/massages")
    public ResponseEntity<?> createMassage(@PathVariable(name = "id") long id,@RequestBody Massage massage){
        Optional<User> userOptional = userRepo.findById(id);
        if (massage.getText().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            massage.setUser(userOptional.get());
            massageRepo.save(massage);
            return new ResponseEntity<Massage>(HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Iterable<User>> find(){
        final Iterable<User> users = userRepo.findAll();
        if (users.iterator().hasNext()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/users/{id}/massages")
    public ResponseEntity<Iterable<Massage>> findMassage(@PathVariable(name = "id") long id){
        Optional<User> userOptional = userRepo.findById(id);
        ArrayList<User> userList = new ArrayList<>();
        userOptional.ifPresent(userList :: add);
        List<Massage> massageList = new ArrayList<>();
        Iterator<User> userIterator = userList.iterator();
        while (userIterator.hasNext()) {
            massageList.addAll(userList.iterator().next().getMassages());
            if (massageList.iterator().hasNext()){
                return new ResponseEntity<>(massageList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

    @PutMapping(value = "users/{id}/massages/{massage_id}")
    public ResponseEntity<?> updateMassage(@PathVariable(name = "massage_id") long massage_id, @PathVariable(name = "id") long user_id, @RequestBody Massage massage){
        Optional<Massage> massageOptional = massageRepo.findById(massage_id);
        Optional<User> userOptional = userRepo.findById(user_id);
        if (massageOptional.isPresent() && userOptional.isPresent()){
            User user = userOptional.get();
            for (Massage massage1 : user.getMassages()){
                if (massage1.getId().equals(massage_id)){
                    massage.setId(massage_id);
                    massage.setUser(user);
                    massageRepo.save(massage);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @DeleteMapping(value = "users/{id}/massages/{massage_id}")
    public ResponseEntity<?> deleteMassage(@PathVariable(name = "id") long user_id, @PathVariable(name = "massage_id") long massage_id) {
        Optional<User> userOptional = userRepo.findById(user_id);
        Optional<Massage> massageOptional = massageRepo.findById(massage_id);
        if (userOptional.isPresent() && massageOptional.isPresent()){
            User user = userOptional.get();
            for (Massage massage : user.getMassages()){
                if (massage.getId().equals(massage_id)){
                    massageRepo.delete(massage);
                    return new ResponseEntity<Massage>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/user/{id}/massages/{massage_id}")
    public ResponseEntity<?> readMassageById(@PathVariable(name = "id") long user_id, @PathVariable(name = "massage_id") long massage_id){
        Optional<User> userOptional = userRepo.findById(user_id);
        Optional<Massage> massageOptional = massageRepo.findById(massage_id);
        if (userOptional.isPresent() && massageOptional.isPresent()){
            User user = userOptional.get();
            for (Massage massage : user.getMassages()){
                if (massage.getId().equals(massage_id)){
                    return new ResponseEntity<>(massage,HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> readById (@PathVariable(name = "id") long id){
        final Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isEmpty()){
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(userOptional,HttpStatus.OK);
        }
    }
}
