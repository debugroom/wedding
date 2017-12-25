package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.Function;
import org.debugroom.wedding.domain.entity.FunctionPK;

public interface FunctionRepository extends JpaRepository<Function, FunctionPK>{
}
