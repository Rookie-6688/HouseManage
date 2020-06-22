package house.demo.Mapper;

import house.demo.bean.BedNumber;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AdminUserManageMapper {
    @Select("select * from user where username=#{username} and password=#{password} and type=1")
    public User adminlogin(User user);

    @Delete("delete from user where uid=#{uid}")
    public int dele(String uid);

    @Insert("insert into user values(#{uid},#{username},#{name},#{password},#{email},#{type},#{job},#{sex},#{star})")
    public int adminadduser(User user);

    public List<User> partfind(QueryVo vo);

    public int partfindcount(QueryVo vo);

    @Select("select * from user where uid=#{uid}")
    public User getuserByid(String uid);
}
