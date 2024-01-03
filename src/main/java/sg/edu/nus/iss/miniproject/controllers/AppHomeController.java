package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.miniproject.Service.UserService;
import sg.edu.nus.iss.miniproject.models.User;

@Controller
@RequestMapping("")
public class AppHomeController {

    @Autowired
    UserService userSvc;

    @PostMapping("/signup")
    public String DisplayCalendar(@Valid @ModelAttribute("user") User user, BindingResult bindings, Model m, HttpSession sess) {
        
        if (bindings.hasErrors())
            return "signup";

        if (user != null) {

            sess.setAttribute("user", user);
            m.addAttribute("user", user);
             m.addAttribute("username", user.getUsername());
            userSvc.saveUser(user);

        } else {
            System.out.println("no user");
        }

        return "redirect:/calendar";
    }

    @GetMapping("/alltask")
    public String DisplayTasks(HttpSession sess, Model m) {

        User user = (User) sess.getAttribute("user");
        m.addAttribute("user", user);

        return "alltask";

    }

}
