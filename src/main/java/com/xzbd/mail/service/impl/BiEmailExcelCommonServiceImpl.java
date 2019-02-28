package com.xzbd.mail.service.impl;

import com.xzbd.mail.dao.BiEmailExcelCommonDao;
import com.xzbd.mail.service.BiEmailExcelCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BiEmailExcelCommonServiceImpl implements BiEmailExcelCommonService {
    @Autowired
    private BiEmailExcelCommonDao biEmailExcelCommonDao;
    @Override
    public List<Map<String, String>> execDefinedSql(String querySQL) {
        return biEmailExcelCommonDao.execDefinedSql(querySQL);
    }
}
