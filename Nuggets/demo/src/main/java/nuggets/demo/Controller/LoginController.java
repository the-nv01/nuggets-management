package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.UserRepository;
import nuggets.demo.Session.SessionOperatorDetails;
import nuggets.demo.Service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class LoginController {

    private final LoginService loginService;

    private final SessionOperatorDetails sessionOperatorDetails;

    private final UserRepository userRepository;

    @GetMapping("/login-register.html")
    public ModelAndView loginRegister() {
        ModelAndView modelAndView = new ModelAndView("login-register");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("isCorrectLogin", true);
        modelAndView.addObject("isCorrectRegister", true);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute User user) {
        ModelAndView modelAndView = new ModelAndView("index");

        User account = loginService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (ObjectUtils.isEmpty(account)) {
            modelAndView.addObject("isCorrectLogin", false);
            modelAndView.setViewName("login-register");
        } else {
            sessionOperatorDetails.setForm("account", account);
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute User user) {
        ModelAndView modelAndView = new ModelAndView("index");

        if (!ObjectUtils.isEmpty(userRepository.getUserByUsername(user.getUsername()))) {
            modelAndView.addObject("isCorrectRegister", false);
            modelAndView.setViewName("login-register");
        } else {
            user.setRole("user");
            loginService.register(user);
        }

        return modelAndView;
    }

}
