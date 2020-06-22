package house.demo.Service;

import house.demo.bean.BedNumber;
import house.demo.bean.Houses;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;

import java.util.List;
import java.util.Map;

public interface AdminHouseManageService {
    public Houses update(BedNumber bedNumber, Houses houses);

    public Houses dele(String bid);

    public int addonehouse(BedNumber bedNumber, Houses houses);

    public int addtwohouse(BedNumber bedNumber, Houses houses);

    public Houses getsamehouse(String location);

    public List<BedNumber> getsamehousecount(String hid);

    public PageModel searchbednumber(QueryVo vo);

    public int getSameBed(Map<String, Object> map);
}
