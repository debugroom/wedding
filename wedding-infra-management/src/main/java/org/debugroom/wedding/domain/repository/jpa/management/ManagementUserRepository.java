package org.debugroom.wedding.domain.repository.jpa.management;

import org.debugroom.wedding.domain.entity.management.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ManagementUserRepository extends JpaRepository<User, String>, 
											JpaSpecificationExecutor<User>{
}
