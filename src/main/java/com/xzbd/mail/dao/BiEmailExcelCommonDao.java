package com.xzbd.mail.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiEmailExcelCommonDao {
    @Select("${querySQL}")
    List<Map<String, String>> execDefinedSql(@Param("querySQL") String querySQL);
}
