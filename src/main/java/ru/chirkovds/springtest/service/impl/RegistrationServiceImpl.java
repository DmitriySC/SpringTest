package ru.chirkovds.springtest.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chirkovds.springtest.controllers.ClientController;
import ru.chirkovds.springtest.entity.Client;
import ru.chirkovds.springtest.entity.DTO.RegistrationDTO;
import ru.chirkovds.springtest.entity.User;
import ru.chirkovds.springtest.entity.enums.UserRole;
import ru.chirkovds.springtest.service.ClientService;
import ru.chirkovds.springtest.service.RegistrationService;
import ru.chirkovds.springtest.service.UserService;

@Transactional
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private Log log = LogFactory.getLog(ClientController.class);
    private ClientService clientService;
    private UserService userService;

    public void saveNewClient(RegistrationDTO registrationDTO) {
        Client client = new Client();
        client.setInn(registrationDTO.getInn());
        client.setClient(registrationDTO.getClient());
        client.setClientSpecific(registrationDTO.getClientSpecific());
        client.setAddress(registrationDTO.getAddress());
        client.setPhone(registrationDTO.getPhone());
        clientService.saveClient(client);
        log.info("Client ID: " + client.getId() + " was created success.");
        BCryptPasswordEncoder bCrEnc = new BCryptPasswordEncoder(12);
        User user = new User();
        user.setClient(client);

        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setMiddleName(registrationDTO.getMiddleName());
        user.setPassword(registrationDTO.getPassword());
        user.setUsername(registrationDTO.getUsername());
        user.setRole(UserRole.ADMIN);
        user.setPassword(bCrEnc.encode(registrationDTO.getPassword()));
        userService.saveUser(user);
        log.info("Admin for Client ID: " + client.getId() + " was created success.");
    }
    @Autowired
    public void setClientService(@Qualifier("jpaClientService") ClientService clientService) {
        this.clientService = clientService;
    }
    @Autowired
    public void setUserService(@Qualifier("jpaUserService") UserService userService){
        this.userService = userService;
    }
}