package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Repository.OrderMemberRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AdminPageController {

    private final CartRepository cartRepository;

    private final OrderMemberRepository orderMemberRepository;

    private final CartService cartService;

    private final SessionOperatorDetails sessionOperatorDetails;


    @GetMapping("/admin-page.html")
    public ModelAndView adminPage() {
        ModelAndView mav = new ModelAndView("admin-page");
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        if (!Objects.equals(user.getRole(), "admin")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        mav.addObject("orderMembers", orderMemberRepository.findAllByOrderByDateDesc());
        return mav;
    }
}
