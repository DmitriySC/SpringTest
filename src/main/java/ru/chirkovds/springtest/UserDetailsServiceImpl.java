package ru.chirkovds.springtest;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.chirkovds.springtest.entity.User;
import ru.chirkovds.springtest.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;

    private Log log = LogFactory.getLog(UserDetailsServiceImpl.class);

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    log.info("Finding user by Username: " + userName);
        User user = userService.findByUsername(userName);
        log.info("Find user: " + user.getUsername() + ", password: " + user.getPassword());
        Set<GrantedAuthority> role = new HashSet<GrantedAuthority>();
        role.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), role);
        /*UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
        log.info(userDetails.getUsername() + ", " + userDetails.getPassword() + ", " + userDetails.getAuthorities());*/
        return userDetails;
    }
    @Autowired
    public void setUserService(@Qualifier("jpaUserService") UserService userService){
        this.userService = userService;
    }
}