package net.nettape.client.gui.ui;

import java.util.HashSet;
import java.util.Set;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL1Window;
import net.nettape.client.gui.ui.custom.XButton;
import net.nettape.dal.object.*;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class MainWindow extends AbstractL1Window {

	private static final Logger logger = Logger.getLogger(MainWindow.class);

	private static boolean backUpFirstRun = false;

	private Backupset backUp;

	private Restore restore;
	private final Set<Backup> backUpRestore = new HashSet<Backup>();

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

	@Override
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
			this.xButtonReports.setImage(AppImages.IMG_USER_ACCOUNT);
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
		BackupLogWindow dlg = new BackupLogWindow();
		dlg.open();
		return false;
	}

	/*
	 * backup set button action
	 */
	private boolean itemBackupSetAction() {
		return false;
	}

	/*
	 * schedule button action
	 */
	private boolean itemScheduleAction() {
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
		// TODO action
		return false;
	}

	/*
	 * help button action
	 */
	private boolean itemHelpAction() {
		// TODO action
		return false;
	}

	private boolean itemBackUpAction() {
		ChangePasswordWindow dlg = new ChangePasswordWindow();
		dlg.open();
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
		try {
			ServersWindow dlg = new ServersWindow();
			dlg.open();
			return true;
		} catch (Exception e) {
			MainWindow.logger.fatal(e, e);
			return false;
		}
	}

	@Override
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

	@Override
	public void populateData() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
