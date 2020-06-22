package house.demo.Mapper;

import house.demo.bean.BedNumber;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Select("select * from user where username=#{username}")
    public List<String> getloginname(String username);


    @Select("select * from user where username=#{username} and uid!=#{uid}")
    public List<String> checkname(QueryVo vo);

    @Select("select * from user where email=#{email} and uid!=#{uid}")
    public List<String> checkemail(QueryVo vo);

    @Select("select * from user where username=#{username} and password=#{password}")
    public User login(User user);

    @Insert("insert into user values(#{uid},#{username},#{name},#{password},#{phone},0,#{job},#{sex},#{star})")
    public Integer register(User user);

    @Select("select * from user where uid=#{uid}")
    public User getuserByid(String uid);

    public int changeuser(User user);

    @Select("select * from user where uid=#{uid} and password=#{password}")
    public User changejudge(User user);

    public List<BedNumber> getorder(String uid);

    public List<BedNumber> getpay(String uid);

    @Update("update bednumber set state=0,uid=null,latedate=null where bid=#{bid}")
    public int concel(String bid);

    @Update("update bednumber set state=1,latedate=DATE_ADD(latedate,INTERVAL 23 DAY) where bid=#{bid}")
    public int pay(String bid);

    @Update(("update bednumber set state=0,uid=null,latedate=null where bid=#{bid}"))
    public int removeroom(String bid);

    @Select("select * from user where phone=#{phone}")
    List<String> getloginPhone(String phone);
}
