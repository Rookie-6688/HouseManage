package house.demo.Service;

import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import house.demo.bean.User;

import java.util.List;
import java.util.Map;

public interface AdminUserManageService {
    public User getAdminUser(User user);

//	public PageModel getUser(int page);

    public User deleuser(String uid);

    public User adminuseradd(User user);

    public PageModel partusersearch(QueryVo vo);

    public List<String> getname(String username);

    public List<String> getemail(String email);
}
