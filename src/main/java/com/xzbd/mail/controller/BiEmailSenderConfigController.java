package com.xzbd.mail.controller;

import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;
import com.xzbd.mail.domain.BiEmailSenderConfigDO;
import com.xzbd.mail.service.BiEmailSenderConfigService;
import com.xzbd.system.domain.UserDO;
import com.xzbd.system.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 邮件发送者邮箱配置
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */

@Controller
@RequestMapping("/mail/biEmailSenderConfig")
public class BiEmailSenderConfigController {
    @Autowired
    private BiEmailSenderConfigService biEmailSenderConfigService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("mail:biEmailSenderConfig:biEmailSenderConfig")
    String BiEmailSenderConfig() {
        return "mail/biEmailSenderConfig/biEmailSenderConfig";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mail:biEmailSenderConfig:biEmailSenderConfig")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("del", 0);
        List<BiEmailSenderConfigDO> biEmailSenderConfigList = biEmailSenderConfigService.list(query);
        if (CollectionUtils.isNotEmpty(biEmailSenderConfigList))
            for (BiEmailSenderConfigDO sender : biEmailSenderConfigList) {
                Long createManID = sender.getCreateMan();
                Long updateManID = sender.getLastOptionMan();
                UserDO creatMan = userService.get(createManID);
                UserDO updateMan = userService.get(updateManID);
                if(Objects.nonNull(creatMan))
                    sender.setCreateManStr(creatMan.getName());
                if(Objects.nonNull(updateMan))
                    sender.setLastOptionManStr(updateMan.getName());
            }
        int total = biEmailSenderConfigService.count(query);
        PageUtils pageUtils = new PageUtils(biEmailSenderConfigList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("mail:biEmailSenderConfig:add")
    String add() {
        return "mail/biEmailSenderConfig/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("mail:biEmailSenderConfig:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        BiEmailSenderConfigDO biEmailSenderConfig = biEmailSenderConfigService.get(id);
        model.addAttribute("biEmailSenderConfig", biEmailSenderConfig);
        return "mail/biEmailSenderConfig/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mail:biEmailSenderConfig:add")
    public R save(BiEmailSenderConfigDO biEmailSenderConfig) {
        Map<String, Object> query = new HashMap<>();
        query.put("emailName", biEmailSenderConfig.getEmailName());
        query.put("del", 0);
        List<BiEmailSenderConfigDO> list = biEmailSenderConfigService.list(query);
        if (CollectionUtils.isNotEmpty(list))
            return R.error("邮箱名称已经存在");
        if (biEmailSenderConfigService.save(biEmailSenderConfig) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("mail:biEmailSenderConfig:edit")
    public R update(BiEmailSenderConfigDO biEmailSenderConfig) {

        if (biEmailSenderConfig.getId() == null)
            return R.error("该记录不存在");
        Map<String, Object> query = new HashMap<>();
        query.put("emailName", biEmailSenderConfig.getEmailName());
        query.put("del", 0);
        List<BiEmailSenderConfigDO> list = biEmailSenderConfigService.list(query);
        if (CollectionUtils.isNotEmpty(list) && list.size() > 0)
            if ((list.size() == 1 && (!list.get(0).getId().equals(biEmailSenderConfig.getId()))) || list.size() > 1)
                return R.error("邮箱名称已经存在");

        int update = biEmailSenderConfigService.update(biEmailSenderConfig);
        if (update > 0)
            return R.ok();
        else
            return R.error("修改失败");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailSenderConfig:remove")
    public R remove(Long id) {
        if (Objects.isNull(id))
            return R.error("该记录不存在");
        BiEmailSenderConfigDO biEmailSenderConfig = biEmailSenderConfigService.get(id);
        if (Objects.isNull(biEmailSenderConfig))
            return R.error("该记录不存在");
        biEmailSenderConfig.setDel(1);
        if (biEmailSenderConfigService.update(biEmailSenderConfig) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailSenderConfig:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        biEmailSenderConfigService.batchRemove(ids);
        return R.ok();
    }

}
