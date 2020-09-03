package house.demo.Config.shiro;

import house.demo.Service.AdminUserManageService;
import house.demo.Service.LoginService;
import house.demo.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: lijincan
 * @date: 2020年02月26日 13:19
 * @Description: TODO
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    LoginService loginService;
    @Autowired
    AdminUserManageService adminUserManageService;
    /**
     * 用户每次访问需要授权（authc类型）的请求时都会执行这段代码，如果处于未登陆状态则会直接被拦截而去往指定的登录页面，不会执行这个方法
     * 在这段代码中可以用
     * principalCollection.getPrimaryPrincipal()通过输入的账号密码获取数据库中的对象，然后加以判断
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");

        HashSet<String> roles = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        User user = (User) principalCollection.getPrimaryPrincipal();     //输入的用户信息组成的用户对象
        User user1= loginService.login(user);
        User adminUser = adminUserManageService.getAdminUser(user);
        if(user1!=null){
            roles.add("user");
        }
        if(adminUser!=null){
            roles.add("admin");
        }


        info.setRoles(roles);
//        info.addStringPermission("user:add");
        //获取当前登录的对象
//        Subject subject = SecurityUtils.getSubject();

//        User currentUser = (User) subject.getPrincipal();
//        info.addStringPermission(currentUser.getPrams());

        return info;
    }

    /**
     * 用户认证的方法，在需要登陆验证时自动被调用（也就是登陆时会调用），
     * 主要作用是如果用户名密码正确就为容器添加用户信息，但是并没有为subject添加权限或授权，紧接着就会调用上面的zation方法来添加权限和授权，
     * 也就是下面只是添加用户信息到容器中，真正的授权是上面方法。
     * @param AuthenticationToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //加密userToken的密码
//        HashedCredentialsMatcher credentialsMatcher=new HashedCredentialsMatcher();
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        credentialsMatcher.setHashIterations(2);
//        this.setCredentialsMatcher(credentialsMatcher);
//        //对数据库中的密码进行加密，参数1：加密算法，参数2：需要加密的数据，参数3：盐值，参数4：加密次数
//        Object md5 = new SimpleHash("MD5", "123456", "", 3);

        //从token中取到用户名再去查用户密码
        System.out.println("用户名"+userToken.getUsername());
        System.out.println("用户名密码"+new String(userToken.getPassword()));
        User user1 = new User();
        user1.setUsername(userToken.getUsername());
        user1.setPassword(new String(userToken.getPassword()));
        User user = loginService.login(user1);
        if (user==null){
            user = adminUserManageService.getAdminUser(user1);
            if(user==null){
                return null;
            }
            return new SimpleAuthenticationInfo();
        }


        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        if(user.getType()==0){
            session.setAttribute("user",user);
        }else if(user.getType()==1){
            session.setAttribute("adminuser",user);
        }

        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }

    public static void main(String[] args) {
        //使用shiro对数据进行加密，不同的加密算法，加密次数，盐值都会造成加密的结果不同
        //对数据库中的密码进行加密，参数1：加密算法，参数2：需要加密的数据，参数3：盐值，参数4：加密次数
       Object md5 =new SimpleHash("MD5", "123456", "", 3);
        System.out.println(md5);
    }
}
