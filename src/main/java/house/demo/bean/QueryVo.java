package house.demo.bean;

import lombok.Data;

import java.io.Serializable;
//@AllArgsConstructor
@Data
public class QueryVo implements Serializable {
    private Integer StartIndex;
    private Integer Pagesize;            //单个页面存放的数据量
    private Integer CurrentPage;
    private Integer much;
    private Integer much2;
    private String roomtype;             //房屋规模
    private Integer high;
    private Integer high2;
    private Integer livetype;
    private String username;
    private String sex;
    private String order;
    private String job;
    private Integer state;
    private String uid;
    private String bid;
    private String cid;
    private String phone;
    private String name;
    private String hid;
    private String location;
    private BedNumber bedNumber;
    private Houses houses;
}
