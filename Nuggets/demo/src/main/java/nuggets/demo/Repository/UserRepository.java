package nuggets.demo.Repository;

import nuggets.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User getUserByUsernameAndPassword(String username, String password);

    User save(User user);

    User getUserByUsername(String username);
}
