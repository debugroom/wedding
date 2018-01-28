package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@Table("audit_log")
public class AuditLog implements Serializable{

	private static final long serialVersionUID = -7677580632450144556L;

	public AuditLog() {
	}

	@PrimaryKey
	private LogPK logpk;
	@Column("service_name")
	private String serviceName;
	@Column("host_ip_address")
	private String hostIpAddress;
	@Column("track_id")
	private String trackId;
	@Column("session_id")
	private String sessionId;
	@Column("request_ip_address")
	private String requestIpAddress;
	@Column("referer")
	private String referer;
	@Column("user_agent")
	private String userAgent;
	@Column("cookie")
	private String cookie;
	@Column("header_dump")
	private String headerDump;
	@Column("view_name")
	private String viewName;
	@Column("parameters")
	private String parameters;
	@Column("ver")
	private int ver;
	@Column("last_udpated_date")
	private Date lastUpdatedDate;
	

}
