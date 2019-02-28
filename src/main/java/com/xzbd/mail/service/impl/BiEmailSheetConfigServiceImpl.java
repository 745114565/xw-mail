package com.xzbd.mail.service.impl;

import com.xzbd.common.utils.ShiroUtils;
import com.xzbd.mail.domain.BiEmailExcelSheetConfigDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailSheetConfigDao;
import com.xzbd.mail.domain.BiEmailSheetConfigDO;
import com.xzbd.mail.service.BiEmailSheetConfigService;

import javax.annotation.Resource;


@Service
public class BiEmailSheetConfigServiceImpl implements BiEmailSheetConfigService {
	@Autowired
	private BiEmailSheetConfigDao biEmailSheetConfigDao;
	
	@Override
	public BiEmailSheetConfigDO get(Long id){
		return biEmailSheetConfigDao.get(id);
	}
	
	@Override
	public List<BiEmailSheetConfigDO> list(Map<String, Object> map){
		return biEmailSheetConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return biEmailSheetConfigDao.count(map);
	}
	
	@Override
	public int save(BiEmailSheetConfigDO biEmailSheetConfig){

		Long userId = ShiroUtils.getUserId();
		Date time = new Date();

		biEmailSheetConfig.setCreateMan(userId);
		biEmailSheetConfig.setCreateTime(time);

		biEmailSheetConfig.setLastOptionMan(userId);
		biEmailSheetConfig.setLastOptionTime(time);

		biEmailSheetConfig.setDel(0);

		return biEmailSheetConfigDao.save(biEmailSheetConfig);
	}
	
	@Override
	public int update(BiEmailSheetConfigDO biEmailSheetConfig){
		Long userId = ShiroUtils.getUserId();
		Date time = new Date();
		biEmailSheetConfig.setLastOptionMan(userId);
		biEmailSheetConfig.setLastOptionTime(time);

		return biEmailSheetConfigDao.update(biEmailSheetConfig);
	}
	
	@Override
	public int remove(Long id){
		return biEmailSheetConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return biEmailSheetConfigDao.batchRemove(ids);
	}
	
}
