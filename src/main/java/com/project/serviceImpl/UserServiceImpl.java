package com.project.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.modal.Users;
import com.project.repository.UserRepository;
import com.project.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserRepository userRepository;

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public ArrayList<Users> findAllUsersByTenantId(UUID tenantId) {
		return null;
	}

	@Override
	public Optional<Users> findUserByIdAndTenantId(UUID userId, UUID tenantId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteByUserIdAndTenantId(UUID userId, UUID tenantId) {
		// TODO Auto-generated method stub
		
	}
}
