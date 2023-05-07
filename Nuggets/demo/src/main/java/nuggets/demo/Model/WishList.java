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
public class WishList {
    @Id
    @Column (name = "wish_list_id")
    private Integer wishListId;

    @Column
    private String username;

    @Column (name = "product_id")
    private Integer productId;
}
