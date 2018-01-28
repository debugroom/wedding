package org.debugroom.wedding.domain.repository.cassandra.message.impl;

import java.util.HashMap;
import java.util.Map;

import org.debugroom.wedding.domain.entity.message.User;
import org.springframework.cassandra.core.ResultSetExtractor;
import org.springframework.dao.DataAccessException;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;

public class UserMapExtractor implements ResultSetExtractor<Map<String, User>>{

	@Override
	public Map<String, User> extractData(ResultSet resultSet) 
			throws DriverException, DataAccessException {
		
		Map<String, User> userMap = new HashMap<String, User>();

		while(resultSet.iterator().hasNext()){
			Row row = resultSet.one();
			String userId = row.getString("user_id");
			User user = User.builder()
							.userId(userId)
							.firstName(row.getString("first_name"))
							.lastName(row.getString("last_name"))
							.loginId(row.getString("login_id"))
							.authorityLevel(row.getInt("authority_level"))
							.isBrideSide(row.getBool("is_bride_side"))
							.imageFilePath(row.getString("image_file_path"))
							.lastLoginDate(row.getTimestamp("last_login_date"))
							.lastUpdatedDate(row.getTimestamp("last_updated_date"))
							.ver(row.getInt("ver"))
							.build();
			userMap.put(userId, user);
		}
		return userMap;

	}

}
