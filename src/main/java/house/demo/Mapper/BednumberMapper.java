package house.demo.Mapper;

import house.demo.bean.BedNumber;
import house.demo.bean.Houses;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface BednumberMapper {
    public List<BedNumber> getuselist(String hid);

    public List<BedNumber> getOrderList(String hid);

    public List<BedNumber> getnotlist(String hid);

    public BedNumber getbedBybid(String bid);

    @Select("select hid from Bednumber where bid=#{bid}")
    public String gethidBybid(String bid);

    @Update("update bednumber set state=2,uid=#{uid},latedate=DATE_ADD(#{latedate},INTERVAL 7 DAY) where bid=#{bid}")
    public int orderhouse(Map<String, Object> map);

    @Update("update bednumber set star=star+1 where bid=#{bid}")
    public int addCollect(String bid);

    @Update("update bednumber set star=star-1 where bid=#{bid}")
    public int deleCollect(String bid);
}
