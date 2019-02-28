package com.xzbd.system.controller;

import com.xzbd.common.annotation.Log;
import com.xzbd.common.config.XwMailConfig;
import com.xzbd.common.controller.BaseController;
import com.xzbd.common.domain.FileDO;
import com.xzbd.common.domain.Tree;
import com.xzbd.common.service.FileService;
import com.xzbd.common.utils.MD5Utils;
import com.xzbd.common.utils.R;
import com.xzbd.common.utils.ShiroUtils;
import com.xzbd.system.domain.IndexPageConfigDO;
import com.xzbd.system.domain.MenuDO;
import com.xzbd.system.service.IndexPageConfigService;
import com.xzbd.system.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String INDEX_PAGE = "index";

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    XwMailConfig xwMailConfig;
    @Autowired
    private IndexPageConfigService indexPageConfigService;

    @GetMapping({"/", ""})
    String welcome(Model model) {

        return "redirect:/login";
    }

    @Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.get(getUser().getPicId());
        if (fileDO != null && fileDO.getUrl() != null) {
            if (fileService.isExist(fileDO.getUrl())) {
                model.addAttribute("picUrl", fileDO.getUrl());
            } else {
                model.addAttribute("picUrl", "/img/photo_s.jpg");
            }
        } else {
            model.addAttribute("picUrl", "/img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("username", xwMailConfig.getUsername());
        model.addAttribute("password", xwMailConfig.getPassword());
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password) {

        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    String main() {
        Long userId = ShiroUtils.getUserId();
        IndexPageConfigDO ipc = indexPageConfigService.findByUserId(userId);
        if (ipc != null) {
            String menuId = ipc.getIndexPagePath();
            MenuDO menuDO = menuService.get(Long.valueOf(menuId));
            if (menuDO != null && StringUtils.isNotBlank(menuDO.getUrl())) {
                return "redirect:" + menuDO.getUrl();
            } else {
                return INDEX_PAGE;
            }
        }
        return INDEX_PAGE;
    }

}
