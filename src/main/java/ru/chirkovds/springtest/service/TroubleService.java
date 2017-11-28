package ru.chirkovds.springtest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.chirkovds.springtest.entity.Trouble;

import java.util.List;
import java.util.Set;

public interface TroubleService {
    List<Trouble> findAll();
    Trouble findOne(Long id);
    Trouble findOne(Long id, Set<String> fetchPolicy);
    Trouble saveTrouble(Trouble trouble);
    void deleteTrouble(Long id);
    List<Trouble> findByClientId(Long id);
    Page<Trouble> findByClientId(Long id, Pageable pageable);
}