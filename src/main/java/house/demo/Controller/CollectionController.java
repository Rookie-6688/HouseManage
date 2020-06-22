package house.demo.Controller;

import com.alibaba.druid.support.json.JSONUtils;
import house.demo.Service.CollectionService;
import house.demo.bean.Collections;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import house.demo.mq.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CollectionController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    CollectionService collectionService;

    @RequestMapping("/collect")
    public @ResponseBody
    void collect(String bid, HttpSession session) {
        QueryVo vo = new QueryVo();
        User user = (User) session.getAttribute("user");
        vo.setCid(UUID.randomUUID().toString().substring(0, 5));
        vo.setBid(bid);
        vo.setUid(user.getUid());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,RabbitMQConfig.BIND_KEY,vo);
    }

    //详情页删除
    @RequestMapping("/delecollect")
    public @ResponseBody
    void delecollectByAjax(String bid, HttpSession session) {
        QueryVo vo = new QueryVo();
        User user = (User) session.getAttribute("user");
        vo.setUid(user.getUid());
        vo.setBid(bid);
        Collections collections = collectionService.getoneCollectionBybid(vo);
        collectionService.deleCollectionBycid(collections.getCid());
    }

    //个人信息页面单条删除
    @RequestMapping("/deleusercollect")
    public @ResponseBody
    void delecollect(String cid, HttpSession session) {
        collectionService.deleCollectionBycid(cid);
    }

    @RequestMapping("/deleselect")        // ajax批量删除
    public @ResponseBody
    void deleselect(String data) {
        List<Map<String, String>> list = (List) JSONUtils.parse(data);        //data格式:[{"cid":"49276"},{"cid":"aee1c"}]
        for (Map map : list) {
            collectionService.deleCollectionBycid(map.get("cid") + "");
        }
    }
}
