package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailContentConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件内容配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailContentConfigDao {

	BiEmailContentConfigDO get(Long id);
	
	List<BiEmailContentConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailContentConfigDO biEmailContentConfig);
	
	int update(BiEmailContentConfigDO biEmailContentConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    int removeByJobId(Long jobId);

	int batchInsert(List<BiEmailContentConfigDO> contents);
}
