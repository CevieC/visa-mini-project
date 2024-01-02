package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.miniproject.Service.UserService;
import sg.edu.nus.iss.miniproject.models.User;

@RestController
public class LoginRestController {

    @Autowired
    UserService usrSvc;

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session, Model model) {

        String email = user.getEmail();
        String password = user.getPassword();
        String result = usrSvc.login(email, password, session, model);

        if (result.equals("redirect:/dashboard")) {
            return "calendar";
        } else {
            return "signup";
        }
    }

}
