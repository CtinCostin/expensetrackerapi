package com.george.expensetrackerapi.service;

import com.george.expensetrackerapi.entity.User;
import com.george.expensetrackerapi.entity.UserModel;
import com.george.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.george.expensetrackerapi.exception.ResourceNotFoundException;
import com.george.expensetrackerapi.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User createUser(UserModel user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException("The email " + user.getEmail() + " is already present!");
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return userRepository.save(newUser);
    }

    @Override
    @ResponseBody
    public User readUser(Long id) {
//        User user = null;
//        Session session = sessionFactory.openSession();
//        String hql = "FROM User U WHERE U.id = :user_id";
//        Query query = session.createQuery(hql);
//        query.setParameter("user_id", id);
//        user = (User) query.getResultList();
//        return user;
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user is not present for id " + id));
    }

    @Override
    public List<User> readAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("From User", User.class).getResultList();
        session.close();
        return users;
    }

    @Override
    public User updateUser(UserModel user, Long id) {
        User userToUpdate = readUser(id);
        userToUpdate.setName(user.getName() != null ? user.getName() : userToUpdate.getName());
        userToUpdate.setEmail(user.getEmail() != null ? user.getEmail() : userToUpdate.getEmail());
        userToUpdate.setPassword(user.getPassword() != null ? user.getPassword() : userToUpdate.getPassword());
        userToUpdate.setAge(user.getAge() != null ? user.getAge() : userToUpdate.getAge());
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        User user = readUser(id);
        userRepository.delete(user);
    }
}
