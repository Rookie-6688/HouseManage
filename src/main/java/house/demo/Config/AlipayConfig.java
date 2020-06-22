package house.demo.Config;

import org.springframework.stereotype.Component;

@Component
public class AlipayConfig {
    // 作为身份标识的应用ID
    public static String app_id = "2016102400750284";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key  = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCYEkA72u4/q0DmpVZFeR0jQy/lGcwDjO+Ema54C70Sj917FUPFr3KfOp60FmtgXSuHfoCeWV8eUSs2/kn4x3fn6anhLnqTmrhkdXNx/9l6HCmn+G2eVdiD5gDbWRMx3iQDXmyaVeXWOUz55EhCIEMBBRXfXsC9CFT4XK0OgHDLo85ctBS0xYhHriKa6tPKWGdWppScD7o7qRmwb2j5EdcZnM9aBR75Rpi4bzxgoiIvQdI7bspUETS/SBGKb+n+sNb9x+wwyp2L4V/6zXNsirOQAzhWXKmc1LhAprEF8FnI58r2kVGrvr2WbLFBCZsopZb2DKAbbW7cAMCQIrbN5jnRAgMBAAECggEAUrn6/5TF7xT2pYJkQD72vuwZ7gPunC/bTMNdyih4ntRspDyZNb2QGVcGZumbWDzXJUqIJZxwxklZvPzwuMmqTRssMzfZrtqIXP4X43hPksInVx0qZOxGcNp6tNjsPECgTsTbR8pa0gMyAO9S78YlLTTbBTzClRMnRkUET9NHiT+kNozfs+kTN+03Tgw0Lu/e3fM7UfDjKPk7CNeGzpRH78IWbXF5sN4jQGCGjic6U/LKwyP1YtWYOtSxT6dGjlmsJXb+KqBNvdupmpSwvLqoSVzP5CpHGynl9h/gttlXLzwrJpYFOTcgTEfuGGH5wcJmi2sRK3++NClNt+MolAg/wQKBgQDxo4NeZfkgS+5zinYOnN7zeg00j79NvDl8+0Z3/HqtzZqJbDiiRSnPZ/frTNTG0OX9G4CvXzM3SvL74NspWfuFn6v3MJn1T9eD8W6ENjmIUgbYdb3FfKykbSCVY4/uEdWHNC8VyIwYjN8676BLjlZFYVk9dsHkZcOW8/Wga/yO9QKBgQChG/yJYX2vaHVxWIX8yi6ITHw96vdgn0k17NVrh1p7cRpxA/G3mIMB6FJF20Oo5TrIW7W/r7YwHfy0qlozdm9yok0300700iFM9KypStuSyP4szXKMqA/xXA4zmP+c2r+zRHUpxPNx+UP5WJAKq/GutqrZw5jjVCY6k4ZEJH+97QKBgQDCBiiC0OCkJTQ3hDNdcmq3GAaocNAMTBnhGCayS4H6MsBmngYvjdePmY0BW/TyG2TAwqemqYuHV2AaDagM4RrMZbSATH8i8dQ8Ns0lziCOLEcg4hHaFEzPzmw+Z+53G8zXvngcRqbvQ5YvTZ/pa0YseYldiBESIwBVIljVUkA+TQKBgCqyuR5L2TyYya9tj60shGKZKsBaOo/AB24kQ3fJGqDmm+Rh8DkBUY863DspXmgvwIGDVMrlrbpY6DWVhGxpMiZGf4KRHTbjTfIQUoZXkZP4cuy60mYW0IreI+Fm5i/TBcVkGk0kpoSJVIuL/jjl0xrDzMoD/cwZ7DvQB2Cd78ENAoGBAIOBDac1rl/Q6ptZMlhVqUovAnPZ+tTcvrgUSQiQiJscbaWrVvE+L+CdN7zZBnv6h791P9UM6WwsobehYy9KuWXhj0TxeUpOaHdF6Iz4t5Zd20Aah1MY7bEQPxvtEN4AARQ/YYw31rIiVW8x43ndI2U4v8aPtxkG/v/wYnwV3T5O";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAodcGY3b8QasK5xY1/Ta/7Kj+b1Bw5Rz6WJPnJyfriob4MjSYskrW0qUXPS4J6+emEpsxHl9kfZTvNU78QIc2IoVQd4FUJYNekNQDZQyFgPJ6+TpxB/E6n6Ns5zVAKJ8sZwhdZhABeHki55IBUjOB67k/snRn4ymo2qeVZEhfRNC0rXtzd1TL7sd2JWuHwAABQ1wHGmVgvcMhJB635M89lXy/dcH3gRE4i4lzTFXytaHlBtWRJTkQ5z4lndjyjn8JxgoGrvRlqeSJICkJ9Jaoj1pNH7ThZEKOW3lDQp2CtGdFXztmNaN+LUy7863XtwJje3qbEcnFUWeGkvn0zPDi5wIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://192.168.1.100:8080/House/overpay";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080/House/backHouse";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}