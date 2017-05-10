package ru.chirkovds.springtest.repositiry;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.chirkovds.springtest.entity.Client;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

}