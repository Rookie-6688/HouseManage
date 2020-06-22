package house.demo.Service;

import house.demo.Mapper.UserMapper;
import house.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LoginService {

    public User login(User user);

    public User register(User user);

    public List<String> getname(String username);

    public List<String> getPhone(String email);
}
