package sg.edu.nus.iss.miniproject.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.nus.iss.miniproject.repository.UserRepo;
import sg.edu.nus.iss.miniproject.models.User;
import org.springframework.ui.Model;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public String generateID() {

        return UUID.randomUUID().toString();
    }

    public void saveUser(User user) {
        userRepo.saveUserRecord(user);
    }

    public String login(String email, String password, HttpSession session, Model model) {

        User user = userRepo.login(email, password);
        System.out.println("user: " + user);
        if (user == null) {
            model.addAttribute("error", "Invalid email/password");
            return "redirect:/";
        }

        session.setAttribute("user", user);

        return "redirect:/dashboard";
    }
}
