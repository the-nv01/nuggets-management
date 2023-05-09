package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.CartDTO;
import nuggets.demo.Model.Coupon;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.ProductImage;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Repository.CouponRepository;
import nuggets.demo.Repository.ProductImageRepository;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    @GetMapping("/single-product.html")
    public ModelAndView singleProduct(@RequestParam (value = "product_id") Integer productId) {
        ModelAndView modelAndView = new ModelAndView("single-product");

        Product product = productRepository.findAllByProductId(productId);
        modelAndView.addObject("product", product);

        List<ProductImage> productImages = productImageRepository.findAllByProductId(productId);
        modelAndView.addObject("productImages", productImages);

        return modelAndView;
    }
}
