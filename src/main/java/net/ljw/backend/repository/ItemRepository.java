package net.ljw.backend.repository;

import net.ljw.backend.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Items, Integer> {

    List<Items> findByIdIn(List<Integer> ids);

}
