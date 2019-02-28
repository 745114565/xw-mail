package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailReceiverGroupConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 邮件收件人组配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailReceiverGroupConfigDao {

	BiEmailReceiverGroupConfigDO get(Long id);
	
	List<BiEmailReceiverGroupConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig);
	
	int update(BiEmailReceiverGroupConfigDO biEmailReceiverGroupConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	@Select("SELECT * FROM bi_email_receiver_group_config " +
			" WHERE id IN (#{value})")
    List<BiEmailReceiverGroupConfigDO> listFindInIds(String ids);
}
