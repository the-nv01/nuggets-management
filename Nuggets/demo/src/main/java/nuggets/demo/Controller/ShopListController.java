package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.SearchRequest;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Service.ServiceImpl.IndexServiceImpl;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ShopListController {

    private final IndexServiceImpl indexService;

    private final CartService cartService;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final SessionOperatorDetails sessionOperatorDetails;

    @GetMapping("/shop-list-left-sidebar.html")
    public ModelAndView shopListLeftSidebar(@ModelAttribute SearchRequest searchRequest,
                                            @RequestParam (value = "category_id", required = false) Integer categoryId) {
        ModelAndView mav = new ModelAndView("shop-list-left-sidebar");
        initCart(mav);

        List<Product> products = new ArrayList<>();
        String name = "";
        Integer categoryIdSearch = ObjectUtils.isEmpty(categoryId) ? 0 : categoryId;
        Double minPrice = 0.0;
        Double maxPrice = 999999999.0;
        Integer discount = -1;
        Integer inStock = 0;
        String order = "nameAsc";
        if (!Objects.equals(searchRequest, new SearchRequest())) {
            name = ObjectUtils.isEmpty(searchRequest.getProductName()) ? "" : searchRequest.getProductName();
            categoryIdSearch = ObjectUtils.isEmpty(searchRequest.getCategoryId()) ? 0 : searchRequest.getCategoryId();
            minPrice = ObjectUtils.isEmpty(searchRequest.getMinPrice()) ? 0.0 : searchRequest.getMinPrice();
            maxPrice = ObjectUtils.isEmpty(searchRequest.getMaxPrice()) ? 99999999999.0 : searchRequest.getMaxPrice();
            discount = ObjectUtils.isEmpty(searchRequest.getIsDiscounting()) ? -1 : 0;
            inStock = ObjectUtils.isEmpty(searchRequest.getNotInStock()) ? 0 : -1;
            order = ObjectUtils.isEmpty(searchRequest.getOrder()) ? "nameAsc" : searchRequest.getOrder();
        }
        if (categoryIdSearch == 0) {
            if (order.equals("nameAsc")) {
                products = productRepository.findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameAsc(
                        name, minPrice, maxPrice, discount, inStock);
            } else if (order.equals("nameDesc")) {
                products = productRepository.findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameDesc(
                        name, minPrice, maxPrice, discount, inStock);
            } else if (order.equals("priceAsc")) {
                products = productRepository.findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceAsc(
                        name, minPrice, maxPrice, discount, inStock);
            } else {
                products = productRepository.findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceDesc(
                        name, minPrice, maxPrice, discount, inStock);
            }
        } else {
            if (order.equals("nameAsc")) {
                products = productRepository.findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameAsc(
                        name, categoryIdSearch, minPrice, maxPrice, discount, inStock);
            } else if (order.equals("nameDesc")) {
                products = productRepository.findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameDesc(
                        name, categoryIdSearch, minPrice, maxPrice, discount, inStock);
            } else if (order.equals("priceAsc")) {
                products = productRepository.findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceAsc(
                        name, categoryIdSearch, minPrice, maxPrice, discount, inStock);
            } else {
                products = productRepository.findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceDesc(
                        name, categoryIdSearch, minPrice, maxPrice, discount, inStock);
            }
        }
        mav.addObject("products", products);
        mav.addObject("searchRequest", searchRequest);

        return mav;
    }

    private void initCart(ModelAndView mav) {
        List<Product> productsByUser = cartService.getProductsByUser();
        mav.addObject("productsByUser", productsByUser);
        mav.addObject("memberWishlist", sessionOperatorDetails.existsForm("memberWishlist") ? sessionOperatorDetails.getForm("memberWishlist", ArrayList.class) : new ArrayList<>());
        mav.addObject("subtotal", calTotalPrice(productsByUser));
        mav.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());
    }
    public Double calTotalPrice(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getNewPrice() * product.getQuantityProductCart();
        }
        return sum;
    }

}
