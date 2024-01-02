package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.edu.nus.iss.miniproject.Service.UserService;
import sg.edu.nus.iss.miniproject.models.User;

@Controller
public class LoginPageController {

    @Autowired
    UserService usrSvc;

    //
    @GetMapping("")
    public String GetLanding() {

        return "landing";
    }

    @GetMapping("/access")
    public String GetAccess(Model m) {

        User user = new User();
        user.setUserId(usrSvc.generateID());
        m.addAttribute("user", user);

        return "signup";
    }

    @GetMapping("/login")
    public String GetLogin() {
        return "login";
    }

}
