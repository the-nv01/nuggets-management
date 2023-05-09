package nuggets.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ProductImage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column (name = "product_image_id")
    private Integer productImageId;

    @Column (name = "product_id")
    private Integer productId;

    @Column
    private String image;
}
