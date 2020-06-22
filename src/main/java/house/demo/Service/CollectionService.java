package house.demo.Service;

import house.demo.bean.Collections;
import house.demo.bean.QueryVo;

import java.util.List;
import java.util.Map;

public interface CollectionService {
    public List<Collections> getCollectionsByuid(String uid);

    public Integer addCollection(QueryVo vo);

    public Integer deleCollectionBycid(String cid);

    public Collections getoneCollectionBybid(QueryVo vo);

    Integer getCountBybid(String bid);
}
