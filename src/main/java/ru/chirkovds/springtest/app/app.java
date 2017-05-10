package ru.chirkovds.springtest.app;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.chirkovds.springtest.entity.Client;
import ru.chirkovds.springtest.entity.Trouble;
import ru.chirkovds.springtest.entity.User;
import ru.chirkovds.springtest.service.ClientService;
import ru.chirkovds.springtest.service.CustomerService;
import ru.chirkovds.springtest.service.TroubleService;
import ru.chirkovds.springtest.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class app {
    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:app-context-annotation.xml");
        ctx.refresh();

        ClientService clientService = ctx.getBean("jpaClientService", ClientService.class);
        UserService userService = ctx.getBean("jpaUserService", UserService.class);
        CustomerService customerService = ctx.getBean("jpaCustomerService", CustomerService.class);
        TroubleService troubleService = ctx.getBean("jpaTroubleService", TroubleService.class);

        Set<String> policySet = new HashSet<String>(2, 1.0f);
        Client newClient = clientService.findOne(8l, policySet);
        String newClientName = "ИП 8";
        newClient.setClient(newClientName);
        clientService.saveClient(newClient);
    }
    private static void clientList (String message, List<Client> clientList) {
        System.out.println(message);
        for (Client client : clientList) {
            System.out.println(client);
        }
    }
    private static void userList (String message, List<User> userList) {
        System.out.println(message);
        for (User user : userList) {
            System.out.println(user);
        }
    }
    private static void troubleList (String message, List<Trouble> troubleList) {
        System.out.println(message);
        for (Trouble trouble : troubleList) {
            System.out.println(trouble);
        }
    }
}