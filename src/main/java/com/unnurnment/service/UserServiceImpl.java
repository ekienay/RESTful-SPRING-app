package service;

import dao.DAO;
import model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements DAO<User,Integer> {

    private static final Map<Integer,User> USER_MAP = new HashMap<Integer, User>();
    private static final AtomicInteger User_Id_Generate = new AtomicInteger();

    public void create(User user) {
        final int userId = User_Id_Generate.incrementAndGet();
        user.setId(userId);
        USER_MAP.put(userId,user);
    }

    public List<User> findAll() {
        return new ArrayList<User>(USER_MAP.values());
    }

    public User readById(Integer integer) {
        return USER_MAP.get(integer);
    }

    public boolean update(User user, Integer integer) {
        if(USER_MAP.containsKey(integer)){
            user.setId(integer);
            USER_MAP.put(integer,user);
            return true;
        }
        return false;
    }

    public boolean delete(Integer integer) {
        return USER_MAP.remove(integer) != null;
    }
}
