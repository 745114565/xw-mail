package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailSheetConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件Excel中sheet配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailSheetConfigDao {

	BiEmailSheetConfigDO get(Long id);
	
	List<BiEmailSheetConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailSheetConfigDO biEmailSheetConfig);
	
	int update(BiEmailSheetConfigDO biEmailSheetConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

//    BiEmailSheetConfigDO saveAndReturn(BiEmailSheetConfigDO biEmailSheetConfig);
}
