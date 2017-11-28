package ru.chirkovds.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.chirkovds.springtest.entity.DTO.TroubleDTO;
import ru.chirkovds.springtest.service.UserService;

@Service ("CheckSecurity")
public class CheckSecurity {

    private UserService userService;

    public boolean checkTrouble (TroubleDTO troubleDTO){
        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = authUser.getUsername();
        Long clientID = userService.findByUsername(userName).getClientId();
        Long id = troubleDTO.getClientId();
        return id.equals(clientID);
    }
    @Autowired
    public void setUserService(@Qualifier("jpaUserService") UserService userService){
        this.userService = userService;
    }
}