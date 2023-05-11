package nuggets.demo.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.UserRepository;
import nuggets.demo.Service.LoginService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userRepository.getUserByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional
    public User register(User user) {
        return userRepository.save(user);
    }
}
