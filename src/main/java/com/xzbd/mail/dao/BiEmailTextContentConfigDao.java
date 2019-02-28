package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailTextContentConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件文本内容配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailTextContentConfigDao {

	BiEmailTextContentConfigDO get(Long id);
	
	List<BiEmailTextContentConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailTextContentConfigDO biEmailTextContentConfig);
	
	int update(BiEmailTextContentConfigDO biEmailTextContentConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
