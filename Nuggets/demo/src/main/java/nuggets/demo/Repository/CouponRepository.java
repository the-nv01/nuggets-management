package nuggets.demo.Repository;

import nuggets.demo.Model.Cart;
import nuggets.demo.Model.Coupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Integer> {
    Coupon findCouponByCouponCode(String couponCode);
}
