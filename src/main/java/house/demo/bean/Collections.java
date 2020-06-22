package house.demo.bean;

import org.springframework.stereotype.Component;

public class Collections {
    private String cid;
    private User user;
    private BedNumber bedNumber;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BedNumber getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(BedNumber bedNumber) {
        this.bedNumber = bedNumber;
    }

    public Collections(String cid, User user, BedNumber bedNumber) {
        this.cid = cid;
        this.user = user;
        this.bedNumber = bedNumber;
    }

    public Collections() {
    }
}
