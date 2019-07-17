package com.timeline.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeline.dao.PostDao;
import com.timeline.vo.PostVo;
import com.timeline.vo.UserVo;

@Service
public class PostService {
	
	@Autowired
	private PostDao dao;
	
	public int writePheed(PostVo pVo, UserVo uVo){
		pVo.setUserNo(uVo.getUserNo());
		return dao.writePheed(pVo);
	}
	
	public List<PostVo> loadPheed(){
		return dao.loadPheed();
	}
	
	public List<PostVo> loadMyPheed(UserVo vo){
		return dao.loadMyPheed(vo);
	}
	
	public PostVo detailPheed(UserVo uVo, PostVo pVo) {
		pVo.setUserNo(uVo.getUserNo());
		
		PostVo searched = dao.detailPheed(pVo);
		dao.hitPheed(pVo);
		
		return searched;
	}
	
	@Transactional
	public int likeTogglePheed(UserVo uVo, PostVo pVo) {
		Map<String, Object> liked = new HashMap<String, Object>();
		liked.put("userNo", uVo.getUserNo());
		liked.put("postNo", pVo.getPostNo());
		
		int result;
		int exist = dao.findLiked(liked);
		
		if(exist == 0) {
			// like function
			 result = dao.likePheed(liked);
		}else {
			// unlike function
			result = dao.unlikePheed(liked);
		}
		return result;
	}
}
