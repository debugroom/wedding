package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import java.util.Date;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@PrimaryKeyClass
public class LogPK implements Serializable{

	private static final long serialVersionUID = -6804101510377675527L;
	public LogPK(){}

	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String userId;
	@PrimaryKeyColumn(name = "time_stamp", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private Date timeStamp;
	
}
