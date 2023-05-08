package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Cart;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Repository.UserRepository;
import nuggets.demo.Repository.WishListRepository;
import nuggets.demo.Service.WishListService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    private final WishListRepository wishListRepository;

    private final CartRepository cartRepository;

    private final SessionOperatorDetails sessionOperatorDetails;

    private final UserRepository userRepository;

    @GetMapping("/wishlist.html")
    public ModelAndView initView() {
        sessionOperatorDetails.setForm("account", userRepository.getUserByUsername("the"));

        ModelAndView modelAndView = new ModelAndView("wishlist");

        modelAndView.addObject("wishList", wishListService.getWishListByUser());

        return modelAndView;
    }

    @GetMapping("/delete-wishlist-product")
    @Transactional
    public ModelAndView deleteWishlistProduct(@RequestParam(value = "product_id") Integer productId) {
        User user = sessionOperatorDetails.getForm("account", User.class);
        wishListRepository.deleteWishListByUsernameAndProductId(user.getUsername(), productId);
        return new ModelAndView("redirect:/wishlist.html");
    }

    @GetMapping("/add-member-product")
    @Transactional
    public ModelAndView addMemberProduct(@RequestParam(value = "product_id") Integer productId) {
        User user = sessionOperatorDetails.getForm("account", User.class);
        wishListRepository.deleteWishListByUsernameAndProductId(user.getUsername(), productId);

        Cart cart = cartRepository.findCartByUsernameAndProductId(user.getUsername(), productId);
        if (ObjectUtils.isEmpty(cart)) {
            cart = new Cart();
            cart.setProductId(productId);
            cart.setQuantity(1);
            cart.setUsername(user.getUsername());
        } else {
            cart.setQuantity(cart.getQuantity() + 1);
        }
        cartRepository.save(cart);

        return new ModelAndView("redirect:/wishlist.html");
    }
}
