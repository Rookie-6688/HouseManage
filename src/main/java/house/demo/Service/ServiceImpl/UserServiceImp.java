package house.demo.Service.ServiceImpl;

import house.demo.Mapper.BednumberMapper;
import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.Service.CollectionService;
import house.demo.Service.UserService;
import house.demo.bean.BedNumber;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import house.demo.component.BedbumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    BednumberMapper bednumberMapper;
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    CollectionService collectionService;
    @Override
    public User getUserByid(String uid) {
        return userMapper.getuserByid(uid);
    }

    @Override
    public User changeuser(User user) {
        userMapper.changeuser(user);
        return userMapper.getuserByid(user.getUid());
    }

    @Override
    public User changejudge(User user) {
        return userMapper.changejudge(user);
    }

    @Override
    public List<BedNumber> getorder(String uid) {
        List<BedNumber> list = userMapper.getorder(uid);
        for (BedNumber bedNumber : list) {
            BedbumberUtil.getbednumber(houseMapper, userMapper, bedNumber);
            bedNumber.setStar(collectionService.getCountBybid(bedNumber.getBid()));
        }
        if (list.size() == 0) {
            list = null;
        }
        return list;
    }

    @Override
    public List<BedNumber> getpay(String uid) {
        List<BedNumber> list = userMapper.getpay(uid);
        for (BedNumber bedNumber : list) {
            bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
            bedNumber.setStar(collectionService.getCountBybid(bedNumber.getBid()));
        }
        if (list.size() == 0) {
            list = null;
        }
        return list;
    }

    @Caching(
            put = {
                    @CachePut(cacheNames = {"BedNumber"}, key = "#bid"),
            },
            evict = {
                    @CacheEvict(cacheNames = {"HouseLiveBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"HouseEmptyBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"HouseOrderBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"IndexBedNumber"}),
                    @CacheEvict(cacheNames = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public BedNumber concel(String bid) {
        userMapper.concel(bid);
        return BedbumberUtil.getbednumberbybid(bednumberMapper, houseMapper, userMapper, bid);
    }

    @Caching(
            put = {
                    @CachePut(value = {"BedNumber"}, key = "#bid"),
            },
            evict = {
                    @CacheEvict(cacheNames = {"HouseLiveBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"HouseEmptyBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"HouseOrderBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"IndexBedNumber"}),
                    @CacheEvict(cacheNames = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public BedNumber pay(String bid) {
        userMapper.pay(bid);
        return BedbumberUtil.getbednumberbybid(bednumberMapper, houseMapper, userMapper, bid);
    }

    @Caching(
            put = {
                    @CachePut(value = {"BedNumber"}, key = "#bid"),
            },
            evict = {
                    @CacheEvict(cacheNames = {"HouseLiveBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"HouseEmptyBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"HouseOrderBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(cacheNames = {"IndexBedNumber"}),
                    @CacheEvict(cacheNames = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(cacheNames = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public BedNumber removeroom(String bid) {
        userMapper.removeroom(bid);
        return BedbumberUtil.getbednumberbybid(bednumberMapper, houseMapper, userMapper, bid);
    }

    @Override
    public List<String> checkemail(QueryVo vo) {
        return userMapper.checkemail(vo);
    }
}
