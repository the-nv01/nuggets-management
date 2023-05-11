package nuggets.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private Integer categoryId;

    private String productName;

    private Double minPrice;

    private Double maxPrice;

    private Boolean isDiscounting;

    private Boolean notInStock = false;

    private String order;
}
