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
	private static final TreeMap<String, String> defaultLanguageSettingsWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageOpenFileWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageLanguageWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageUserProfileWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageLoginWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageBackupScheduleWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageRestoreFilterCriteriaWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageRestoreFilterWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageBackupSourceWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageBackupSettingsWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageFilesWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageChangePathWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageRestoreWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageSearchWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageBackUpFilterWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultLanguageSelectDirectoryWindow = new TreeMap<String, String>();
	private static final TreeMap<String, String> defaultChangePasswordWindow = new TreeMap<String, String>();
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

	public static final String KEY_SETTINGS_WINDOW = "SettingsWindow";
	public static final String SETTINGS_WINDOW_TEXT_TITLE = "title";
	public static final String SETTINGS_WINDOW_TEXT_EXPLAIN = "explain";

	public static final String KEY_ADVANCED_BACKUP_SOURCE_WINDOW = "AdvancedBackupSourceWindow";
	public static final String ADVANCED_BACKUP_SOURCE_WINDOW_TEXT_TITLE = "title";
	public static final String ADVANCED_BACKUP_SOURCE_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String ADVANCED_BACKUP_SOURCE_WINDOW_LABEL_ALLFOLDERS = "labelAllFolders";
	public static final String ADVANCED_BACKUP_SOURCE_WINDOW_BUTTON_BACKUP_FILTER = "buttonBackUpFilter";
	public static final String ADVANCED_BACKUP_SOURCE_WINDOW_MESSAGE_BOX_ONE_FILE = "messageBoxOneFile";

	public static final String KEY_LANGUAGE_WINDOW = "LanguageWindow";
	public static final String LANGUAGE_WINDOW_TEXT_TITLE = "title";
	public static final String LANGUAGE_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String LANGUAGE_WINDOW_TEXT_LANGUAGE_NAME = "languageName";
	public static final String LANGUAGE_WINDOW_MESSAGE_BOX_LANGUAGE_NAME_EMPTY = "languageNameEmpty";
	public static final String LANGUAGE_WINDOW_MESSAGE_BOX_TEXT_EMPTY = "textEmpty";

	public static final String KEY_USER_PROFILE_WINDOW = "UserProfileWindow";
	public static String USER_PROFILE_WINDOW_TITLE = "title";
	public static String USER_PROFILE_WINDOW_EXPLAIN = "explain";
	public static String USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TITLE = "groupUserInformation";
	public static String USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_NAME = "textLoginName";
	public static String USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_PASSWORD = "textPassword";
	public static String USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_BUTTON_CHANGE = "buttonChange";
	public static String USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_BUTTON_SAVE_PASSWORD = "buttonSavePassword";
	public static String USER_PROFILE_WINDOW_GROUP_CONTACT_TITLE = "groupContact";
	public static String USER_PROFILE_WINDOW_GROUP_CONTACT_TEXT_NAME = "textName";
	public static String USER_PROFILE_WINDOW_GROUP_CONTACT_TEXT_EMAIL = "textEmail";
	public static String USER_PROFILE_WINDOW_GROUP_TIME_ZONE_TITLE = "groupTimeZone";

	public static final String KEY_LOGIN_WINDOW = "LoginWindow";
	public static String LOGIN_WINDOW_TITLE = "title";
	public static String LOGIN_WINDOW_EXPLAIN = "explain";
	public static String LOGIN_WINDOW_GROUP_USER_INFORMATION_TITLE = "groupUserInformation";
	public static String LOGIN_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_NAME = "textLoginName";
	public static String LOGIN_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_PASSWORD = "textPassword";
	public static String LOGIN_WINDOW_GROUP_USER_INFORMATION_BUTTON_CHANGE = "buttonChange";
	public static String LOGIN_WINDOW_GROUP_USER_INFORMATION_BUTTON_SAVE_PASSWORD = "buttonSavePassword";
	public static String LOGIN_WINDOW_GROUP_CONTACT_TITLE = "groupContact";
	public static String LOGIN_WINDOW_GROUP_CONTACT_TEXT_NAME = "textName";
	public static String LOGIN_WINDOW_GROUP_CONTACT_TEXT_EMAIL = "textEmail";
	public static String LOGIN_WINDOW_GROUP_TIME_ZONE_TITLE = "groupTimeZone";
	public static String LOGIN_WINDOW_GROUP_SERVER_INFORMATION_TITLE = "serverInformation";
	public static String LOGIN_WINDOW_GROUP_SERVER_INFORMATION_TEXT_SERVER_NAME = "serverName";

	public static final String KEY_BACKUP_SCHEDULE_WINDOW = "BackupScheduleWindow";
	public static String BACKUP_SCHEDULE_WINDOW_TITLE = "title";
	public static String BACKUP_SCHEDULE_WINDOW_EXPLAIN = "explain";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_RUN_SCHEDULE = "buttonRunSchedule";
	public static String BACKUP_SCHEDULE_WINDOW_LABEL_BACKUP = "labelBackup";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_SUNDAY = "buttonSunday";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_MONDAY = "buttonMonday";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_TUESDAY = "buttonTuesday";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_WEDNESDAY = "buttonWednesday";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_THURSDAY = "buttonThursday";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_FRIDAY = "buttonFriday";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_SATURDAY = "buttonSaturday";
	public static String BACKUP_SCHEDULE_WINDOW_GROUP_TIME = "groupTime";
	public static String BACKUP_SCHEDULE_WINDOW_LABEL_START = "labelStart";
	public static String BACKUP_SCHEDULE_WINDOW_LABEL_STOP = "labelStop";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_ON_COMPLETION = "buttonOnCompletion";
	public static String BACKUP_SCHEDULE_WINDOW_BUTTON_AFTER = "buttonAfter";
	public static String BACKUP_SCHEDULE_WINDOW_LABEL_HOURS = "labelHours";

	public static final String KEY_RESTORE_FILTER_CRITERIA_WINDOW = "RestoreFilterCriteriaWindow";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_TEXT_TITLE = "title";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_GROUP_PATTERN = "groupPattern";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_TEXT_PATTERN = "textPattern";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TYPE = "comboType";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_BUTTON_MATCH_CASE = "buttonMatchCase";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_EXACT = "exact";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_START_WITH = "start with";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_ENDS_WITH = "ends with";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER = "messageBoxTextIllegalCharacter";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT = "messageBoxTextOneDot";
	public static final String RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_PATTERN_EMPTY = "messageBoxTextPatternEmpty";

	public static final String KEY_BACKUP_SOURCE_WINDOW = "BackUpSourceWindow";
	public static final String BACKUP_SOURCE_WINDOW_TEXT_TITLE = "title";
	public static final String BACKUP_SOURCE_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String BACKUP_SOURCE_WINDOW_BUTTON_ADVANCE = "buttonAdvanced";
	public static final String BACKUP_SOURCE_WINDOW_BUTTON_ENCRYPT_FILES = "buttonEncryptFiles";
	public static final String BACKUP_SOURCE_WINDOW_BUTTON_COMPRESSION = "buttonCompression";

	public static final String KEY_BACKUP_SETTINGS_WINDOW = "BackupSettingsWindow";
	public static final String BACKUP_SETTINGS_WINDOW_TEXT_TITLE = "title";
	public static final String BACKUP_SETTINGS_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String BACKUP_SETTINGS_WINDOW_GROUP_LANGUAGE = "groupLanguage";
	public static final String BACKUP_SETTINGS_WINDOW_GROUP_ENCRYPTION = "groupEncryption";
	public static final String BACKUP_SETTINGS_WINDOW_GROUP_TEMPORARY_FILE = "groupTemporaryFile";
	public static final String BACKUP_SETTINGS_WINDOW_GROUP_ADVANCE_SETTINGS = "groupAdvanceSettings";
	public static final String BACKUP_SETTINGS_WINDOW_GROUP_RECYCLE_BIN = "groupRecycleBin";
	public static final String BACKUP_SETTINGS_WINDOW_TEXT_ENCRYPTION_KEY = "textEncryptingKey";
	public static final String BACKUP_SETTINGS_WINDOW_BUTTON_MASK_ENCRYPTING_KEY = "buttonMaskEncryptingKey";
	public static final String BACKUP_SETTINGS_WINDOW_BUTTON_CHANGE = "buttonChange";
	public static final String BACKUP_SETTINGS_WINDOW_COMBO_RECYCLE_BIN = "comboRecycleBin";
	public static final String BACKUP_SETTINGS_WINDOW_RECYCLE_BIN_DAYS = "recycleBinDays";
	public static final String BACKUP_SETTINGS_WINDOW_BUTTON_BACKUP_FILE = "buttonBackupFile";
	public static final String BACKUP_SETTINGS_WINDOW_MESSAGE_DELETE_LANGUAGE_QUESTION = "messageDeleteLanguageQuestion";
	public static final String BACKUP_SETTINGS_WINDOW_MESSAGE_DELETE_LANGUAGE_ERROR = "messageDeleteLanguageError";

	public static final String KEY_FILES_WINDOW = "FilesWindow";
	public static final String FILES_WINDOW_TABLE_NAME = "table.Name";
	public static final String FILES_WINDOW_TABLE_SIZE = "table.Size";
	public static final String FILES_WINDOW_TABLE_TYPE = "table.Type";
	public static final String FILES_WINDOW_TABLE_MODIFIED = "table.Modified";
	public static final String FILES_WINDOW_COMBO_ITEMS = "comboItems";
	public static final String FILES_WINDOW_COMBO_PAGE = "comboPage";

	public static final String KEY_CHANGE_PATH_WINDOW = "ChangePathWindow";
	public static final String CHANGE_PATH_WINDOW_TEXT_TITLE = "title";
	public static final String CHANGE_PATH_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String CHANGE_PATH_WINDOW_MESSAGE_BOX_TEXT_ONE_ITEM_SELECTED = "messageBoxTextOneItemSelected";

	public static final String KEY_RESTORE_WINDOW = "RestoreWindow";
	public static final String RESTORE_WINDOW_TEXT_TITLE = "title";
	public static final String RESTORE_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String RESTORE_WINDOW_BUTTON_SHOW_FILES = "buttonShowFiles";
	public static final String RESTORE_WINDOW_BUTTON_SHOW_ALL_FILES = "buttonShowAllFiles";
	public static final String RESTORE_WINDOW_BUTTON_FILTER = "buttonFilter";
	public static final String RESTORE_WINDOW_GROUP_RESTORE = "groupRestore";
	public static final String RESTORE_WINDOW_BUTTON_ORIGINAL_LOCATION = "labelOriginalLocation";
	public static final String RESTORE_WINDOW_LABEL_ORIGINAL_LOCATION = "buttonOriginalLocation";
	public static final String RESTORE_WINDOW_BUTTON_ALTERNATIVE_LOCATION = "buttonAlternativeLocation";
	public static final String RESTORE_WINDOW_BUTTON_CHANGE = "buttonChange";
	public static final String RESTORE_WINDOW_BUTTON_DELETE_EXTRA_FILE = "buttonDeleteExtraFiles";
	public static final String RESTORE_WINDOW_BUTTON_RESTORE_FILE = "buttonRestoreFile";
	public static final String RESTORE_WINDOW_BUTTON_SEARCH = "buttonSearch";
	public static final String RESTORE_WINDOW_BUTTON_OVERWRITE = "buttonOverwrite";

	public static final String KEY_SEARCH_WINDOW = "SearchWindow";
	public static final String SEARCH_WINDOW_TEXT_TITLE = "title";
	public static final String SEARCH_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String SEARCH_WINDOW_TEXT_LOOK_IN = "textLookIn";
	public static final String SEARCH_WINDOW_BUTTON_CHANGE = "buttonChange";
	public static final String SEARCH_WINDOW_TEXT_PATTERN = "textPattern";
	public static final String SEARCH_WINDOW_COMBO_TYPE = "comboType";
	public static final String SEARCH_WINDOW_COMBO_TYPE_CONTAINS = "comboTypeContains";
	public static final String SEARCH_WINDOW_COMBO_TYPE_EXACT = "comboTypeExact";
	public static final String SEARCH_WINDOW_COMBO_TYPE_START_WITH = "comboTypeStartWith";
	public static final String SEARCH_WINDOW_COMBO_TYPE_ENDS_WITH = "comboTypeEndWith";

	public static final String SEARCH_WINDOW_COMBO_APPLY_TO = "comboApplyTo";
	public static final String SEARCH_WINDOW_COMBO_APPLY_TO_FILES_DIRECTORIES = "comboApplyToFileAndDirectory";
	public static final String SEARCH_WINDOW_COMBO_APPLY_TO_FILES = "comboApplyToFiles";
	public static final String SEARCH_WINDOW_COMBO_APPLY_TO_DIRECTORIES = "comboApplyToDirectories";
	public static final String SEARCH_WINDOW_BUTTON_SEARCH_SUB_FOLDERS = "buttonSearchSubFolders";
	public static final String SEARCH_WINDOW_BUTTON_MATCH_CASE = "buttonMatchCase";
	public static final String SEARCH_WINDOW_BUTTON_SEARCH = "buttonSearch";
	public static final String SEARCH_WINDOW_BUTTON_STOP = "buttonStop";
	public static final String SEARCH_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER = "messageBoxTextIllegalCharacter";
//	public static final String SEARCH_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT = "messageBoxTextOneDot";
	public static final String SEARCH_WINDOW_MESSAGE_BOX_TEXT_LOOK_IN_DIR = "messageBoxTextLookInDir";

	public static final String KEY_BACKUP_FILTER_WINDOW = "BackUpFilterWindow";
	public static final String BACKUP_FILTER_WINDOW_TEXT_TITLE = "title";
	public static final String BACKUP_FILTER_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String BACKUP_FILTER_WINDOW_COMBO_OPTION_1 = "comboOption1";
	public static final String BACKUP_FILTER_WINDOW_COMBO_OPTION_2 = "comboOption2";
	public static final String BACKUP_FILTER_WINDOW_GROUP_FILE_NAME_EXTENSION = "groupFileNameExtension";
	public static final String BACKUP_FILTER_WINDOW_COMBO_APPLY_TO = "comboApplyTo";
	public static final String BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ONE_FILTER = "messageBoxTextOneFilter";
	public static final String BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATH_NOT_EXIST = "messageBoxTextPathNotExist";
	public static final String BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATH_DUPLICATE = "messageBoxTextPathDuplicate";
	public static final String BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATTERN_EMPTY = "messageBoxTextPaternEmpty";
	public static final String BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER = "messageBoxTextIllegalCharacter";
	public static final String BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT = "messageBoxTextOneDot";
	public static final String BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATTERN_DUPLICATE = "messageBoxTextPatternDuplicate";

	public static final String KEY_BACKUP_LOG_WINDOW = "BackupLogWindow";
	public static final String BACKUP_LOG_WINDOW_TITLE = "title";
	public static final String BACKUP_LOG_WINDOW_EXPLAIN = "explain";
	public static final String BACKUP_LOG_WINDOW_COMBO_BACKUP_SET = "comboBackupSet";
	public static final String BACKUP_LOG_WINDOW_COMBO_LOG = "comboLog";
	public static final String BACKUP_LOG_WINDOW_COMBO_SHOW = "comboShow";
	public static final String BACKUP_LOG_WINDOW_COMBO_LOGS_PER_PAGE = "comboLogsPerPage";
	public static final String BACKUP_LOG_WINDOW_COMBO_PAGE = "comboPage";
	public static final String BACKUP_LOG_WINDOW_TABLE_COLUMN_TYPE = "tableColumnType";
	public static final String BACKUP_LOG_WINDOW_TABLE_COLUMN_LOG = "tableColumnLog";
	public static final String BACKUP_LOG_WINDOW_TABLE_COLUMN_TIME = "tableColumnTime";

	public static final String KEY_CHANGE_PASSWORD_WINDOW = "ChangePasswordWindow";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_TITLE = "title";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_EXPLAIN = "explain";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_OLD_PASSWORD = "buttonOldPassword";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_NEW_PASSWORD = "buttonNewPassword";
	public static final String CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_CONFIRM_PASSWORD = "buttonConfirmPassword";

	public static final String KEY_RESTORE_FILTER_WINDOW = "RestoreFilterWindow";
	public static final String RESTORE_FILTER_WINDOW_TEXT_TITLE = "title";
	public static final String RESTORE_FILTER_WINDOW_TEXT_EXPLAIN = "explain";
	// public static final String RESTORE_FILTER_WINDOW_COMBO_OPTION_1 = "comboOption1";
	// public static final String RESTORE_FILTER_WINDOW_COMBO_OPTION_2 = "comboOption2";
	public static final String RESTORE_FILTER_WINDOW_GROUP_FILE_NAME_EXTENSION = "groupFileNameExtension";
	// public static final String RESTORE_FILTER_WINDOW_COMBO_APPLY_TO = "comboApplyTo";

	public static final String KEY_SELECT_DIRECTORY_WINDOW = "SelectDirectoryFilterWindow";
	public static final String SELECT_DIRECTORY_WINDOW_TEXT_TITLE = "title";
	public static final String SELECT_DIRECTORY_WINDOW_TEXT_EXPLAIN = "explain";

	
	private Language() {
		setDefaultLanguage();
	}

	public static Language getInstance() {
		try {
			if (instance == null)
				instance = new Language();
			return instance;
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			return null;
		}
	}

	public static TreeMap<String, String> getDefaultLanguageMainWindow() {
		return defaultLanguageMainWindow;
	}

	public static void setDefaultLanguageGeneral() {
		if (defaultLanguageGeneral.size() == 0) {
			defaultLanguageGeneral.put(GENERAL_TEXT_BUTTON_OK, "OK");
			defaultLanguageGeneral.put(GENERAL_TEXT_BUTTON_CANCEL, "Cancel");
			defaultLanguageGeneral.put(GENERAL_TEXT_BUTTON_NO, "NO");
			defaultLanguageGeneral.put(GENERAL_TEXT_BUTTON_YES, "YES");
			defaultLanguageGeneral.put(GENERAL_TEXT_MESSAGE_TITLE_ERROR,
					"ERROR");
			defaultLanguageGeneral.put(GENERAL_TEXT_MESSAGE_TITLE_INFO, "INFO");
			defaultLanguageGeneral.put(GENERAL_TEXT_MESSAGE_TITLE_WARNING,
					"WARNING");
			defaultLanguageGeneral.put(GENERAL_TEXT_MESSAGE_TITLE_QUESTION,
					"QUESTION");
			defaultLanguageGeneral.put(GENERAL_TEXT_MESSAGE_TITLE_WAITING,
					"WAITING");
			defaultLanguageGeneral.put(GENERAL_TEXT_FILETYPE_UNKNOWN, "File");
			defaultLanguageGeneral.put(GENERAL_TEXT_FILETYPE_NONE, "File");
			defaultLanguageGeneral.put(GENERAL_TEXT_FILETYPE_FOLDER, "Folder");
			defaultLanguageGeneral.put(GENERAL_TEXT_FILESIZE_KB, "kb");

			defaultLanguage.put(KEY_GENERAL, defaultLanguageGeneral);
		}
	}

	public static void setDefaultLanguageMainWindow() {
		if (defaultLanguageMainWindow.size() == 0) {
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_TITLE,
					"Backup and Restore");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT,
					"USER ACCOUNT");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET,
					"BACKUP SET");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_SCHEDULE,
					"SCHEDULE");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_SETTINGS,
					"SETTINGS");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_REPORTS,
					"REPORTS");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_BACKUP,
					"BACKUP");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_RESTORE,
					"RESTORE");
			defaultLanguageMainWindow.put(MAIN_WINDOW_TEXT_BUTTON_HELP, "HELP");

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

			defaultLanguage.put(KEY_MAIN_WINDOW, defaultLanguageMainWindow);
		}
	}

	public static void setDefaultLanguageSettingsWindow() {
		if (defaultLanguageSettingsWindow.size() == 0) {
			defaultLanguageSettingsWindow.put(SETTINGS_WINDOW_TEXT_TITLE,
					"Application Setings");
			defaultLanguageSettingsWindow.put(SETTINGS_WINDOW_TEXT_EXPLAIN,
			"Here you can change the application settings.");
			defaultLanguage.put(KEY_SETTINGS_WINDOW,
					defaultLanguageSettingsWindow);
		}
	}

	public static void setDefaultLanguageAdvancedBackupSourceWindow() {
		if (defaultLanguageOpenFileWindow.size() == 0) {
			defaultLanguageOpenFileWindow.put(ADVANCED_BACKUP_SOURCE_WINDOW_TEXT_TITLE,
					"Advance Backup Source");
			defaultLanguageOpenFileWindow.put(ADVANCED_BACKUP_SOURCE_WINDOW_TEXT_EXPLAIN,
			"Please select the folders or files you want to backup.");
			defaultLanguageOpenFileWindow.put(ADVANCED_BACKUP_SOURCE_WINDOW_LABEL_ALLFOLDERS,
					"All Folders");
			defaultLanguageOpenFileWindow.put(ADVANCED_BACKUP_SOURCE_WINDOW_BUTTON_BACKUP_FILTER,
					"Backup filter");
			defaultLanguageOpenFileWindow.put(ADVANCED_BACKUP_SOURCE_WINDOW_MESSAGE_BOX_ONE_FILE,
					"You must select at least one file or directory.");
			defaultLanguage.put(KEY_ADVANCED_BACKUP_SOURCE_WINDOW,
					defaultLanguageOpenFileWindow);
		}
	}

	public static void setDefaultLanguageLanguageWindow() {
		if (defaultLanguageLanguageWindow.size() == 0) {
			defaultLanguageLanguageWindow.put(LANGUAGE_WINDOW_TEXT_TITLE,
					"Languages");
			defaultLanguageLanguageWindow.put(LANGUAGE_WINDOW_TEXT_EXPLAIN,
			"You can edit languages or make new languages.");
			defaultLanguageLanguageWindow.put(LANGUAGE_WINDOW_TEXT_LANGUAGE_NAME,
					"Language name");
			defaultLanguageLanguageWindow.put(LANGUAGE_WINDOW_MESSAGE_BOX_LANGUAGE_NAME_EMPTY,
					"Language name can not be empty.");
			defaultLanguageLanguageWindow.put(LANGUAGE_WINDOW_MESSAGE_BOX_TEXT_EMPTY,
					"All texts must be completed.");
			defaultLanguage.put(KEY_LANGUAGE_WINDOW,
					defaultLanguageLanguageWindow);
		}
	}

	public static void setDefaultLanguageUserProfileWindow() {
		if (defaultLanguageUserProfileWindow.size() == 0) {
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_TITLE,
					"User profile");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_EXPLAIN,
			"Please fill in your user information.");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TITLE,
					"User Information");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_NAME,
					"Login Name");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_PASSWORD,
					"Password");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_BUTTON_CHANGE,
					"Change");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_BUTTON_SAVE_PASSWORD,
					"Save password");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_CONTACT_TITLE,
					"Contact");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_CONTACT_TEXT_NAME,
					"Name");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_CONTACT_TEXT_EMAIL,
					"Email");
			defaultLanguageUserProfileWindow.put(USER_PROFILE_WINDOW_GROUP_TIME_ZONE_TITLE,
					"Time Zone");
			defaultLanguage.put(KEY_USER_PROFILE_WINDOW,
					defaultLanguageUserProfileWindow);
		}
	}
	
	public static void setDefaultLanguageLoginWindow() {
		if (defaultLanguageLoginWindow.size() == 0) {
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_TITLE,
					"Login information");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_EXPLAIN,
			"Please enter the server name and your user information.");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_USER_INFORMATION_TITLE,
					"User Information");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_NAME,
					"Login Name");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_PASSWORD,
					"Password");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_USER_INFORMATION_BUTTON_CHANGE,
					"Change");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_USER_INFORMATION_BUTTON_SAVE_PASSWORD,
					"Save password");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_CONTACT_TITLE,
					"Contact");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_CONTACT_TEXT_NAME,
					"Name");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_CONTACT_TEXT_EMAIL,
					"Email");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_TIME_ZONE_TITLE,
					"Time Zone");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_SERVER_INFORMATION_TITLE,
			"Server Information");
			defaultLanguageLoginWindow.put(LOGIN_WINDOW_GROUP_SERVER_INFORMATION_TEXT_SERVER_NAME,
			"Server Name");
			defaultLanguage.put(KEY_LOGIN_WINDOW,
					defaultLanguageLoginWindow);
		}
	}

	public static void setDefaultLanguageBackupScheduleWindow() {
		if (defaultLanguageBackupScheduleWindow.size() == 0) {
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_TITLE,
					"Backup Schedule");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_EXPLAIN,
			"Please enter a schedule to run a backup set.");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_RUN_SCHEDULE,
					"Run scheduled backup on this computer");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_LABEL_BACKUP,
					"Backup on the following day(s) every week");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_SUNDAY,
					"Sunday");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_MONDAY,
					"Monday");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_TUESDAY,
					"Tuesday");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_WEDNESDAY,
					"Wednesday");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_THURSDAY,
					"Thursday");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_FRIDAY,
					"Friday");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_SATURDAY,
					"Saturday");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_GROUP_TIME,
					"Time");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_LABEL_START,
					"Start");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_LABEL_STOP,
					"Stop");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_ON_COMPLETION,
					"on completion (Full Backup)");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_BUTTON_AFTER,
					"after");
			defaultLanguageBackupScheduleWindow.put(BACKUP_SCHEDULE_WINDOW_LABEL_HOURS,
					"hour(s)");

			defaultLanguage.put(KEY_BACKUP_SCHEDULE_WINDOW,
					defaultLanguageBackupScheduleWindow);
		}
	}

	public static void setDefaultLanguageRestoreFilterCriteriaWindow() {
		if (defaultLanguageRestoreFilterCriteriaWindow.size() == 0) {
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_TEXT_TITLE,
					"Filter");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_TEXT_EXPLAIN,
			"You can define filters for your restore.");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_GROUP_PATTERN,
					"Filter");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_TEXT_PATTERN,
					"Pattern");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TYPE,
					"Type");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_BUTTON_MATCH_CASE,
					"Match case");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_EXACT,
					"exact");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_START_WITH,
					"start with");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_ENDS_WITH,
					"ends with");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER,
					"You can not use illegal characters.");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT,
					"Pattern must have at least one [.].");
			defaultLanguageRestoreFilterCriteriaWindow.put(RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_PATTERN_EMPTY,
					"Pattern can not be empty.");
			defaultLanguage.put(KEY_RESTORE_FILTER_CRITERIA_WINDOW,
					defaultLanguageRestoreFilterCriteriaWindow);
		}
	}

	public static void setDefaultLanguageBachupSourceWindow() {
		if (defaultLanguageBackupSourceWindow.size() == 0) {
			defaultLanguageBackupSourceWindow.put(BACKUP_SOURCE_WINDOW_TEXT_TITLE,
					"Backup Source");
			defaultLanguageBackupSourceWindow.put(BACKUP_SOURCE_WINDOW_TEXT_EXPLAIN,
			"Press advanced to select files or folders to backup.");
			defaultLanguageBackupSourceWindow.put(BACKUP_SOURCE_WINDOW_BUTTON_ADVANCE,
					"Advanced (Browse files)");
			defaultLanguageBackupSourceWindow.put(BACKUP_SOURCE_WINDOW_BUTTON_COMPRESSION,
			"Use Compression");
			defaultLanguageBackupSourceWindow.put(BACKUP_SOURCE_WINDOW_BUTTON_ENCRYPT_FILES,
			"Encrypt my files on the server");
			defaultLanguage.put(KEY_BACKUP_SOURCE_WINDOW,
					defaultLanguageBackupSourceWindow);
		}
	}

	public static void setDefaultLanguageBachupSettingsWindow() {
		if (defaultLanguageBackupSettingsWindow.size() == 0) {
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_TEXT_TITLE,
					"Backup Settings");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_TEXT_EXPLAIN,
			"Here you can change the backup settings.");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_GROUP_LANGUAGE,
					"Language");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_GROUP_ENCRYPTION,
					"Encryption");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_TEXT_ENCRYPTION_KEY,
					"Encrypting key");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_BUTTON_MASK_ENCRYPTING_KEY,
					"Mask encrypting key");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_GROUP_TEMPORARY_FILE,
					"Temporary Directory for storing backup files");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_BUTTON_CHANGE,
					"Change");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_GROUP_RECYCLE_BIN,
					"Recycle Bin");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_COMBO_RECYCLE_BIN,
					"Keep deleted file(s) for");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_RECYCLE_BIN_DAYS,
					"day(s)");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_GROUP_ADVANCE_SETTINGS,
					"Advanced Settings");
			defaultLanguageBackupSettingsWindow.put(BACKUP_SETTINGS_WINDOW_BUTTON_BACKUP_FILE,
					"Backup File Permissions");
			defaultLanguage.put(KEY_BACKUP_SETTINGS_WINDOW,
					defaultLanguageBackupSettingsWindow);
		}
	}

	public static void setDefaultLanguageFilesWindow() {
		if (defaultLanguageFilesWindow.size() == 0) {
			defaultLanguageFilesWindow.put(FILES_WINDOW_TABLE_NAME, "Name");
			defaultLanguageFilesWindow.put(FILES_WINDOW_TABLE_SIZE, "Size");
			defaultLanguageFilesWindow.put(FILES_WINDOW_TABLE_TYPE, "Type");
			defaultLanguageFilesWindow.put(FILES_WINDOW_TABLE_MODIFIED,
					"Modified");
			defaultLanguageFilesWindow.put(FILES_WINDOW_COMBO_ITEMS,
					"Items per page");
			defaultLanguageFilesWindow.put(FILES_WINDOW_COMBO_PAGE, "Page");
			defaultLanguage.put(KEY_FILES_WINDOW, defaultLanguageFilesWindow);
		}
	}

	public static void setDefaultLanguageChangePathWindow() {
		if (defaultLanguageChangePathWindow.size() == 0) {
			defaultLanguageChangePathWindow.put(CHANGE_PATH_WINDOW_TEXT_TITLE,
					"Change Path");
			defaultLanguageChangePathWindow.put(CHANGE_PATH_WINDOW_TEXT_EXPLAIN,
			"Please select a folder.");
			defaultLanguageChangePathWindow.put(CHANGE_PATH_WINDOW_MESSAGE_BOX_TEXT_ONE_ITEM_SELECTED,
			"You must select at least one folder.");
			defaultLanguage.put(KEY_CHANGE_PATH_WINDOW,
					defaultLanguageChangePathWindow);
		}
	}

	public static void setDefaultLanguageRestoreWindow() {
		if (defaultLanguageRestoreWindow.size() == 0) {
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_TEXT_TITLE,
					"Restore");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_TEXT_EXPLAIN,
			"Please select the items to be restored.");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_SHOW_FILES,
					"Show files as of jobs");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_SHOW_ALL_FILES,
					"Show all files");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_FILTER,
					"Filter");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_GROUP_RESTORE,
					"Restore file to");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_ORIGINAL_LOCATION,
					"Original location");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_LABEL_ORIGINAL_LOCATION,
					"Original location");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_ALTERNATIVE_LOCATION,
					"Alternativ location");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_CHANGE,
					"Change");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_DELETE_EXTRA_FILE,
					"Delete extra files");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_RESTORE_FILE,
					"Restore file permision");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_OVERWRITE,
					"Overwrite existing files");
			defaultLanguageRestoreWindow.put(RESTORE_WINDOW_BUTTON_SEARCH,
					"Search");
			defaultLanguage.put(KEY_RESTORE_WINDOW,
					defaultLanguageRestoreWindow);
		}
	}

	public static void setDefaultLanguageSearchWindow() {
		if (defaultLanguageSearchWindow.size() == 0) {
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_TEXT_TITLE, "Search");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_TEXT_EXPLAIN, "You can search for items here.");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_TEXT_LOOK_IN,
					"Look in");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_BUTTON_CHANGE,
					"Change");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_TEXT_PATTERN,
					"Pattern");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_TYPE, "Type");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_APPLY_TO,
					"Apply to");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_TYPE_CONTAINS,
					"contains");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_TYPE_EXACT,
					"exact");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_TYPE_START_WITH,
					"starts with");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_TYPE_ENDS_WITH,
					"ends with");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_APPLY_TO_FILES_DIRECTORIES,
					"files and directories");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_APPLY_TO_FILES,
					"files only");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_COMBO_APPLY_TO_DIRECTORIES,
					"directories only");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_BUTTON_SEARCH_SUB_FOLDERS,
					"Search subfolders");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_BUTTON_MATCH_CASE,
					"Match case");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_BUTTON_SEARCH,
					"Search");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_BUTTON_STOP, "Stop");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER,
					"You can not use illegal characters.");
//			defaultLanguageSearchWindow.put(SEARCH_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT,
//					"Pattern must have at least one [.].");
			defaultLanguageSearchWindow.put(SEARCH_WINDOW_MESSAGE_BOX_TEXT_LOOK_IN_DIR,
			"You must select a directory.");
			defaultLanguage.put(KEY_SEARCH_WINDOW, defaultLanguageSearchWindow);
		}
	}

	public static void setDefaultLanguageBackUpFilterWindow() {
		if (defaultLanguageBackUpFilterWindow.size() == 0) {
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_TEXT_TITLE,
					"Backup filters");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_TEXT_EXPLAIN,
			"You can define filters for your backup set.");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_COMBO_APPLY_TO,
					"Apply to");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_GROUP_FILE_NAME_EXTENSION,
					"File name extension");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_COMBO_OPTION_1,
					"include files with the above characteristic(s)");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_COMBO_OPTION_2,
					"exclude files with the above characteristic(s)");

			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ONE_FILTER,
					"You must enter at least one filter.");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATH_NOT_EXIST,
					"The path does not exist.");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATH_DUPLICATE,
					"This path already exist in list.");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATTERN_EMPTY,
					"You must enter at least one pattern.");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER,
					"You can not use illegal characters.");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT,
					"Pattern must have at least one [.].");
			defaultLanguageBackUpFilterWindow.put(BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATTERN_DUPLICATE,
					"This pattern already exist in list.");

			defaultLanguage.put(KEY_BACKUP_FILTER_WINDOW,
					defaultLanguageBackUpFilterWindow);
		}
	}

	public static void setDefaultLanguageRestoreFilterWindow() {
		if (defaultLanguageRestoreFilterWindow.size() == 0) {
			defaultLanguageRestoreFilterWindow.put(BACKUP_FILTER_WINDOW_TEXT_TITLE,
					"Restore filters");
			defaultLanguageRestoreFilterWindow.put(BACKUP_FILTER_WINDOW_TEXT_EXPLAIN,
			"You can define filters for you restore.");
			defaultLanguageRestoreFilterWindow.put(BACKUP_FILTER_WINDOW_GROUP_FILE_NAME_EXTENSION,
					"File name extension");

			defaultLanguage.put(KEY_RESTORE_FILTER_WINDOW,
					defaultLanguageRestoreFilterWindow);
		}
	}

	public static void setDefaultLanguageSelectDirectoryWindow() {
		if (defaultLanguageSelectDirectoryWindow.size() == 0) {
			defaultLanguageSelectDirectoryWindow.put(SELECT_DIRECTORY_WINDOW_TEXT_TITLE,
					"Select folder");
			defaultLanguageSelectDirectoryWindow.put(SELECT_DIRECTORY_WINDOW_TEXT_EXPLAIN,
			"Please select a folder.");
			defaultLanguage.put(KEY_SELECT_DIRECTORY_WINDOW,
					defaultLanguageSelectDirectoryWindow);
		}
	}
	
	public static void setDefaultBackupLogWindow() {
		if (Language.defaultBackupLogWindow.size() == 0) {

			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_TITLE, "Reports");
			Language.defaultBackupLogWindow.put(Language.BACKUP_LOG_WINDOW_EXPLAIN, "Backup, Error and Restore reports.");
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


	public static void setDefaultLanguage() {
		setDefaultLanguageGeneral();
		setDefaultLanguageMainWindow();
		setDefaultLanguageSettingsWindow();
		setDefaultLanguageAdvancedBackupSourceWindow();
		setDefaultLanguageLanguageWindow();
		setDefaultLanguageUserProfileWindow();
		setDefaultLanguageLoginWindow();
		setDefaultLanguageBackupScheduleWindow();
		setDefaultLanguageRestoreFilterCriteriaWindow();
		setDefaultLanguageRestoreFilterWindow();
		setDefaultLanguageBachupSourceWindow();
		setDefaultLanguageBachupSettingsWindow();
		setDefaultLanguageFilesWindow();
		setDefaultLanguageChangePathWindow();
		setDefaultLanguageRestoreWindow();
		setDefaultLanguageSearchWindow();
		setDefaultLanguageBackUpFilterWindow();
		setDefaultLanguageSelectDirectoryWindow();
		setDefaultChangePasswordWindow();
		setDefaultBackupLogWindow();
	}

	@SuppressWarnings("unchecked")
	public void activateDefaultLanguage() {
		try {
			language = (TreeMap<String, TreeMap<String, String>>) defaultLanguage.clone();
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	public void setLanguage(String fileName) {
		try {
			if (fileName.indexOf("ENGLISH(default)") != -1) {
				language = (TreeMap<String, TreeMap<String, String>>) defaultLanguage.clone();// if
				// default
				// language is
				// selected
				return;
			}
			INIFile ini = new INIFile(AppConstants.LNG_DIR + fileName);
			ini.loadFile();
			language = ini.getSections();
		} catch (Exception e) {
			logger.fatal(e, e);
			language = (TreeMap<String, TreeMap<String, String>>) defaultLanguage.clone();// if file
			// not
			// found
		}
	}

	public static String getText(String section, String key) {
		String text = "";
		try {
			TreeMap<String, String> prop = language.get(section);
			if (prop == null) {
				prop = defaultLanguage.get(section);
				if (prop == null)
					return " - ";
			}
			text = prop.get(key);
			if (text == null || text.length() == 0) {
				text = defaultLanguage.get(section).get(key);
				if (text == null || text.length() == 0)
					text = " - ";
			}

		} catch (Exception e) {
			logger.fatal(e, e);
			return " - ";
		}
		return text;
	}

	public static String getTextMainWindow(String key) {
		return getText(KEY_MAIN_WINDOW, key);
	}

	public static String getTextSettingsWindow(String key) {
		return getText(KEY_SETTINGS_WINDOW, key);
	}

	public static String getTextAdvancedBackupSourceWindow(String key) {
		return getText(KEY_ADVANCED_BACKUP_SOURCE_WINDOW, key);
	}

	public static String getTextUserProfileWindow(String key) {
		return getText(KEY_USER_PROFILE_WINDOW, key);
	}

	public static String getTextLoginWindow(String key) {
		return getText(KEY_LOGIN_WINDOW, key);
	}

	public static String getTextLanguageWindow(String key) {
		return getText(KEY_LANGUAGE_WINDOW, key);
	}

	public static String getTextBackupScheduleWindow(String key) {
		return getText(KEY_BACKUP_SCHEDULE_WINDOW, key);
	}

	public static String getTextRestoreFilterCriteriaWindow(String key) {
		return getText(KEY_RESTORE_FILTER_CRITERIA_WINDOW, key);
	}

	public static String getTextRestoreFilterWindow(String key) {
		return getText(KEY_RESTORE_FILTER_WINDOW, key);
	}

	public static String getTextBackupSourceWindow(String key) {
		return getText(KEY_BACKUP_SOURCE_WINDOW, key);
	}

	public static String getTextBackupSettingsWindow(String key) {
		return getText(KEY_BACKUP_SETTINGS_WINDOW, key);
	}

	public static String getTextFilesWindow(String key) {
		return getText(KEY_FILES_WINDOW, key);
	}

	public static String getTextChangePathWindow(String key) {
		return getText(KEY_CHANGE_PATH_WINDOW, key);
	}

	public static String getTextRestoreWindow(String key) {
		return getText(KEY_RESTORE_WINDOW, key);
	}

	public static String getTextSearchWindow(String key) {
		return getText(KEY_SEARCH_WINDOW, key);
	}

	public static String getTextBackUpFilterWindow(String key) {
		return getText(KEY_BACKUP_FILTER_WINDOW, key);
	}

	public static String getTextSelectDirectoryWindow(String key) {
		return getText(KEY_SELECT_DIRECTORY_WINDOW, key);
	}

	public static String getTextBackupLogWindow(String key) {
		return Language.getText(Language.KEY_BACKUP_LOG_WINDOW, key);
	}
	
	public static String getTextChangePasswordWindow(String key) {
		return Language.getText(Language.KEY_CHANGE_PASSWORD_WINDOW, key);
	}

	public static TreeMap<String, String> getLanguageWindow(String key) {
		return language.get(key);
	}

	public static TreeMap<String, TreeMap<String, String>> getDefaultLanguage() {
		return defaultLanguage;
	}
}
