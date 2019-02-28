package com.xzbd.mail.controller;

import java.util.List;
import java.util.Map;

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

import com.xzbd.mail.domain.BiEmailContentConfigDO;
import com.xzbd.mail.service.BiEmailContentConfigService;
import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;

/**
 * 邮件内容配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
 
@Controller
@RequestMapping("/mail/biEmailContentConfig")
public class BiEmailContentConfigController {
	@Autowired
	private BiEmailContentConfigService biEmailContentConfigService;
	
	@GetMapping()
	@RequiresPermissions("mail:biEmailContentConfig:biEmailContentConfig")
	String BiEmailContentConfig(){
	    return "mail/biEmailContentConfig/biEmailContentConfig";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mail:biEmailContentConfig:biEmailContentConfig")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BiEmailContentConfigDO> biEmailContentConfigList = biEmailContentConfigService.list(query);
		int total = biEmailContentConfigService.count(query);
		PageUtils pageUtils = new PageUtils(biEmailContentConfigList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("mail:biEmailContentConfig:add")
	String add(){
	    return "mail/biEmailContentConfig/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("mail:biEmailContentConfig:edit")
	String edit(@PathVariable("id") Long id,Model model){
		BiEmailContentConfigDO biEmailContentConfig = biEmailContentConfigService.get(id);
		model.addAttribute("biEmailContentConfig", biEmailContentConfig);
	    return "mail/biEmailContentConfig/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mail:biEmailContentConfig:add")
	public R save( BiEmailContentConfigDO biEmailContentConfig){
		if(biEmailContentConfigService.save(biEmailContentConfig)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mail:biEmailContentConfig:edit")
	public R update( BiEmailContentConfigDO biEmailContentConfig){
		biEmailContentConfigService.update(biEmailContentConfig);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("mail:biEmailContentConfig:remove")
	public R remove( Long id){
		if(biEmailContentConfigService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("mail:biEmailContentConfig:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		biEmailContentConfigService.batchRemove(ids);
		return R.ok();
	}
	
}
