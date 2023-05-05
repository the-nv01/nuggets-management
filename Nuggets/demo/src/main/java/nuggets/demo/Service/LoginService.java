package nuggets.demo.Service;

import nuggets.demo.Model.User;

public interface LoginService {
    User getUserByUsernameAndPassword(String username, String password);

    User register(User user);
}
