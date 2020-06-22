package house.demo.Service.ServiceImpl;

import house.demo.Mapper.AdminHouseManageMapper;
import house.demo.Mapper.BednumberMapper;
import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.Service.AdminHouseManageService;
import house.demo.bean.BedNumber;
import house.demo.bean.Houses;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import house.demo.component.BedbumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.ValueExp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminHouseManageServiceImp implements AdminHouseManageService {

    @Autowired
    AdminHouseManageMapper adminHouseMangerMapper;
    @Autowired
    BednumberMapper bednumberMapper;
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    UserMapper userMapper;

    @Cacheable(value = {"AdminHouseMessageSearch"}, key = "#vo.toString()")
    @Override
    public PageModel searchbednumber(QueryVo vo) {
        Integer state = vo.getState();
        String location = vo.getLocation();
        String hid = vo.getHid();
        Integer much3 = vo.getMuch();
        Integer much = vo.getMuch();
        if (much == 1900) {
            vo.setMuch2(-1);
            vo.setMuch(1900);
        } else if (much == 2500) {
            vo.setMuch2(1901);
        } else if (much == 4000) {
            vo.setMuch2(2501);
        } else if (much == 4800) {
            vo.setMuch2(4001);
        } else if (much == 4801) {
            vo.setMuch2(4801);
            vo.setMuch(-1);
        }

        Integer getsomecount = adminHouseMangerMapper.searchhousecount(vo);
        PageModel pageModel = new PageModel(vo.getCurrentPage(), getsomecount, 8);

        vo.setPagesize(8);
        vo.setStartIndex(pageModel.getStartIndex());
        List<BedNumber> list = adminHouseMangerMapper.searchhouselist(vo);

        if (list.size() == 0) {                            //在指定页没有找到合适的值跳到第一页找值,这里判断size是因为没有差到符合的值返回的是一个空的容器而不是null值
            pageModel.setCurrentPageNum(1);
            vo.setStartIndex(pageModel.getStartIndex());
            list = adminHouseMangerMapper.searchhouselist(vo);
            if (list.size() == 0) {                        //如果第一页还是没有合适的值归为null
                list = null;
            }
        }
        if (list != null) {                                //防止查找为null报错
            for (BedNumber bedNumber : list) {
                bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
            }
        }
        pageModel.setList(list);
        pageModel.setUrl("adminhousesearch&hid=" + hid + "&location=" + location + "&state=" + state + "&much=" + much3 + "&page=");
        return pageModel;
    }

    @Caching(
            put = {
                    @CachePut(value = {"House"}, key = "#result.hid")
            },
            evict = {
                    @CacheEvict(value = {"BedNumber"}, key = "#bedNumber.bid"),
                    @CacheEvict(value = {"HouseLiveBedNumber"}, key = "#result.hid"),
                    @CacheEvict(value = {"HouseEmptyBedNumber"}, key = "#result.hid"),
                    @CacheEvict(value = {"HouseOrderBedNumber"}, key = "#result.hid"),
                    @CacheEvict(value = {"IndexBedNumber"}),
                    @CacheEvict(value = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(value = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(value = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public Houses update(BedNumber bedNumber, Houses houses) {
        Map<String, Object> map = new HashMap<>();
        map.put("hid", houses.getHid());
        map.put("number", bedNumber.getNumber());
        map.put("bid", bedNumber.getBid());
        int i = adminHouseMangerMapper.updatebed(bedNumber);
        int i2 = adminHouseMangerMapper.updatehouse(houses);
        if (houses.getLivetype().equals("0")) {
            adminHouseMangerMapper.deleotherbed(map);            //合租变整租时将其他合租的房间删除
        }
        Houses houses1 = houseMapper.gethouseByhid(houses.getHid());
        return houses1;
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = {"House"}, key = "#result.hid"),
                    @CacheEvict(cacheNames = {"BedNumber"}, key = "#bid"),
                    @CacheEvict(cacheNames = {"HouseLiveBedNumber"}, key = "#result.hid"),
                    @CacheEvict(cacheNames = {"HouseEmptyBedNumber"}, key = "#result.hid"),
                    @CacheEvict(cacheNames = {"HouseOrderBedNumber"}, key = "#result.hid"),
                    @CacheEvict(cacheNames = {"IndexBedNumber"}),
                    @CacheEvict(cacheNames = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public Houses dele(String bid) {
        String hid = bednumberMapper.gethidBybid(bid);
        Houses houses = houseMapper.gethouseByhid(hid);
        String roomtype = houses.getRoomtype();
        Map<String, String> map = new HashMap<>();
        map.put("hid", hid);
        if (houses.getLivetype().equals("1")) {
            if (roomtype.equals("四室一厅")) {
                map.put("roomtype", "三室一厅");
                adminHouseMangerMapper.delebed(bid);
                adminHouseMangerMapper.dechangeByhid(map);
            } else if (roomtype.equals("三室一厅")) {
                map.put("roomtype", "两室一厅");
                adminHouseMangerMapper.delebed(bid);
                adminHouseMangerMapper.dechangeByhid(map);
            } else if (roomtype.equals("两室一厅")) {
                map.put("roomtype", "一室一厅");
                adminHouseMangerMapper.delebed(bid);
                adminHouseMangerMapper.dechangeByhid(map);
            } else if (roomtype.equals("一室一厅")) {
                adminHouseMangerMapper.delebed(bid);
                adminHouseMangerMapper.delehouseByhid(hid);
            }
        } else {
            adminHouseMangerMapper.delebed(bid);
            adminHouseMangerMapper.delehouseByhid(hid);
        }
        return houses;
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = {"IndexBedNumber"}),
                    @CacheEvict(cacheNames = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public int addonehouse(BedNumber bedNumber, Houses houses) {
        List<BedNumber> list = adminHouseMangerMapper.getsamehousecount(bedNumber.getHouses().getHid());
        int i = list != null ? list.size() : 0;
        if ((houses.getRoomtype().equals("一室一厅") && i != 0) || (houses.getRoomtype().equals("四室一厅") && i > 3) || (houses.getRoomtype().equals("两室一厅") && i > 1) || (houses.getRoomtype().equals("三室一厅") && i > 2)) {
            return -1;
        }
        int room = bedNumber.getNumber();
        if ((houses.getRoomtype().equals("一室一厅") && room != 1) || (houses.getRoomtype().equals("四室一厅") && (room < 1 || room > 4)) || (houses.getRoomtype().equals("两室一厅") && (room < 1 || room > 2)) || (houses.getRoomtype().equals("三室一厅") && (room < 1 || room > 3))) {
            return -2;
        }
        int i2 = adminHouseMangerMapper.addbed(bedNumber);
        return i2;
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = {"IndexBedNumber"}),
                    @CacheEvict(cacheNames = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public int addtwohouse(BedNumber bedNumber, Houses houses) {
        if (houses.getLivetype().equals("0")) {
            bedNumber.setNumber(0);
        }
        int room = bedNumber.getNumber();
        if (houses.getLivetype().equals("1")) {
            if (houses.getRoomtype().equals("一室一厅") && room == 1) {
                int i2 = adminHouseMangerMapper.addbed(bedNumber);
                int i = adminHouseMangerMapper.addhouse(houses);
                return i + i2;
            } else if (houses.getRoomtype().equals("两室一厅") && (room < 3 && room > 0)) {
                int i2 = adminHouseMangerMapper.addbed(bedNumber);
                int i = adminHouseMangerMapper.addhouse(houses);
                return i + i2;
            } else if (houses.getRoomtype().equals("三室一厅") && (room < 4 && room > 0)) {
                int i2 = adminHouseMangerMapper.addbed(bedNumber);
                int i = adminHouseMangerMapper.addhouse(houses);
                return i + i2;
            } else if (houses.getRoomtype().equals("四室一厅") && (room < 5 && room > 0)) {
                int i2 = adminHouseMangerMapper.addbed(bedNumber);
                int i = adminHouseMangerMapper.addhouse(houses);
                return i + i2;
            } else {
                return -2;
            }
        }
        int i2 = adminHouseMangerMapper.addbed(bedNumber);
        int i = adminHouseMangerMapper.addhouse(houses);
        return i + i2;
    }

    @Override
    public Houses getsamehouse(String location) {
        return adminHouseMangerMapper.getsamehouse(location);
    }

    @Override
    public List<BedNumber> getsamehousecount(String hid) {
        return adminHouseMangerMapper.getsamehousecount(hid);
    }

    @Override
    public int getSameBed(Map<String, Object> map) {
        return adminHouseMangerMapper.getSameBed(map);
    }
}
