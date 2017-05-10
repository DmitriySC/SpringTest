package ru.chirkovds.springtest.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chirkovds.springtest.service.CustomerService;

@RequestMapping("/ajax")
@Controller
@Transactional

public class AjaxController {
    private Log log = LogFactory.getLog(CaseController.class);
    private CustomerService customerService;
    //private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    /*@RequestMapping(value = "/findcustomer", params = "term", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Customer ajaxCustomer (@RequestParam (value = "term", required = true) String ajaxTerm){
        log.info("AJAX. Finding customer like: " + ajaxTerm);
        Customer customer = new Customer();
        customer = customerService.findFirst10ByLastNameLike(ajaxTerm);
        System.out.println("AJAX cus: " + customer);
        return customer;
    }*/

    public void setCustomerService(@Qualifier("jpaCustomerService") CustomerService customerService){
        this.customerService = customerService;
    }
}
