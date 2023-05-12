package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.*;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.CouponRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class CartController {

    private final CategoryRepository categoryRepository;

    private final CouponRepository couponRepository;

    private final CartService cartService;

    private final SessionOperatorDetails sessionOperatorDetails;

    @GetMapping("/shopping-cart.html")
    public ModelAndView initView(@RequestParam (value = "coupon_code", required = false) String couponCode) {
        ModelAndView modelAndView = new ModelAndView("shopping-cart");

        List<Product> productsByUser = cartService.getProductsByUser();

        modelAndView.addObject("productsByUser", productsByUser);
        modelAndView.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());
        modelAndView.addObject("isLogin", sessionOperatorDetails.existsForm("account"));
        modelAndView.addObject("searchRequest", new SearchRequest());

        modelAndView.addObject("products", productsByUser);
        modelAndView.addObject("carts", new CartDTO());
        modelAndView.addObject("memberWishlist", sessionOperatorDetails.existsForm("memberWishlist") ? sessionOperatorDetails.getForm("memberWishlist", ArrayList.class) : new ArrayList<>());
        modelAndView.addObject("memberCarts", sessionOperatorDetails.existsForm("memberCarts") ? sessionOperatorDetails.getForm("memberCarts", ArrayList.class) : new ArrayList<>());
        modelAndView.addObject("subtotal", calTotalPrice(productsByUser));
        modelAndView.addObject("now", LocalDate.now().toString());

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
        modelAndView.addObject("discountName", couponCode);
        modelAndView.addObject("discount", discount);
        modelAndView.addObject("totalPrice", calTotalPrice(productsByUser)*(100-discount)/100);
        return modelAndView;
    }

    @GetMapping("/delete-member-product")
    public ModelAndView deleteProduct(@RequestParam (value = "product_id") Integer productId) {
        cartService.deleteMemberProduct(productId);

        return new ModelAndView("redirect:/shopping-cart.html");
    }

    @PostMapping("/update-cart")
    public ModelAndView updateCart(@ModelAttribute CartDTO cartDTO) {
        cartService.updateCart(cartDTO);
        return new ModelAndView("redirect:/shopping-cart.html");
    }

    public Double calTotalPrice(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getNewPrice() * product.getQuantityProductCart();
        }
        return sum;
    }
}
