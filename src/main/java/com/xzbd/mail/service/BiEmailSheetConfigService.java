package com.xzbd.mail.service;

import com.xzbd.mail.domain.BiEmailSheetConfigDO;

import java.util.List;
import java.util.Map;

/**
 * 邮件Excel中sheet配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public interface BiEmailSheetConfigService {
	
	BiEmailSheetConfigDO get(Long id);
	
	List<BiEmailSheetConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailSheetConfigDO biEmailSheetConfig);
	
	int update(BiEmailSheetConfigDO biEmailSheetConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
