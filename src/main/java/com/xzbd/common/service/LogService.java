package com.xzbd.common.service;

import org.springframework.stereotype.Service;

import com.xzbd.common.domain.LogDO;
import com.xzbd.common.domain.PageDO;
import com.xzbd.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
