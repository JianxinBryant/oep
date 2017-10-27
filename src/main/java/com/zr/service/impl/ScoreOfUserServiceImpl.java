package com.zr.service.impl;

import com.zr.dao.ScoreOfUserDao;
import com.zr.dao.impl.ScoreOfUserDaoImpl;
import com.zr.service.ScoreOfUserService;

public class ScoreOfUserServiceImpl implements ScoreOfUserService{

	ScoreOfUserDao sod = new ScoreOfUserDaoImpl();
	
	@Override
	public void updateScore(int u_id, int e_id, int score) {
		
		sod.updateScore(u_id, e_id, score);
		
	}

}
