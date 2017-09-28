package com.zr.dao;

import java.util.List;

import com.zr.model.Optionofquestion;

public interface OptionofquestionDao {

	public List<Optionofquestion> queryOptionofquestionsByQ_id(int q_id);
}
