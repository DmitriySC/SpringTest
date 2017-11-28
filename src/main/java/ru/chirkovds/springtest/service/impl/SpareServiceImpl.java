package ru.chirkovds.springtest.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chirkovds.springtest.entity.Spare;
import ru.chirkovds.springtest.repositiry.SpareRepository;
import ru.chirkovds.springtest.service.SpareService;

@Repository
@Transactional
@Service("jpaSpareService")
public class SpareServiceImpl implements SpareService{
    private Log log = LogFactory.getLog(SpareService.class);
    private SpareRepository spareRepository;

    public void deleteSpare(Long id) {
        spareRepository.delete(id);
        log.info("Deleting Spare: ID " + id);
    }

    public Spare saveSpare(Spare spare) {
        Spare savedSpare = spareRepository.saveAndFlush(spare);
        if (spare.getId() == null){
            log.info("Inserting SpareID " + spare.getId());
        }else {
            log.info("Updating SpareID: " + spare.getId());
        }
        return savedSpare;
    }
    @Autowired
    public void setSpareService (SpareRepository spareRepository){
        this.spareRepository = spareRepository;
    }
}
