package org.debugroom.wedding.domain.service.operation;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.repository.jpa.UserRepository;

@Service("backupService")
public class BackupServiceImpl implements BackupService {

	@Inject
	UserRepository userRepository;
	
	@Override
	public List<User> getUserDump() {
		return userRepository.findAll();
	}

}
