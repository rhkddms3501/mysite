package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	private static final int LIST_SIZE = 5; // 리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; // 리스팅되는 게시물의 수
	
	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
		return boardRepository.selectByNo(no);
	}
	
	public BoardVo getContents(Long no, Long UserNo) {
		return null;
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.updateByNo(vo);
	}
	
	public void deleteContents(Long no, Long userNo) {
		boardRepository.deleteByNoAndUserNo(no, userNo);
	}
	
	public Map<String, Object> getContentsList(int page, String keyword) {			
		int totalCount = boardRepository.getTotalCount(keyword);
//		System.out.println("totalCount = " + totalCount);
		  
		 
		// 1. view에서 게시판 리스트를 렌더링 하기 위한 데이터 값 계산
		int beginPage = 0;
		int prePage = 0;
		int nextPage = 0;
		int endPage = 0;
		
		// 2. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(page, keyword, LIST_SIZE);
		
		// 3. 리스트 정보를 맵에 저장
		
//		Map<String, Object> map = new HashMap<>();
		Map<String, Object> map = Map.of("list", list);
		return map;
		
		
	}
}
