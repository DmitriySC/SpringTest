package ru.chirkovds.springtest.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chirkovds.springtest.entity.User;
import ru.chirkovds.springtest.repositiry.UserRepository;
import ru.chirkovds.springtest.service.UserService;

import java.util.List;

@Service("jpaUserService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {
    private Log log = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    @Transactional (readOnly = true)
    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Transactional (readOnly = true)
    public User findOne(Long id) {
        log.info("Finding User ID: " + id);
        return userRepository.findOne(id);
    }
    @Transactional(readOnly = true)
    public User findByUsername(String username){
        log.info("Finding User by Login: " + username);
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> findByClientId(Long id) {
        log.info("Finding Trouble's for Client ID: " + id);
        List<User> userList = Lists.newArrayList(userRepository.findByClientId(id));
        Hibernate.initialize(userList);
        return userList;
    }

    public User saveUser(User user) {
        User savedUser = userRepository.saveAndFlush(user);
        if(user.getId() == null) {
            log.info("User First name: " + user.getFirstName() + " for Client INN " + user.getClient().getInn() + " inserting.");
        } else {
            log.info("User ID: " + user.getId() + " updating.");
        }
        return savedUser;
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
        log.info("User ID: " + id + " was deleted.");
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}