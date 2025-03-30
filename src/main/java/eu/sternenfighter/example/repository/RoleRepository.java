package eu.sternenfighter.example.repository;

import eu.sternenfighter.example.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
