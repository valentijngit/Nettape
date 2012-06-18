package net.nettape.client.gui.ui;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.TreeMap;

import net.nettape.client.Global;
import net.nettape.client.MessageHandler;
import net.nettape.client.command.ReceiveBackupSetsCommand;
import net.nettape.client.command.SendBackupSetCommand;
import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.*;


import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.object.BackupSet;
import net.nettape.object.Constants.ConnectionType;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 24, 2009
 * @description
 */

public class BackupSettingsWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(BackupSettingsWindow.class);

	private Group groupLanguage;

	private Combo comboLanguage;

	private Button buttonAddLanguage;
	private Button buttonModLanguage;
	private Button buttonDelLanguage;

	private Text textEncryptingKey;
	private Button buttonMaskEncryptingKey;
	private Text textTemp;
	private Button buttonChange;
	private Combo comboRecycleBin;
	private Button buttonBackupFile;
	private Button buttonEncryption;
	
	public BackupSettingsWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_TEXT_TITLE),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_TEXT_EXPLAIN),
			AppImages.IMG_SETTINGS,
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_PROCESS));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			if (this.comboLanguage.getSelectionIndex() == 0) {
				Language.getInstance().activateDefaultLanguage();
				logger.info("default language acctivated.");
			} else {
				Language.getInstance().setLanguage(this.comboLanguage.getText()
						+ ".lng");
				logger.info("set language - " + this.comboLanguage.getText());
			}
			TreeMap<String, String> p = new TreeMap<String, String>();
			p.put("language", this.comboLanguage.getText() + ".lng");
			p.put("Encryption", String.valueOf(this.buttonEncryption.getSelection()));
			p.put("EncryptionKey", this.textEncryptingKey.getText());
			p.put("TempBackupFolder", this.textTemp.getText());
			p.put("BackupFilePermissions", String.valueOf(this.buttonBackupFile.getSelection()));
			p.put("RemoveDeletedFilesAfterDays", this.comboRecycleBin.getText());
			AppConstants.getSettings().put("SETTINGS", p);

			INIFile ini = new INIFile(AppConstants.SETTINGS_DIR
					+ "settings.ini");
			ini.setSections(AppConstants.getSettings());
			ini.saveFile();

			// TODO save for rest of data
			

			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private boolean buttonChangeAction() {
		boolean result = false;
		try {
			SelectDirectoryFilterWindow dlg = new SelectDirectoryFilterWindow();
			dlg.open();
			if (dlg.getExitChoiceAction() == SWT.OK) {
				this.textTemp.setText(dlg.getResult());
			}
			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	public void createGUI() {
		this.groupLanguage = new Group(this.container, SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		this.groupLanguage.setLayoutData(data);
		// this.groupLanguage.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_LANGUAGE));
		GridLayout lay = new GridLayout(5, false);
		this.groupLanguage.setLayout(lay);

		Composite comp = new Composite(this.groupLanguage, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) this.groupLanguage.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		lay.marginTop=0;
		lay.marginHeight=0;
		lay.marginWidth=0;
		comp.setLayout(lay);

//		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_USER));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_LANGUAGE));

		this.comboLanguage = new Combo(this.groupLanguage, SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 130;
		this.comboLanguage.setLayoutData(data);
		this.comboLanguage.addListener(SWT.Selection, this);

		this.buttonAddLanguage = new Button(this.groupLanguage, SWT.PUSH);
		this.buttonAddLanguage.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 23;
		this.buttonAddLanguage.setLayoutData(data);
		this.buttonAddLanguage.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAddLanguage,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		this.buttonModLanguage = new Button(this.groupLanguage, SWT.PUSH);
		this.buttonModLanguage.setImage(AppImages.getImage16(AppImages.IMG_NOTE_EDIT));
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 23;
		this.buttonModLanguage.setLayoutData(data);
		this.buttonModLanguage.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonModLanguage,
				AppImages.getImage16(AppImages.IMG_NOTE_EDIT),
				AppImages.getImage16Focus(AppImages.IMG_NOTE_EDIT));

		this.buttonDelLanguage = new Button(this.groupLanguage, SWT.PUSH);
		this.buttonDelLanguage.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 23;
		this.buttonDelLanguage.setLayoutData(data);
		this.buttonDelLanguage.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonDelLanguage,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));

		Group groupEncryption = new Group(this.container, SWT.NONE);
		// groupEncryption.setText("       "
		// +
		// Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_ENCRYPTION));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupEncryption.setLayout(new GridLayout(3, false));
		groupEncryption.setLayoutData(data);
		// WidgetUtils.drawImageOnGroup(groupEncryption,
		// AppImages.getImage16(AppImages.IMG_LOCK));

		comp = new Composite(groupEncryption, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) groupEncryption.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		lay.marginTop=0;
		lay.marginHeight=0;
		lay.marginWidth=0;
		comp.setLayout(lay);

//		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_LOCK));
		this.buttonEncryption = new Button(comp, SWT.CHECK);
		this.buttonEncryption.addListener(SWT.Selection, this);
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_ENCRYPTION));
		new Label(groupEncryption, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_TEXT_ENCRYPTION_KEY)
				+ ":");
		this.textEncryptingKey = new Text(groupEncryption, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		data.heightHint = 15;
		this.textEncryptingKey.setLayoutData(data);
		new Label(groupEncryption, SWT.NONE).setText("");
		this.buttonMaskEncryptingKey = new Button(groupEncryption, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		this.buttonMaskEncryptingKey.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BUTTON_MASK_ENCRYPTING_KEY));
		this.buttonMaskEncryptingKey.setLayoutData(data);
		this.buttonMaskEncryptingKey.addListener(SWT.Selection, this);
		this.buttonMaskEncryptingKey.setSelection(true);
		this.textEncryptingKey.setEchoChar(this.buttonMaskEncryptingKey.getSelection()	? '*'
				: '\0');

		Group groupTemporaryFile = new Group(this.container, SWT.NONE);
		// groupTemporaryFile.setText("       "
		// +
		// Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_TEMPORARY_FILE));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupTemporaryFile.setLayout(new GridLayout(3, false));
		groupTemporaryFile.setLayoutData(data);
		// WidgetUtils.drawImageOnGroup(groupTemporaryFile,
		// AppImages.getImage16(AppImages.IMG_FOLDER_FULL_ADD));

		comp = new Composite(groupTemporaryFile, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) groupTemporaryFile.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		lay.marginTop=0;
		lay.marginHeight=0;
		lay.marginWidth=0;
		comp.setLayout(lay);

//		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_FOLDER_FULL_ADD));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_TEMPORARY_FILE));

		this.textTemp = new Text(groupTemporaryFile, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 15;
		data.horizontalSpan = 2;
		this.textTemp.setLayoutData(data);
		this.buttonChange = new Button(groupTemporaryFile, SWT.PUSH);
		data = new GridData(SWT.NONE, SWT.CENTER, true, true);
		data.horizontalSpan = 1;
		data.widthHint = 100;
		data.heightHint = 23;
		this.buttonChange.setImage(AppImages.getImage16(AppImages.IMG_REFRESH));
		this.buttonChange.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BUTTON_CHANGE));
		this.buttonChange.setLayoutData(data);
		this.buttonChange.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonChange,
				AppImages.getImage16(AppImages.IMG_REFRESH),
				AppImages.getImage16Focus(AppImages.IMG_REFRESH));

		Group groupRecycleBin = new Group(this.container, SWT.NONE);
		// groupRecycleBin.setText("       "
		// +
		// Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_RECYCLE_BIN));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupRecycleBin.setLayout(new GridLayout(4, false));
		groupRecycleBin.setLayoutData(data);
		// WidgetUtils.drawImageOnGroup(groupRecycleBin,
		// AppImages.getImage16(AppImages.IMG_FOLDER_REMOVE));

		comp = new Composite(groupRecycleBin, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) groupRecycleBin.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		lay.marginTop=0;
		lay.marginHeight=0;
		lay.marginWidth=0;
		comp.setLayout(lay);

//		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_FOLDER_REMOVE));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_RECYCLE_BIN));

		new Label(groupRecycleBin, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMBO_RECYCLE_BIN)
				+ " ");
		this.comboRecycleBin = new Combo(groupRecycleBin, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		this.comboRecycleBin.setLayoutData(data);
		new Label(groupRecycleBin, SWT.NONE).setText(" "
				+ Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_RECYCLE_BIN_DAYS));

		Group groupAdvanceSettings = new Group(this.container, SWT.NONE);
		// groupAdvanceSettings.setText("       "
		// +
		// Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_ADVANCE_SETTINGS));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupAdvanceSettings.setLayout(new GridLayout(3, false));
		groupAdvanceSettings.setLayoutData(data);
		// WidgetUtils.drawImageOnGroup(groupAdvanceSettings,
		// AppImages.getImage16(AppImages.IMG_FOLDER_PROCESS));

		comp = new Composite(groupAdvanceSettings, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) groupAdvanceSettings.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		lay.marginTop=0;
		lay.marginHeight=0;
		lay.marginWidth=0;
		comp.setLayout(lay);

//		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_FOLDER_PROCESS));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GROUP_ADVANCE_SETTINGS));

		this.buttonBackupFile = new Button(groupAdvanceSettings, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 3;
		this.buttonBackupFile.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BUTTON_BACKUP_FILE));
		this.buttonBackupFile.setLayoutData(data);
		
	}

	private void populateComboLanguage() {
		try {
			this.comboLanguage.removeAll();
			this.comboLanguage.add(AppConstants.DEFAULT_LANGUAGE);// for default language set
			this.comboLanguage.select(0);
			this.buttonDelLanguage.setEnabled(false);
			this.buttonModLanguage.setEnabled(false);

			File dirLanguage = new File(AppConstants.LNG_DIR);
			File[] files = dirLanguage.listFiles();
			if (files == null || files.length == 0)
				return;
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().indexOf(".lng") != -1)
					this.comboLanguage.add(files[i].getName().substring(0,
							files[i].getName().length() - 4));
			}
			TreeMap<String, String> p = AppConstants.getSettings().get("SETTINGS");
			if (p != null) {
				String language = p.get("language");
				language = language.indexOf(".lng") >= 0 ? language.substring(0,
																language.length() - 4)
														: language;// condition for DEFAULT LANGUAGE
				int index = this.comboLanguage.indexOf(language);
				if (index > 0)
					this.comboLanguage.select(index);
			}
			this.buttonDelLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																							: false);
			this.buttonModLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																							: false);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonAddLanguage) {
					LanguageWindow dlg = new LanguageWindow("");
					dlg.open();
					if (dlg.getExitChoiceAction() == SWT.OK) {
						this.populateComboLanguage();
					}

				} else if (src == this.buttonModLanguage) {
					LanguageWindow dlg = new LanguageWindow(
						this.comboLanguage.getText() + ".lng");
					dlg.open();
					if (dlg.getExitChoiceAction() == SWT.OK) {
						this.populateComboLanguage();
					}
				} else if (src == this.buttonDelLanguage) {
					if (MessageBoxWindow.question("Do you really  want to delete this language?") == SWT.NO)
						return;
					File file = new File(AppConstants.LNG_DIR + this.comboLanguage.getText()
							+ ".lng");
					if (!file.exists())
						return;
					if (!file.delete()) {
						MessageBoxWindow.error("This language can not be deleted!");
						return;
					}
					populateComboLanguage();
				} else if (src == this.comboLanguage) {
					this.buttonDelLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																									: false);
					this.buttonModLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																									: false);
				} else if (src == this.buttonMaskEncryptingKey) {
					this.textEncryptingKey.setEchoChar(this.buttonMaskEncryptingKey.getSelection()	? '*'
																									: '\0');
				} else if (src == this.buttonChange) {
					this.buttonChangeAction();
				} else if (src == this.buttonEncryption) {
					this.textEncryptingKey.setEnabled(this.buttonEncryption.getSelection());
					this.buttonMaskEncryptingKey.setEnabled(this.buttonEncryption.getSelection());
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void populateData() {
		try
		{
			this.populateComboLanguage();
			for (int i = 1; i <= 15; i++) {
				this.comboRecycleBin.add(i + "");
			}
			this.comboRecycleBin.select(4);
			
			//TODO: AppConstants.checkSettings() - checks that ALL settings are present, otherwise adds settings with default settings
			TreeMap<String, String> p = AppConstants.getSettings().get("SETTINGS");
			if(p != null)
			{
				if(p.get("Encryption") != null)
					this.buttonEncryption.setSelection(Boolean.parseBoolean(p.get("Encryption")));
				if(p.get("EncryptionKey") != null)
					this.textEncryptingKey.setText(p.get("EncryptionKey"));
				if(p.get("TempBackupFolder") != null)
					this.textTemp.setText(p.get("TempBackupFolder"));
				if(p.get("BackupFilePermissions") != null)
					this.buttonBackupFile.setSelection(Boolean.parseBoolean(p.get("BackupFilePermissions")));
				if(p.get("RemoveDeletedFilesAfterDays") != null)
					this.comboRecycleBin.select(this.comboRecycleBin.indexOf(p.get("RemoveDeletedFilesAfterDays")));
			}

			this.textEncryptingKey.setEnabled(this.buttonEncryption.getSelection());
			this.buttonMaskEncryptingKey.setEnabled(this.buttonEncryption.getSelection());
		}
		catch(Exception ex)
		{
			//TODO: show message to user
		}

	}

	protected boolean validateData() {
		// TODO Auto-generated method stub
		return true;
	}

	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
