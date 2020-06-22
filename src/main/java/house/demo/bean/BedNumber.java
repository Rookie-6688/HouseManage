package house.demo.bean;

import java.io.Serializable;
import java.util.Date;

public class BedNumber implements Serializable {
    private String bid;
    private Houses houses;
    private int number;
    private User user;
    private int state;
    private double price;
    private double area;
    private int sun;
    private int star;
    private Date latedate;

    public Date getLatedate() {
        return latedate;
    }

    public void setLatedate(Date latedate) {
        this.latedate = latedate;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Houses getHouses() {
        return houses;
    }

    public void setHouses(Houses houses) {
        this.houses = houses;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getSun() {
        return sun;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }


    public BedNumber() {
    }

    public BedNumber(String bid, Houses houses, int number, User user, int state, double price, double area, int sun, int star, Date latedate) {
        this.bid = bid;
        this.houses = houses;
        this.number = number;
        this.user = user;
        this.state = state;
        this.price = price;
        this.area = area;
        this.sun = sun;
        this.star = star;
        this.latedate = latedate;
    }

    @Override
    public String toString() {
        return "BedNumber{" +
                "bid='" + bid + '\'' +
                ", houses=" + houses +
                ", number=" + number +
                ", user=" + user +
                ", state=" + state +
                ", price=" + price +
                ", area=" + area +
                ", sun=" + sun +
                ", star=" + star +
                ", latedate=" + latedate +
                '}';
    }
}
