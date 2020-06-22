package house.demo.component;

import house.demo.Mapper.BednumberMapper;
import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.bean.BedNumber;

public class BedbumberUtil {

    public static BedNumber getbednumber(HouseMapper houseMapper, UserMapper userMapper, BedNumber bedNumber) {
        if (bedNumber != null) {
            bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
            if (bedNumber.getUser() != null) {
                bedNumber.setUser(userMapper.getuserByid(bedNumber.getUser().getUid()));
            }
        }
        return bedNumber;
    }

    public static BedNumber getbednumberbybid(BednumberMapper bednumberMapper, HouseMapper houseMapper, UserMapper userMapper, String bid) {
        BedNumber bedNumber = bednumberMapper.getbedBybid(bid);
        if (bedNumber != null) {
            bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
            if (bedNumber.getUser() != null) {
                bedNumber.setUser(userMapper.getuserByid(bedNumber.getUser().getUid()));
            }
        }
        return bedNumber;
    }
}
