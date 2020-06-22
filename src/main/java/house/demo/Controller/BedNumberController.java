package house.demo.Controller;

import house.demo.Service.BedNumberService;
import house.demo.Service.CollectionService;
import house.demo.Service.HouseService;
import house.demo.Service.LoginService;
import house.demo.bean.*;
import house.demo.bean.Collections;
import house.demo.component.AutoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class BedNumberController {
    @Autowired
    BedNumberService bedNumberService;
    @Autowired
    HouseService houseService;
    @Autowired
    CollectionService collectionService;
    @Autowired
    LoginService loginService;

    @RequestMapping("/getdetails&bid={bid}")
    public String getdetails(@PathVariable String bid, Model model, HttpSession session, HttpServletRequest request) {
        AutoLogin.AutoLogin(session, request, loginService);
        Integer count=collectionService.getCountBybid(bid);
        model.addAttribute("count",count);
        BedNumber bedNumber = bedNumberService.getbedBybid(bid);
        model.addAttribute("bednumber", bedNumber);
        String hid = bedNumber.getHouses().getHid();
        List<BedNumber> uselist = bedNumberService.getusedetails(hid);
        if (uselist.size() > 0) {
            model.addAttribute("uselist", uselist);
        }
        List<BedNumber> orderDetailsList = bedNumberService.getOrderDetailsList(hid);
        if (orderDetailsList.size() > 0) {
            model.addAttribute("orderList", orderDetailsList);
        }
        List<BedNumber> notlist = bedNumberService.getnotdetails(hid);
        Iterator<BedNumber> iterator = notlist.iterator();
        while (iterator.hasNext()) {                    //删除该房间中的自己信息
            if (iterator.next().getNumber() == bedNumber.getNumber()) {
                iterator.remove();
            }
        }
        if (notlist.size() > 0) {
            model.addAttribute("notlist", notlist);
        }

        QueryVo vo = new QueryVo();
        User user = (User) session.getAttribute("user");
        if (user != null) {                                    //未登陆状态
            vo.setUid(user.getUid());
            vo.setBid(bid);
            Collections collections = collectionService.getoneCollectionBybid(vo);
            if (collections != null) {
                model.addAttribute("iscollect", "iconfont iconicon_heart_outline__c");
            } else {
                model.addAttribute("iscollect", "iconfont iconicon_heart_outline__c_");
            }
        } else {
            model.addAttribute("iscollect", "iconfont iconicon_heart_outline__c_");
        }
        return "details";
    }

        @RequestMapping("/orderhouse")
        public @ResponseBody
        void orderhouse(String bid, HttpSession session) {
            Map<String, Object> map = new HashMap<>();
            Object user = (User) session.getAttribute("user");
            String hid = bedNumberService.getbedBybid(bid).getHouses().getHid();
            map.put("hid", hid);
            map.put("uid", ((User) user).getUid());
            map.put("bid", bid);
            map.put("latedate", new Date());
            bedNumberService.orderhouse(map);
        }

}
