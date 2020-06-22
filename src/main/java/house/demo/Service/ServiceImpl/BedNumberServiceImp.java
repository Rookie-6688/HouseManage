package house.demo.Service.ServiceImpl;

import house.demo.Mapper.BednumberMapper;
import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.Service.BedNumberService;
import house.demo.bean.BedNumber;
import house.demo.bean.User;
import house.demo.component.BedbumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BedNumberServiceImp implements BedNumberService {

    @Autowired
    BednumberMapper bednumberMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    HouseMapper houseMapper;

    @Cacheable(value = {"HouseLiveBedNumber"}, key = "#hid")
    public List<BedNumber> getusedetails(String hid) {
        List<BedNumber> list = bednumberMapper.getuselist(hid);
        System.out.println(list);
        if (list.size() > 0) {
            for (BedNumber bedNumber : list) {
                bedNumber.setHouses(houseMapper.gethouseByhid(hid));
                String uid = bedNumber.getUser().getUid();
                User user = userMapper.getuserByid(uid);
                bedNumber.setUser(user);
            }
        }
        return list;
    }

    @Cacheable(value = {"HouseEmptyBedNumber"}, key = "#hid")
    public List<BedNumber> getnotdetails(String hid) {
        List<BedNumber> list = bednumberMapper.getnotlist(hid);
        if (list.size() > 0) {
            for (BedNumber bedNumber : list) {
                bedNumber.setHouses(houseMapper.gethouseByhid(hid));
            }
        }
        return list;
    }

    @Cacheable(value = {"HouseOrderBedNumber"}, key = "#hid")
    public List<BedNumber> getOrderDetailsList(String hid) {
        List<BedNumber> list = bednumberMapper.getOrderList(hid);
        if (list.size() > 0) {
            for (BedNumber bedNumber : list) {
                bedNumber.setHouses(houseMapper.gethouseByhid(hid));
            }
        }
        return list;
    }

    @Cacheable(value = {"BedNumber"}, key = "#bid")
    @Override
    public BedNumber getbedBybid(String bid) {
        BedNumber bedNumber = bednumberMapper.getbedBybid(bid);
        if (bedNumber != null) {
            bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
        }
        return bedNumber;
    }

    @Caching(
            put = {
                    @CachePut(value = {"BedNumber"}, key = "#map.get('bid')"),
            },
            evict = {
                    @CacheEvict(cacheNames = {"HouseLiveBedNumber"}, key = "#map.get('hid')"),
                    @CacheEvict(cacheNames = {"HouseEmptyBedNumber"}, key = "#map.get('hid')"),
                    @CacheEvict(cacheNames = {"HouseOrderBedNumber"}, key = "#map.get('hid')"),
                    @CacheEvict(cacheNames = {"IndexBedNumber"}),
                    @CacheEvict(cacheNames = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminHouseMessageSearch"}, allEntries = true)
            }
    )
    @Override
    public BedNumber orderhouse(Map<String, Object> map) {
        bednumberMapper.orderhouse(map);
        return BedbumberUtil.getbednumberbybid(bednumberMapper, houseMapper, userMapper, map.get("bid") + "");
    }

}
