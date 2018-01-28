package org.debugroom.wedding.domain.repository.cassandra.message.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.repository.NoRepositoryBean;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import org.debugroom.wedding.domain.entity.message.User;
import org.debugroom.wedding.domain.repository.cassandra.message.UserRepositoryCustom;

@NoRepositoryBean
public class UserRepositoryImpl implements UserRepositoryCustom{

	@Autowired
	@Qualifier("cassandraTemplate")
	CassandraOperations cassandraOperations;
	
	@Autowired
	@Qualifier("cassandraAdminOperations")
	CassandraAdminOperations cassandraAdminOperations;

	@Override
	public Map<String, User> findAllForMap() {
		Select select = QueryBuilder.select().from("users");
		return cassandraOperations.query(select, new UserMapExtractor());
	}

}
