package com.xzbd.system.service.impl;

import com.xzbd.system.dao.IndexPageConfigDao;
import com.xzbd.system.domain.IndexPageConfigDO;
import com.xzbd.system.service.IndexPageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service
public class IndexPageConfigServiceImpl implements IndexPageConfigService {
	@Autowired
	private IndexPageConfigDao indexPageConfigDao;
	
	@Override
	public IndexPageConfigDO get(Long id){
		return indexPageConfigDao.get(id);
	}
	
	@Override
	public List<IndexPageConfigDO> list(Map<String, Object> map){
		return indexPageConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return indexPageConfigDao.count(map);
	}
	
	@Override
	public int save(IndexPageConfigDO indexPageConfig){
		return indexPageConfigDao.save(indexPageConfig);
	}
	
	@Override
	public int update(IndexPageConfigDO indexPageConfig){
		return indexPageConfigDao.update(indexPageConfig);
	}
	
	@Override
	public int remove(Long id){
		return indexPageConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return indexPageConfigDao.batchRemove(ids);
	}

	@Override
	public IndexPageConfigDO findByUserId(Long userId) {
		return indexPageConfigDao.findByUserId(userId);
	}

}
