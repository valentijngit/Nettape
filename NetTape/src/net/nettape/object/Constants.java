package net.nettape.object;

import java.io.*;
import net.nettape.dal.object.*;
import java.util.ArrayList;

public class Constants {
	
	public enum ConnectionType {
		SOCKETS (0), HTTP (1);
		public final int type;
		ConnectionType(int type)
		{
			this.type = type;
		}
	}
	public enum BackupItemType {
		SNAPSHOT (1), DIFFERENTIAL (2), INCREMENTAL (3);
		public final int type;
		BackupItemType(int type)
		{
			this.type = type;
		}
	}
	public enum BackupSetItemType {
		FILE (1), FOLDER (2);
		public final int type;
		BackupSetItemType(int type)
		{
			this.type = type;
		}
	}
	public enum BackupSetType {
		SNAPSHOT (1), DIFFERENTIAL (2), INCREMENTAL(3);
		public final int type;
		BackupSetType(int type)
		{
			this.type = type;
		}
	}
	public enum SendBackupCommand {
		SENDFILE (1), RECEIVECHECKSUMS (2), SENDFILESINFOLDER (3), DONOTHING (4), SENDDELTAS (5);
		private final int type;
		SendBackupCommand(int type)
		{
			this.type = type;
		}
	}
	public enum ReceiveRestoreCommand {
		RECEIVEFILE (1), GOTONEXT (2), RECEIVEPERMISSIONS (3);
		private final int type;
		ReceiveRestoreCommand(int type)
		{
			this.type = type;
		}
	}
	public enum ScheduleType {
		DAILY (1), WEEKLY (2), MONTHLY (3), YEARLY(4), CUSTOM (5);
		private final Integer type;
		ScheduleType(Integer type)
		{
			this.type = type;
		}
	}
	public enum MessageType {
		SYSTEM_ERROR (1), BACKUP_ERROR (2), REPLICATION_ERROR (3), INFO (4), BACKUP (4), RESTORE (5), RESTORE_ERROR (6) ;
		private final Integer type;
		MessageType(Integer type)
		{
			this.type = type;
		}
	}
}
