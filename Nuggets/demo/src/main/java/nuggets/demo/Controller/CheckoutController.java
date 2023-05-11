package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.*;
import nuggets.demo.Repository.*;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class CheckoutController {

    private final OrderMemberRepository orderMemberRepository;

    private final CouponRepository couponRepository;

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final CategoryRepository categoryRepository;

    private final CartService cartService;

    private final SessionOperatorDetails sessionOperatorDetails;

    @GetMapping("/checkout.html")
    public ModelAndView initView(@RequestParam (value = "coupon_code", required = false) String couponCode) {
        ModelAndView modelAndView = new ModelAndView("checkout");
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("isCorrectLogin", true);
        modelAndView.addObject("isCorrectLogin", true);

        List<Product> productsByUser = cartService.getProductsByUser();

        modelAndView.addObject("user", sessionOperatorDetails.existsForm("account") ? sessionOperatorDetails.getForm("account", User.class) : new User());
        modelAndView.addObject("orderMember", new OrderMember());
        modelAndView.addObject("products", productsByUser);
        modelAndView.addObject("carts", new CartDTO());
        modelAndView.addObject("memberWishlist", sessionOperatorDetails.existsForm("memberWishlist") ? sessionOperatorDetails.getForm("memberWishlist", ArrayList.class) : new ArrayList<>());
        modelAndView.addObject("memberCarts", sessionOperatorDetails.existsForm("memberCarts") ? sessionOperatorDetails.getForm("memberCarts", ArrayList.class) : new ArrayList<>());
        modelAndView.addObject("subtotal", calTotalPrice(productsByUser));
        modelAndView.addObject("now", LocalDate.now().toString());
        modelAndView.addObject("productsByUser", productsByUser);
        modelAndView.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());
        modelAndView.addObject("isLogin", sessionOperatorDetails.existsForm("account"));
        modelAndView.addObject("searchRequest", new SearchRequest());

        Integer discount = 0;
        String alertCoupon = "";
        if (!ObjectUtils.isEmpty(couponCode)) {
            Coupon coupon = couponRepository.findCouponByCouponCode(couponCode);
            if (!ObjectUtils.isEmpty(coupon)) {
                LocalDate now = LocalDate.now();
                LocalDate reserveFrom = LocalDate.parse(coupon.getReserveFrom());
                LocalDate reserveTo = LocalDate.parse(coupon.getReserveTo());
                discount = reserveFrom.isAfter(now) || reserveTo.isBefore(now) ? 0 : coupon.getDiscount();
                if (discount == 0) alertCoupon = "The coupon does not work.";
            } else {
                alertCoupon = "The coupon does not exist.";
            }
        }

        modelAndView.addObject("alertCoupon", alertCoupon);
        modelAndView.addObject("discount", discount);
        modelAndView.addObject("totalPrice", calTotalPrice(productsByUser)*(100-discount)/100);

        return modelAndView;
    }

    @PostMapping("/order-history")
    @Transactional
    public ModelAndView Order(@ModelAttribute OrderMember orderMember) {
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        if (!orderMember.getIsNotMemberAddress()) {
            User newUser = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .firstName(orderMember.getFirstName())
                    .lastName(orderMember.getLastName())
                    .email(orderMember.getEmail())
                    .role(user.getRole())
                    .country(orderMember.getCountry())
                    .company(orderMember.getCompany())
                    .address(orderMember.getAddress())
                    .apartment(orderMember.getApartment())
                    .city(orderMember.getCity())
                    .state(orderMember.getState())
                    .phone(orderMember.getPhone())
                    .build();
            userRepository.save(newUser);
        }
        cartRepository.deleteCartByUsername(user.getUsername());
        orderMember.setOrderStatus(0);
        orderMemberRepository.save(orderMember);
        return new ModelAndView("redirect:/index-2.html");
    }

    public Double calTotalPrice(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getNewPrice() * product.getQuantityProductCart();
        }
        return sum;
    }
}
