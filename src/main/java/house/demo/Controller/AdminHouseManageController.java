package house.demo.Controller;

import house.demo.Service.AdminHouseManageService;
import house.demo.Service.BedNumberService;
import house.demo.Service.HouseService;
import house.demo.bean.BedNumber;
import house.demo.bean.Houses;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class AdminHouseManageController {

    @Autowired
    AdminHouseManageService adminHouseMangerService;
    @Autowired
    BedNumberService bedNumberService;
    @Autowired
    HouseService houseService;

    @RequestMapping("/adminhousesearch&hid={hid}&location={location}&state={state}&much={much}&page={page}")
    public String searchHouse(@PathVariable String hid, @PathVariable String location, @PathVariable Integer state, @PathVariable Integer much, @PathVariable Integer page, Model model) {
        QueryVo vo = new QueryVo();
        QueryVo vo2 = new QueryVo();
        vo.setHid(hid);
        vo.setLocation(location);
        vo.setState(state);
        vo.setMuch(much);
        vo.setCurrentPage(page);
        vo2.setHid(hid);
        vo2.setLocation(location);
        vo2.setState(state);
        vo2.setMuch(much);
        PageModel pageModel = adminHouseMangerService.searchbednumber(vo);
        model.addAttribute("pagemodel", pageModel);
        model.addAttribute("vo", vo2);
        return "admin/House/list";
    }

    @RequestMapping("/adminedithouse&bid={bid}")
    public String edit(@PathVariable String bid, Model model) {
        BedNumber bedNumber = bedNumberService.getbedBybid(bid);
        model.addAttribute("bednumber", bedNumber);
        return "admin/House/edit";
    }

    @RequestMapping("/adminHouse_update")
    public String updatehouse(BedNumber bedNumber, Houses houses, Model model, Integer page) {
        String roomtype = houses.getRoomtype();
        int number = bedNumber.getNumber();
        if (houses.getLivetype().equals("1")) {
            if ((roomtype.equals("一室一厅") && (number != 1)) || (roomtype.equals("两室一厅") && (number < 1 || number > 2)) || (roomtype.equals("三室一厅") && (number < 1 || number > 3)) || (roomtype.equals("四室一厅") && (number < 1 || number > 4))) {
                bedNumber.setHouses(houses);
                model.addAttribute("message", "请检查房间号!");
                model.addAttribute("house", houses);
                model.addAttribute("bednumber", bedNumber);
                return "forward:/adminedithouse&bid=" + bedNumber.getBid();
            }
        } else {
            bedNumber.setNumber(0);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("hid", houses.getHid());
        map.put("number", bedNumber.getNumber());
        map.put("bid", bedNumber.getBid());
        int samebed = adminHouseMangerService.getSameBed(map);
        if (samebed != 0 && houses.getLivetype().equals("1")) {                                        //修改后的房间号在当前房屋里已经存在时返回提示语句
            model.addAttribute("message", "同一个房屋只能有唯一的房间号!");
            bedNumber.setHouses(houses);
            model.addAttribute("bednumber", bedNumber);
            return "forward:/adminedithouse&bid=" + bedNumber.getBid();
        }
//		else if(samebed!=0&&houses.getLivetype().equals("0")){
//			model.addAttribute("message", "未改变数据，请修改要修改的数据!");
//			model.addAttribute("house",houses);
//			model.addAttribute("bednumber",bedNumber);
//			return "forward:/adminedithouse&bid="+bedNumber.getBid()+"&page="+page;
//		}
        QueryVo vo = new QueryVo();
        adminHouseMangerService.update(bedNumber, houses);


        model.addAttribute("message", "修改成功!");
        model.addAttribute("house", houses);
        model.addAttribute("bednumber", bedNumber);
        return "forward:/adminhousesearch&hid=&location=&state=&much=-1&page=1";
    }

    @RequestMapping("/adminclearhouse&bid={bid}")
    public String clearhouse(@PathVariable String bid, Model model) {
        adminHouseMangerService.dele(bid);
        model.addAttribute("message", "删除成功");
        return "forward:/adminhousesearch&hid=&location=&state=&much=-1&page=1";
    }

    @RequestMapping("/adminaddbed")
    public String addbed(Houses houses, BedNumber bedNumber, Model model) {
        boolean b = true;
        Houses getsamehouse = adminHouseMangerService.getsamehouse(houses.getLocation());
        String hid = null;

        //判断是不是同一个房屋的
        if (getsamehouse != null) {
            if (getsamehouse.getLivetype().equals("0")) {
                model.addAttribute("message", "该房屋为整租，请检查位置是否正确");
                model.addAttribute("house", houses);
                model.addAttribute("bednumber", bedNumber);
                return "admin/House/add";
            }
            if (getsamehouse.getRoomtype().equals(houses.getRoomtype()) && getsamehouse.getHigh() == houses.getHigh() && (houses.getLivetype().equals("1")) && getsamehouse.getImages().equals(houses.getImages())) {
                hid = getsamehouse.getHid();
                b = false;
            } else {
                model.addAttribute("message", "添加错误，请检查房屋信息是否正确!");
                model.addAttribute("house", houses);
                model.addAttribute("bednumber", bedNumber);
                return "admin/House/add";
            }
        } else {
            hid = UUID.randomUUID().toString().substring(0, 6);
        }

        houses.setHid(hid);
        //设置bednumber里的houses属性
        Houses houses1 = new Houses();
        houses1.setHid(hid);
        bedNumber.setHouses(houses1);
        bedNumber.setBid(UUID.randomUUID().toString().substring(0, 6));
        int i = 0;
        if (b == false) {
            i = adminHouseMangerService.addonehouse(bedNumber, houses);
        } else {
            i = adminHouseMangerService.addtwohouse(bedNumber, houses);
        }
        if (i == -1) {
            model.addAttribute("message", "超出该房屋最大规模");
            model.addAttribute("house", houses);
            model.addAttribute("bednumber", bedNumber);
            return "admin/House/add";
        }
        if (i == -2) {
            model.addAttribute("message", "房间号不在范围内，请检查");
            model.addAttribute("house", houses);
            model.addAttribute("bednumber", bedNumber);
            return "admin/House/add";
        }
        model.addAttribute("message", "添加成功");
        return "forward:/adminhousesearch&hid=&location=&state=&much=-1&page=1";
    }
}
