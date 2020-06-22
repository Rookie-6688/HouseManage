package house.demo.Service;

import house.demo.bean.BedNumber;
import house.demo.bean.QueryVo;
import house.demo.bean.User;

import java.util.List;

public interface UserService {

    public User getUserByid(String uid);

    public User changeuser(User user);

    public User changejudge(User user);

    public List<BedNumber> getorder(String uid);

    public List<BedNumber> getpay(String uid);

    public BedNumber concel(String bid);

    public BedNumber pay(String bid);

    public BedNumber removeroom(String bid);

    public List<String> checkemail(QueryVo vo);
}
