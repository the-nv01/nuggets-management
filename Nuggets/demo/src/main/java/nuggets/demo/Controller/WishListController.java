package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Service.WishListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @GetMapping("/wishlist.html")
    public ModelAndView initView() {
        ModelAndView modelAndView = new ModelAndView("wishlist");

        modelAndView.addObject("wishList", wishListService.getWishListByUser());

        return modelAndView;
    }
}
