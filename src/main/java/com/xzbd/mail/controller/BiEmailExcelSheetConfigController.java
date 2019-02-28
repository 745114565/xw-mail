package com.xzbd.mail.controller;

import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;
import com.xzbd.mail.domain.BiEmailExcelSheetConfigDO;
import com.xzbd.mail.domain.BiEmailSenderConfigDO;
import com.xzbd.mail.domain.BiEmailSheetConfigDO;
import com.xzbd.mail.service.BiEmailExcelSheetConfigService;
import com.xzbd.mail.service.BiEmailSheetConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮件内容配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
 
@Controller
@RequestMapping("/mail/biEmailExcelSheetConfig")
public class BiEmailExcelSheetConfigController {
	@Autowired
	private BiEmailExcelSheetConfigService biEmailExcelSheetConfigService;
	@Autowired
	private BiEmailSheetConfigService biEmailSheetConfigService;
	
	@GetMapping()
	@RequiresPermissions("mail:biEmailExcelSheetConfig:biEmailExcelSheetConfig")
	String BiEmailExcelSheetConfig(){
	    return "mail/biEmailExcelSheetConfig/biEmailExcelSheetConfig";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mail:biEmailExcelSheetConfig:biEmailExcelSheetConfig")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BiEmailExcelSheetConfigDO> biEmailExcelSheetConfigList = biEmailExcelSheetConfigService.list(query);
		int total = biEmailExcelSheetConfigService.count(query);
		PageUtils pageUtils = new PageUtils(biEmailExcelSheetConfigList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("mail:biEmailExcelSheetConfig:add")
	String add(){
	    return "mail/biEmailExcelSheetConfig/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("mail:biEmailExcelSheetConfig:edit")
	String edit(@PathVariable("id") Long id,Model model){
		BiEmailExcelSheetConfigDO biEmailExcelSheetConfig = biEmailExcelSheetConfigService.get(id);
		model.addAttribute("biEmailExcelSheetConfig", biEmailExcelSheetConfig);
	    return "mail/biEmailExcelSheetConfig/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mail:biEmailExcelSheetConfig:add")
	public R save( BiEmailExcelSheetConfigDO biEmailExcelSheetConfig){
		if(biEmailExcelSheetConfigService.save(biEmailExcelSheetConfig)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mail:biEmailExcelSheetConfig:edit")
	public R update( BiEmailExcelSheetConfigDO biEmailExcelSheetConfig){
		biEmailExcelSheetConfigService.update(biEmailExcelSheetConfig);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("mail:biEmailExcelSheetConfig:remove")
	public R remove( Long id){
		if(biEmailExcelSheetConfigService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("mail:biEmailExcelSheetConfig:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		biEmailExcelSheetConfigService.batchRemove(ids);
		return R.ok();
	}
	/**
	 *
	 */
	@ResponseBody
	@GetMapping("/list2option")
	public R list2option(Long excelId){
		Map<String, Object> map = new HashMap<>();
		List<BiEmailSheetConfigDO>  sheets = biEmailSheetConfigService.list(null);
		map.put("list", sheets);
		List<BiEmailExcelSheetConfigDO> ess = biEmailExcelSheetConfigService.listByExcelId(excelId);
		map.put("ess",ess);
		return R.ok(map);
	}
	
}
