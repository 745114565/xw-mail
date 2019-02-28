package com.xzbd.system.controller;

import com.xzbd.common.domain.Tree;
import com.xzbd.common.utils.PageUtils;
import com.xzbd.common.utils.Query;
import com.xzbd.common.utils.R;
import com.xzbd.common.utils.ShiroUtils;
import com.xzbd.system.domain.IndexPageConfigDO;
import com.xzbd.system.domain.MenuDO;
import com.xzbd.system.dto.Menu2indexPage;
import com.xzbd.system.service.IndexPageConfigService;
import com.xzbd.system.service.MenuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-24 11:19:43
 */
 
@Controller
@RequestMapping("/sys/indexPageConfig")
public class IndexPageConfigController {
	@Autowired
	private IndexPageConfigService indexPageConfigService;
	@Autowired
	private MenuService menuService;
	
	@GetMapping()
	@RequiresPermissions("sys:indexPageConfig:indexPageConfig")
	String IndexPageConfig(){
	    return "sys/indexPageConfig/indexPageConfig";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("sys:indexPageConfig:indexPageConfig")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<IndexPageConfigDO> indexPageConfigList = indexPageConfigService.list(query);
		int total = indexPageConfigService.count(query);
		PageUtils pageUtils = new PageUtils(indexPageConfigList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("listAll")
	public R listAll(){
		Map<String,Object> res = new HashMap<>();
		Long userId = ShiroUtils.getUserId();
		List<Menu2indexPage> resO = new ArrayList<>();
		IndexPageConfigDO ipc = indexPageConfigService.findByUserId(userId);
		final String id = (ipc!=null) ?  ipc.getIndexPagePath() + "" : "0";
		List<Tree<MenuDO>> listMenuTree = menuService.listMenuTree(userId);
		if(CollectionUtils.isNotEmpty(listMenuTree)){
			listMenuTree.stream().forEach(o->{
				List<Tree<MenuDO>> children = o.getChildren();
				if(CollectionUtils.isNotEmpty(children)){
					children.stream().forEach(item->{
						Menu2indexPage dto = new Menu2indexPage();
						dto.setModulName(o.getText());
						dto.setPageName(item.getText());
						dto.setPageUrl(item.getId());
						dto.setChecked(id.equals(item.getId()));
						resO.add(dto);
					});
				}

			});
		}
		res.put("res", resO);
		return R.ok(res);
	}

	@PutMapping("setting")
	@ResponseBody
	public R setting(Long pageId) {

		Long userId = ShiroUtils.getUserId();
		IndexPageConfigDO old = indexPageConfigService.findByUserId(userId);
		if (old != null) {
			old.setIndexPagePath(pageId + "");
			int update = indexPageConfigService.update(old);
			if (update > 0)
				return R.ok("设置成功");
		} else {
			IndexPageConfigDO ipc = new IndexPageConfigDO();
			ipc.setId(userId);
			ipc.setIndexPagePath(pageId + "");
			int save = indexPageConfigService.save(ipc);
			if (save > 0)
				return R.ok("设置成功");
		}

		return R.error();
	}


	@DeleteMapping("setting")
	@ResponseBody
	public R del() {
		Long userId = ShiroUtils.getUserId();
		int delete = indexPageConfigService.remove(userId);
		if (delete > 0)
			return R.ok("取消设置成功");
		return R.error();
	}


	@GetMapping("/add")
	@RequiresPermissions("sys:indexPageConfig:add")
	String add(){
	    return "sys/indexPageConfig/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("sys:indexPageConfig:edit")
	String edit(@PathVariable("id") Long id,Model model){
		IndexPageConfigDO indexPageConfig = indexPageConfigService.get(id);
		model.addAttribute("indexPageConfig", indexPageConfig);
	    return "sys/indexPageConfig/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("sys:indexPageConfig:add")
	public R save( IndexPageConfigDO indexPageConfig){
		if(indexPageConfigService.save(indexPageConfig)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("sys:indexPageConfig:edit")
	public R update( IndexPageConfigDO indexPageConfig){
		indexPageConfigService.update(indexPageConfig);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("sys:indexPageConfig:remove")
	public R remove( Long id){
		if(indexPageConfigService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("sys:indexPageConfig:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		indexPageConfigService.batchRemove(ids);
		return R.ok();
	}
	
}
