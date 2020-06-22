package house.demo.Service;

import house.demo.bean.Collections;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;

import java.util.List;

public interface AdminCollectManageService {

    public PageModel AdminSearchCollect(QueryVo vo);

    public int addCollect(QueryVo vo);

    public int deleCollect(List<String> cid);

    public Collections getcollectBycid(String cid);

    public int updatecollect(QueryVo vo);
}
