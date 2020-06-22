package house.demo.Service.ServiceImpl;

import house.demo.Mapper.AdminOrderManageMapper;
import house.demo.Mapper.BednumberMapper;
import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.Service.AdminOrderManageService;
import house.demo.bean.BedNumber;
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

import java.util.List;

@Transactional
@Service
public class AdminOrderManageServiceImp implements AdminOrderManageService {

    @Autowired
    AdminOrderManageMapper adminOrderManageMapper;
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BednumberMapper bednumberMapper;

    @Cacheable(value = {"AdminCustomerHouseSearch"}, key = "#vo.toString()")
    @Override
    public PageModel searchOrder(QueryVo vo) {
        String bid = vo.getBid();
        String location = vo.getLocation();
        Integer livetype = vo.getLivetype();
        Integer much3 = vo.getMuch();
        Integer much = vo.getMuch();
        Integer state = vo.getState();
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

        Integer getsomecount = adminOrderManageMapper.searchbedcount(vo);
        PageModel pageModel = new PageModel(vo.getCurrentPage(), getsomecount, 8);

        vo.setPagesize(8);
        vo.setStartIndex(pageModel.getStartIndex());
        List<BedNumber> list = adminOrderManageMapper.searchbedlist(vo);

        if (list.size() == 0) {                            //在指定页没有找到合适的值跳到第一页找值,这里判断size是因为没有差到符合的值返回的是一个空的容器而不是null值
            pageModel.setCurrentPageNum(1);
            vo.setStartIndex(pageModel.getStartIndex());
            list = adminOrderManageMapper.searchbedlist(vo);
            if (list.size() == 0) {                        //如果第一页还是没有合适的值归为null
                list = null;
            }
        }
        if (list != null) {                                //防止查找为null报错
            for (BedNumber bedNumber : list) {
                if(bedNumber.getUser()!=null){
                    bedNumber.setUser(userMapper.getuserByid(bedNumber.getUser().getUid()));
                }
                bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
            }
        }
        pageModel.setList(list);
        pageModel.setUrl("adminfindbednumber&state=" + state + "&bid=" + bid + "&location=" + location + "&livetype=" + livetype + "&much=" + much3 + "&page=");
        return pageModel;
    }

    @Caching(
            put = {
                    @CachePut(value = {"BedNumber"}, key = "#bedNumber.bid"),
            },
            evict = {
                    @CacheEvict(value = {"HouseLiveBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(value = {"HouseEmptyBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(value = {"HouseOrderBedNumber"}, key = "#result.houses.hid"),
                    @CacheEvict(value = {"IndexBedNumber"}),
                    @CacheEvict(value = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(value = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(value = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public BedNumber updateOrder(BedNumber bedNumber) {
        adminOrderManageMapper.updatebednumber(bedNumber);
        return BedbumberUtil.getbednumberbybid(bednumberMapper, houseMapper, userMapper, bedNumber.getBid());
    }

    @Override
    public PageModel findOverTime(QueryVo vo) {
        Integer getsomecount = adminOrderManageMapper.searchbedOvertime(vo.getState());
        PageModel pageModel = new PageModel(vo.getCurrentPage(), getsomecount, 8);

        vo.setPagesize(8);
        vo.setStartIndex(pageModel.getStartIndex());
        List<BedNumber> list = adminOrderManageMapper.searchbedOvertimeList(vo);
        if (list.size() == 0) {                            //在指定页没有找到合适的值跳到第一页找值,这里判断size是因为没有差到符合的值返回的是一个空的容器而不是null值
            pageModel.setCurrentPageNum(1);
            vo.setStartIndex(pageModel.getStartIndex());
            list = adminOrderManageMapper.searchbedOvertimeList(vo);
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
        pageModel.setUrl("adminfindovertime&state=" + vo.getState()+"&page=" );
        return pageModel;
    }
    @Caching(
            evict = {
                    @CacheEvict(value = {"BedNumber"}, allEntries = true),
                    @CacheEvict(value = {"HouseLiveBedNumber"}, allEntries = true),
                    @CacheEvict(value = {"HouseEmptyBedNumber"}, allEntries = true),
                    @CacheEvict(value = {"HouseOrderBedNumber"},allEntries = true),
                    @CacheEvict(value = {"IndexBedNumber"}),
                    @CacheEvict(value = {"CustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(value = {"AdminCustomerHouseSearch"}, allEntries = true),
                    @CacheEvict(value = {"AdminHouseMessageSearch"}, allEntries = true),
            }
    )
    @Override
    public void clearOver(int state) {
        adminOrderManageMapper.clearOver(state);
    }
}
