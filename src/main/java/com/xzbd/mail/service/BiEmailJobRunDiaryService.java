package com.xzbd.mail.service;

import com.xzbd.mail.domain.BiEmailJobRunDiaryDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public interface BiEmailJobRunDiaryService {
	
	BiEmailJobRunDiaryDO get(Long id);
	
	List<BiEmailJobRunDiaryDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailJobRunDiaryDO biEmailJobRunDiary);
	
	int update(BiEmailJobRunDiaryDO biEmailJobRunDiary);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
