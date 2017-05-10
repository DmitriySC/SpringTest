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
import ru.chirkovds.springtest.entity.Trouble;
import ru.chirkovds.springtest.repositiry.TroubleRepository;
import ru.chirkovds.springtest.service.TroubleService;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
@Service("jpaTroubleService")
public class TroubleServiceImpl implements TroubleService {
    private Log log = LogFactory.getLog(TroubleService.class);
    private TroubleRepository troubleRepository;

    @Transactional(readOnly = true)
    public List<Trouble> findAll() {
        List<Trouble> troubleList = Lists.newArrayList(troubleRepository.findAll());
        Hibernate.initialize(troubleList);
        return troubleList;
    }

    @Transactional(readOnly = true)
    public List<Trouble> findByClientId(Long id){
        log.info("Finding Trouble's for Client ID: " + id);
        List<Trouble> troubleList = Lists.newArrayList(troubleRepository.findByClientId(id));
        Hibernate.initialize(troubleList);
        return troubleList;
    }
    @Transactional(readOnly = true)
    public Trouble findOne(Long id, Set fetchPolicy) {
        log.info("Finding Trouble ID: " + id);
        Trouble trouble = troubleRepository.findOne(id);
        if (fetchPolicy.contains("Customer")) {
            try {
                Hibernate.initialize(trouble.getCustomer());
            } catch (NullPointerException e) {
                log.info("Customer is not set!!!");
            }
        }
        if (fetchPolicy.contains("Client")) {
            try {
                Hibernate.initialize(trouble.getClient());
            } catch (NullPointerException e) {
                log.info("Client is not set!!!");
            }
        }
        return trouble;
    }

    public Trouble saveTrouble(Trouble trouble) {
        Trouble savedTrouble = troubleRepository.save(trouble);
        if(trouble.getId() == null) {
            log.info("Trouble for Customer_ID: " + trouble.getCustomer().getId() + " inserting.");
        } else {
            log.info("Trouble ID: " + trouble.getClient() + " updating.");
        }
        return savedTrouble;
    }

    public void deleteTrouble(Long id) {
        troubleRepository.delete(id);
        log.info("Trouble ID: " + id + " was deleted.");
    }
    @Transactional(readOnly = true)
    public Page<Trouble> findByClientId(Long id, Pageable pageable){
        return troubleRepository.findByClientId(id, pageable);
    }
    @Autowired
    public void setTroubleRepository(TroubleRepository troubleRepository){
        this.troubleRepository = troubleRepository;
    }
}