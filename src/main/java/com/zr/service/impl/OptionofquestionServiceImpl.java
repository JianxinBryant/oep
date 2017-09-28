package com.zr.service.impl;

import java.util.List;

import com.zr.dao.OptionofquestionDao;
import com.zr.dao.impl.OptionofquestionDaoImpl;
import com.zr.model.Optionofquestion;
import com.zr.service.OptionofquestionService;

public class OptionofquestionServiceImpl implements OptionofquestionService{

	OptionofquestionDao ooqd = new OptionofquestionDaoImpl();
	@Override
	public List<Optionofquestion> queryOptionofquestionsByQ_id(int q_id) {
		
		List<Optionofquestion> list = ooqd.queryOptionofquestionsByQ_id(q_id);
		return list;
	}

}
