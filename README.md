# HouseManage
房屋出租系统，毕设项目

使用技术为springboot+shiro+rabbitmq+nginx+redis+mybatis+tomcat+Linux+docker，
shiro是进行项目总体的权限控制，rabbitmq是进行一些异步操作的，redis是用来将一些不经常修改的热点数据保存起来方便下次获取，
mybatis进行数据库操作

项目整体是先打成一个war包nginx是放置在Linux上的docker的tomcat容器中，并且通过一个nginx镜像生成三个nginx容器，其中一个用于负载均衡，
另外两个用作加载静态资源的。

项目功能主要分为前台租客页面和后台管理员页面，租客的主要功能是登陆（谷歌图片验证码）、注册（MD5数据加密+阿里云大于手机短信验证）、查看、收藏、购买（支付宝沙箱操作模拟支付过程）房屋信息。
后台管理用户、房屋、订单信息
