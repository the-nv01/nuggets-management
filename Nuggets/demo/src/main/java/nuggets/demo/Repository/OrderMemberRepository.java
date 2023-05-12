package nuggets.demo.Repository;

import nuggets.demo.Model.OrderMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMemberRepository extends CrudRepository<OrderMember, String> {

    OrderMember save(OrderMember orderMember);

    OrderMember findByOrderId(Integer orderId);

    List<OrderMember> findAllByOrderByDateDesc();

    List<OrderMember> findAllByUsernameOrderByOrderIdDesc(String username);

}
