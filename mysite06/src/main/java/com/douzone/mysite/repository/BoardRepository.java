package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> findAllByPageAndKeyword(int page, String keyword, int size) {
		Map<String, Object> map = new HashMap<>();
		map.put("startOffset", (page-1)*size);
		map.put("size", size);
		map.put("keyword", keyword);
		
		return sqlSession.selectList("board.findAllByPageAndKeyword", map);
	}

	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.getTotalCount", keyword);
	}

	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}

	public void insertReply(BoardVo vo) {
		sqlSession.update("board.updateReply", vo);
		sqlSession.insert("board.insertReply", vo);
	}

	

	
	
	// =====================================================================
	public int getLastPage(String searchWord) {
		return sqlSession.selectOne("board.getLastPage", searchWord);
	}

	public List<BoardVo> findBySearchWordAndOffsetAndPageSize(String searchWord, Long offset, Long pageSize) {
		Map<String, Object> map = new HashMap<>();
		map.put("searchWord", searchWord);
		map.put("offset", offset);
		map.put("pageSize", pageSize);
		return sqlSession.selectList("board.findBySearchWordAndOffsetAndPageSize", map);
	}
	
	public BoardVo selectByNo(Long no) {		
		return sqlSession.selectOne("board.selectByNo", no);
	}
	
	public void updateByNo(Long no, String title, String contents) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("title", title);
		map.put("contents", contents);
		sqlSession.update("board.updateByNo", map);
	}
	public void deleteByNo(Long no) {
		sqlSession.delete("board.deleteByNo", no);
		
	}

	

}
