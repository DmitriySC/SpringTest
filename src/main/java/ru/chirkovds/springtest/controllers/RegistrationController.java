package ru.chirkovds.springtest.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.chirkovds.springtest.Message;
import ru.chirkovds.springtest.entity.DTO.RegistrationDTO;
import ru.chirkovds.springtest.service.RegistrationService;

import javax.validation.Valid;
import java.util.Locale;

@RequestMapping("/registration")
@Controller
@Transactional
public class RegistrationController {
    private Log log = LogFactory.getLog(ClientController.class);
    private MessageSource messageSource;
    private RegistrationService registrationService;

    @Transactional
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String registration(@ModelAttribute("registrationDTO") @Valid RegistrationDTO registrationDTO, BindingResult bindingResult, Model uiModel, RedirectAttributes redirectAttributes, Locale locale){
        log.info("Registration new Client with user");
        if(bindingResult.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("client_create_fail", new Object[]{}, locale)));
            uiModel.addAttribute("registrationDTO", registrationDTO);
            return "registration";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("client_create_success", new Object[]{}, locale)));
        registrationService.saveNewClient(registrationDTO);
        return "redirect:/login";
    }
    @RequestMapping(method = RequestMethod.GET)
    public String registrationForm(Model uiModel){
        log.info("Registration form for new Client with user");
        RegistrationDTO registrationDTO = new RegistrationDTO();//
        uiModel.addAttribute(registrationDTO);//
        return "registration";
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Autowired
    public void setRegistrationService(RegistrationService registrationService){
        this.registrationService = registrationService;
    }
}
