package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.SearchRequest;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class InitViewController {

    private final CategoryRepository categoryRepository;

    private final CartService cartService;

    private final SessionOperatorDetails sessionOperatorDetails;

    @GetMapping("/about-us.html")
    public ModelAndView aboutUs() {
        ModelAndView mav = new ModelAndView("about-us");
        initCart(mav);
        return mav;
    }

    @GetMapping("/contact.html")
    public ModelAndView contact() {
        ModelAndView mav = new ModelAndView("contact");
        initCart(mav);
        return mav;
    }

    @GetMapping("/accounts.html")
    public ModelAndView accounts() {
        ModelAndView mav = new ModelAndView("accounts");
        initCart(mav);
        return mav;
    }

    @GetMapping("/blog-2-column.html")
    public ModelAndView blog2Column() {
        ModelAndView mav = new ModelAndView("blog-2-column");
        initCart(mav);
        return mav;
    }

    @GetMapping("/shop-3-column.html")
    public ModelAndView shop3Column() {
        ModelAndView mav = new ModelAndView("shop-3-column");
        initCart(mav);
        return mav;
    }

    @GetMapping("/shop-left-sidebar.html")
    public ModelAndView shopLeftSidebar() {
        ModelAndView mav = new ModelAndView("shop-left-sidebar");
        initCart(mav);
        return mav;
    }

    @GetMapping("/complete_notification.html")
    public ModelAndView complete() {
        ModelAndView mav = new ModelAndView("complete_notification");
        return mav;
    }

    private void initCart(ModelAndView modelAndView) {
        List<Product> productsByUser = cartService.getProductsByUser();
        modelAndView.addObject("productsByUser", productsByUser);
        modelAndView.addObject("memberWishlist", sessionOperatorDetails.existsForm("memberWishlist") ? sessionOperatorDetails.getForm("memberWishlist", ArrayList.class) : new ArrayList<>());
        modelAndView.addObject("subtotal", calTotalPrice(productsByUser));
        modelAndView.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());
        modelAndView.addObject("isLogin", sessionOperatorDetails.existsForm("account"));
        modelAndView.addObject("searchRequest", new SearchRequest());
    }

    private Double calTotalPrice(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getNewPrice() * product.getQuantityProductCart();
        }
        return sum;
    }

}
