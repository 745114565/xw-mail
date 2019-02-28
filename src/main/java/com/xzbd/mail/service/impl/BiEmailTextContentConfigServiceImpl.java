package com.xzbd.mail.service.impl;

import com.xzbd.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailTextContentConfigDao;
import com.xzbd.mail.domain.BiEmailTextContentConfigDO;
import com.xzbd.mail.service.BiEmailTextContentConfigService;



@Service
public class BiEmailTextContentConfigServiceImpl implements BiEmailTextContentConfigService {
	@Autowired
	private BiEmailTextContentConfigDao biEmailTextContentConfigDao;
	
	@Override
	public BiEmailTextContentConfigDO get(Long id){
		return biEmailTextContentConfigDao.get(id);
	}
	
	@Override
	public List<BiEmailTextContentConfigDO> list(Map<String, Object> map){
		return biEmailTextContentConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return biEmailTextContentConfigDao.count(map);
	}
	
	@Override
	public int save(BiEmailTextContentConfigDO biEmailTextContentConfig){
		Long userId = ShiroUtils.getUserId();
		Date time = new Date();

		biEmailTextContentConfig.setCreateMan(userId);
		biEmailTextContentConfig.setCreateTime(time);

		biEmailTextContentConfig.setLastOptionMan(userId);
		biEmailTextContentConfig.setLastOptionTime(time);

		biEmailTextContentConfig.setDel(0);
		return biEmailTextContentConfigDao.save(biEmailTextContentConfig);
	}
	
	@Override
	public int update(BiEmailTextContentConfigDO biEmailTextContentConfig){
		Long userId = ShiroUtils.getUserId();
		Date time = new Date();

		biEmailTextContentConfig.setLastOptionMan(userId);
		biEmailTextContentConfig.setLastOptionTime(time);

		return biEmailTextContentConfigDao.update(biEmailTextContentConfig);
	}
	
	@Override
	public int remove(Long id){
		return biEmailTextContentConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return biEmailTextContentConfigDao.batchRemove(ids);
	}
	
}
