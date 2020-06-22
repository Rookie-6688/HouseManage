package house.demo.Service.ServiceImpl;

import house.demo.Mapper.UserMapper;
import house.demo.Service.LoginService;
import house.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    UserMapper userMapper;

    public User login(User user) {
        User user1 = userMapper.login(user);
        return user1;
    }

    public User register(User user) {
        Integer i = userMapper.register(user);
        return user;
    }

    public List<String> getname(String username) {
        return userMapper.getloginname(username);
    }

    @Override
    public List<String> getPhone(String email) {
        return userMapper.getloginPhone(email);
    }

}
