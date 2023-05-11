package nuggets.demo.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.User;
import nuggets.demo.Model.WishList;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Repository.UserRepository;
import nuggets.demo.Repository.WishListRepository;
import nuggets.demo.Service.WishListService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final WishListRepository wishListRepository;

    private final SessionOperatorDetails sessionOperatorDetails;

    @Override
    public List<Product> getWishListByUser() {
        User user = new User();
        if (sessionOperatorDetails.existsForm("account")) {
            user = sessionOperatorDetails.getForm("account", User.class);
        }
        List<WishList> wishList = wishListRepository.findAllByUsernameOrderByDateDesc(user.getUsername());

        return wishList.stream().map(wish -> {
            return productRepository.findAllByProductId(wish.getProductId());
        }).collect(Collectors.toList());
    }
}
