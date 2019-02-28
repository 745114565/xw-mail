package com.xzbd.mail.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  邮件公用Service
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 * */
public interface BiEmailExcelCommonService {
    List<Map<String, String>> execDefinedSql( String querySQL);
}
