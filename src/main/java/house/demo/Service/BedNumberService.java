package house.demo.Service;

import house.demo.bean.BedNumber;

import java.util.List;
import java.util.Map;

public interface BedNumberService {

    public List<BedNumber> getusedetails(String id);

    public List<BedNumber> getnotdetails(String id);

    public List<BedNumber> getOrderDetailsList(String hid);

    public BedNumber getbedBybid(String bid);

    public BedNumber orderhouse(Map<String, Object> bid);


}
