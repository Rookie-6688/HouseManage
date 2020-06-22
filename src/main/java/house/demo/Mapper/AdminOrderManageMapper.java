package house.demo.Mapper;

import house.demo.bean.BedNumber;
import house.demo.bean.QueryVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminOrderManageMapper {
//	public List<BedNumber> getorder(QueryVo vo);

//	@Select("select count(*) from bednumber where state=#{state}")
//	public int getordercount(QueryVo vo);

    @Update("update bednumber set state=#{state},uid=#{user.uid},latedate=#{latedate} where bid=#{bid}")
    public int updatebednumber(BedNumber bednumber);

    public int searchbedcount(QueryVo vo);

    public List<BedNumber> searchbedlist(QueryVo vo);

    Integer searchbedOvertime(Integer state);

    List<BedNumber> searchbedOvertimeList(QueryVo vo);

    void clearOver(int state);
}
