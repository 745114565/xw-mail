package com.xzbd.mail.service;

import com.xzbd.common.utils.R;
import com.xzbd.mail.domain.BiEmailJobConfigDO;

import java.util.List;
import java.util.Map;

/**
 * 邮件作业任务配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public interface BiEmailJobConfigService {
	
	BiEmailJobConfigDO get(Long id);
	
	List<BiEmailJobConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailJobConfigDO biEmailJobConfig);
	
	int update(BiEmailJobConfigDO biEmailJobConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    void sendEmail(Long id, String date) throws Exception;

    void initSchedule() throws Exception;
}
