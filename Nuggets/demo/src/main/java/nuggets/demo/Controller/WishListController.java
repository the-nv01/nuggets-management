package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.*;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.UserRepository;
import nuggets.demo.Repository.WishListRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Service.WishListService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    private final WishListRepository wishListRepository;

    private final CartRepository cartRepository;

    private final SessionOperatorDetails sessionOperatorDetails;

    private final UserRepository userRepository;

    private final CartService cartService;

    private final CategoryRepository categoryRepository;

    @GetMapping("/wishlist.html")
    public ModelAndView initView() {

        ModelAndView modelAndView = new ModelAndView("wishlist");

        modelAndView.addObject("wishList", wishListService.getWishListByUser());
        initCart(modelAndView);

        return modelAndView;
    }

    @GetMapping("/delete-wishlist-product")
    @Transactional
    public ModelAndView deleteWishlistProduct(@RequestParam(value = "product_id") Integer productId) {
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        wishListRepository.deleteWishListByUsernameAndProductId(user.getUsername(), productId);
        sessionOperatorDetails.setForm("memberWishlist", wishListRepository.findAllByUsernameOrderByDateDesc(user.getUsername()));
        return new ModelAndView("redirect:/wishlist.html");
    }

    @GetMapping("/add-wishlist")
    @Transactional
    public ModelAndView addWishlist(@RequestParam(value = "product_id") Integer productId) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("redirect:/wishlist.html");
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/login-register.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        WishList wish = wishListRepository.findWishListByProductIdAndUsernameOrderByDateDesc(productId, user.getUsername());

        if (ObjectUtils.isEmpty(wish)) {
            WishList wishAdd = new WishList();
            wishAdd.setUsername(user.getUsername());
            wishAdd.setProductId(productId);
            wishAdd.setDate(conVertStringToDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")));
            wishListRepository.save(wishAdd);
        } else {
            wish.setDate(conVertStringToDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")));
            wishListRepository.save(wish);
        }
        sessionOperatorDetails.setForm("memberWishlist", wishListRepository.findAllByUsernameOrderByDateDesc(user.getUsername()));

        return modelAndView;
    }

    @GetMapping("/add-member-product")
    @Transactional
    public ModelAndView addMemberProduct(@RequestParam(value = "product_id") Integer productId) throws ParseException {
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/login-register.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        wishListRepository.deleteWishListByUsernameAndProductId(user.getUsername(), productId);

        Cart cart = cartRepository.findCartByUsernameAndProductIdOrderByDateDesc(user.getUsername(), productId);
        if (ObjectUtils.isEmpty(cart)) {
            cart = new Cart();
            cart.setProductId(productId);
            cart.setQuantity(1);
            cart.setUsername(user.getUsername());
            cart.setDate(conVertStringToDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")));
        } else {
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setDate(conVertStringToDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")));
        }
        cartRepository.save(cart);

        return new ModelAndView("redirect:/shopping-cart.html");
    }

    private static Date conVertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(date);
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
