package ru.chirkovds.springtest.repositiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.chirkovds.springtest.entity.Trouble;

import java.util.List;

public interface TroubleRepository extends PagingAndSortingRepository<Trouble, Long> {
    List<Trouble> findByClientId(Long id);
    Page<Trouble> findByClientId(Long id, Pageable pageable);
}
