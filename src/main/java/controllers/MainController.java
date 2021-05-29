package controllers;

import dao.DAO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {


    private final DAO<User,Integer> userIntegerDAO;

    @Autowired
    public MainController(DAO<User, Integer> userIntegerDAO) {
        this.userIntegerDAO = userIntegerDAO;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody User user){
        userIntegerDAO.create(user);

        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> find(){
        final List<User> users = userIntegerDAO.findAll();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<List<User>>(users, HttpStatus.OK)
                : new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User user){
        final boolean updated = userIntegerDAO.update(user,id);

        return updated
                ? new ResponseEntity<User>(HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id){
        final boolean deleted = userIntegerDAO.delete(id);

        return deleted
                ? new ResponseEntity<User>(HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> readById (@PathVariable(name = "id") int id){
        final User user = userIntegerDAO.readById(id);

        return user != null
                ? new ResponseEntity<User>(HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
}
