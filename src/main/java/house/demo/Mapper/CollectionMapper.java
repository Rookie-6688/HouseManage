package house.demo.Mapper;

import house.demo.bean.Collections;
import house.demo.bean.QueryVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CollectionMapper {
    public List<Collections> getCollectionByuid(String uid);

    @Insert("insert into collections values(#{cid},#{uid},#{bid})")
    public Integer addCollection(QueryVo vo);

    @Delete("delete from collections where cid=#{cid}")
    public Integer deleCollectionBycid(String cid);

    @Select("select * from collections where uid=#{uid} and bid=#{bid}")
    public Collections getoneCollectionBybid(QueryVo vo);

    @Select("select bid from collections where cid=#{cid}")
    public String getbidBycid(String cid);

    @Select("select count(*) from collections where bid=#{bid}")
    Integer getCountByBid(String bid);
}
