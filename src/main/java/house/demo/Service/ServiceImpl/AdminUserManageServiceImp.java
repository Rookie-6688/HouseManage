package house.demo.Service.ServiceImpl;

import house.demo.Mapper.AdminHouseManageMapper;
import house.demo.Mapper.AdminUserManageMapper;
import house.demo.Mapper.UserMapper;
import house.demo.Service.AdminUserManageService;
import house.demo.bean.BedNumber;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AdminUserManageServiceImp implements AdminUserManageService {

    @Autowired
    AdminUserManageMapper adminUserMapper;
    @Autowired
    AdminHouseManageMapper adminHouseManageMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public User getAdminUser(User user) {
        return adminUserMapper.adminlogin(user);
    }

    @Override
    public User deleuser(String uid) {
        adminHouseManageMapper.delebedByuid(uid);            //删除用户会将该用户订购的房屋变成待订购状态
        adminUserMapper.dele(uid);
        return null;
    }

    @Override
    public User adminuseradd(User user) {
        adminUserMapper.adminadduser(user);
        return user;
    }

    @Override
    public PageModel partusersearch(QueryVo vo) {
        String name = vo.getName();
        String job = vo.getJob();
        String sex = vo.getSex();
        String uid = vo.getUid();
        int page = vo.getCurrentPage();

        PageModel pageModel = new PageModel(page, adminUserMapper.partfindcount(vo), 16);
        vo.setStartIndex(pageModel.getStartIndex());
        vo.setPagesize(16);
        List<User> list = adminUserMapper.partfind(vo);
        if (list.size() == 0) {
            pageModel.setCurrentPageNum(1);
            vo.setStartIndex(pageModel.getStartIndex());
            list = adminUserMapper.partfind(vo);
            if (list.size() == 0) {
                list = null;
            }
        }
        pageModel.setList(list);
        pageModel.setUrl("adminpartfind&uid=" + uid + "&name=" + name + "&sex=" + sex + "&job=" + job + "&page=");
        return pageModel;
    }

    @Override
    public List<String> getname(String username) {
        return userMapper.getloginname(username);
    }

    @Override
    public List<String> getemail(String email) {
        return userMapper.getloginPhone(email);
    }
}
