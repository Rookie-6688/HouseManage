package house.demo.Controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import house.demo.Config.AlipayConfig;
import house.demo.Service.BedNumberService;
import house.demo.Service.UserService;
import house.demo.bean.BedNumber;
import house.demo.bean.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Controller
public class PayController {
    @Autowired
    BedNumberService bedNumberService;
    @Autowired
    UserService userService;
    @RequestMapping("/pay/{bid}")
    public  void payMent(HttpServletResponse response, HttpServletRequest request,@PathVariable("bid")String bid) throws IOException {
        double price = bedNumberService.getbedBybid(bid).getPrice();
        System.out.println(bid+"==============");
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(AlipayConfig.return_url);
        aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填
        String order_number = new String(UUID.randomUUID().toString().substring(0,9));
        //付款金额，从前台获取，必填
        String total_amount = new String(""+price);
        //订单名称，必填
        String subject = new String("房屋订购");
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + UUID.randomUUID().toString().substring(0,6)+bid + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(aliPayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //输出
        out.println(result);
    }
    @RequestMapping("/overpay")
    public @ResponseBody
    void overpay(HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        String out_trade_no = request.getParameter("out_trade_no");
        String bid=out_trade_no.substring(6,out_trade_no.length());
        System.out.println("bid========================"+bid);
        userService.pay(bid);
    }

    @RequestMapping("/backHouse")
    public String backHouse(HttpSession session, Model model,HttpServletRequest request){
        String uid = ((User) session.getAttribute("user")).getUid();
        String out_trade_no = request.getParameter("out_trade_no");
        String bid=out_trade_no.substring(6,out_trade_no.length());
        userService.pay(bid);
        List<BedNumber> orderlist = userService.getorder(uid);
        List<BedNumber> paylist = userService.getpay(uid);
        model.addAttribute("orderlist", orderlist);
        model.addAttribute("paylist", paylist);
        return "/MySpace/MyOrder";
    }
}
