package house.demo.Service;

import house.demo.bean.BedNumber;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;

public interface AdminOrderManageService {

    public PageModel searchOrder(QueryVo vo);

    public BedNumber updateOrder(BedNumber bedNumber);

    PageModel findOverTime(QueryVo vo);

    void clearOver(int state);
}
