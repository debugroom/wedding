package org.debugroom.wedding.domain.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.model.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String>{
}
