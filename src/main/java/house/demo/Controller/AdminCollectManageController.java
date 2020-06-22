package house.demo.Controller;

import com.alibaba.druid.support.json.JSONUtils;
import house.demo.Service.AdminCollectManageService;
import house.demo.Service.BedNumberService;
import house.demo.Service.CollectionService;
import house.demo.Service.UserService;
import house.demo.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class AdminCollectManageController {

    @Autowired
    AdminCollectManageService adminCollectManageService;
    @Autowired
    BedNumberService bedNumberService;
    @Autowired
    UserService userService;
    @Autowired
    CollectionService collectionService;

    //	@RequestMapping("/adminCollectManage&page={page}")
//	public String getcollect(@PathVariable int page, Model model){
//		QueryVo vo=new QueryVo();
//		vo.setCurrentPage(page);
//		vo.setPagesize(10);
//		PageModel pageModel = adminCollectManageService.getall(vo);
//		model.addAttribute("pagemodel",pageModel);
//		model.addAttribute("page",page);
//		return "admin/Collect/list";
//	}
    @RequestMapping("/adminsearchcollect&location={location}&name={name}&page={page}")
    public String AdminSearchCollect(@PathVariable String location, @PathVariable String name, @PathVariable int page, Model model) {
        QueryVo vo = new QueryVo();
        QueryVo vo2 = new QueryVo();
        vo.setLocation(location);
        vo.setName(name);
        vo.setPagesize(10);
        vo2.setLocation(location);
        vo2.setName(name);
        vo.setCurrentPage(page);
        PageModel pageModel = adminCollectManageService.AdminSearchCollect(vo);
        model.addAttribute("pagemodel", pageModel);
        model.addAttribute("vo", vo2);
        return "admin/Collect/list";
    }


    @RequestMapping("/adminsearchuser")
    public @ResponseBody
    String SearchUser(String uid) {
        User user = userService.getUserByid(uid);
        if (user != null) {
            return user.getUsername();
        } else {
            return null;
        }
    }

    @RequestMapping("/adminsearchuhouse")
    public @ResponseBody
    BedNumber AdminSearchuHouse(String bid) {
        BedNumber bedNumber = bedNumberService.getbedBybid(bid);
        if (bedNumber != null) {
            return bedNumber;
        } else {
            return null;
        }
    }

    @RequestMapping("/adminaddCollect")
    public String AddCollect(String uid, String bid, Model model) {
        QueryVo vo = new QueryVo();
        vo.setUid(uid);
        vo.setBid(bid);
        vo.setCid(UUID.randomUUID().toString().substring(0, 6));
        BedNumber bedNumber = bedNumberService.getbedBybid(bid);
        User user = userService.getUserByid(uid);
        if (bedNumber == null || user == null) {
            model.addAttribute("message", "房屋ID或用户ID不存在");
            model.addAttribute("uid", uid);
            model.addAttribute("bid", bid);
            return "admin/Collect/add";
        }
        int i = adminCollectManageService.addCollect(vo);
        return "forward:/adminsearchcollect&location=&name=&page="+1;
    }

    @RequestMapping("/admindeletecollect&cid={cid}")
    public String DeleCollect(@PathVariable String cid, Model model) {
        collectionService.deleCollectionBycid(cid);
        model.addAttribute("message", "删除成功");
        return "forward:/adminsearchcollect&location=&name=&page="+1;
    }

    @RequestMapping("/admineditcollect&cid={cid}&page={page}")
    public String EditCollect(@PathVariable String cid, @PathVariable int page, Model model) {
        Collections collections = adminCollectManageService.getcollectBycid(cid);
        model.addAttribute("collection", collections);
        model.addAttribute("page", page);
        return "admin/Collect/edit";
    }

    @RequestMapping("/adminupdateCollect")
    public String UpdateCollect(String uid, String bid, String cid, Model model, int page) {
        QueryVo vo = new QueryVo();
        vo.setBid(bid);
        vo.setUid(uid);
        vo.setCid(cid);
        int i = adminCollectManageService.updatecollect(vo);
        if (i > 0) {
            model.addAttribute("message", "修改成功");
            return "adminsearchcollect&location=&name=&page="+page;
        } else {
            model.addAttribute("message", "修改失败");
            return "adminsearchcollect&location=&name=&page="+page;
        }
    }

    @RequestMapping("/admindeleselect")        // ajax批量删除
    public @ResponseBody
    void deleselect(String data) {
        List<Map<String, String>> list = (List) JSONUtils.parse(data);        //data格式:[{"cid":"49276"},{"cid":"aee1c"}]
        for (Map map : list) {
            collectionService.deleCollectionBycid(map.get("cid") + "");
        }
    }
}
