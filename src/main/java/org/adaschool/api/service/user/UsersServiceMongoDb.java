package org.adaschool.api.service.user;

import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceMongoDb implements UsersService {

    private final UserMongoRepository userMongoRepository;

    @Autowired
    public UsersServiceMongoDb(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public User save(User user) {
        try {
            userMongoRepository.insert(user);
            Optional<User> user1 = userMongoRepository.findById(user.getId());
            return user1.orElse(user);
        } catch (DuplicateKeyException ex) {
            System.out.println("User already added");
            return null;
        }
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> userOptional = userMongoRepository.findById(id);
        return userOptional;
    }

    @Override
    public List<User> all() {
        return userMongoRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        try{
            userMongoRepository.deleteById(id);
        }catch (IllegalArgumentException ex){

        }
    }

    @Override
    public User update(User user, String userId) {
        if (userMongoRepository.existsById(userId)) {
            return userMongoRepository.save(user);
        } else {
            System.out.println("The user doesn't exists");
            return null;
        }
    }
}
