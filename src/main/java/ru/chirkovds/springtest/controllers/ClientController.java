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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.chirkovds.springtest.Message;
import ru.chirkovds.springtest.UrlUtil;
import ru.chirkovds.springtest.entity.Client;
import ru.chirkovds.springtest.entity.Grid.ClientGrid;
import ru.chirkovds.springtest.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
//@PreAuthorize("isAuthenticated()") or @PreAuthorize("#user.name == 'admin'")
//@Secured(value = {"ROLE_ROOT"})
@RequestMapping("/clients")
@Controller
@Transactional
public class ClientController {
    private Log log = LogFactory.getLog(ClientController.class);
    private ClientService clientService;
    private MessageSource messageSource;

    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ClientGrid listGrid(
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows,
            @RequestParam(value = "sidx", required = false) String sortBy,
            @RequestParam(value = "sord", required = false) String order) {
        log.info("Listing clients for grid with page: " + page + " rows: " + rows);
        log.info("Listing clients for grid with sort: " + sortBy + " order: " + order);
// Обработать поле, по которому производится сортировка
        Sort sort = null;
        if (sortBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, sortBy);
            } else {
                sort = new Sort(Sort.Direction.ASC, sortBy);
            }
        }
// Сконструировать страничный запрос для текущей страницы.
// Примечание: нумерация страниц для Spring Data JPA начинается с О,
// тогда как в jqGrid - с 1
        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else {
            pageRequest = new PageRequest(page - 1, rows);
        }
        Page<Client> clientPage = clientService.findAllByPage(pageRequest);

        ClientGrid clientGrid = new ClientGrid();
        clientGrid.setCurrentPage(clientPage.getNumber() + 1);
        clientGrid.setTotalPages(clientPage.getTotalPages());
        clientGrid.setTotalRecords(clientPage.getTotalElements());
        clientGrid.setClientData(Lists.newArrayList(clientPage.iterator()));
        System.out.println(clientGrid);
        return clientGrid;
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
        return "clients/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    //@Secured(value = {"ROLE_USER","ROLE_ROOT"})
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Set<String> policySet = new HashSet<String>(2, 1.0f);
        policySet.add("userList");
        Client client = clientService.findOne(id, policySet);
        uiModel.addAttribute("client", client);
        uiModel.addAttribute("users", client.getUserList());
        return "clients/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid Client client, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Updating client:");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("client_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("client", client);
            return "clients/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("client_save_success", new Object[]{}, locale)));
        clientService.saveClient(client);
        return "redirect:/clients/" + UrlUtil.encodeUrlPathSegment(client.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid Client client, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
        log.info("Creating Client:" + client);
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("client_create_fail", new Object[]{}, locale)));
            uiModel.addAttribute("client", client);
            return "clients/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("client_create_success", new Object[]{}, locale)));
        clientService.saveClient(client);
        log.info("Client ID: " + client.getId() + "was created success");
        return "redirect:/clients/" + UrlUtil.encodeUrlPathSegment(client.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Set<String> policySet = new HashSet<String>(2, 1.0f);
        uiModel.addAttribute("client", clientService.findOne(id, policySet));
        return "clients/update";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        log.info("Create client form:");
        Client client = new Client();
        uiModel.addAttribute("client", client);
        log.info(client);
        return "clients/create";
    }

    @Autowired
    public void setClientService(@Qualifier("jpaClientService") ClientService clientService) {
        this.clientService = clientService;
    }
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}