package com.xzbd.mail.service.impl;

import com.xzbd.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailExcelConfigDao;
import com.xzbd.mail.domain.BiEmailExcelConfigDO;
import com.xzbd.mail.service.BiEmailExcelConfigService;



@Service
public class BiEmailExcelConfigServiceImpl implements BiEmailExcelConfigService {
	@Autowired
	private BiEmailExcelConfigDao biEmailExcelConfigDao;
	
	@Override
	public BiEmailExcelConfigDO get(Long id){
		return biEmailExcelConfigDao.get(id);
	}
	
	@Override
	public List<BiEmailExcelConfigDO> list(Map<String, Object> map){
		return biEmailExcelConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return biEmailExcelConfigDao.count(map);
	}
	
	@Override
	public int save(BiEmailExcelConfigDO biEmailExcelConfig){
		Long userId = ShiroUtils.getUserId();
		Date time = new Date();

		biEmailExcelConfig.setCreateMan(userId);
		biEmailExcelConfig.setCreateTime(time);

		biEmailExcelConfig.setLastOptionMan(userId);
		biEmailExcelConfig.setLastOptionTime(time);

		biEmailExcelConfig.setDel(0);


		return biEmailExcelConfigDao.save(biEmailExcelConfig);
	}
	
	@Override
	public int update(BiEmailExcelConfigDO biEmailExcelConfig){
		Long userId = ShiroUtils.getUserId();
		Date time = new Date();
		biEmailExcelConfig.setLastOptionMan(userId);
		biEmailExcelConfig.setLastOptionTime(time);
		return biEmailExcelConfigDao.update(biEmailExcelConfig);
	}
	
	@Override
	public int remove(Long id){
		return biEmailExcelConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return biEmailExcelConfigDao.batchRemove(ids);
	}
	
}
