package org.debugroom.wedding.domain.repository.jpa;

import org.debugroom.wedding.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String>{

}
