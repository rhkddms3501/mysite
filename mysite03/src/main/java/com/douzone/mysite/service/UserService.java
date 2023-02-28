package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired //setter, gatter 없이 가능?
	private UserRepository userRepository;

	public void join(UserVo vo) {
		userRepository.insert(vo);
	}

	public UserVo getUser(UserVo vo) {
//		return userRepository.findByEmailAndPassword(vo);
		return userRepository.findByEmailAndPassword(vo.getEmail(), vo.getPassword());
	}

	public UserVo getUser(Long no) {
		return userRepository.findByNo(no);
	}
	
	public UserVo findUser(Long no) {
		return userRepository.findByNo(no);
	}

	public void updateUser(UserVo vo) {
		userRepository.update(vo);
	}

	public UserVo getUser(String email) {
		
		return userRepository.findByEmail(email);
	}
}

