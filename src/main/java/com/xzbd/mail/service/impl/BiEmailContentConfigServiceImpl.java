package com.xzbd.mail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailContentConfigDao;
import com.xzbd.mail.domain.BiEmailContentConfigDO;
import com.xzbd.mail.service.BiEmailContentConfigService;



@Service
public class BiEmailContentConfigServiceImpl implements BiEmailContentConfigService {
	@Autowired
	private BiEmailContentConfigDao biEmailContentConfigDao;
	
	@Override
	public BiEmailContentConfigDO get(Long id){
		return biEmailContentConfigDao.get(id);
	}
	
	@Override
	public List<BiEmailContentConfigDO> list(Map<String, Object> map){
		return biEmailContentConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return biEmailContentConfigDao.count(map);
	}
	
	@Override
	public int save(BiEmailContentConfigDO biEmailContentConfig){
		return biEmailContentConfigDao.save(biEmailContentConfig);
	}
	
	@Override
	public int update(BiEmailContentConfigDO biEmailContentConfig){
		return biEmailContentConfigDao.update(biEmailContentConfig);
	}
	
	@Override
	public int remove(Long id){
		return biEmailContentConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return biEmailContentConfigDao.batchRemove(ids);
	}

	@Override
	public int removeByJobId(Long jobId) {
		return biEmailContentConfigDao.removeByJobId(jobId);
	}

	@Override
	public int batchInsert(List<BiEmailContentConfigDO> contents) {
		return biEmailContentConfigDao.batchInsert(contents);
	}

}
