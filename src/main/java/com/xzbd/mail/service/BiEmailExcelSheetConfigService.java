package com.xzbd.mail.service;

import com.xzbd.mail.domain.BiEmailExcelSheetConfigDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 邮件内容配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public interface BiEmailExcelSheetConfigService {

	BiEmailExcelSheetConfigDO get(Long id);
	
	List<BiEmailExcelSheetConfigDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(BiEmailExcelSheetConfigDO biEmailExcelSheetConfig);
	
	int update(BiEmailExcelSheetConfigDO biEmailExcelSheetConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	int batchInsert(List<BiEmailExcelSheetConfigDO> esList);

	int removeByExcelId(@Param("excelId") Long excelId);

    List<BiEmailExcelSheetConfigDO> listByExcelId(Long excelId);
}
