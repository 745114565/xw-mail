package com.xzbd.system.dao;


import java.util.List;
import java.util.Map;

import com.xzbd.system.domain.IndexPageConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-24 11:19:43
 */
@Mapper
public interface IndexPageConfigDao {

	IndexPageConfigDO get(Long id);
	
	List<IndexPageConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(IndexPageConfigDO indexPageConfig);
	
	int update(IndexPageConfigDO indexPageConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	@Select("select * from sys_index_page_config where id = #{value}")
	IndexPageConfigDO findByUserId(Long userId);
}
