package eu.sternenfighter.example.service;

import eu.sternenfighter.example.entity.User;
import eu.sternenfighter.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @return List of all Users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
