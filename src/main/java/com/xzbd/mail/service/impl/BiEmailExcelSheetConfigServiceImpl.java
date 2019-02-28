package com.xzbd.mail.service.impl;

import com.xzbd.mail.dao.BiEmailSheetConfigDao;
import com.xzbd.mail.domain.BiEmailSheetConfigDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailExcelSheetConfigDao;
import com.xzbd.mail.domain.BiEmailExcelSheetConfigDO;
import com.xzbd.mail.service.BiEmailExcelSheetConfigService;


@Service
public class BiEmailExcelSheetConfigServiceImpl implements BiEmailExcelSheetConfigService {
    @Autowired
    private BiEmailExcelSheetConfigDao biEmailExcelSheetConfigDao;
    @Autowired
    private BiEmailSheetConfigDao biEmailSheetConfigDao;

    @Override
    public BiEmailExcelSheetConfigDO get(Long id) {
        return biEmailExcelSheetConfigDao.get(id);
    }

    @Override
    public List<BiEmailExcelSheetConfigDO> list(Map<String, Object> map) {
        return biEmailExcelSheetConfigDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return biEmailExcelSheetConfigDao.count(map);
    }

    @Override
    public int save(BiEmailExcelSheetConfigDO biEmailExcelSheetConfig) {
        return biEmailExcelSheetConfigDao.save(biEmailExcelSheetConfig);
    }

    @Override
    public int update(BiEmailExcelSheetConfigDO biEmailExcelSheetConfig) {
        return biEmailExcelSheetConfigDao.update(biEmailExcelSheetConfig);
    }

    @Override
    public int remove(Long id) {
        return biEmailExcelSheetConfigDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return biEmailExcelSheetConfigDao.batchRemove(ids);
    }

    @Override
    public int batchInsert(List<BiEmailExcelSheetConfigDO> eslist) {
        return biEmailExcelSheetConfigDao.batchInsert(eslist);
    }

    @Override
    public int removeByExcelId(Long id) {
        return biEmailExcelSheetConfigDao.removeByExcelId(id);
    }

    @Override
    public List<BiEmailExcelSheetConfigDO> listByExcelId(Long excelId) {
        return biEmailExcelSheetConfigDao.listByExcelId(excelId);
    }

}
