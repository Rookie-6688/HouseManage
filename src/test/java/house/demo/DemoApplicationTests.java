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

import java.util.ArrayList;
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
    @Test
    public void tt(){
        System.out.println(10^(0+1));
    }


    public List<String> getCombination(int num){
        List<String> combinationList=new ArrayList<>();

        List<Integer> list=new ArrayList<>();
        for(int i=0;i<=num;i++){
            list.add(i);
        }

        for(int m1=0;m1<list.size()-1;m1++) {
            Integer integer1 = list.get(m1);
            for (int m2 = m1 + 1; m2 < list.size(); m2++) {
                Integer integer2 = list.get(m2);
                combinationList.add(integer1 + "" + integer2);
            }
        }

        if(num>3){              //长度大于3就要有三位数结尾
            for(int m1=0;m1<list.size()-2;m1++){
                Integer integer1 = list.get(m1);
                for(int m2=m1+1;m2<list.size()-1;m2++){
                    Integer integer2 = list.get(m2);
                    for(int m3=m1+2;m3<list.size();m3++){
                        Integer integer3 = list.get(m3);
                        combinationList.add(""+integer1+integer2+integer3);
                    }
                }
            }
        }

        return combinationList;
    }
    @Test
    public void test(){
        List<String> combination = getCombination(3);
        System.out.println(combination);
    }

}
