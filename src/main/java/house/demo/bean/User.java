package house.demo.bean;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private String uid;
    private String username;
    private String password;
    private String phone;
    private Integer type;
    private String job;
    private String sex;
    private String star;
    private String name;

}
