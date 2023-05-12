package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.CartDTO;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.SearchRequest;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Service.ServiceImpl.IndexServiceImpl;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class IndexController {

    private final IndexServiceImpl indexService;

    private final CartService cartService;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final SessionOperatorDetails sessionOperatorDetails;

    @GetMapping("")
    public ModelAndView initView() {
        ModelAndView mav = new ModelAndView("redirect:/index-2.html");
        return mav;
    }

    @GetMapping("/index-2.html")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index-2");
        List<Product> productsByUser = cartService.getProductsByUser();
        mav.addObject("productsByUser", productsByUser);
        mav.addObject("memberWishlist", sessionOperatorDetails.existsForm("memberWishlist") ? sessionOperatorDetails.getForm("memberWishlist", ArrayList.class) : new ArrayList<>());
        mav.addObject("subtotal", calTotalPrice(productsByUser));
        mav.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());
        mav.addObject("isLogin", sessionOperatorDetails.existsForm("account"));
        mav.addObject("searchRequest", new SearchRequest());

        mav.addObject("products", indexService.findAll());
        mav.addObject("largeProducts", indexService.getLargeProducts());
        mav.addObject("monitorProducts", productRepository.findAllByCategoryId(1));
        mav.addObject("watchProducts", productRepository.findAllByCategoryId(8));
        mav.addObject("phoneProducts", productRepository.findAllByCategoryId(7));
        return mav;
    }
    public Double calTotalPrice(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getNewPrice() * product.getQuantityProductCart();
        }
        return sum;
    }

}
