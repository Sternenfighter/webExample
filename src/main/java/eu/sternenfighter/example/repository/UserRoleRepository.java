package eu.sternenfighter.example.repository;

import eu.sternenfighter.example.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUserId(int userId);
}
