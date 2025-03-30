package eu.sternenfighter.example.view.admin;

import eu.sternenfighter.example.entity.User;
import eu.sternenfighter.example.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.util.List;

@Named
@ViewScoped
public class UserView {
    @Getter
    private List<User> users;

    private final UserService userService;

    @Inject
    public UserView(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        users = userService.findAll();
    }
}
