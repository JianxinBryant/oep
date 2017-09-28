package com.zr.service;

import java.util.List;

import com.zr.model.Optionofquestion;

public interface OptionofquestionService {

	public List<Optionofquestion> queryOptionofquestionsByQ_id(int q_id);
}
