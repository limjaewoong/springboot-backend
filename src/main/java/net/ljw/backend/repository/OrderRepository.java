package net.ljw.backend.repository;

import net.ljw.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByMemberIdOrderByIdDesc(int memberId);
}
