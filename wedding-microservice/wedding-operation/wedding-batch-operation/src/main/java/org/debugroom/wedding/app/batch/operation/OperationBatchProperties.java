package org.debugroom.wedding.app.batch.operation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OperationBatchProperties {

	@Value("${operation.backup.root.directory}")
	private String operationBackupRootDirectory;
	
	@Value("${operation.backup.directory.postgres}")
	private String operationBackupPostgresDirectory;
	
	@Value("${operation.backup.directory.cassandra}")
	private String operationBackupCassandraDirectory;
	
	@Value("${operation.backup.data.postgres.address}")
	private String operationBackupDataPostgresAddress;
	
	@Value("${operation.backup.data.postgres.affiliation}")
	private String operationBackupDataPostgresAffiliation;
	
	@Value("${operation.backup.data.postgres.credential}")
	private String operationBackupDataPostgresCredential;
	
	@Value("${operation.backup.data.postgres.email}")
	private String operationBackupDataPostgresEmail;
	
	@Value("${operation.backup.data.postgres.function}")
	private String operationBackupDataPostgresFunction;
	
	@Value("${operation.backup.data.postgres.folder}")
	private String operationBackupDataPostgresFolder;
	
	@Value("${operation.backup.data.postgres.groupFolder}")
	private String operationBackupDataPostgresGroupFolder;
	
	@Value("${operation.backup.data.postgres.groupNotification}")
	private String operationBackupDataPostgresGroupNotification;
	
	@Value("${operation.backup.data.postgres.groupVisibleMovie}")
	private String operationBackupDataPostgresGroupVisibleMovie;
	
	@Value("${operation.backup.data.postgres.groupVisiblePhoto}")
	private String operationBackupDataPostgresGroupVisiblePhoto;
	
	@Value("${operation.backup.data.postgres.group}")
	private String operationBackupDataPostgresGroup;
	
	@Value("${operation.backup.data.postgres.information}")
	private String operationBackupDataPostgresInformation;
	
	@Value("${operation.backup.data.postgres.menu}")
	private String operationBackupDataPostgresMenu;
	
	@Value("${operation.backup.data.postgres.movie}")
	private String operationBackupDataPostgresMovie;
	
	@Value("${operation.backup.data.postgres.movieRelatedFolder}")
	private String operationBackupDataPostgresMovieRelatedFolder;
	
	@Value("${operation.backup.data.postgres.movieRelatedUser}")
	private String operationBackupDataPostgresMovieRelatedUser;
	
	@Value("${operation.backup.data.postgres.notification}")
	private String operationBackupDataPostgresNotification;
	
	@Value("${operation.backup.data.postgres.photo}")
	private String operationBackupDataPostgresPhoto;
	
	@Value("${operation.backup.data.postgres.photoRelatedFolder}")
	private String operationBackupDataPostgresPhotoRelatedFolder;
	
	@Value("${operation.backup.data.postgres.photoRelatedUser}")
	private String operationBackupDataPostgresPhotoRelatedUser;
	
	@Value("${operation.backup.data.postgres.request}")
	private String operationBackupDataPostgresRequest;
	
	@Value("${operation.backup.data.postgres.requestStatus}")
	private String operationBackupDataPostgresRequestStatus;
	
	@Value("${operation.backup.data.postgres.userRelatedFolder}")
	private String operationBackupDataPostgresUserRelatedFolder;
	
	@Value("${operation.backup.data.postgres.user}")
	private String operationBackupDataPostgresUser;
	
	@Value("${operation.backup.data.cassandra.group}")
	private String operationBackupDataCassandraGroup;

	@Value("${operation.backup.data.cassandra.groupRelatedMessageBoard}")
	private String operationBackupDataCassandraGroupRelatedMessageBoard;

	@Value("${operation.backup.data.cassandra.groupRelatedUser}")
	private String operationBackupDataCassandraGroupRelatedUser;

	@Value("${operation.backup.data.cassandra.message}")
	private String operationBackupDataCassandraMessage;

	@Value("${operation.backup.data.cassandra.messageBoard}")
	private String operationBackupDataCassandraMessageBoard;

	@Value("${operation.backup.data.cassandra.messageBoardRelatedGroup}")
	private String operationBackupDataCassandraMessageBoardRelatedGroup;

	@Value("${operation.backup.data.cassandra.user}")
	private String operationBackupDataCassandraUser;

	@Value("${operation.backup.data.cassandra.userRelatedGroup}")
	private String operationBackupDataCassandraUserRelatedGroup;

	
}
