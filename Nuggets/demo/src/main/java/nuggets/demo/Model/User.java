package nuggets.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class User {
    @Id
    @Column
    private String username;

    @Column
    private String password;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String role;

    @Column
    private String country;

    @Column
    private String company;

    @Column
    private String address;

    @Column
    private String apartment;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private Integer phone;

    private String backUrl;

    private String destinationUrl;
}
