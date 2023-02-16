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

//	private static final int LIST_SIZE = 5; // 리스팅되는 게시물의 수
	private static final Long PAGE_SIZE = 10L; // 리스팅되는 게시물의 수

	public Map<String, Object> getContentsList(String getOffset, String getSearchWord) {
		
		System.out.println("getOffset ====================== " + getOffset);
		
		String searchWord = getSearchWord;
		Long offset = 0L;

		if (searchWord == null) {
			searchWord = "";
		}
		int maxPage = boardRepository.getLastPage(searchWord);
		
		if (getOffset != null) {
			offset = Long.parseLong(getOffset);
			if (offset < 0) {
				offset = 0L;
			} else {
				offset = offset * 10 - 10;
			}
		}
		
		if (offset > maxPage) {

			if (maxPage % 10 == 0) {
				offset = (long) (maxPage-10);
			} else {
				offset = (long) (maxPage / 10) * 10;
			}
		}
		
		if (maxPage % 10 == 0) {
			maxPage = maxPage / 10;
		} else {
			maxPage = maxPage / 10 + 1;
		}
		
		List<BoardVo> list = boardRepository.findBySearchWordAndOffsetAndPageSize(searchWord, offset, PAGE_SIZE);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("searchWord", searchWord);
		map.put("offset", ((((offset.intValue() / 50) + 1) * 5) - 4));
		map.put("maxPage", maxPage);
		map.put("currentPage", (offset.intValue() + 10) / 10 );
		
		return map;
	}
	
	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public Map<String, Object> addReply(BoardVo vo, String currentPage, String searchWord) {
		boardRepository.insertReply(vo);
		Map<String, Object> map = new HashMap<>();
		map.put("offset", currentPage);
		map.put("searchWord", searchWord);
		return map;
		
	}
	
	public Map<String, Object> getContents(String getNo, String getCurrentPage, String getSearchWord) {
		Long no = Long.parseLong(getNo);
		String currentPage = getCurrentPage;
		String searchWord = getSearchWord == null ? "" : getSearchWord;
		
		BoardVo vo = boardRepository.selectByNo(no);
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardVo", vo);
		map.put("currentPage", currentPage);
		map.put("searchWord", searchWord);
		return map;
	}
	
	public void updateContents(String getNo, String getTitle, String getContents, String getCurrentPage, String getSearchWord) {
		Long no = Long.parseLong(getNo);
		
		
		boardRepository.updateByNo(no, getTitle, getContents);
	}
	
	public void deleteContents(String getNo) {
		Long no = Long.parseLong(getNo);
		boardRepository.deleteByNo(no);
	}

	

	
}
