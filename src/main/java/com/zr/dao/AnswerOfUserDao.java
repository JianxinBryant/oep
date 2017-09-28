package com.zr.dao;

import com.zr.model.Answerofuser;

public interface AnswerOfUserDao {
	
	public Answerofuser selectAnswerofuser(int u_id, int e_id, int q_id);
	
	public void commitAnswer(int u_id, int e_id, int q_id, String answer);
	
	public boolean checkAnswer(int u_id, int e_id, int q_id);
	
	public void deleteAnswer(int u_id, int e_id, int q_id);
}
