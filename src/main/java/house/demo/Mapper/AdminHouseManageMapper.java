package house.demo.Mapper;

import house.demo.bean.BedNumber;
import house.demo.bean.Houses;
import house.demo.bean.QueryVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import java.util.List;
import java.util.Map;

public interface AdminHouseManageMapper {

    public int searchhousecount(QueryVo vo);

    public List<BedNumber> searchhouselist(QueryVo vo);

    @CacheEvict(value = {"House"}, keyGenerator = "firstParamKeyGenerator")
    @Delete("delete from houses where hid=#{hid}")              /*当删除的房间是一室一厅时会直接删除house里的house*/
    public int delehouseByhid(String hid);

    @Update("update houses set roomtype=#{roomtype} where hid=#{hid}")
    public int dechangeByhid(Map<String, String> map);                        /*当删除的房间不是一室一厅时会修改house里的roomtype类型*/

    @Delete("delete from bednumber where bid=#{bid}")
    public int delebed(String bid);

    public int updatebed(BedNumber bedNumber);

    public int getSameBed(Map<String, Object> map);                        //更新,添加操作时防止修改（添加）的房间号在当前房间已经存在

    @CacheEvict(value = {"BedNumber"}, allEntries = true)
    @Delete("delete from bednumber where hid=#{hid} and bid!=#{bid}")
    public int deleotherbed(Map map);            /*用于更新操作时将原本合租的房子变成整租，这时将其他房间删除*/

    @Update("update houses set livetype=#{livetype},images=#{images},location=#{location},roomtype=#{roomtype},high=#{high} where hid=#{hid}")
    public int updatehouse(Houses houses);

    @Insert("insert into bednumber(bid,hid,number,state,price,area,sun,star) values(#{bid},#{houses.hid},#{number},0,#{price},#{area},#{sun},0)")
    public int addbed(BedNumber bedNumber);

    @Insert("insert into houses values(#{hid},#{livetype},#{images},#{location},#{roomtype},#{high})")
    public int addhouse(Houses houses);

    @Select("select * from houses where location=#{location}")
    public Houses getsamehouse(String location);                             /*防止添加的房屋已存在*/

    @Select("select * from bednumber where hid=#{hid}")
    public List<BedNumber> getsamehousecount(String hid);

    @Update("update bednumber set uid=null,latedate=null,state=0 where uid=#{uid}")
    public int delebedByuid(String uid);
}
