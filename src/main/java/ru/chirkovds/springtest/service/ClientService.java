package ru.chirkovds.springtest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.chirkovds.springtest.entity.Client;

import java.util.List;
import java.util.Set;

public interface ClientService {
    List<Client> findAll();
    Client findOne(Long id);
    Client findOne(Long id, Set fetchPolicy);
    Client saveClient(Client client);
    void deleteClient(Long id);
    Page<Client> findAllByPage (Pageable pageable);
}