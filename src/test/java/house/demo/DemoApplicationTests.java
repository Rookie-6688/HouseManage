package house.demo;

import house.demo.Mapper.HouseMapper;
import house.demo.Mapper.UserMapper;
import house.demo.bean.BedNumber;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
@Ignore
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    HouseMapper houseMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;    //操作k-v都是字符串的

    @Autowired
    RedisTemplate redisTemplate;    //k-v都是对象的

    @Test
    void contextLoads() {
        List<BedNumber> list = userMapper.getpay("11");
        for (BedNumber bedNumber : list) {
            bedNumber.setHouses(houseMapper.gethouseByhid(bedNumber.getHouses().getHid()));
        }
        redisTemplate.opsForList().leftPushAll("list", list);
    }

}
