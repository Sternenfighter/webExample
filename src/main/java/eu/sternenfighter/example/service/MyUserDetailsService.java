package eu.sternenfighter.example.service;

import eu.sternenfighter.example.entity.Role;
import eu.sternenfighter.example.entity.User;
import eu.sternenfighter.example.entity.UserRole;
import eu.sternenfighter.example.repository.RoleRepository;
import eu.sternenfighter.example.repository.UserRepository;
import eu.sternenfighter.example.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public MyUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository,
                                RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Search user by the given username
     * @param username username of the user
     * @return Security UserDetails
     * @throws UsernameNotFoundException if user not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
            List<Role> roles = new ArrayList<>();
            userRoles.forEach(userRole -> roleRepository.findById(userRole.getId()).ifPresent(roles::add));
            return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
                    .password(user.getPassword()).roles(roles.stream().map(Role::getName).toArray(String[]::new))
                    .disabled(!user.isEnabled()).build();
        }
        throw new UsernameNotFoundException(username);
    }
}
