package house.demo.Mapper;

import house.demo.bean.BedNumber;
import house.demo.bean.Houses;
import house.demo.bean.QueryVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HouseMapper {

    public List<BedNumber> getsome(QueryVo vo);

    public Integer getsomecount(QueryVo vo);

    @Select("select * from houses where hid=#{id}")
    public Houses gethouseByhid(String id);

    public List<BedNumber> getindex();
}
