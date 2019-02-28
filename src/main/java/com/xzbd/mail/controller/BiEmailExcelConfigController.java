package com.xzbd.mail.controller;

import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;
import com.xzbd.common.utils.ShiroUtils;
import com.xzbd.mail.domain.BiEmailExcelConfigDO;
import com.xzbd.mail.domain.BiEmailExcelSheetConfigDO;
import com.xzbd.mail.service.BiEmailExcelConfigService;
import com.xzbd.mail.service.BiEmailExcelSheetConfigService;
import com.xzbd.system.domain.UserDO;
import com.xzbd.system.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 邮件Excel内容配置
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */

@Controller
@RequestMapping("/mail/biEmailExcelConfig")
public class BiEmailExcelConfigController {
    @Autowired
    private BiEmailExcelConfigService biEmailExcelConfigService;
    @Autowired
    private BiEmailExcelSheetConfigService biEmailExcelSheetConfigService;
    @Autowired
    private UserService userService;
    @GetMapping()
    @RequiresPermissions("mail:biEmailExcelConfig:biEmailExcelConfig")
    String BiEmailExcelConfig() {
        return "mail/biEmailExcelConfig/biEmailExcelConfig";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mail:biEmailExcelConfig:biEmailExcelConfig")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("del",0);
        List<BiEmailExcelConfigDO> biEmailExcelConfigList = biEmailExcelConfigService.list(query);
        if (CollectionUtils.isNotEmpty(biEmailExcelConfigList))
            for (BiEmailExcelConfigDO obj : biEmailExcelConfigList) {
                Long createManID = obj.getCreateMan();
                Long updateManID = obj.getLastOptionMan();
                UserDO creatMan = userService.get(createManID);
                UserDO updateMan = userService.get(updateManID);
                if(Objects.nonNull(creatMan))
                    obj.setCreateManStr(creatMan.getName());
                if(Objects.nonNull(updateMan))
                    obj.setLastOptionManStr(updateMan.getName());
            }
        int total = biEmailExcelConfigService.count(query);
        PageUtils pageUtils = new PageUtils(biEmailExcelConfigList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("mail:biEmailExcelConfig:add")
    String add() {
        return "mail/biEmailExcelConfig/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("mail:biEmailExcelConfig:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        BiEmailExcelConfigDO biEmailExcelConfig = biEmailExcelConfigService.get(id);
        model.addAttribute("biEmailExcelConfig", biEmailExcelConfig);
        return "mail/biEmailExcelConfig/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mail:biEmailExcelConfig:add")
    public R save(BiEmailExcelConfigDO biEmailExcelConfig) {

        // 判断是否唯一
        Map<String, Object> query = builtQueryMap(biEmailExcelConfig);
        List<BiEmailExcelConfigDO> olds = biEmailExcelConfigService.list(query);
        if (CollectionUtils.isNotEmpty(olds) && olds.size() > 0)
            return R.error("Excel名称已存在！");
        if (biEmailExcelConfigService.save(biEmailExcelConfig) > 0) {
            // 映射 sheet
            Long id = biEmailExcelConfig.getId();
            List<Long> sheetIds = biEmailExcelConfig.getSheets();
            if (CollectionUtils.isNotEmpty(sheetIds)) {
                Long userId = ShiroUtils.getUserId();
                Date time = new Date();
                List<BiEmailExcelSheetConfigDO> esList = new ArrayList<>();
                sheetIds.stream().forEach(item -> {
                    BiEmailExcelSheetConfigDO obj = new BiEmailExcelSheetConfigDO();
                    obj.setExcelId(id);
                    obj.setSheetId(item);
                    obj.setCreateMan(userId);
                    obj.setCreateTime(time);
                    obj.setDel(0);
                    esList.add(obj);
                });
                biEmailExcelSheetConfigService.batchInsert(esList);
            }
            return R.ok();
        }
        return R.error();
    }

    private Map<String, Object> builtQueryMap(BiEmailExcelConfigDO obj) {
        Map<String, Object> query = new HashMap<>();
        if (Objects.nonNull(obj)) {
//            query.put("id", obj.getId());
            query.put("name", obj.getName());
            query.put("del", 0);
        }
        return query;
    }

    /**
     * 修改
     */
    @ResponseBody
    @GetMapping(value = "/update")
    @RequiresPermissions("mail:biEmailExcelConfig:edit")
    public R update(BiEmailExcelConfigDO biEmailExcelConfig) {
        Long id = biEmailExcelConfig.getId();
        if (null == id)
            return R.error("该记录不存在");
        Map<String, Object> query = builtQueryMap(biEmailExcelConfig);
        List<BiEmailExcelConfigDO> olds = biEmailExcelConfigService.list(query);
        if (CollectionUtils.isNotEmpty(olds) && olds.size() > 0)
            if ((olds.size() == 1 && !olds.get(0).getId().equals(id)) || olds.size() > 1)
                return R.error("Excel名称已存在！");
        int update = biEmailExcelConfigService.update(biEmailExcelConfig);
        if (update > 0) {
            // 删除原来的sheet影射
            biEmailExcelSheetConfigService.removeByExcelId(id);
            // 设置新的影射
            List<Long> sheetIds = biEmailExcelConfig.getSheets();
            if (CollectionUtils.isNotEmpty(sheetIds)) {
                Long userId = ShiroUtils.getUserId();
                Date time = new Date();
                List<BiEmailExcelSheetConfigDO> esList = new ArrayList<>();
                sheetIds.stream().forEach(item -> {
                    BiEmailExcelSheetConfigDO obj = new BiEmailExcelSheetConfigDO();
                    obj.setExcelId(id);
                    obj.setSheetId(item);
                    obj.setCreateMan(userId);
                    obj.setCreateTime(time);
                    obj.setDel(0);
                    esList.add(obj);
                });
                biEmailExcelSheetConfigService.batchInsert(esList);
            }
            return R.ok();
        }
        return R.error("更新失败");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailExcelConfig:remove")
    public R remove(Long id) {
        if (null == id)
            return R.error("该记录不存在");
        BiEmailExcelConfigDO biEmailReceiverGroupConfig = biEmailExcelConfigService.get(id);
        if (Objects.isNull(biEmailReceiverGroupConfig))
            return R.error("该记录不存在");
        biEmailReceiverGroupConfig.setDel(1);
        int update = biEmailExcelConfigService.update(biEmailReceiverGroupConfig);
        if (update > 0) {
            biEmailExcelSheetConfigService.removeByExcelId(id);
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailExcelConfig:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        biEmailExcelConfigService.batchRemove(ids);
        return R.ok();
    }

}
