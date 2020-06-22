package house.demo.Mapper;

import house.demo.bean.Collections;
import house.demo.bean.QueryVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminCollectManageMapper {

    public List<Collections> SearchCollectList(QueryVo vo);

    public int SearchCollectCount(QueryVo vo);

    @Insert("insert into collections values(#{cid},#{uid},#{bid})")
    public int addCollect(QueryVo vo);

    public Collections getCollectByCid(String cid);

    @Update("update collections set uid=#{uid},bid=#{bid} where cid=#{cid}")
    public int updatecollect(QueryVo vo);
}
