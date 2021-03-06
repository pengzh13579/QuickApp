package cn.pzh.system.web.project;

import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.common.utils.Convert;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.business.sys.service.MenuService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
@EnableTransactionManagement
@MapperScan (basePackages = "cn.pzh.system.web.project.dao.**.mapper.**")
public class Application {

    @Autowired
    private MenuService menuService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 登录
     * @return 登录
     */
    @RequestMapping ("/login")
    public String login() {
        return ViewConstants.LOGIN;
    }

    @RequestMapping ("/index")
    public String index(Model model) {
        model.addAttribute("menuInfo",menuService.listIndexMenus(Convert.toIntArray(ShiroKit.getUser().getRoleId())));
        model.addAttribute("userInfo", ShiroKit.getUser());
        return ViewConstants.INDEX;
    }

}
