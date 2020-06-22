package house.demo.Service.ServiceImpl;

import house.demo.Mapper.BednumberMapper;
import house.demo.Mapper.CollectionMapper;
import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.Service.CollectionService;
import house.demo.bean.Collections;
import house.demo.bean.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CollectionServiceImp implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BednumberMapper bednumberMapper;
    @Autowired
    HouseMapper houseMapper;

    @Override
    public List<Collections> getCollectionsByuid(String uid) {
        List<Collections> list = collectionMapper.getCollectionByuid(uid);
        System.out.println(list);
        if (list.size() != 0) {
            for (Collections collections : list) {
                String bid = collections.getBedNumber().getBid();
                collections.setBedNumber(bednumberMapper.getbedBybid(bid));
                collections.getBedNumber().setHouses(houseMapper.gethouseByhid(bednumberMapper.gethidBybid(bid)));
            }
        } else {
            list = null;
        }
        return list;
    }

    @Override
    public Integer addCollection(QueryVo vo) {
        bednumberMapper.addCollect(vo.getBid());
        return collectionMapper.addCollection(vo);
    }


    @Override
    public Integer deleCollectionBycid(String cid) {
        String bid = collectionMapper.getbidBycid(cid);
        bednumberMapper.deleCollect(bid);
        return collectionMapper.deleCollectionBycid(cid);
    }

    @Override
    public Collections getoneCollectionBybid(QueryVo vo) {
        return collectionMapper.getoneCollectionBybid(vo);
    }

    @Override
    public Integer getCountBybid(String bid) {
        return collectionMapper.getCountByBid(bid);
    }

}
