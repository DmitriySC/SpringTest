package ru.chirkovds.springtest.repositiry;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.chirkovds.springtest.entity.Spare;

public interface SpareRepository extends JpaRepository<Spare, Long>{
}
