package com.zr.service;

import com.zr.model.Answerofuser;

public interface AnswerOfUserService {
	
	public Answerofuser selectAnswerofuser(int u_id, int e_id, int q_id);

	 public void commitAnswer(int u_id, int e_id, int q_id, String answer);
	 
	 public void delectAnswer(int u_id, int e_id, int q_id);
	 
	 public boolean checkAnswer(int u_id, int e_id, int q_id);
}
