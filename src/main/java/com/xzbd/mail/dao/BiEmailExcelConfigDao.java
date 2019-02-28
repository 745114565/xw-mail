package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailExcelConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件Excel内容配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailExcelConfigDao {

	BiEmailExcelConfigDO get(Long id);
	
	List<BiEmailExcelConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailExcelConfigDO biEmailExcelConfig);
	
	int update(BiEmailExcelConfigDO biEmailExcelConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
