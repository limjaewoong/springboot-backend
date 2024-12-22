package net.ljw.backend.repository;

import net.ljw.backend.entity.Items;
import net.ljw.backend.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Members, Integer> {
    public Members findByEmailAndPassword(String email, String passwrod);
}
