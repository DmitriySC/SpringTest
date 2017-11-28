package ru.chirkovds.springtest;

import org.springframework.stereotype.Service;
import ru.chirkovds.springtest.entity.DTO.SpareDTO;
import ru.chirkovds.springtest.entity.Spare;

import java.util.List;

@Service("CompareSpare")
public class CompareSpare {
    public List<Spare> compareSpareList(List<SpareDTO> spareDTOList, List<Spare> spareList) {
        for (SpareDTO spareDTO : spareDTOList) {
            for (Spare spare : spareList) {
                if (spareDTO.getId().equals(spare.getId())) {
                    if (spareDTO.getQty() != 0) {
                        spare.setQty(spareDTO.getQty());
                    } else {
                        spare.setTrouble(null);
                    }
                }
            }
        }

        return spareList;
    }
}