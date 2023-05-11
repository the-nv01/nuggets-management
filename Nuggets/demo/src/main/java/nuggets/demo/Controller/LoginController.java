package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.SearchRequest;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.UserRepository;
import nuggets.demo.Repository.WishListRepository;
import nuggets.demo.Service.CartService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class LoginController {

    private final LoginService loginService;

    private final CartService cartService;

    private final SessionOperatorDetails sessionOperatorDetails;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final WishListRepository wishListRepository;

    @GetMapping("/login-register.html")
    public ModelAndView loginRegister() {
        ModelAndView modelAndView = new ModelAndView("login-register");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("isCorrectLogin", true);
        modelAndView.addObject("isCorrectRegister", true);

        List<Product> productsByUser = cartService.getProductsByUser();
        modelAndView.addObject("productsByUser", productsByUser);
        modelAndView.addObject("memberWishlist", sessionOperatorDetails.existsForm("memberWishlist") ? sessionOperatorDetails.getForm("memberWishlist", ArrayList.class) : new ArrayList<>());
        modelAndView.addObject("subtotal", calTotalPrice(productsByUser));
        modelAndView.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());
        modelAndView.addObject("isLogin", sessionOperatorDetails.existsForm("account"));
        modelAndView.addObject("searchRequest", new SearchRequest());

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute User user) {
        User account = loginService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        ModelAndView modelAndView = new ModelAndView(user.getDestinationUrl());

        if (ObjectUtils.isEmpty(account)) {
            modelAndView.addObject("isCorrectLogin", false);
            modelAndView.setViewName(user.getBackUrl());
        } else {
            sessionOperatorDetails.setForm("account", account);
            sessionOperatorDetails.setForm("memberWishlist", wishListRepository.findAllByUsernameOrderByDateDesc(account.getUsername()));
            if (Objects.equals(account.getRole(), "admin")) {
                modelAndView.setViewName("redirect:/admin-page.html");
            }
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

    private Double calTotalPrice(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getNewPrice() * product.getQuantityProductCart();
        }
        return sum;
    }

}
