package com.xzbd.mail.controller;

import java.util.HashMap;
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

import com.xzbd.mail.domain.BiEmailJobRunDiaryDO;
import com.xzbd.mail.service.BiEmailJobRunDiaryService;
import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;

/**
 * 
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
 
@Controller
@RequestMapping("/mail/biEmailJobRunDiary")
public class BiEmailJobRunDiaryController {
	@Autowired
	private BiEmailJobRunDiaryService biEmailJobRunDiaryService;
	
	@GetMapping("/index")
	@RequiresPermissions("mail:biEmailJobRunDiary:biEmailJobRunDiary")
	public String index(){
	    return "mail/biEmailJobRunDiary/biEmailJobRunDiary";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mail:biEmailJobRunDiary:biEmailJobRunDiary")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Map<String,Object> res = new HashMap<>();
        Query query = new Query(params);
		List<BiEmailJobRunDiaryDO> biEmailJobRunDiaryList = biEmailJobRunDiaryService.list(query);
		int total = biEmailJobRunDiaryService.count(query);
		PageUtils pageUtils = new PageUtils(biEmailJobRunDiaryList, total);
		res.put("page",pageUtils);
		return R.ok(res);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("mail:biEmailJobRunDiary:add")
	String add(){
	    return "mail/biEmailJobRunDiary/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("mail:biEmailJobRunDiary:edit")
	String edit(@PathVariable("id") Long id,Model model){
		BiEmailJobRunDiaryDO biEmailJobRunDiary = biEmailJobRunDiaryService.get(id);
		model.addAttribute("biEmailJobRunDiary", biEmailJobRunDiary);
	    return "mail/biEmailJobRunDiary/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mail:biEmailJobRunDiary:add")
	public R save( BiEmailJobRunDiaryDO biEmailJobRunDiary){
		if(biEmailJobRunDiaryService.save(biEmailJobRunDiary)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mail:biEmailJobRunDiary:edit")
	public R update( BiEmailJobRunDiaryDO biEmailJobRunDiary){
		biEmailJobRunDiaryService.update(biEmailJobRunDiary);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("mail:biEmailJobRunDiary:remove")
	public R remove( Long id){
		if(biEmailJobRunDiaryService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("mail:biEmailJobRunDiary:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		biEmailJobRunDiaryService.batchRemove(ids);
		return R.ok();
	}
	
}
