package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class InitViewController {

    @GetMapping("/404.html")
    public ModelAndView init404() {
        return new ModelAndView("404");
    }

    @GetMapping("/about-us.html")
    public ModelAndView aboutUs() {
        return new ModelAndView("about-us");
    }

    @GetMapping("/blog-2-column.html")
    public ModelAndView blog2Column() {
        return new ModelAndView("blog-2-column");
    }

    @GetMapping("/blog-3-column.html")
    public ModelAndView blog3Column() {
        return new ModelAndView("blog-3-column");
    }

    @GetMapping("/blog-audio-format.html")
    public ModelAndView blogAudioFormat() {
        return new ModelAndView("blog-audio-format");
    }

    @GetMapping("/blog-details.html")
    public ModelAndView blogDetails() {
        return new ModelAndView("blog-details");
    }

    @GetMapping("/blog-details-left-sidebar.html")
    public ModelAndView blogDetailsLeftSidebar() {
        return new ModelAndView("blog-details-left-sidebar");
    }

    @GetMapping("/blog-details-right-sidebar.html")
    public ModelAndView blogDetailsRightSidebar() {
        return new ModelAndView("blog-details-right-sidebar");
    }

    @GetMapping("/blog-gallery-format.html")
    public ModelAndView blogGalleryFormat() {
        return new ModelAndView("blog-gallery-format");
    }

    @GetMapping("/blog-left-sidebar.html")
    public ModelAndView blogLeftSidebar() {
        return new ModelAndView("blog-left-sidebar");
    }

    @GetMapping("/blog-list.html")
    public ModelAndView blogList() {
        return new ModelAndView("blog-list");
    }

    @GetMapping("/blog-list-left-sidebar.html")
    public ModelAndView blogListLeftSidebar() {
        return new ModelAndView("blog-list-left-sidebar");
    }

    @GetMapping("/blog-list-right-sidebar.html")
    public ModelAndView blogListRightSidebar() {
        return new ModelAndView("blog-list-right-sidebar");
    }

    @GetMapping("/blog-right-sidebar.html")
    public ModelAndView blogRightSidebar() {
        return new ModelAndView("blog-right-sidebar");
    }

    @GetMapping("/blog-video-format.html")
    public ModelAndView blogVideoFormat() {
        return new ModelAndView("blog-video-format");
    }

    @GetMapping("/cart.html")
    public ModelAndView cart() {
        return new ModelAndView("cart");
    }

    @GetMapping("/checkout.html")
    public ModelAndView checkout() {
        return new ModelAndView("checkout");
    }

    @GetMapping("/compare.html")
    public ModelAndView compare() {
        return new ModelAndView("compare");
    }

    @GetMapping("/contact.html")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @GetMapping("/faq.html")
    public ModelAndView faq() {
        return new ModelAndView("faq");
    }

    @GetMapping("/index-2.html")
    public ModelAndView index2() {
        return new ModelAndView("index-2");
    }

    @GetMapping("/index-3.html")
    public ModelAndView index3() {
        return new ModelAndView("index-3");
    }

    @GetMapping("/index-4.html")
    public ModelAndView index4() {
        return new ModelAndView("index-4");
    }

    @GetMapping("/product-details.html")
    public ModelAndView productDetails() {
        return new ModelAndView("product-details");
    }

    @GetMapping("/shop-3-column.html")
    public ModelAndView shop3Column() {
        return new ModelAndView("shop-3-column");
    }

    @GetMapping("/shop-4-column.html")
    public ModelAndView shop4Column() {
        return new ModelAndView("shop-4-column");
    }

    @GetMapping("/shop-left-sidebar.html")
    public ModelAndView shopLeftSidebar() {
        return new ModelAndView("shop-left-sidebar");
    }

    @GetMapping("/shop-list.html")
    public ModelAndView shopList() {
        return new ModelAndView("shop-list");
    }

    @GetMapping("/shop-list-left-sidebar.html")
    public ModelAndView shopListLeftSidebar() {
        return new ModelAndView("shop-list-left-sidebar");
    }

    @GetMapping("/shop-list-right-sidebar.html")
    public ModelAndView shopListRightSidebar() {
        return new ModelAndView("shop-list-right-sidebar");
    }

    @GetMapping("/shop-right-sidebar.html")
    public ModelAndView shopRightSidebar() {
        return new ModelAndView("shop-right-sidebar");
    }

    @GetMapping("/shopping-cart.html")
    public ModelAndView shoppingCart() {
        return new ModelAndView("shopping-cart");
    }

    @GetMapping("/single-product.html")
    public ModelAndView singleProduct() {
        return new ModelAndView("single-product");
    }

    @GetMapping("/single-product-affiliate.html")
    public ModelAndView singleProductAffiliate() {
        return new ModelAndView("single-product-affiliate");
    }

    @GetMapping("/single-product-carousel.html")
    public ModelAndView singleProductCarousel() {
        return new ModelAndView("single-product-carousel.html");
    }

    @GetMapping("/single-product-gallery-left.html")
    public ModelAndView singleProductGalleryLeft() {
        return new ModelAndView("single-product-gallery-left");
    }

    @GetMapping("/single-product-gallery-right.html")
    public ModelAndView singleProductGalleryRight() {
        return new ModelAndView("single-product-gallery-right");
    }

    @GetMapping("/single-product-group.html")
    public ModelAndView singleProductGroup() {
        return new ModelAndView("single-product-group");
    }

    @GetMapping("/single-product-normal.html")
    public ModelAndView singleProductNormal() {
        return new ModelAndView("single-product-normal");
    }

    @GetMapping("/single-product-sale.html")
    public ModelAndView singleProductSale() {
        return new ModelAndView("single-product-sale");
    }

    @GetMapping("/single-product-tab-style-left.html")
    public ModelAndView singleProductTabStyleLeft() {
        return new ModelAndView("single-product-tab-style-left");
    }

    @GetMapping("/single-product-tab-style-right.html")
    public ModelAndView singleProductTabStyleRight() {
        return new ModelAndView("single-product-tab-style-right");
    }

    @GetMapping("/single-product-tab-style-top.html")
    public ModelAndView singleProductTabStyleTop() {
        return new ModelAndView("single-product-tab-style-top");
    }

    @GetMapping("/wishlist.html")
    public ModelAndView wishList() {
        return new ModelAndView("wishlist");
    }

    @GetMapping("/accounts.html")
    public ModelAndView accounts() {
        return new ModelAndView("accounts");
    }

    @GetMapping("/add-product.html")
    public ModelAndView addProduct() {
        return new ModelAndView("add-product");
    }

    @GetMapping("/admin-page.html")
    public ModelAndView adminPage() {
        return new ModelAndView("admin-page");
    }

}
