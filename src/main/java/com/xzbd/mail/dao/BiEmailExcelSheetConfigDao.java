package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailExcelSheetConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 邮件内容配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailExcelSheetConfigDao {

	BiEmailExcelSheetConfigDO get(Long id);
	
	List<BiEmailExcelSheetConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailExcelSheetConfigDO biEmailExcelSheetConfig);
	
	int update(BiEmailExcelSheetConfigDO biEmailExcelSheetConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    int batchInsert(List<BiEmailExcelSheetConfigDO> eslist);

	int removeByExcelId(Long id);

	@Select("select * from bi_email_excel_sheet_config where del = 0 and excel_id = #{value}")
    List<BiEmailExcelSheetConfigDO> listByExcelId(Long excelId);
}
