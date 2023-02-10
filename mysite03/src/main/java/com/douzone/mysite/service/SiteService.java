package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService {

	@Autowired
	private SiteRepository siteRepository;
	
	

	public SiteVo getSite() {
		
		return siteRepository.find();
	}

	public void updateSite(SiteVo vo) {
		siteRepository.update(vo);
	}
}
