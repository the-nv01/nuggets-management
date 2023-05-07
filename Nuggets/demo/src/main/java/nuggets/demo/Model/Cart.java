package nuggets.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Cart {
    @Id
    @Column (name = "cart_id")
    private Integer cartId;

    @Column
    private String username;

    @Column (name = "product_id")
    private Integer productId;

    @Column
    private Integer quantity;
}
