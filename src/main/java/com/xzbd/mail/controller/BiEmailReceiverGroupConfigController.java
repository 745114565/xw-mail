package com.xzbd.mail.controller;

import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;
import com.xzbd.mail.domain.BiEmailReceiverGroupConfigDO;
import com.xzbd.mail.service.BiEmailReceiverGroupConfigService;
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
 * 邮件收件人组配置
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */

@Controller
@RequestMapping("/mail/biEmailReceiverGroupConfig")
public class BiEmailReceiverGroupConfigController {
    @Autowired
    private BiEmailReceiverGroupConfigService biEmailReceiverGroupConfigService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:biEmailReceiverGroupConfig")
    String BiEmailReceiverGroupConfig() {
        return "mail/biEmailReceiverGroupConfig/biEmailReceiverGroupConfig";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:biEmailReceiverGroupConfig")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("del", 0);
        List<BiEmailReceiverGroupConfigDO> biEmailReceiverGroupConfigList = biEmailReceiverGroupConfigService.list(query);
        if (CollectionUtils.isNotEmpty(biEmailReceiverGroupConfigList))
            for (BiEmailReceiverGroupConfigDO obj : biEmailReceiverGroupConfigList) {
                Long createManID = obj.getCreateMan();
                Long updateManID = obj.getLastOptionMan();
                UserDO creatMan = userService.get(createManID);
                UserDO updateMan = userService.get(updateManID);
                if(Objects.nonNull(creatMan))
                    obj.setCreateManStr(creatMan.getName());
                if(Objects.nonNull(updateMan))
                    obj.setLastOptionManStr(updateMan.getName());
            }
        int total = biEmailReceiverGroupConfigService.count(query);
        PageUtils pageUtils = new PageUtils(biEmailReceiverGroupConfigList, total);
        return pageUtils;
    }

    @ResponseBody
    @GetMapping("/listAll")
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:biEmailReceiverGroupConfig")
    public R listAll(@RequestParam Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();
        //查询列表数据
        params.put("del", 0);
        List<BiEmailReceiverGroupConfigDO> biEmailReceiverGroupConfigList = biEmailReceiverGroupConfigService.list(params);
        map.put("groups",biEmailReceiverGroupConfigList);
        return R.ok(map);
    }

    @GetMapping("/add")
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:add")
    String add() {
        return "mail/biEmailReceiverGroupConfig/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig = biEmailReceiverGroupConfigService.get(id);
        model.addAttribute("biEmailReceiverGroupConfig", biEmailReceiverGroupConfig);
        return "mail/biEmailReceiverGroupConfig/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:add")
    public R save(BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig) {
        Map<String, Object> query = new HashMap<>();
        query.put("name", biEmailReceiverGroupConfig.getName());
        query.put("del", 0);
        List<BiEmailReceiverGroupConfigDO> list = biEmailReceiverGroupConfigService.list(query);
        if (CollectionUtils.isNotEmpty(list))
            return R.error("收件组名称已经存在");
        if (biEmailReceiverGroupConfigService.save(biEmailReceiverGroupConfig) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:edit")
    public R update(BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig) {
        Long id = biEmailReceiverGroupConfig.getId();
        if (null == id)
            return R.error("该记录不存在");
        BiEmailReceiverGroupConfigDO old = biEmailReceiverGroupConfigService.get(id);
        if(Objects.isNull(old))
            return R.error("该记录不存在");
        Map<String, Object> query = new HashMap<>();
        query.put("name", biEmailReceiverGroupConfig.getName());
        query.put("del", 0);
        List<BiEmailReceiverGroupConfigDO> list = biEmailReceiverGroupConfigService.list(query);
        if (CollectionUtils.isNotEmpty(list))
            if(list.size()==1 && (!list.get(0).getId().equals(id)) || list.size() > 1)
            return R.error("收件组名称已经存在");

        old.setName(biEmailReceiverGroupConfig.getName());
        old.setGroupDesc(biEmailReceiverGroupConfig.getGroupDesc());
        old.setGroupElement(biEmailReceiverGroupConfig.getGroupElement());
        int update = biEmailReceiverGroupConfigService.update(old);
        if (update > 0)
            return R.ok();
        return R.error("更新失败");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:remove")
    public R remove(Long id) {
        if (null == id)
            return R.error("该记录不存在");
        BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig = biEmailReceiverGroupConfigService.get(id);
        if(Objects.isNull(biEmailReceiverGroupConfig))
            return R.error("该记录不存在");
        biEmailReceiverGroupConfig.setDel(1);
        int update = biEmailReceiverGroupConfigService.update(biEmailReceiverGroupConfig);
        if (update > 0)
            return R.ok();
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailReceiverGroupConfig:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        biEmailReceiverGroupConfigService.batchRemove(ids);
        return R.ok();
    }

}
