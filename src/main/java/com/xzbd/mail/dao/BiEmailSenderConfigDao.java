package com.xzbd.mail.dao;

import com.xzbd.mail.domain.BiEmailSenderConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件发送者邮箱配置
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
@Mapper
public interface BiEmailSenderConfigDao {

	BiEmailSenderConfigDO get(Long id);
	
	List<BiEmailSenderConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BiEmailSenderConfigDO biEmailSenderConfig);
	
	int update(BiEmailSenderConfigDO biEmailSenderConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    List<BiEmailSenderConfigDO> getActiveSenderAddrs();
}
