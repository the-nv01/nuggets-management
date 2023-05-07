package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Cart;
import nuggets.demo.Model.CartDTO;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class CartController {

    private final CartRepository cartRepository;

    private final CartService cartService;

    @GetMapping("/shopping-cart.html")
    public ModelAndView initView(@RequestParam (value = "coupon_code", required = false) String couponCode) {
        ModelAndView modelAndView = new ModelAndView("shopping-cart");

        List<Product> productsByUser = cartService.getProductsByUser();

        modelAndView.addObject("products", productsByUser);
        modelAndView.addObject("carts", new CartDTO());
        modelAndView.addObject("subtotal", calTotalPrice(productsByUser));

        //set default discount
        Integer discount = 10;

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
