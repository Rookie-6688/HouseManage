package house.demo.Service.ServiceImpl;

import house.demo.Mapper.*;
import house.demo.Service.AdminCollectManageService;
import house.demo.bean.BedNumber;
import house.demo.bean.Collections;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AdminCollectManageServiceImp implements AdminCollectManageService {
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    AdminCollectManageMapper adminCollectManageMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BednumberMapper bednumberMapper;
    @Autowired
    HouseMapper houseMapper;
//	@Override
//	public PageModel getall(QueryVo vo) {
//		int i = adminCollectManageMapper.getallcount(vo);
//		PageModel pageModel=new PageModel(vo.getCurrentPage(),i,vo.getPagesize());
//		vo.setStartIndex(pageModel.getStartIndex());
//		List<Collections> list = adminCollectManageMapper.getall(vo);
//		for (Collections collections : list) {
//			collections.setBedNumber(bednumberMapper.getbedBybid(collections.getBedNumber().getBid()));
//			collections.getBedNumber().setHouses(houseMapper.gethouseByhid(collections.getBedNumber().getHouses().getHid()));
//			collections.setUser(userMapper.getuserByid(collections.getUser().getUid()));
//		}
//		pageModel.setList(list);
//		pageModel.setUrl("adminCollectManage&page=");
//		return pageModel;
//	}

    @Override
    public PageModel AdminSearchCollect(QueryVo vo) {
        String location = vo.getLocation();
        String name = vo.getName();
        int i = adminCollectManageMapper.SearchCollectCount(vo);
        PageModel pageModel = new PageModel(vo.getCurrentPage(), i, vo.getPagesize());
        vo.setStartIndex(pageModel.getStartIndex());
        List<Collections> list = adminCollectManageMapper.SearchCollectList(vo);
        if (list.size() == 0) {                                            //当前条件为数据为空则调回第一页，如果第一页也为空则设为null来使页面可以判断显示特定的信息
            pageModel.setCurrentPageNum(1);
            vo.setStartIndex(pageModel.getStartIndex());
            list = adminCollectManageMapper.SearchCollectList(vo);
            if (list.size() == 0) {
                list = null;
            }
        }
        if (list != null) {
            for (Collections collections : list) {
                collections.setBedNumber(bednumberMapper.getbedBybid(collections.getBedNumber().getBid()));
                collections.getBedNumber().setHouses(houseMapper.gethouseByhid(collections.getBedNumber().getHouses().getHid()));
                collections.setUser(userMapper.getuserByid(collections.getUser().getUid()));
            }
        }
        pageModel.setList(list);
        pageModel.setUrl("adminsearchcollect&location=" + location + "&name=" + name + "&page=");
        return pageModel;
    }

    @Override
    public int addCollect(QueryVo vo) {
        bednumberMapper.addCollect(vo.getBid());
        return collectionMapper.addCollection(vo);
    }

    @Override
    public int deleCollect(List<String> cid) {
        int i = 0, i2 = 0;
        for (String s : cid) {
            String bid = collectionMapper.getbidBycid(s);
            i = i + bednumberMapper.deleCollect(bid);
            i2 = i2 + collectionMapper.deleCollectionBycid(s);
        }
        return i + i2;
    }

    @Override
    public Collections getcollectBycid(String cid) {
        return adminCollectManageMapper.getCollectByCid(cid);
    }

    @Override
    public int updatecollect(QueryVo vo) {
        return adminCollectManageMapper.updatecollect(vo);
    }
}
