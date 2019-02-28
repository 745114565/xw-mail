package com.xzbd.mail.service.impl;

import com.xzbd.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailReceiverGroupConfigDao;
import com.xzbd.mail.domain.BiEmailReceiverGroupConfigDO;
import com.xzbd.mail.service.BiEmailReceiverGroupConfigService;



@Service
public class BiEmailReceiverGroupConfigServiceImpl implements BiEmailReceiverGroupConfigService {
	@Autowired
	private BiEmailReceiverGroupConfigDao biEmailReceiverGroupConfigDao;
	
	@Override
	public BiEmailReceiverGroupConfigDO get(Long id){
		return biEmailReceiverGroupConfigDao.get(id);
	}
	
	@Override
	public List<BiEmailReceiverGroupConfigDO> list(Map<String, Object> map){
		return biEmailReceiverGroupConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return biEmailReceiverGroupConfigDao.count(map);
	}
	
	@Override
	public int save(BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig){
		Long userId = ShiroUtils.getUserId();
		Date time = new Date();

		biEmailReceiverGroupConfig.setCreateMan(userId);
		biEmailReceiverGroupConfig.setCreateTime(time);

		biEmailReceiverGroupConfig.setLastOptionMan(userId);
		biEmailReceiverGroupConfig.setLastOptionTime(time);

		biEmailReceiverGroupConfig.setDel(0);
		return biEmailReceiverGroupConfigDao.save(biEmailReceiverGroupConfig);
	}
	
	@Override
	public int update(BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig){
		Long userId = ShiroUtils.getUserId();
		Date time = new Date();

		biEmailReceiverGroupConfig.setLastOptionMan(userId);
		biEmailReceiverGroupConfig.setLastOptionTime(time);
		return biEmailReceiverGroupConfigDao.update(biEmailReceiverGroupConfig);
	}
	
	@Override
	public int remove(Long id){
		return biEmailReceiverGroupConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return biEmailReceiverGroupConfigDao.batchRemove(ids);
	}

	@Override
	public List<BiEmailReceiverGroupConfigDO> listFindInIds(String ids) {
		return biEmailReceiverGroupConfigDao.listFindInIds(ids);
	}

}
