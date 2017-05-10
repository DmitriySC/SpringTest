package ru.chirkovds.springtest.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chirkovds.springtest.Message;

import java.util.Locale;

@RequestMapping("/")
@Controller

public class LoginController {
    private Log log = LogFactory.getLog(LoginController.class);
    private MessageSource messageSource;

    @RequestMapping()
    public String loginRoot(Model uiModel, Locale locale){
        log.info("Login");
        return "login";
    }
    @RequestMapping("workspace")
    public String workspace(Model uiModel, Locale locale){
        log.info("Success authorisation");
        return "workspace";
    }
    @RequestMapping("login")
    public String login(Model uiModel, Locale locale){
        log.info("Login");
        return "login";
    }

    @RequestMapping(value = "/login", params = "error")
    public String loginError(Model uiModel, Locale locale){
        log.info("Login failed detected!");
        uiModel.addAttribute("message", new Message("error", messageSource.getMessage("message_login_fail", new Object[]{}, locale)));
        return "login";
    }
    @Autowired
    public void setMessageSource(MessageSource messageSource){
        this.messageSource = messageSource;
    }
}