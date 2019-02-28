package com.xzbd.mail.controller;

import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;
import com.xzbd.mail.domain.BiEmailTextContentConfigDO;
import com.xzbd.mail.service.BiEmailTextContentConfigService;
import com.xzbd.system.domain.UserDO;
import com.xzbd.system.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 邮件文本内容配置
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */

@Controller
@RequestMapping("/mail/biEmailTextContentConfig")
public class BiEmailTextContentConfigController {
    @Autowired
    private BiEmailTextContentConfigService biEmailTextContentConfigService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("mail:biEmailTextContentConfig:biEmailTextContentConfig")
    String BiEmailTextContentConfig() {
        return "mail/biEmailTextContentConfig/biEmailTextContentConfig";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mail:biEmailTextContentConfig:biEmailTextContentConfig")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        params.put("del", 0);
        Query query = new Query(params);
        List<BiEmailTextContentConfigDO> biEmailTextContentConfigList = biEmailTextContentConfigService.list(query);
        if (CollectionUtils.isNotEmpty(biEmailTextContentConfigList))
            for (BiEmailTextContentConfigDO obj : biEmailTextContentConfigList) {
                Long createManID = obj.getCreateMan();
                Long updateManID = obj.getLastOptionMan();
                UserDO creatMan = userService.get(createManID);
                UserDO updateMan = userService.get(updateManID);
                if (Objects.nonNull(creatMan))
                    obj.setCreateManStr(creatMan.getName());
                if (Objects.nonNull(updateMan))
                    obj.setLastOptionManStr(updateMan.getName());
            }
        int total = biEmailTextContentConfigService.count(query);
        PageUtils pageUtils = new PageUtils(biEmailTextContentConfigList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("mail:biEmailTextContentConfig:add")
    String add() {
        return "mail/biEmailTextContentConfig/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("mail:biEmailTextContentConfig:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        BiEmailTextContentConfigDO biEmailTextContentConfig = biEmailTextContentConfigService.get(id);
        model.addAttribute("biEmailTextContentConfig", biEmailTextContentConfig);
        return "mail/biEmailTextContentConfig/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mail:biEmailTextContentConfig:add")
    public R save(BiEmailTextContentConfigDO biEmailTextContentConfig) {
        if (biEmailTextContentConfigService.save(biEmailTextContentConfig) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("mail:biEmailTextContentConfig:edit")
    public R update(BiEmailTextContentConfigDO biEmailTextContentConfig) {
        Long id = biEmailTextContentConfig.getId();
        if (null == id)
            return R.error("该记录不存在");
        int update = biEmailTextContentConfigService.update(biEmailTextContentConfig);
        if (update > 0)
            return R.ok();
        return R.error("更新失败");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailTextContentConfig:remove")
    public R remove(Long id) {
        if (null == id)
            return R.error("该记录不存在");
        BiEmailTextContentConfigDO biEmailReceiverGroupConfig = biEmailTextContentConfigService.get(id);
        if (Objects.isNull(biEmailReceiverGroupConfig))
            return R.error("该记录不存在");
        biEmailReceiverGroupConfig.setDel(1);
        int update = biEmailTextContentConfigService.update(biEmailReceiverGroupConfig);
        if (update > 0)
            return R.ok();
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailTextContentConfig:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        biEmailTextContentConfigService.batchRemove(ids);
        return R.ok();
    }

}
