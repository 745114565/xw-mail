package com.xzbd.system.service;

import com.xzbd.system.domain.IndexPageConfigDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-24 11:19:43
 */
public interface IndexPageConfigService {
	
	IndexPageConfigDO get(Long id);
	
	List<IndexPageConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(IndexPageConfigDO indexPageConfig);
	
	int update(IndexPageConfigDO indexPageConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	IndexPageConfigDO findByUserId(Long userId);
}
