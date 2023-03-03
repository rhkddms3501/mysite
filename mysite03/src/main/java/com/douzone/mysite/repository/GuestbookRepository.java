package com.douzone.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
//	public List<GuestbookVo> findAll() {
//		return sqlSession.selectList("guestbook.findAll");
//	}
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public List<GuestbookVo> findAll(Long startNo) {
		return sqlSession.selectList("guestbook.findAllByNo", startNo);
	}

	public void insert(GuestbookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
	}

	public int deleteByNoAndPassword(Long no, String password) {
		Map<String, Object> map = Map.of("no", no, "password", password);
		return sqlSession.delete("guestbook.deleteByNoAndPassword", map);
	}
	
}
