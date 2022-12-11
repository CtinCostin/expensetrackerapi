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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User createUser(UserModel user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException("The email " + user.getEmail() + " is already present!");
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    @ResponseBody
    public User readUser() {
//        User user = null;
//        Session session = sessionFactory.openSession();
//        String hql = "FROM User U WHERE U.id = :user_id";
//        Query query = session.createQuery(hql);
//        query.setParameter("user_id", id);
//        user = (User) query.getResultList();
//        return user;
        Long userId = getLoggedInUser().getId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("The user is not present for id " + userId));
    }

    @Override
    public List<User> readAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("From User", User.class).getResultList();
        session.close();
        return users;
    }

    @Override
    public User updateUser(UserModel user) {
        User userToUpdate = readUser();
        userToUpdate.setName(user.getName() != null ? user.getName() : userToUpdate.getName());
        userToUpdate.setEmail(user.getEmail() != null ? user.getEmail() : userToUpdate.getEmail());
        userToUpdate.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : bcryptEncoder.encode(userToUpdate.getPassword()));
        userToUpdate.setAge(user.getAge() != null ? user.getAge() : userToUpdate.getAge());
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser() {
        User user = readUser();
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found for the email " + email));
    }
}
