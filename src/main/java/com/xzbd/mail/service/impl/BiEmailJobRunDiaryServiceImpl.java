package com.xzbd.mail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xzbd.mail.dao.BiEmailJobRunDiaryDao;
import com.xzbd.mail.domain.BiEmailJobRunDiaryDO;
import com.xzbd.mail.service.BiEmailJobRunDiaryService;



@Service
public class BiEmailJobRunDiaryServiceImpl implements BiEmailJobRunDiaryService {
	@Autowired
	private BiEmailJobRunDiaryDao biEmailJobRunDiaryDao;
	
	@Override
	public BiEmailJobRunDiaryDO get(Long id){
		return biEmailJobRunDiaryDao.get(id);
	}
	
	@Override
	public List<BiEmailJobRunDiaryDO> list(Map<String, Object> map){
		return biEmailJobRunDiaryDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return biEmailJobRunDiaryDao.count(map);
	}
	
	@Override
	public int save(BiEmailJobRunDiaryDO biEmailJobRunDiary){
		return biEmailJobRunDiaryDao.save(biEmailJobRunDiary);
	}
	
	@Override
	public int update(BiEmailJobRunDiaryDO biEmailJobRunDiary){
		return biEmailJobRunDiaryDao.update(biEmailJobRunDiary);
	}
	
	@Override
	public int remove(Long id){
		return biEmailJobRunDiaryDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return biEmailJobRunDiaryDao.batchRemove(ids);
	}
	
}
