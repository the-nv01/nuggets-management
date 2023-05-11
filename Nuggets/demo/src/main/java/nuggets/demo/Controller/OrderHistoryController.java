package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.OrderMember;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.SearchRequest;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.OrderMemberRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class OrderHistoryController {

    private final CategoryRepository categoryRepository;

    private final CartService cartService;

    private final OrderMemberRepository orderMemberRepository;

    private final SessionOperatorDetails sessionOperatorDetails;


    @GetMapping("/order-history.html")
    public ModelAndView adminPage() {
        ModelAndView mav = new ModelAndView("order-history");
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        mav.addObject("orderMembers", orderMemberRepository.findAllByUsernameOrderByDateDesc(user.getUsername()));
        initCart(mav);
        return mav;
    }

    @GetMapping("/receive-order")
    public ModelAndView receiveOrder(@RequestParam (value = "order_id") Integer orderId) {
        OrderMember orderMember = orderMemberRepository.findByOrderId(orderId);
        orderMember.setOrderStatus(2);
        return new ModelAndView("redirect:/order-history.html");
    }

    @GetMapping("/delivery-order")
    public ModelAndView deliveryOrder(@RequestParam (value = "order_id") Integer orderId) {
        OrderMember orderMember = orderMemberRepository.findByOrderId(orderId);
        orderMember.setOrderStatus(1);
        return new ModelAndView("redirect:/admin-page.html");
    }

    @GetMapping("/cancel-order")
    public ModelAndView cancelOrder(@RequestParam (value = "order_id") Integer orderId) {
        OrderMember orderMember = orderMemberRepository.findByOrderId(orderId);
        orderMember.setOrderStatus(3);
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        } else {
            User user = sessionOperatorDetails.getForm("account", User.class);
            if (Objects.equals(user.getRole(), "admin"))
                return new ModelAndView("redirect:/admin-page.html");
            else return new ModelAndView("redirect:/order-history.html");
        }

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
