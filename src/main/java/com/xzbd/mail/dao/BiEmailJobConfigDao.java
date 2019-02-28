package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailJobConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件作业任务配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailJobConfigDao {

	BiEmailJobConfigDO get(Long id);
	
	List<BiEmailJobConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailJobConfigDO biEmailJobConfig);
	
	int update(BiEmailJobConfigDO biEmailJobConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
