package com.xzbd.mail.service.impl;

import com.xzbd.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailSenderConfigDao;
import com.xzbd.mail.domain.BiEmailSenderConfigDO;
import com.xzbd.mail.service.BiEmailSenderConfigService;


@Service
public class BiEmailSenderConfigServiceImpl implements BiEmailSenderConfigService {
    @Autowired
    private BiEmailSenderConfigDao biEmailSenderConfigDao;

    @Override
    public BiEmailSenderConfigDO get(Long id) {
        return biEmailSenderConfigDao.get(id);
    }

    @Override
    public List<BiEmailSenderConfigDO> list(Map<String, Object> map) {
        return biEmailSenderConfigDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return biEmailSenderConfigDao.count(map);
    }

    @Override
    public int save(BiEmailSenderConfigDO biEmailSenderConfig) {
        Long userId = ShiroUtils.getUserId();
        biEmailSenderConfig.setCreateMan(userId);
        biEmailSenderConfig.setLastOptionMan(userId);
        Date time = new Date();
        biEmailSenderConfig.setCreateTime(time);
        biEmailSenderConfig.setLastOptionTime(time);
        biEmailSenderConfig.setDel(0);
        return biEmailSenderConfigDao.save(biEmailSenderConfig);
    }

    @Override
    public int update(BiEmailSenderConfigDO biEmailSenderConfig) {
        Long userId = ShiroUtils.getUserId();
        biEmailSenderConfig.setLastOptionMan(userId);
        Date time = new Date();
        biEmailSenderConfig.setLastOptionTime(time);
        return biEmailSenderConfigDao.update(biEmailSenderConfig);
    }

    @Override
    public int remove(Long id) {
        return biEmailSenderConfigDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return biEmailSenderConfigDao.batchRemove(ids);
    }

    @Override
    public List<BiEmailSenderConfigDO> getActiveSenderAddrs() {
        return biEmailSenderConfigDao.getActiveSenderAddrs();
    }



}
