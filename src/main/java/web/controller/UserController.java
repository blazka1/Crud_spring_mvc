package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String printWelcome(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping(value = "/users")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("email") String email) {
        User user = new User(username, email);
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/users/edit")
    public String editUser(@RequestParam("id") Long id, ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edituser";
    }

    @PostMapping(value = "/users/update")
    public String updateUser(@RequestParam("id") Long id,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email) {
        User user = userService.getUserById(id);
        user.setUsername(username);
        user.setEmail(email);
        userService.updateUser(user);
        return "redirect:/users";
    }
}
