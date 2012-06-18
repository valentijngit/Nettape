package net.nettape.client.gui.ui;

import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import net.nettape.client.Global;
import net.nettape.client.MessageHandler;
import net.nettape.client.command.ReceiveBackupSetsCommand;
import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL1Window;
import net.nettape.client.gui.ui.custom.XButton;
import net.nettape.client.gui.util.AppUtils;
import net.nettape.dal.object.*;
import net.nettape.object.BackupSet;
import net.nettape.object.BackupWithItems;
import net.nettape.object.Constants;
import net.nettape.object.RestoreItem;
import net.nettape.object.RestoreWithBackupItems;
import net.nettape.client.command.*;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class MainWindow extends AbstractL1Window {

	private static final Logger logger = Logger.getLogger(MainWindow.class);

	private static boolean backUpFirstRun = false;

	private Backupset backupset;
	private BackupSet backupSet;

	private RestoreWithBackupItems restore;
	private Set<BackupWithItems> backupList = new HashSet<BackupWithItems>();
	private RestoreWithBackupItems restoreWithBackupitems;

	private XButton xButtonBackUp;
	private XButton xButtonRestore;
	private XButton xButtonUserAccount;
	private XButton xButtonBackupSet;
	private XButton xButtonSchedule;
	private XButton xButtonSettings;
	private XButton xButtonReports;
	private XButton xButtonHelp;

	public MainWindow() {
		super(null,
			SWT.APPLICATION_MODAL,
			Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_TITLE),
			610,
			495);
		try {
			this.setImage(AppImages.getImageMisc(AppImages.IMG_MISC_APPLICATION));
			GridLayout lay = new GridLayout(3, false);
			lay.marginWidth = 0;
			lay.marginHeight = 0;
			lay.marginTop = 0;
			lay.marginBottom = 0;
			this.shell.setLayout(lay);

			this.createGUI();
		} catch (Exception ex) {
			MainWindow.logger.fatal(ex, ex);
		}
	}

	public void createGUI() {
		try {

			int nrChars = 40;
			int leftMargin = 100;
			int between = 5;
			this.shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

			Image img = AppImages.getImageMisc(AppImages.IMG_MISC_MAIN);

			Composite comp = new Composite(this.shell, SWT.NONE);
			GridLayout lay = new GridLayout(5, false);

			lay.marginTop = 0;
			comp.setLayout(lay);
			GridData gd = new GridData(SWT.FILL, SWT.FILL, false, false);
			gd.horizontalSpan = 3;
			gd.widthHint = img.getImageData().width;
			gd.heightHint = img.getImageData().height;
			comp.setLayoutData(gd);
			comp.setBackgroundImage(img);
			comp.setBackgroundMode(SWT.INHERIT_FORCE);

			Label label = new Label(comp, SWT.NONE);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 5;
			gd.heightHint = 155;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = leftMargin;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);
			this.xButtonBackUp = new XButton(comp, true);
			this.xButtonBackUp.setImage(AppImages.IMG_NEXT);
			this.xButtonBackUp.setNrCharsOnLine(nrChars);
			this.xButtonBackUp.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP));
			this.xButtonBackUp.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_DETAILS));
			this.xButtonBackUp.addListener(SWT.MouseUp, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = between;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);
			this.xButtonRestore = new XButton(comp, true);
			this.xButtonRestore.setImage(AppImages.IMG_REPEAT);
			this.xButtonRestore.setNrCharsOnLine(nrChars);
			this.xButtonRestore.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE));
			this.xButtonRestore.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE_DETAILS));
			this.xButtonRestore.addListener(SWT.MouseUp, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			label = new Label(comp, SWT.NONE);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 5;
			gd.heightHint = 70;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = leftMargin;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			this.xButtonUserAccount = new XButton(comp, false);
			this.xButtonUserAccount.setImage(AppImages.IMG_USER_ACCOUNT);
			this.xButtonUserAccount.setNrCharsOnLine(nrChars);
			this.xButtonUserAccount.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT));
			this.xButtonUserAccount.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT_DETAILS));
			this.xButtonUserAccount.addListener(SWT.MouseUp, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = between;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);
			this.xButtonBackupSet = new XButton(comp, false);
			this.xButtonBackupSet.setImage(AppImages.IMG_BACKUP_SET);
			this.xButtonBackupSet.setNrCharsOnLine(nrChars);
			this.xButtonBackupSet.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET));
			this.xButtonBackupSet.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET_DETAILS));
			this.xButtonBackupSet.addListener(SWT.MouseUp, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = leftMargin;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			this.xButtonSchedule = new XButton(comp, false);
			this.xButtonSchedule.setImage(AppImages.IMG_SCHEDULE);
			this.xButtonSchedule.setNrCharsOnLine(nrChars);
			this.xButtonSchedule.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE));
			this.xButtonSchedule.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE_DETAILS));
			this.xButtonSchedule.addListener(SWT.MouseUp, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = between;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);
			this.xButtonSettings = new XButton(comp, false);
			this.xButtonSettings.setImage(AppImages.IMG_SETTINGS);
			this.xButtonSettings.setNrCharsOnLine(nrChars);
			this.xButtonSettings.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS));
			this.xButtonSettings.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS_DETAILS));
			this.xButtonSettings.addListener(SWT.MouseUp, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = leftMargin;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);

			this.xButtonReports = new XButton(comp, false);
			this.xButtonReports.setImage(AppImages.IMG_REPORTS);
			this.xButtonReports.setNrCharsOnLine(nrChars);
			this.xButtonReports.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS));
			this.xButtonReports.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS_DETAILS));
			this.xButtonReports.addListener(SWT.MouseUp, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData();
			gd.widthHint = between;
			// label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			label.setLayoutData(gd);
			this.xButtonHelp = new XButton(comp, false);
			this.xButtonHelp.setImage(AppImages.IMG_HELP);
			this.xButtonHelp.setNrCharsOnLine(nrChars);
			this.xButtonHelp.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_HELP));
			this.xButtonHelp.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_HELP_DETAILS));
			this.xButtonHelp.addListener(SWT.MouseUp, this);

		} catch (Exception ex) {
			MainWindow.logger.fatal(ex, ex);
		}
	}

	private void changeLanguage() {
		this.shell.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_TITLE));
		this.xButtonUserAccount.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT));
		this.xButtonUserAccount.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT_DETAILS));

		this.xButtonBackupSet.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET));
		this.xButtonBackupSet.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET_DETAILS));

		this.xButtonSchedule.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE));
		this.xButtonSchedule.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE_DETAILS));

		this.xButtonSettings.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS));
		this.xButtonSettings.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS_DETAILS));

		this.xButtonReports.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS));
		this.xButtonReports.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS_DETAILS));

		this.xButtonHelp.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_HELP));
		this.xButtonHelp.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_HELP_DETAILS));

		this.xButtonBackUp.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP));
		this.xButtonBackUp.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_DETAILS));

		this.xButtonRestore.setTitle(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE));
		this.xButtonRestore.setDetails(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE_DETAILS));
		this.shell.layout();
	}

	/*
	 * user account button action
	 */
	private boolean itemUserAccountAction() {
		UserProfileWindow dlg = new UserProfileWindow();
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			// TODO action if ok
		}
		return false;

	}

	/*
	 * backup set button action
	 */
	private boolean itemBackupSetAction() {
		try
		{
			ReceiveBackupSetsCommand receiveBackupSetsCommand = new ReceiveBackupSetsCommand(Global.connection);
			LinkedList<BackupSet> backupSetList = receiveBackupSetsCommand.Execute();
			BackupSet backupSet = null;
			if(backupSetList.size() > 0)
			{
				backupSet = backupSetList.get(0);
			}
			BackUpSourceWindow dlg = new BackUpSourceWindow(backupSet);
			dlg.open();
			if (dlg.getExitChoiceAction() == SWT.OK) {

				this.backupSet = (BackupSet)dlg.getResult();
				
				// send the backupset to the server
				Method[] method = new Method[1];
				String[] text = new String[1];

				method[0] = MainWindow.class.getMethod("sendBackupsetToServer");
				text[0] = "Saving backup set on server...";
				WaitMessageWindow executor = new WaitMessageWindow(shell,true);
				executor.open(this,method, text);
			}
		}
		catch(Exception ex)
		{
			
		}
		return false;
	}

	/*
	 * schedule button action
	 */
	private boolean itemScheduleAction() {
		BackupScheduleWindow dlg = new BackupScheduleWindow();
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			// TODO action if ok
		}
		return false;
	}

	/*
	 * setting button action
	 */
	private boolean itemSettingsAction() {
		BackupSettingsWindow dlg = new BackupSettingsWindow();
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			MainWindow.logger.info("settings saved.");
			this.changeLanguage();
		}
		return false;
	}

	/*
	 * reports button action
	 */
	private boolean itemReportsAction() {
		try
		{
			ReceiveBackupSetsCommand receiveBackupSetsCommand = new ReceiveBackupSetsCommand(Global.connection);
			LinkedList<BackupSet> backupSetList = receiveBackupSetsCommand.Execute();
			BackupLogWindow dlg = new BackupLogWindow(backupSetList);
			dlg.open();
		}
		catch(Exception ex)
		{}
		return false;
	}

	/*
	 * help button action
	 */
	private boolean itemHelpAction() {
		// TODO action
		return false;
	}
	private Set<Filter> setFilter;

	private boolean itemBackUpAction() {
		/*
		AdvancedBackupSourceWindow dlg1 = new AdvancedBackupSourceWindow(this.backupset);
		dlg1.open();

		Backup backupTEST = new Backup();
		backupTEST.setName("TEST" + this.backUpRestore.size());

		if (dlg1.getExitChoiceAction() == SWT.OK) {
			this.backupset = dlg1.getResult();
			backupTEST.setBackupset(dlg1.getResult());
			this.backUpRestore.add(backupTEST);
		}
		// BackUpFilterWindow dlg1 = new BackUpFilterWindow(this.setFilter);
		// dlg1.open();
		// if (dlg1.getExitChoiceAction() == SWT.OK) {
		// this.setFilter = dlg1.getResult();
		// System.err.println("SIZE::" + this.setFilter.size());
		// }
		*/
		
		// see on the server if backupsets are entered for this client
		try
		{
			ReceiveBackupSetsCommand receiveBackupSetsCommand = new ReceiveBackupSetsCommand(Global.connection);
			LinkedList<BackupSet> backupSetList = receiveBackupSetsCommand.Execute();
			if (backupSetList.size() <= 0) {
				BackUpSourceWindow dlg = new BackUpSourceWindow(null);
				dlg.open();
				if (dlg.getExitChoiceAction() == SWT.OK) {

					this.backupSet = (BackupSet)dlg.getResult();
					
					// send the backupset to the server
					Method[] method = new Method[1];
					String[] text = new String[1];

					method[0] = MainWindow.class.getMethod("sendBackupsetToServer");
					text[0] = "Saving backup set on server...";
					WaitMessageWindow executor = new WaitMessageWindow(shell,true);
					executor.open(this,method, text);
					sendBackupToServer();
				}
			}
			else
			{
				this.backupSet = new BackupSet();
				this.backupSet = backupSetList.getFirst();
				// now start the backup
				sendBackupToServer();
			}

		}
		catch(SocketException ex)
		{
			AppUtils.reLogin();
		}
		catch(Exception ex)
		{
			MessageHandler.HandleMessage(null,true,"Could not check for backup sets on the server.",false,null, null);
		}
		
		
		
		
		return false;
	}

	public static void connect() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			MainWindow.logger.fatal(e, e);
		}
	}

	public static void sendRequest() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			MainWindow.logger.fatal(e, e);
		}
	}

	public static void receivingData() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			MainWindow.logger.fatal(e, e);
		}
	}

	/*
	 * restore button action
	 */
	private boolean itemRestoreAction() {
		try
		{
			this.restore = null;
			RestoreWindow dlg = new RestoreWindow(this.restore, this.backupList);
			dlg.open();
			if(dlg.errors)
			{
				if(dlg.relogin)
				{
					LoginWindow loginwindow = new LoginWindow(false);
					loginwindow.open();
					if (loginwindow.getExitChoiceAction() == SWT.OK) {
						Global.connection = loginwindow.connection;
					}
				}
			}
			else
			{
				if (dlg.getExitChoiceAction() == SWT.OK) {
					this.restoreWithBackupitems = dlg.getResult();
					//start the restore
					Method[] method = new Method[1];
					String[] text = new String[1];
					method[0] = MainWindow.class.getMethod("receiveRestoreFromServer");
					text[0] = "Executing restore...";
					WaitMessageWindow executor = new WaitMessageWindow(shell,true);
					executor.open(this,method, text);
					
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			MessageHandler.HandleMessage(null,true,"Could not open restore window.",false,null, null);
			return false;
		}
	}

	public void handleEvent(Event arg0) {
		Widget src = null;
		try {
			src = arg0.widget;
			switch (arg0.type) {
				case SWT.MouseUp:
					if (this.xButtonUserAccount.equals(src)) {
						this.itemUserAccountAction();
					} else if (this.xButtonBackupSet.equals(src)) {
						this.itemBackupSetAction();
					} else if (this.xButtonSchedule.equals(src)) {
						this.itemScheduleAction();
					} else if (this.xButtonSettings.equals(src)) {
						this.itemSettingsAction();
					} else if (this.xButtonReports.equals(src)) {
						this.itemReportsAction();
					} else if (this.xButtonHelp.equals(src)) {
						this.itemHelpAction();
					} else if (this.xButtonBackUp.equals(src)) {
						this.itemBackUpAction();
					} else if (this.xButtonRestore.equals(src)) {
						this.itemRestoreAction();
					}
					break;

				default:
					break;
			}
		} catch (Exception e) {
			MainWindow.logger.fatal(e, e);
		}

	}

	public void populateData() {
	// TODO Auto-generated method stub

	}

	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendBackupsetToServer()
	{
		try
		{
			//first get the settings from the settings window and add them to the backupset
			//in the pro version, this should be defined per backupset and not per settings window
			//TODO: AppConstants.checkSettings() - checks that ALL settings are present, otherwise adds settings with default settings
			TreeMap<String, String> p = AppConstants.getSettings().get("SETTINGS");
			if(p != null)
			{
				if(p.get("BackupFilePermissions") != null)
					this.backupSet.backupset.setFilepermissions(Boolean.parseBoolean(p.get("BackupFilePermissions")));
				//TODO: set retention schedule with p.get("RemoveDeletedFilesAfterDays")
			}
			SendBackupSetCommand sendBackupSetCommand = new SendBackupSetCommand(Global.connection,this.backupSet);
			sendBackupSetCommand.Execute();
			sendBackupSetCommand = null;
		}
		catch(Exception ex)
		{
			MessageHandler.HandleMessage(null,true,"Could not save the backup set.",false,null, null);
		}
		
	}
	public void sendBackupToServer()
	{
		try
		{
			DoBackupWindow backupWindow = new DoBackupWindow(this.shell,true);
			backupWindow.open(backupSet);
		}
		catch(Exception ex)
		{
			MessageHandler.HandleMessage(null,true,"Could not start the backup.",false,null, null);
		}
	}
	public void receiveRestoreFromServer()
	{
		try
		{
			ReceiveRestoreCommand receiveRestoreCommand = new ReceiveRestoreCommand(Global.connection, this.restoreWithBackupitems); 
			receiveRestoreCommand.Execute(false);
			receiveRestoreCommand = null;
		}
		catch(Exception ex)
		{
			//TODO: this should get its own window, underneath does not work now because of thread access!
			MessageHandler.HandleMessage(null,true,"Could not start the restore.",false,null, null);
		}
	}
}
