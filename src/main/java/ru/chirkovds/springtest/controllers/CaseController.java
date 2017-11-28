package ru.chirkovds.springtest.controllers;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.chirkovds.springtest.CheckSecurity;
import ru.chirkovds.springtest.CompareSpare;
import ru.chirkovds.springtest.Message;
import ru.chirkovds.springtest.UrlUtil;
import ru.chirkovds.springtest.entity.Client;
import ru.chirkovds.springtest.entity.Customer;
import ru.chirkovds.springtest.entity.DTO.TroubleDTO;
import ru.chirkovds.springtest.entity.Grid.TroubleGrid;
import ru.chirkovds.springtest.entity.Trouble;
import ru.chirkovds.springtest.service.ClientService;
import ru.chirkovds.springtest.service.CustomerService;
import ru.chirkovds.springtest.service.TroubleService;
import ru.chirkovds.springtest.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

//@PreAuthorize("isAuthenticated()") or @PreAuthorize("#user.name == 'admin'")
//@Secured(value = {"ROLE_ROOT"})
@RequestMapping("/cases")
@Controller
@Transactional
public class CaseController {
    private Log log = LogFactory.getLog(CaseController.class);
    private TroubleService troubleService;
    private UserService userService;
    private ClientService clientService;
    private CustomerService customerService;
    private MessageSource messageSource;
    private CheckSecurity checkSecurity;
    private CompareSpare compareSpare;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TroubleGrid listGrid(
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows,
            @RequestParam(value = "sidx", required = false) String sortBy,
            @RequestParam(value = "sord", required = false) String order) {
        log.info("Listing cases for grid with page: " + page + " rows: " + rows);
        log.info("Listing cases for grid with sort: " + sortBy + " order: " + order);
// Обработать поле, по которому производится сортировка
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("incomDateString")) orderBy = "incomDate";
        if (orderBy != null && orderBy.equals("outDateString")) orderBy = "outDate";
        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else {
                sort = new Sort(Sort.Direction.ASC, orderBy);
            }
        }
// Сконструировать страничный запрос для текущей страницы.
// Примечание: нумерация страниц для Spring Data JPA начинается с О,
// тогда как в jqGrid - с 1
        PageRequest pageRequest;
        if (sort != null) {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else {
            pageRequest = new PageRequest(page - 1, rows);
        }
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = authUser.getUsername();
        Long clientID = userService.findByUsername(userName).getClientId();
        //System.out.println("Cases fo Client ID: " + clientID);
        Page<Trouble> troublePage = troubleService.findByClientId(clientID, pageRequest);
        TroubleGrid troubleGrid = new TroubleGrid();
        troubleGrid.setCurrentPage(troublePage.getNumber() + 1);
        troubleGrid.setTotalPages(troublePage.getTotalPages());
        troubleGrid.setTotalRecords(troublePage.getTotalElements());
        troubleGrid.setTroubleData(Lists.newArrayList(troublePage.iterator()));
        return troubleGrid;
    }

    @RequestMapping(value = "/ajax/findcustomer", params = "term", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Customer> ajaxCustomer (@RequestParam (value = "term", required = true) String ajaxTerm){
        log.info("AJAX. Finding customer like: " + ajaxTerm);
        List<Customer> customer = customerService.findByLastNameContaining(ajaxTerm);
        System.out.println("AJAX. Customer: " + customer);
        return customer;
}

    //@Secured(value = {"ROLE_ROOT"})
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        /*log.info("Listing clients");
        List<Client> clients = clientService.findAll();
        uiModel.addAttribute("clients", clients);
        log.info("No. of Clients: " + clients.size());

        for (Client client : clients) {
            System.out.println(client);
        }*/
        return "cases/list";
    }

    /*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    //@Secured(value = {"ROLE_USER","ROLE_ROOT"})
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Set<String> policySet = new HashSet<String>(2, 1.0f);
        policySet.add("Customer");
        Trouble trouble = troubleService.findOne(id, policySet);
        uiModel.addAttribute("trouble", trouble);
        return "cases/show";
    }*/

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Set<String> policySet = new HashSet<String>(2, 1.0f);
        policySet.add("Customer");
        policySet.add("Client");
        policySet.add("Spare");
        Trouble trouble = troubleService.findOne(id, policySet);
        TroubleDTO troubleDTO = new TroubleDTO(trouble);
        if (checkSecurity.checkTrouble(troubleDTO)) {
            System.out.println(troubleDTO);
                    uiModel.addAttribute("troubleDTO", troubleDTO);
            return "cases/show";
        }
        else return "403";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid TroubleDTO troubleDTO, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Updating case: " + troubleDTO.getId());
        if (bindingResult.hasErrors()) {
            log.error("Binding error!" + bindingResult.toString());
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("case_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("troubleDTO", troubleDTO);
            return "cases/show";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("case_save_success", new Object[]{}, locale)));

        Set<String> fetchPolicy = new HashSet<String>();
        fetchPolicy.add("Customer");
        fetchPolicy.add("Client");
        fetchPolicy.add("Spare");
        Trouble trouble = troubleService.findOne(troubleDTO.getId(), fetchPolicy);
        trouble.setOutDate(troubleDTO.getOutDate());
        trouble.setCaseDesc(troubleDTO.getCaseDesc());
        trouble.setResult(troubleDTO.getResult());
        trouble.setPaymentStatus(troubleDTO.getPaymentStatus());
        trouble.setSummCase(troubleDTO.getSummCase());
        trouble.setVersion(troubleDTO.getTroubleVersion());

        trouble.setSpareList(compareSpare.compareSpareList(troubleDTO.getSpareList(), trouble.getSpareList()));
        Customer customer = trouble.getCustomer();
        customer.setPhone(troubleDTO.getPhone());
        trouble.setCustomer(customer);
        troubleService.saveTrouble(trouble);
        return "redirect:/cases/" + UrlUtil.encodeUrlPathSegment(troubleDTO.getId().toString(), httpServletRequest) + "?form";
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid TroubleDTO troubleDTO, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Creating Case:" + troubleDTO);
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("case_create_fail", new Object[]{}, locale)));
            uiModel.addAttribute("troubleDTO", troubleDTO);
            return "cases/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("case_create_success", new Object[]{}, locale)));
        Trouble trouble = new Trouble();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = authUser.getUsername();
        ru.chirkovds.springtest.entity.User user = userService.findByUsername(userName);
        Client client = clientService.findOne(user.getClientId(), new HashSet<String>());
        Customer customer = customerService.findOne(troubleDTO.getCustomerId());
        trouble.setClient(client);
        trouble.setCustomer(customer);
        trouble.setIncomDate(troubleDTO.getIncomDate());
        trouble.setCaseDesc(troubleDTO.getCaseDesc());
        troubleService.saveTrouble(trouble);
        log.info("Case ID: " + trouble.getId() + "was created success");
        return "redirect:/cases/" + UrlUtil.encodeUrlPathSegment(trouble.getId().toString(), httpServletRequest) + "?form";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        log.info("Create Case form:");
        TroubleDTO troubleDTO = new TroubleDTO();
        uiModel.addAttribute("troubleDTO", troubleDTO);
        log.info(troubleDTO);
        return "cases/create";
    }
    @Autowired
    public void setTroubleService(@Qualifier("jpaTroubleService") TroubleService troubleService) {
        this.troubleService = troubleService;
    }
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @Autowired
    public void setUserService(@Qualifier("jpaUserService") UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setClientService(@Qualifier("jpaClientService") ClientService clientService) {
        this.clientService = clientService;
    }
    @Autowired
    public void setCustomerService(@Qualifier("jpaCustomerService") CustomerService customerService) {
        this.customerService = customerService;
    }
    @Autowired
    public void setCheckSecurity(CheckSecurity checkSecurity){
        this.checkSecurity = checkSecurity;
    }

    @Autowired
    public void setCompareSpare (CompareSpare compareSpare) {
        this.compareSpare = compareSpare;
    }
}