package house.demo.bean;


import java.io.Serializable;

public class Houses implements Serializable {
    private String hid;
    private String livetype;
    private String images;
    private String location;
    private String roomtype;
    private int high;

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getLivetype() {
        return livetype;
    }

    public void setLivetype(String livetype) {
        this.livetype = livetype;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public Houses(String hid, String livetype, String images, String location, String roomtype, int high) {
        this.hid = hid;
        this.livetype = livetype;
        this.images = images;
        this.location = location;
        this.roomtype = roomtype;
        this.high = high;
    }

    public Houses() {
    }

    @Override
    public String toString() {
        return "Houses{" +
                "hid='" + hid + '\'' +
                ", livetype='" + livetype + '\'' +
                ", images='" + images + '\'' +
                ", location='" + location + '\'' +
                ", roomtype='" + roomtype + '\'' +
                '}';
    }
}
