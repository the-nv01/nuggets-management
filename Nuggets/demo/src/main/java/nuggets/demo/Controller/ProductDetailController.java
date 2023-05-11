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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final CategoryRepository categoryRepository;

    private final CartService cartService;

    private final SessionOperatorDetails sessionOperatorDetails;

    @GetMapping("/single-product.html")
    public ModelAndView singleProduct(@RequestParam (value = "product_id") Integer productId) {
        ModelAndView modelAndView = new ModelAndView("single-product");

        Product product = productRepository.findAllByProductId(productId);
        modelAndView.addObject("product", product);

        List<ProductImage> productImages = productImageRepository.findAllByProductId(productId);
        modelAndView.addObject("productImages", productImages);

        initCart(modelAndView);

        return modelAndView;
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
