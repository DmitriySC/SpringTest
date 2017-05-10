package ru.chirkovds.springtest.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chirkovds.springtest.entity.Client;
import ru.chirkovds.springtest.repositiry.ClientRepository;
import ru.chirkovds.springtest.service.ClientService;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
@Service("jpaClientService")
public class ClientServiceImpl implements ClientService {
    private Log log = LogFactory.getLog(ClientService.class);
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return Lists.newArrayList(clientRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Client findOne(Long id, Set fetchPolicy) {
        log.info("Finding Client ID: " + id);
        Client client = clientRepository.findOne(id);
        if (fetchPolicy.contains("userList")) {
            try {
                Hibernate.initialize(client.getUserList().size());
            } catch (NullPointerException e) {
                log.info("UserList is Empty");
            }
            if (fetchPolicy.contains("troubleList")) {
                Hibernate.initialize(client.getTroubleList().size());
            }
        }
        return client;
    }

    public Client saveClient(Client client) {
        Client savedClient = clientRepository.save(client);
        if (client.getId() == null) {
            log.info("Client INN: " + client.getInn() + " inserting.");
        } else {
            log.info("Client ID: " + client.getId() + " updating.");
        }
        return savedClient;
    }

    public void deleteClient(Long id) {
        clientRepository.delete(id);
        log.info("Client ID: " + id + " was deleted.");
    }

    @Transactional(readOnly = true)
    public Page<Client> findAllByPage(Pageable pageable){
        return clientRepository.findAll(pageable);
    }
    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}