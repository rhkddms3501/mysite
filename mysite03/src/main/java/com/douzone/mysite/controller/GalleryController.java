package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	GalleryService galleryService;
	
	@Autowired
	FileuploadService fileuploadService;
	
	@RequestMapping("")
	private String main(Model model) {
		List<GalleryVo> list =  galleryService.getImages();
		System.out.println(list);
		
		model.addAttribute("list", list);
		return "gallery/index";
	}
	
	@Auth(role = "ADMIN")
	@RequestMapping("/upload")
	private String upload(
			@RequestParam("comments") String comments,
			@RequestParam("file") MultipartFile file) {
		
		String url = fileuploadService.restore(file);
		
		GalleryVo vo = new GalleryVo();
		vo.setComments(comments);
		vo.setUrl(url);
		
		galleryService.addImage(vo);
		
		return "redirect:/gallery";
	}
	
	@Auth(role = "ADMIN")
	@RequestMapping("/delete/{no}")
	private String delete(
			@PathVariable ("no") Long no) {
		galleryService.removeImage(no);
		return "redirect:/gallery";
	}

}
