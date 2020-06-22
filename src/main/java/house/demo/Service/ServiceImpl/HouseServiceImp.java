package house.demo.Service.ServiceImpl;

import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.Service.HouseService;
import house.demo.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HouseServiceImp implements HouseService {

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    @Cacheable(value = {"CustomerHouseSearch"}, key = "#vo.toString()")
    public PageModel getsome(QueryVo vo) {
        String roomtype = vo.getRoomtype();
        int much = vo.getMuch();
        int high = vo.getHigh();
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
        if (high == 1) {
            vo.setHigh2(1);
            vo.setHigh(5);
        } else if (high == 2) {
            vo.setHigh2(6);
            vo.setHigh(10);
        } else if (high == 3) {
            vo.setHigh2(11);
            vo.setHigh(15);
        } else if (high == 4) {
            vo.setHigh2(16);
            vo.setHigh(-1);
        }
        Integer getsomecount = houseMapper.getsomecount(vo);
        PageModel pageModel = new PageModel(vo.getCurrentPage(), getsomecount, 9);

        vo.setPagesize(9);
        vo.setStartIndex(pageModel.getStartIndex());
        List<BedNumber> list = houseMapper.getsome(vo);

        if (list.size() == 0) {                            //在指定页没有找到合适的值跳到第一页找值,这里判断size是因为没有差到符合的值返回的是一个空的容器而不是null值
            pageModel.setCurrentPageNum(1);
            vo.setStartIndex(pageModel.getStartIndex());
            list = houseMapper.getsome(vo);
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
        pageModel.setUrl("findhouse&roomtype=" + roomtype + "&high=" + high + "&much=" + much + "&livetype=" + vo.getLivetype() + "&order=" + vo.getOrder() + "&num=");
        return pageModel;
    }

    @Cacheable(value = {"House"}, key = "#id")
    public Houses getHousesByid(String id) {
        return houseMapper.gethouseByhid(id);
    }


    @Cacheable(value = {"IndexBedNumber"})
    @Override
    public List<BedNumber> getindex() {
        List<BedNumber> list = houseMapper.getindex();
        for (BedNumber bedNumber : list) {
            bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
        }
        return list;
    }
}
