package net.nettape.client.gui.admin;

import java.util.TreeMap;

import net.nettape.client.gui.AppConstants;

import org.apache.log4j.Logger;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 12, 2009
 * @description
 */

public class Language {

	public static Logger logger = Logger.getLogger(Language.class);

	private static Language instance = null;

	private static final TreeMap<String, String> defaultLanguageGeneral = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageMainWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageLanguageWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageBackupSettingsWindow = new TreeMap<String, String>();

	private static final TreeMap<String, String> defaultLanguagePatternWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultAdvancedInFileDeltaWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultRetencionPolicyWindow = new TreeMap<String, String>();

	private static final TreeMap<String, String> defaultChangePasswordWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultServersWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultBackupLogWindow = new TreeMap<String, String>();

	private static TreeMap<String, TreeMap<String, String>> defaultLanguage = new TreeMap<String, TreeMap<String, String>>();
	private static TreeMap<String, TreeMap<String, String>> language = new TreeMap<String, TreeMap<String, String>>();

	public static final String KEY_GENERAL = "General";
	public static final String GENERAL_TEXT_BUTTON_OK = "buttonOk";
	public static final String GENERAL_TEXT_BUTTON_CANCEL = "buttonCancel";
	public static final String GENERAL_TEXT_BUTTON_YES = "buttonYES";
	public static final String GENERAL_TEXT_BUTTON_NO = "buttonNO";
	public static final String GENERAL_TEXT_MESSAGE_TITLE_ERROR = "messageError";
	public static final String GENERAL_TEXT_MESSAGE_TITLE_WARNING = "messageWarning";
	public static final String GENERAL_TEXT_MESSAGE_TITLE_INFO = "messageInfo";
	public static final String GENERAL_TEXT_MESSAGE_TITLE_QUESTION = "messageQuestion";
	public static final String GENERAL_TEXT_MESSAGE_TITLE_WAITING = "messageWaiting";
	public static final String GENERAL_TEXT_FILETYPE_UNKNOWN = "filetype.Unknown ";
	public static final String GENERAL_TEXT_FILETYPE_NONE = "filetype.None";
	public static final String GENERAL_TEXT_FILETYPE_FOLDER = "filetype.Folder";
	public static final String GENERAL_TEXT_FILESIZE_KB = "filesize.KB";
	public static final String GENERAL_TEXT_DAYS = "days";
	public static final String GENERAL_TEXT_DAY = "day";
	public static final String GENERAL_TEXT_HOURS = "hours";
	public static final String GENERAL_TEXT_BYTES = "bytes";
	public static final String GENERAL_TEXT_KBYTES = "kbytes";
	public static final String GENERAL_TEXT_MINUTES = "minutes";
	public static final String GENERAL_BUTTON_CHANGE = "buttonChange";
	public static final String GENERAL_BUTTON_ADD = "buttonAdd";
	public static final String GENERAL_BUTTON_REMOVE = "buttonRemove";
	public static final String GENERAL_BUTTON_ADVANCED = "buttonAdvanced";

	public static final String GENERAL_SUNDAY = "sunday";
	public static final String GENERAL_MONDAY = "monday";
	public static final String GENERAL_TUESDAY = "tuesday";
	public static final String GENERAL_WEDNESDAY = "wednesday";
	public static final String GENERAL_THURSDAY = "thursday";
	public static final String GENERAL_FRIDAY = "friday";
	public static final String GENERAL_SATURDAY = "saturday";

	public static final String GENERAL_JANUARY = "january";
	public static final String GENERAL_FEBRUARY = "february";
	public static final String GENERAL_MARCH = "march";
	public static final String GENERAL_APRIL = "april";
	public static final String GENERAL_MAY = "may";
	public static final String GENERAL_JUNE = "june";
	public static final String GENERAL_JULY = "july";
	public static final String GENERAL_AUGUST = "august";
	public static final String GENERAL_SEPTEMBER = "september";
	public static final String GENERAL_OCTOBER = "october";
	public static final String GENERAL_NOVEMBER = "november";
	public static final String GENERAL_DECEMBER = "december";

	public static final String KEY_MAIN_WINDOW = "MainWindow";
	public static final String MAIN_WINDOW_TEXT_TITLE = "title";
	public static final String MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT = "buttonUserAccount";
	public static final String MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET = "buttonBackupSet";
	public static final String MAIN_WINDOW_TEXT_BUTTON_SCHEDULE = "buttonSchedule";
	public static final String MAIN_WINDOW_TEXT_BUTTON_SETTINGS = "buttonSettings";
	public static final String MAIN_WINDOW_TEXT_BUTTON_REPORTS = "buttonReports";
	public static final String MAIN_WINDOW_TEXT_BUTTON_HELP = "buttonHelp";
	public static final String MAIN_WINDOW_TEXT_BUTTON_BACKUP = "buttonBackup";
	public static final String MAIN_WINDOW_TEXT_BUTTON_RESTORE = "buttonRestore";

	public static final String MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT_DETAILS = "buttonUserAccount.Details";
	public static final String MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET_DETAILS = "buttonBackupSet.Details";
	public static final String MAIN_WINDOW_TEXT_BUTTON_SCHEDULE_DETAILS = "buttonSchedule.Details";
	public static final String MAIN_WINDOW_TEXT_BUTTON_SETTINGS_DETAILS = "buttonSettings.Details";
	public static final String MAIN_WINDOW_TEXT_BUTTON_REPORTS_DETAILS = "buttonReports.Details";
	public static final String MAIN_WINDOW_TEXT_BUTTON_HELP_DETAILS = "buttonHelp.Details";
	public static final String MAIN_WINDOW_TEXT_BUTTON_BACKUP_DETAILS = "buttonBackup.Details";
	public static final String MAIN_WINDOW_TEXT_BUTTON_RESTORE_DETAILS = "buttonRestore.Details";

	public static final String KEY_LANGUAGE_WINDOW = "LanguageWindow";
	public static final String LANGUAGE_WINDOW_TEXT_TITLE = "title";
	public static final String LANGUAGE_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String LANGUAGE_WINDOW_TEXT_LANGUAGE_NAME = "languageName";
	public static final String LANGUAGE_WINDOW_MESSAGE_BOX_LANGUAGE_NAME_EMPTY = "languageNameEmpty";
	public static final String LANGUAGE_WINDOW_MESSAGE_BOX_TEXT_EMPTY = "textEmpty";

	public static final String KEY_BACKUP_SETTINGS_WINDOW = "BackupSettingsWindow";
	public static final String BACKUP_SETTINGS_WINDOW_TEXT_TITLE = "title";
	public static final String BACKUP_SETTINGS_WINDOW_TEXT_EXPLAIN = "explain";

	public static final String KEY_CHANGE_PASSWORD_WINDOW = "ChangePasswordWindow";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_TITLE = "title";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_OLD_PASSWORD = "buttonOldPassword";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_NEW_PASSWORD = "buttonNewPassword";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_CONFIRM_PASSWORD = "buttonConfirmPassword";

	public static final String KEY_SERVERS_WINDOW = "ServersWindow";
	public static final String SERVERS_WINDOW_TEXT_TITLE = "title";
	public static final String SERVERS_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String SERVERS_WINDOW_TEXT_GROUP_SERVERS = "groupServers";
	public static final String SERVERS_WINDOW_MESSAGE_BOX_TEXT_DUPLICATE_SERVER = "messageDuplicateServer";

	public static final String BACKUP_SETTINGS_WINDOW_GENERAL = "general";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_SOURCE = "backupSource";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_SCHEDULE = "backupSchedule";
	public static final String BACKUP_SETTINGS_WINDOW_ENCRYPTION = "encryption";
	public static final String BACKUP_SETTINGS_WINDOW_CONTINOUS_DATA_PROTECTION = "continousDataProtection";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA = "inFileDelta";
	public static final String BACKUP_SETTINGS_WINDOW_RETENTION_POLICY = "retentionPolicy";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER = "backupFilter";
	public static final String BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL = "commandLineTool";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP = "extraBackup";
	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY = "localCopy";
	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS = "options";

	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_BACKUP_SET = "generalBackupSet";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_NAME = "generalName";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_TYPE = "generalType";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_USER_AUTHENTICATION_FOR_WINDOWS = "generalUserAuthenticationForWindows";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_DOMAIN = "generalDomain";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_USER_NAME = "generalUserName";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_PASSWORD = "generalPassword";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_LANGUAGE = "generalGroupLanguage";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_DELETE_LANGUAGE = "generalDeleteLanguage";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_DELETE_EROR_LANGUAGE = "generalDeleteErrorLanguage";
	public static final String BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_VALIDATE_LANGUAGE = "generalValidateLanguage";

	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_SCHEDULE_BUTTON_ENABLE = "backupScheduleButtonEnable";

	public static final String BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_ALGORITHM = "encryptionGroupAlgorithm";
	public static final String BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_MODE = "encryptionTextMode";
	public static final String BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_KEY_LENGTH = "encryptionTextKeyLength";
	public static final String BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_ENCRYPTING_KEY = "encryptionTextEncryptingKey";
	public static final String BACKUP_SETTINGS_WINDOW_ENCRYPTION_BUTTON_MASK_ENCRYPTING_KEY = "encryptionButtonMaskEncryptingKey";

	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_ENABLE = "continousDataProtectionButtonEnable";
	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_COMBO_TIME = "continousDataProtectionComboTime";
	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_COMBO_MINIMUM = "continousDataProtectionComboMinimum";
	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_LABEL_TYPE = "continousDataProtectionLabelType";
	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_BACKUP_FILES = "continousDataProtectionButtonBackupFiles";
	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_BACKUP_ALL_FILES = "continousDataProtectionButtonBackupAllFiles";
	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_CUSTOM = "continousDataProtectionButtonCustom";
	public static final String BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_DO_NOT_BACKUP = "continousDataProtectionButtonDoNotBackup";

	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_ENABLE = "inFileDeltaButtonEnable";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_BLOCK_SIZE = "inFileDeltaComboBlockSize";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_APPLY_TO = "inFileDeltaGroupApplyTo";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_MINIMUM_SIZE = "inFileDeltaComboMinimumSize";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_IN_FILE_DELTA = "inFileDeltaGroupInFileDelta";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_DEFAULT = "inFileDeltaComboDefault";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_UPLOAD_FILE = "inFileDeltaGroupUploadFile";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_NO_DELTA = "inFileDeltaButtonNoDelta";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_DELTA_RATIO = "inFileDeltaButtonDeltaRatio";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_LABEL_OR = "inFileDeltaLabelOr";
	public static final String BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_LABEL_EXPLICATION = "inFileDeltaLabelExplication";

	public static final String BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_KEEP_DELETE = "retentionPolicyButtonKeepDelete";
	public static final String BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_ADVANCED = "retentionPolicyButtonAdvanced";
	public static final String BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_REMOVE = "retentionPolicyButtonRemove";

	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_TEMPORARY_DIRECTORY = "optionsGroupTemporaryDirectory";
	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_VOLUME_SHADOW = "optionsGroupVolumeShadow";
	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS_BUTTON_ENABLE_VOLUME = "optionsButtonEnableVolume";
	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS_LABEL_VOLUME_EXPLICATION = "optionsLabelVolumeExplication";
	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_ADVANCED = "optionsGroupAdvanced";
	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS_COMBO_TRANSFER_BLOCK = "optionsComboTransferBlock";
	public static final String BACKUP_SETTINGS_WINDOW_OPTIONS_BUTTON_BACKUP_FILE = "optionsButtonBackupFile";

	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_ENABLE_COPY = "localCopyButtonEnableCopy";
	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_COPY = "localCopyGroupCopy";
	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_RETENCION = "localCopyGroupRetencion";
	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_REMOVE = "localCopyButtonRemove";
	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY_COMBO_KEEP_DELETED = "localCopyComboKeepDeleted";
	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_OFFSITE = "localCopyGroupOffsite";
	public static final String BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_SKIP = "localCopyButtonSkip";

	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_OFFLINE = "extraBackupGroupOffline";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_ENABLE = "extraBackupButtonEnable";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_COMBO_BACKUP_INTERVAL = "extraBackupComboBackupInterval";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_COMBO_EMAIL_NOTIFICATION = "extraBackupComboEmailNotification";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_SHOW_BACKUP = "extraBackupButtonShowBackup";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_SYSTEM_LOGOUT = "extraBackupGroupSystemLogout";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_ENABLE_SYSTEM_LOGOUT = "extraBackupButtonEnableSystemLogout";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_BACKUP = "extraBackupGroupBackup";
	public static final String BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_LABEL_FILE = "extraBackupLabelFile";

	public static final String BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_GROUP_PRE_BACKUP = "commandLineToolGroupPreBackup";
	public static final String BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_COMBO_NAME = "commandLineToolComboName";
	public static final String BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_WORKING_DIRECTORY = "commandLineToolTextWorkingDirectory";
	public static final String BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_COMMAND = "commandLineToolTextCommand";
	public static final String BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_GROUP_POST_BACKUP = "commandLineToolGroupPostBackup";

	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_COMBO_NAME = "backupFilterComboName";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_COMBO_TOP_DIRECTORY = "backupFilterComboTopDirectory";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_LABEL_APPLY_TO = "backupFilterLabelApplyTo";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_FILE = "backupFilterButtonFile";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_DIRECTORY = "backupFilterButtonDirectory";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_GROUP_PATTERN_MATCHING = "backupFilterGroupPatternMatching";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_LABEL_TYPE = "backupFilterLabelType";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTON_TEXT = "backupFilterButonText";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTON_UNIX_STYLE = "backupFilterButonUnixStyle";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_GROUP_FILTER_MODE = "backupFilterGroupFilterMode";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_INCLUDE_MATCHED = "backupFilterButtonIncludeMatched";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_EXCLUDE_MATCHED = "backupFilterButtonExcludeMatched";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_EXCLUDE_ALL_OTHERS = "backupFilterButtonExcludeAllOthers";
	public static final String BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_INCLUDE_ALL_OTHERS = "backupFilterButtonIncludeAllOthers";

	public static final String KEY_PATTERN_WINDOW = "PatternWindow";
	public static final String PATTERN_WINDOW_TITLE = "title";
	public static final String PATTERN_WINDOW_GROUP_PATTERN = "groupPattern";

	public static final String KEY_BACKUP_LOG_WINDOW = "BackupLogWindow";
	public static final String BACKUP_LOG_WINDOW_TITLE = "title";
	public static final String BACKUP_LOG_WINDOW_COMBO_BACKUP_SET = "comboBackupSet";
	public static final String BACKUP_LOG_WINDOW_COMBO_LOG = "comboLog";
	public static final String BACKUP_LOG_WINDOW_COMBO_SHOW = "comboShow";
	public static final String BACKUP_LOG_WINDOW_COMBO_LOGS_PER_PAGE = "comboLogsPerPage";
	public static final String BACKUP_LOG_WINDOW_COMBO_PAGE = "comboPage";
	public static final String BACKUP_LOG_WINDOW_TABLE_COLUMN_TYPE = "tableColumnType";
	public static final String BACKUP_LOG_WINDOW_TABLE_COLUMN_LOG = "tableColumnLog";
	public static final String BACKUP_LOG_WINDOW_TABLE_COLUMN_TIME = "tableColumnTime";

	public static final String KEY_ADVANCED_IN_FILE_DELTA_WINDOW = "AdvancedInFileDeltaWindow";

	public static final String ADVANCED_IN_FILE_DELTA_WINDOW_TITLE = "title";
	public static final String ADVANCED_IN_FILE_DELTA_WINDOW_GROUP_WEEKLY = "groupWeekly";
	public static final String ADVANCED_IN_FILE_DELTA_WINDOW_GROUP_YEARLY = "groupYearly";

	public static final String KEY_RETENCION_POLICY_WINDOW = "RetencionPolicyWindow";

	public static final String RETENCION_POLICY_WINDOW_TITLE = "title";
	public static final String RETENCION_POLICY_WINDOW_TEXT_NAME = "textName";
	public static final String RETENCION_POLICY_WINDOW_GROUP_TYPE = "groupType";
	public static final String RETENCION_POLICY_WINDOW_LABEL_TYPE_EXPLICATION = "labelTypeExplication";
	public static final String RETENCION_POLICY_WINDOW_COMBO_NUMBER_SNAPSHOTS = "comboNumberSnapshots";

	private Language() {
		Language.setDefaultLanguage();
	}

	public static Language getInstance() {
		try {
			if (Language.instance == null) {
				Language.instance = new Language();
			}
			return Language.instance;
		} catch (Exception ex) {
			Language.logger.fatal(ex, ex);
			return null;
		}
	}

	public static TreeMap<String, String> getDefaultLanguageMainWindow() {
		return Language.defaultLanguageMainWindow;
	}

	public static void setDefaultLanguageGeneral() {
		if (Language.defaultLanguageGeneral.size() == 0) {
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_BUTTON_OK, "OK");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_BUTTON_CANCEL, "Cancel");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_BUTTON_NO, "NO");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_BUTTON_YES, "YES");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_MESSAGE_TITLE_ERROR, "ERROR");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_MESSAGE_TITLE_INFO, "INFO");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_MESSAGE_TITLE_WARNING,
					"WARNING");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_MESSAGE_TITLE_QUESTION,
					"QUESTION");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_MESSAGE_TITLE_WAITING,
					"WAITING");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_FILETYPE_UNKNOWN, "File");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_FILETYPE_NONE, "File");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_FILETYPE_FOLDER, "Folder");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_FILESIZE_KB, "kb");

			Language.defaultLanguageGeneral.put(Language.GENERAL_SUNDAY, "Sunday");
			Language.defaultLanguageGeneral.put(Language.GENERAL_MONDAY, "Monday");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TUESDAY, "Tuesday");
			Language.defaultLanguageGeneral.put(Language.GENERAL_WEDNESDAY, "Wednesday");
			Language.defaultLanguageGeneral.put(Language.GENERAL_THURSDAY, "Thursday");
			Language.defaultLanguageGeneral.put(Language.GENERAL_FRIDAY, "Friday");
			Language.defaultLanguageGeneral.put(Language.GENERAL_SATURDAY, "Saturday");

			Language.defaultLanguageGeneral.put(Language.GENERAL_JANUARY, "January");
			Language.defaultLanguageGeneral.put(Language.GENERAL_FEBRUARY, "February");
			Language.defaultLanguageGeneral.put(Language.GENERAL_MARCH, "March");
			Language.defaultLanguageGeneral.put(Language.GENERAL_APRIL, "April");
			Language.defaultLanguageGeneral.put(Language.GENERAL_MAY, "May");
			Language.defaultLanguageGeneral.put(Language.GENERAL_JUNE, "June");
			Language.defaultLanguageGeneral.put(Language.GENERAL_JULY, "July");
			Language.defaultLanguageGeneral.put(Language.GENERAL_AUGUST, "August");
			Language.defaultLanguageGeneral.put(Language.GENERAL_SEPTEMBER, "September");
			Language.defaultLanguageGeneral.put(Language.GENERAL_OCTOBER, "October");
			Language.defaultLanguageGeneral.put(Language.GENERAL_NOVEMBER, "November");
			Language.defaultLanguageGeneral.put(Language.GENERAL_DECEMBER, "December");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_DAY, "Day");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_DAYS, "Day(s)");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_HOURS, "Hour(s)");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_BYTES, "Bytes");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_KBYTES, "Kbytes");
			Language.defaultLanguageGeneral.put(Language.GENERAL_TEXT_MINUTES, "minutes");
			Language.defaultLanguageGeneral.put(Language.GENERAL_BUTTON_CHANGE, "Change");
			Language.defaultLanguageGeneral.put(Language.GENERAL_BUTTON_ADD, "Add");
			Language.defaultLanguageGeneral.put(Language.GENERAL_BUTTON_REMOVE, "Remove");
			Language.defaultLanguageGeneral.put(Language.GENERAL_BUTTON_ADVANCED, "Advanced");

			Language.defaultLanguage.put(Language.KEY_GENERAL, Language.defaultLanguageGeneral);
		}
	}

	public static void setDefaultLanguageMainWindow() {
		if (Language.defaultLanguageMainWindow.size() == 0) {
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_TITLE,
					"Backup and Restore");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT,
					"User account");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET,
					"Backup Set");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE,
					"Schedule");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS,
					"Settings");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS,
					"Reports");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP,
					"Backup now");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE,
					"Restore");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_HELP, "Help");

			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT_DETAILS,
					"Setup your user profile including your username and password to login to the backup server.");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET_DETAILS,
					"Manage you backup set. Select what to backup. Use advanced backup set to choose custom files and folders.");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE_DETAILS,
					"Schedule your backups. Daily, weekly, monthly, yearly. Buy or full backup suite for custom schedules.");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS_DETAILS,
					"Change the application and backup settings. Features like in-file-changes backup can be switched on or off.");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS_DETAILS,
					"Backup, restore and error reports. Only available in full backup suite. You can receive reports by email.");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_DETAILS,
					"Backup your backupset now. You can create a new backupset if you haven't done already.");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE_DETAILS,
					"Recover your lost files. Select which files to recover. You can also define filter.");
			Language.defaultLanguageMainWindow.put(Language.MAIN_WINDOW_TEXT_BUTTON_HELP_DETAILS,
					"Look for answers to your questions and a howto guide here.");

			Language.defaultLanguage.put(Language.KEY_MAIN_WINDOW,
					Language.defaultLanguageMainWindow);
		}
	}

	public static void setDefaultChangePasswordWindow() {
		if (Language.defaultChangePasswordWindow.size() == 0) {
			Language.defaultChangePasswordWindow.put(Language.CHANGE_PASSWORD_WINDOW_TEXT_TITLE,
					"Change password");
			Language.defaultChangePasswordWindow.put(Language.CHANGE_PASSWORD_WINDOW_TEXT_EXPLAIN,
					"Here you can change the password.");
			Language.defaultChangePasswordWindow.put(Language.CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_OLD_PASSWORD,
					"Old password");
			Language.defaultChangePasswordWindow.put(Language.CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_NEW_PASSWORD,
					"New password");
			Language.defaultChangePasswordWindow.put(Language.CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_CONFIRM_PASSWORD,
					"Confirm password");

			Language.defaultLanguage.put(Language.KEY_CHANGE_PASSWORD_WINDOW,
					Language.defaultChangePasswordWindow);
		}
	}

	public static void setDefaultServersWindow() {
		if (Language.defaultServersWindow.size() == 0) {
			Language.defaultServersWindow.put(Language.SERVERS_WINDOW_TEXT_TITLE, "Servers");
			Language.defaultServersWindow.put(Language.SERVERS_WINDOW_TEXT_EXPLAIN,
					"Here you can add/delete servers.");
			Language.defaultServersWindow.put(Language.SERVERS_WINDOW_TEXT_GROUP_SERVERS, "Servers");
			Language.defaultServersWindow.put(Language.SERVERS_WINDOW_MESSAGE_BOX_TEXT_DUPLICATE_SERVER,
					"This server already exist in the list.");
			Language.defaultLanguage.put(Language.KEY_SERVERS_WINDOW, Language.defaultServersWindow);
		}
	}

	public static void setDefaultBackupLogWindow() {
		if (Language.defaultBackupLogWindow.size() == 0) {

			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_TITLE, "Backup Log");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_COMBO_BACKUP_SET,
					"Backup Set");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_COMBO_LOG, "Log");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_COMBO_SHOW, "Show");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_COMBO_LOGS_PER_PAGE,
					"Logs per page");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_COMBO_PAGE, "Page");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_TABLE_COLUMN_TYPE,
					"Type");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_TABLE_COLUMN_LOG, "Log");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_TABLE_COLUMN_TIME,
					"Time");

			Language.defaultLanguage.put(Language.KEY_BACKUP_LOG_WINDOW,
					Language.defaultBackupLogWindow);
		}
	}

	public static void setDefaultLanguageLanguageWindow() {
		if (Language.defaultLanguageLanguageWindow.size() == 0) {
			Language.defaultLanguageLanguageWindow.put(Language.LANGUAGE_WINDOW_TEXT_TITLE,
					"Languages");
			Language.defaultLanguageLanguageWindow.put(Language.LANGUAGE_WINDOW_TEXT_EXPLAIN,
					"You can edit languages or make new languages.");
			Language.defaultLanguageLanguageWindow.put(Language.LANGUAGE_WINDOW_TEXT_LANGUAGE_NAME,
					"Language name");
			Language.defaultLanguageLanguageWindow.put(Language.LANGUAGE_WINDOW_MESSAGE_BOX_LANGUAGE_NAME_EMPTY,
					"Language name can not be empty.");
			Language.defaultLanguageLanguageWindow.put(Language.LANGUAGE_WINDOW_MESSAGE_BOX_TEXT_EMPTY,
					"All texts must be completed.");
			Language.defaultLanguage.put(Language.KEY_LANGUAGE_WINDOW,
					Language.defaultLanguageLanguageWindow);
		}
	}

	public static void setDefaultLanguageBachupSettingsWindow() {
		if (Language.defaultLanguageBackupSettingsWindow.size() == 0) {
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_TEXT_TITLE,
					"Backup Settings");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_TEXT_EXPLAIN,
					"Here you can change the backup settings.");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL,
					"General");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_SOURCE,
					"Backup Source");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_SCHEDULE,
					"Backup Schedule");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION,
					"Encryption");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_CONTINOUS_DATA_PROTECTION,
					"Continous Data Protection");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA,
					"In-File Delta");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_RETENTION_POLICY,
					"Retention Policy");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER,
					"Backup Filter");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL,
					"Command Line Tool");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP,
					"Extra Backup");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY,
					"Local Copy");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS,
					"Options");

			// general
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_BACKUP_SET,
					"Backup Set");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_NAME,
					"Name");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_TYPE,
					"Type");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_USER_AUTHENTICATION_FOR_WINDOWS,
					"User Authentication For Windows");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_DOMAIN,
					"Domain");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_USER_NAME,
					"User Name");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_PASSWORD,
					"Password");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_LANGUAGE,
					"Language");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_DELETE_LANGUAGE,
					"Do you really  want to delete this language?");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_DELETE_EROR_LANGUAGE,
					"This language can not be deleted!");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_VALIDATE_LANGUAGE,
					"You must select the language.");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_SCHEDULE_BUTTON_ENABLE,
					"Run scheduled backup on this computer");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_ALGORITHM,
					"Algorithm");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_MODE,
					"Mode");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_KEY_LENGTH,
					"Key Length");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_ENCRYPTING_KEY,
					"Encrypting Key");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_BUTTON_MASK_ENCRYPTING_KEY,
					"Mask Encrypting Key");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_ENABLE,
					"Enable Continuous Data Protection (local disk only)");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_COMBO_TIME,
					"Time Mark Interval");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_COMBO_MINIMUM,
					"Minimum Update Interval");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_LABEL_TYPE,
					"Type");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_BACKUP_FILES,
					"Backup file(s) selected by backup sources and filters");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_BACKUP_ALL_FILES,
					"Backup all files");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_CUSTOM,
					"Custom");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_DO_NOT_BACKUP,
					"Do not backup files defined as system files");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_ENABLE,
					"Enable In-File Delta");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_BLOCK_SIZE,
					"Block Size");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_APPLY_TO,
					"Apply to files");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_MINIMUM_SIZE,
					"Minimum size");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_IN_FILE_DELTA,
					"In-File Delta Type");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_DEFAULT,
					"Default");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_UPLOAD_FILE,
					"Upload full file when");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_NO_DELTA,
					"No. of Delta >");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_DELTA_RATIO,
					"Delta Ratio * >");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_LABEL_OR,
					"or");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_LABEL_EXPLICATION,
					"* Delta Ratio = Delta File Size / Full File Size");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_KEEP_DELETE,
					"Keep deleted file(s) for");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_ADVANCED,
					"Advanced");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_REMOVE,
					"Remove retencion files for overlap policy");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_TEMPORARY_DIRECTORY,
					"Temporary directory for storing backup files");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_VOLUME_SHADOW,
					"Volume shadow copy");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_BUTTON_ENABLE_VOLUME,
					"Enable Volume Shadow Copy");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_LABEL_VOLUME_EXPLICATION,
					"* Only for Microsoft Windows 2003, XP, Vista & 2008");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_ADVANCED,
					"Advanced Options");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_COMBO_TRANSFER_BLOCK,
					"Transfer Block Size");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_BUTTON_BACKUP_FILE,
					"Backup File Permissions");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_ENABLE_COPY,
					"Copy to Local Hard Disk");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_COPY,
					"Copy to");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_RETENCION,
					"Retencion Policy");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_REMOVE,
					"Remove deleted files in retencion area");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_COMBO_KEEP_DELETED,
					"Keep deleted file(s) for");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_OFFSITE,
					"Offsite Backup");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_SKIP,
					"Skip backing up to Ahsay Offsite Backup Server");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_OFFLINE,
					"Off-line Backup");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_ENABLE,
					"Enable off-line backup");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_COMBO_BACKUP_INTERVAL,
					"Backup Interval");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_COMBO_EMAIL_NOTIFICATION,
					"Email Notification");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_SHOW_BACKUP,
					"Show backup reminder");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_SYSTEM_LOGOUT,
					"System Logout Backup Reminder");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_ENABLE_SYSTEM_LOGOUT,
					"Enable System Logout Backup Reminder");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_BACKUP,
					"Backup");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_LABEL_FILE,
					"File");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_GROUP_PRE_BACKUP,
					"Pre-Backup");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_COMBO_NAME,
					"Name");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_WORKING_DIRECTORY,
					"Working Directory");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_COMMAND,
					"Command");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_GROUP_POST_BACKUP,
					"Post-Backup");

			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_COMBO_NAME,
					"Name");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_COMBO_TOP_DIRECTORY,
					"Top Directory");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_LABEL_APPLY_TO,
					"Apply to");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_FILE,
					"File");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_DIRECTORY,
					"Directory");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_GROUP_PATTERN_MATCHING,
					"Pattern Matching");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_LABEL_TYPE,
					"Type");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTON_TEXT,
					"Text");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTON_UNIX_STYLE,
					"Unix-style Regular Expression");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_GROUP_FILTER_MODE,
					"Filter Mode");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_INCLUDE_MATCHED,
					"Include matched");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_EXCLUDE_MATCHED,
					"Exclude matched");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_EXCLUDE_ALL_OTHERS,
					"exclude all others");
			Language.defaultLanguageBackupSettingsWindow.put(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_INCLUDE_ALL_OTHERS,
					"include all others");

			Language.defaultLanguage.put(Language.KEY_BACKUP_SETTINGS_WINDOW,
					Language.defaultLanguageBackupSettingsWindow);
		}
	}

	public static void setDefaultLanguagePatternWindow() {
		if (Language.defaultLanguagePatternWindow.size() == 0) {

			Language.defaultLanguagePatternWindow.put(Language.PATTERN_WINDOW_TITLE,
					"Add new pattern");
			Language.defaultLanguagePatternWindow.put(Language.PATTERN_WINDOW_GROUP_PATTERN,
					"Pattern");

			Language.defaultLanguage.put(Language.KEY_PATTERN_WINDOW,
					Language.defaultLanguagePatternWindow);
		}
	}

	public static void setDefaultAdvancedInFileDeltaWindow() {
		if (Language.defaultAdvancedInFileDeltaWindow.size() == 0) {

			Language.defaultAdvancedInFileDeltaWindow.put(Language.ADVANCED_IN_FILE_DELTA_WINDOW_TITLE,
					"Advanced In-File Delta Type Setting");
			Language.defaultAdvancedInFileDeltaWindow.put(Language.ADVANCED_IN_FILE_DELTA_WINDOW_GROUP_WEEKLY,
					"Weekly Variation (Override Default Settings)");
			Language.defaultAdvancedInFileDeltaWindow.put(Language.ADVANCED_IN_FILE_DELTA_WINDOW_GROUP_YEARLY,
					"Yearly Variation (Override Weekly Variation or Default Settings)");

			Language.defaultLanguage.put(Language.KEY_ADVANCED_IN_FILE_DELTA_WINDOW,
					Language.defaultAdvancedInFileDeltaWindow);
		}
	}

	public static void setDefaultRetencionPolicyWindow() {
		if (Language.defaultRetencionPolicyWindow.size() == 0) {

			Language.defaultRetencionPolicyWindow.put(Language.RETENCION_POLICY_WINDOW_TITLE,
					"Add new retencion policy");
			Language.defaultRetencionPolicyWindow.put(Language.RETENCION_POLICY_WINDOW_TEXT_NAME,
					"Name");
			Language.defaultRetencionPolicyWindow.put(Language.RETENCION_POLICY_WINDOW_GROUP_TYPE,
					"Type");
			Language.defaultRetencionPolicyWindow.put(Language.RETENCION_POLICY_WINDOW_LABEL_TYPE_EXPLICATION,
					"Keep retencion files for the latest days.");

			Language.defaultRetencionPolicyWindow.put(Language.RETENCION_POLICY_WINDOW_COMBO_NUMBER_SNAPSHOTS,
					"Number of snapshots to keep");

			Language.defaultLanguage.put(Language.KEY_RETENCION_POLICY_WINDOW,
					Language.defaultRetencionPolicyWindow);
		}
	}

	public static void setDefaultLanguage() {
		Language.setDefaultLanguageGeneral();
		Language.setDefaultLanguageMainWindow();
		Language.setDefaultLanguageLanguageWindow();
		Language.setDefaultLanguageBachupSettingsWindow();
		Language.setDefaultLanguagePatternWindow();
		Language.setDefaultAdvancedInFileDeltaWindow();
		Language.setDefaultRetencionPolicyWindow();
		Language.setDefaultChangePasswordWindow();
		Language.setDefaultServersWindow();
		Language.setDefaultBackupLogWindow();
	}

	@SuppressWarnings("unchecked")
	public void activateDefaultLanguage() {
		try {
			Language.language = (TreeMap<String, TreeMap<String, String>>) Language.defaultLanguage.clone();
		} catch (Exception e) {
			Language.logger.fatal(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	public void setLanguage(String fileName) {
		try {
			if (fileName.indexOf("ENGLISH(default)") != -1) {
				Language.language = (TreeMap<String, TreeMap<String, String>>) Language.defaultLanguage.clone();// if
				// default
				// language is
				// selected
				return;
			}
			INIFile ini = new INIFile(AppConstants.LNG_DIR + fileName);
			ini.loadFile();
			Language.language = ini.getSections();
		} catch (Exception e) {
			Language.logger.fatal(e, e);
			Language.language = (TreeMap<String, TreeMap<String, String>>) Language.defaultLanguage.clone();// if
			// file
			// not
			// found
		}
	}

	public static String getText(String section, String key) {
		String text = "";
		try {
			TreeMap<String, String> prop = Language.language.get(section);
			if (prop == null) {
				prop = Language.defaultLanguage.get(section);
				if (prop == null) {
					return " - ";
				}
			}
			text = prop.get(key);
			if ((text == null) || (text.length() == 0)) {
				text = Language.defaultLanguage.get(section).get(key);
				if ((text == null) || (text.length() == 0)) {
					text = " - ";
				}
			}

		} catch (Exception e) {
			Language.logger.fatal(e, e);
			return " - ";
		}
		return text;
	}

	public static String getTextGeneral(String key) {
		return Language.getText(Language.KEY_GENERAL, key);
	}

	public static String getTextMainWindow(String key) {
		return Language.getText(Language.KEY_MAIN_WINDOW, key);
	}

	public static String getTextChangePasswordWindow(String key) {
		return Language.getText(Language.KEY_CHANGE_PASSWORD_WINDOW, key);
	}

	public static String getTextServersWindow(String key) {
		return Language.getText(Language.KEY_SERVERS_WINDOW, key);
	}

	public static String getTextBackupLogWindow(String key) {
		return Language.getText(Language.KEY_BACKUP_LOG_WINDOW, key);
	}

	public static String getTextLanguageWindow(String key) {
		return Language.getText(Language.KEY_LANGUAGE_WINDOW, key);
	}

	public static String getTextBackupSettingsWindow(String key) {
		return Language.getText(Language.KEY_BACKUP_SETTINGS_WINDOW, key);
	}

	public static String getTextPatternWindow(String key) {
		return Language.getText(Language.KEY_PATTERN_WINDOW, key);
	}

	public static String getTextAdvancedInFileDeltaWindow(String key) {
		return Language.getText(Language.KEY_ADVANCED_IN_FILE_DELTA_WINDOW, key);
	}

	public static String getTextRetencionPolicyWindow(String key) {
		return Language.getText(Language.KEY_RETENCION_POLICY_WINDOW, key);
	}

	public static TreeMap<String, String> getLanguageWindow(String key) {
		return Language.language.get(key);
	}

	public static TreeMap<String, TreeMap<String, String>> getDefaultLanguage() {
		return Language.defaultLanguage;
	}
}
