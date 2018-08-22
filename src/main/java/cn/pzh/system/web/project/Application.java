package cn.pzh.system.web.project;

import cn.pzh.system.web.project.sys.service.MenuService;
import cn.pzh.system.web.project.sys.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement
@MapperScan (basePackages = "cn.pzh.system.web.project.**.dao.mapper")
public class Application {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 首页
     *
     * @return 首页
     */
    @RequestMapping ("/index")
    public String userPage(HttpServletRequest request) {
        request.setAttribute("userName", SecurityUtils.getSubject().getPrincipal().toString());
        request.setAttribute("parentMenu", menuService.getMenuList());
        return "index";
    }

    /**
     * 登录
     *
     * @return 登录
     */
    @RequestMapping ("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录
     *
     * @return 登录
     */
    @RequestMapping ("/loginPage")
    public String loginPage() {
        return "login";
    }

    /**
     * 退出
     *
     * @return 退出
     */
    @RequestMapping ("/logout")
    public String logout() {
        String userName = SecurityUtils.getSubject().getPrincipal().toString();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        // 修改用户表在线状态
        userService.updateOnlineStatus(userName);
        return "login";
    }

}
