package house.demo.Service;

import house.demo.Mapper.HouseMapper;
import house.demo.bean.BedNumber;
import house.demo.bean.Houses;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HouseService {

    public PageModel getsome(QueryVo vo);

    public Houses getHousesByid(String id);

    public List<BedNumber> getindex();
}
