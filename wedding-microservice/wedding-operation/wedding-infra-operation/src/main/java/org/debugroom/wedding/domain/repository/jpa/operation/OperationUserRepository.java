package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.User;

public interface OperationUserRepository extends JpaRepository<User, String>{
}
