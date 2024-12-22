package net.ljw.backend.repository;

import net.ljw.backend.entity.Cart;
import net.ljw.backend.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByMemberId(Integer memberId);
    Cart findByMemberIdAndItemId(Integer memberId, Integer itemId);
    void deleteByMemberId(Integer memberId);
}
