package com.xzbd.mail.controller;

import java.util.*;

import com.xzbd.common.service.GeneratorService;
import com.xzbd.mail.domain.BiEmailExcelSheetConfigDO;
import com.xzbd.mail.service.BiEmailExcelCommonService;
import com.xzbd.mail.service.BiEmailExcelSheetConfigService;
import com.xzbd.system.domain.UserDO;
import com.xzbd.system.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzbd.mail.domain.BiEmailSheetConfigDO;
import com.xzbd.mail.service.BiEmailSheetConfigService;
import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;

/**
 * 邮件Excel中sheet配置
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */

@Controller
@RequestMapping("/mail/biEmailSheetConfig")
public class BiEmailSheetConfigController {
    @Autowired
    private BiEmailSheetConfigService biEmailSheetConfigService;
    @Autowired
    private BiEmailExcelSheetConfigService biEmailExcelSheetConfigService;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private BiEmailExcelCommonService biEmailExcelCommonService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("geneSheetConfigs")
    public R geneSheetConfigs(String tableName) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> table = generatorService.getTableInfo(tableName);
        List<Map<String, String>> columns = generatorService.listColumns(tableName);

        BiEmailSheetConfigDO sheetConfig = new BiEmailSheetConfigDO();
        sheetConfig.setSheetName(table.get("tableComment"));
        List<String> sheetTitleList = new ArrayList<>();
        List<String> sheetColumnList = new ArrayList<>();

        for (Map<String, String> col : columns) {
            String colComment = col.get("columnComment");
            String colName = col.get("columnName");
            if (StringUtils.isBlank(colComment))
                colComment = colName;

            StringUtils.replace(colComment, ",", " ");
            StringUtils.replace(colName, ",", " ");

            sheetTitleList.add(colComment);
            sheetColumnList.add(colName);
        }
        String sheetTitle = StringUtils.join(sheetTitleList, ",");
        String sheetColumn = StringUtils.join(sheetColumnList, ",");
        String sql = "select " + sheetColumn +
                "  from  " + tableName;

        sheetConfig.setTitles(sheetTitle);
        sheetConfig.setColoums(sheetColumn);
        sheetConfig.setQSql(sql);
        map.put("sheet", sheetConfig);
        return R.ok(map);
    }

    @GetMapping()
    @RequiresPermissions("mail:biEmailSheetConfig:biEmailSheetConfig")
    String BiEmailSheetConfig() {
        return "mail/biEmailSheetConfig/biEmailSheetConfig";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mail:biEmailSheetConfig:biEmailSheetConfig")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        params.put("del", 0);
        Query query = new Query(params);
        List<BiEmailSheetConfigDO> biEmailSheetConfigList = biEmailSheetConfigService.list(query);
        if (CollectionUtils.isNotEmpty(biEmailSheetConfigList))
            for (BiEmailSheetConfigDO obj : biEmailSheetConfigList) {
                Long createManID = obj.getCreateMan();
                Long updateManID = obj.getLastOptionMan();
                UserDO creatMan = userService.get(createManID);
                UserDO updateMan = userService.get(updateManID);
                if(Objects.nonNull(creatMan))
                    obj.setCreateManStr(creatMan.getName());
                if(Objects.nonNull(updateMan))
                    obj.setLastOptionManStr(updateMan.getName());
            }
        int total = biEmailSheetConfigService.count(query);
        PageUtils pageUtils = new PageUtils(biEmailSheetConfigList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("mail:biEmailSheetConfig:add")
    String add() {
        return "mail/biEmailSheetConfig/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("mail:biEmailSheetConfig:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        BiEmailSheetConfigDO biEmailSheetConfig = biEmailSheetConfigService.get(id);
        model.addAttribute("biEmailSheetConfig", biEmailSheetConfig);
        return "mail/biEmailSheetConfig/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mail:biEmailSheetConfig:add")
    public R save(BiEmailSheetConfigDO biEmailSheetConfig) {
        if (biEmailSheetConfigService.save(biEmailSheetConfig) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("mail:biEmailSheetConfig:edit")
    public R update(BiEmailSheetConfigDO biEmailSheetConfig) {
        Long id = biEmailSheetConfig.getId();
        if (null == id)
            return R.error("该记录不存在");
        int update = biEmailSheetConfigService.update(biEmailSheetConfig);
        if (update > 0)
            return R.ok();
        return R.error("更新失败");
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailSheetConfig:remove")
    public R remove(Long id) {
        if (null == id)
            return R.error("该记录不存在");
        BiEmailSheetConfigDO biEmailReceiverGroupConfig = biEmailSheetConfigService.get(id);
        if (Objects.isNull(biEmailReceiverGroupConfig))
            return R.error("该记录不存在");
        biEmailReceiverGroupConfig.setDel(1);
        int update = biEmailSheetConfigService.update(biEmailReceiverGroupConfig);
        if (update > 0)
            return R.ok();
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailSheetConfig:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        biEmailSheetConfigService.batchRemove(ids);
        return R.ok();
    }

    /**
     *
     */
    @ResponseBody
    @PostMapping("/list2option")
    public R list2option(String excelId) {
        Map<String, Object> map = new HashMap<>();
        List<BiEmailSheetConfigDO> sheets = biEmailSheetConfigService.list(null);
        map.put("list", sheets);
        Map<String, Object> queryMap = new HashMap<>();
        List<BiEmailExcelSheetConfigDO> ess = null;
        if (StringUtils.isNotBlank(excelId)) {
            queryMap.put("excelId;", excelId);
            ess = biEmailExcelSheetConfigService.list(queryMap);
        }
        map.put("ess", ess);
        return R.ok(map);
    }

    @ResponseBody
    @GetMapping("tryOut")
    public R tryOut(String sql){
        Map<String,Object> map = new HashMap<>();
        List<Map<String,String>> list = biEmailExcelCommonService.execDefinedSql(sql);
        map.put("list",list);
        return R.ok(map);
    }
}
