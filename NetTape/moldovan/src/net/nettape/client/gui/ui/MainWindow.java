package net.nettape.client.gui.ui;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL1Window;
import net.nettape.dal.object.*;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class MainWindow extends AbstractL1Window {

	private static final Logger logger = Logger.getLogger(MainWindow.class);

	private static boolean backUpFirstRun = false;

	ToolBar toolBarMain;
	ToolBar toolBarBig1;
	ToolBar toolBarBig2;

	private ToolItem itemUserAccount;
	private ToolItem itemBackupSet;
	private ToolItem itemSchedule;
	private ToolItem itemSettings;
	private ToolItem itemReports;
	private ToolItem itemHelp;

	private ToolItem itemBachUp;
	private ToolItem itemRestore;

	static ToolItem lastItemMainBar = null;
	static ToolItem lastItemBar1 = null;
	static ToolItem lastItemBar2 = null;

	private Backupset backUp;

	private Restore restore;
	private final Set<Backup> backUpRestore = new HashSet<Backup>();

	public MainWindow() {
		super(null,
			SWT.APPLICATION_MODAL,
			Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_TITLE),
			610,
			495);
		try {
			this.setImage(AppImages.getImageMisc(AppImages.IMG_MISC_APPLICATION));
			this.shell.setLayout(new GridLayout(3, false));
			this.createGUI();
		}
		catch (Exception ex) {
			MainWindow.logger.fatal(ex, ex);
		}
	}

	@Override
	public void createGUI() {
		try {
			this.shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			Label label = new Label(this.shell, SWT.NONE);

			label.setImage(AppImages.getImageMisc(AppImages.IMG_MISC_BANNER));
			GridData gd = new GridData();
			gd.horizontalSpan = 3;
			gd.widthHint = 600;
			gd.heightHint = 67;
			label.setLayoutData(gd);
			label.forceFocus();

			Composite comp = new Composite(this.shell, SWT.NONE);
			comp.setLayout(new GridLayout(3, false));
			gd = new GridData();
			gd.horizontalSpan = 3;
			gd.widthHint = 600;
			gd.heightHint = 300;
			comp.setLayoutData(gd);
			comp.setBackgroundImage(AppImages.addImage(AppImages.addImage(AppImages.getImageMisc(AppImages.IMG_MISC_MAIN),
					AppImages.getImageMisc(AppImages.IMG_MISC_LEFT_SQUARE),
					20,
					10),
					AppImages.getImageMisc(AppImages.IMG_MISC_RIGHT_SQUARE),
					320,
					10));
			comp.setBackgroundMode(SWT.INHERIT_FORCE);

			label = new Label(comp, SWT.NONE);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 3;
			gd.heightHint = 70;
			label.setLayoutData(gd);

			this.toolBarBig1 = new ToolBar(comp, SWT.FLAT | SWT.CENTER);

			this.toolBarBig1.addListener(SWT.MouseMove, new Listener() {
				@Override
				public void handleEvent(Event event) {
					ToolItem item = MainWindow.this.toolBarBig1.getItem(new Point(event.x, event.y));
					if (MainWindow.lastItemBar1 != item) {
						if ((MainWindow.lastItemBar1 != null)
								&& (MainWindow.lastItemBar1.getData() != null)) {
							MainWindow.lastItemBar1.setImage(AppImages.getImage128((String) MainWindow.lastItemBar1.getData()));
						}
						MainWindow.lastItemBar1 = item;
						if ((MainWindow.lastItemBar1 != null)
								&& (MainWindow.lastItemBar1.getData() != null)) {
							MainWindow.lastItemBar1.setImage(AppImages.getImage128Focus((String) MainWindow.lastItemBar1.getData()));
						}
					}
				}
			});
			this.toolBarBig1.getShell().getDisplay().timerExec(250, new Runnable() {
				@Override
				public void run() {
					if (MainWindow.this.toolBarBig1.isDisposed()) {
						return;
					}
					Point pt = MainWindow.this.toolBarBig1.getShell().getDisplay().map(null,
							MainWindow.this.toolBarBig1,
							MainWindow.this.toolBarBig1.getShell().getDisplay().getCursorLocation());
					ToolItem item = MainWindow.this.toolBarBig1.getItem(pt);
					if (MainWindow.lastItemBar1 != item) {
						if ((MainWindow.lastItemBar1 != null)
								&& (MainWindow.lastItemBar1.getData() != null)) {
							MainWindow.lastItemBar1.setImage(AppImages.getImage128((String) MainWindow.lastItemBar1.getData()));
						}
						MainWindow.lastItemBar1 = item;
					}
					MainWindow.this.toolBarBig1.getShell().getDisplay().timerExec(250, this);
				}
			});

			ToolItem itemSeparator = new ToolItem(this.toolBarBig1, SWT.SEPARATOR);
			label = new Label(this.toolBarBig1, SWT.NONE);
			gd = new GridData();
			label.setText(" ");
			label.setLayoutData(gd);

			itemSeparator.setWidth(80);
			itemSeparator.setControl(label);

			this.itemBachUp = new ToolItem(this.toolBarBig1, SWT.FLAT);
			this.itemBachUp.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP));
			this.itemBachUp.setImage(AppImages.getImage128(AppImages.IMG_NEXT));
			this.itemBachUp.setData(AppImages.IMG_NEXT);
			this.itemBachUp.addListener(SWT.Selection, this);

			this.toolBarBig2 = new ToolBar(comp, SWT.FLAT | SWT.CENTER);

			this.toolBarBig2.addListener(SWT.MouseMove, new Listener() {
				@Override
				public void handleEvent(Event event) {
					ToolItem item = MainWindow.this.toolBarBig2.getItem(new Point(event.x, event.y));
					if (MainWindow.lastItemBar2 != item) {
						if ((MainWindow.lastItemBar2 != null)
								&& (MainWindow.lastItemBar2.getData() != null)) {
							MainWindow.lastItemBar2.setImage(AppImages.getImage128((String) MainWindow.lastItemBar2.getData()));
						}
						MainWindow.lastItemBar2 = item;
						if ((MainWindow.lastItemBar2 != null)
								&& (MainWindow.lastItemBar2.getData() != null)) {
							MainWindow.lastItemBar2.setImage(AppImages.getImage128Focus((String) MainWindow.lastItemBar2.getData()));
						}
					}
				}
			});
			this.toolBarBig2.getShell().getDisplay().timerExec(250, new Runnable() {
				@Override
				public void run() {
					if (MainWindow.this.toolBarBig2.isDisposed()) {
						return;
					}
					Point pt = MainWindow.this.toolBarBig2.getShell().getDisplay().map(null,
							MainWindow.this.toolBarBig2,
							MainWindow.this.toolBarBig2.getShell().getDisplay().getCursorLocation());
					ToolItem item = MainWindow.this.toolBarBig2.getItem(pt);
					if (MainWindow.lastItemBar2 != item) {
						if ((MainWindow.lastItemBar2 != null)
								&& (MainWindow.lastItemBar2.getData() != null)) {
							MainWindow.lastItemBar2.setImage(AppImages.getImage128((String) MainWindow.lastItemBar2.getData()));
						}
						MainWindow.lastItemBar2 = item;
					}
					MainWindow.this.toolBarBig2.getShell().getDisplay().timerExec(250, this);
				}
			});

			itemSeparator = new ToolItem(this.toolBarBig2, SWT.SEPARATOR);
			label = new Label(this.toolBarBig2, SWT.NONE);
			gd = new GridData();
			label.setText(" ");
			label.setLayoutData(gd);

			itemSeparator.setWidth(160);
			itemSeparator.setControl(label);

			this.itemRestore = new ToolItem(this.toolBarBig2, SWT.PUSH);
			this.itemRestore.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE));
			this.itemRestore.setImage(AppImages.getImage128(AppImages.IMG_REPEAT));
			this.itemRestore.setData(AppImages.IMG_REPEAT);
			this.itemRestore.addListener(SWT.Selection, this);

			label = new Label(comp, SWT.NONE);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 3;
			gd.heightHint = 60;
			label.setLayoutData(gd);

			this.createMainToolbar();

		}
		catch (Exception ex) {
			MainWindow.logger.fatal(ex, ex);
		}
	}

	private void createMainToolbar() {
		this.toolBarMain = new ToolBar(this.shell, SWT.FLAT | SWT.WRAP);
		this.toolBarMain.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		this.toolBarMain.setLayoutData(gd);
		GridLayout lay = new GridLayout(6, true);
		lay.horizontalSpacing = 20;
		lay.marginLeft = 100;
		this.toolBarMain.setLayout(lay);

		this.toolBarMain.addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ToolItem item = MainWindow.this.toolBarMain.getItem(new Point(event.x, event.y));
				if (MainWindow.lastItemMainBar != item) {
					if (MainWindow.lastItemMainBar != null
							&& MainWindow.lastItemMainBar.getData() != null) {
						MainWindow.lastItemMainBar.setImage(AppImages.getImage64((String) MainWindow.lastItemMainBar.getData()));
					}
					MainWindow.lastItemMainBar = item;
					if (MainWindow.lastItemMainBar != null
							&& MainWindow.lastItemMainBar.getData() != null) {
						MainWindow.lastItemMainBar.setImage(AppImages.getImage64Focus((String) MainWindow.lastItemMainBar.getData()));
					}
				}
			}
		});
		this.toolBarMain.getShell().getDisplay().timerExec(250, new Runnable() {
			@Override
			public void run() {
				if (MainWindow.this.toolBarMain.isDisposed()) {
					return;
				}
				Point pt = MainWindow.this.toolBarMain.getShell().getDisplay().map(null,
						MainWindow.this.toolBarMain,
						MainWindow.this.toolBarMain.getShell().getDisplay().getCursorLocation());
				ToolItem item = MainWindow.this.toolBarMain.getItem(pt);
				if (MainWindow.lastItemMainBar != item) {
					if (MainWindow.lastItemMainBar != null
							&& MainWindow.lastItemMainBar.getData() != null) {
						MainWindow.lastItemMainBar.setImage(AppImages.getImage64((String) MainWindow.lastItemMainBar.getData()));
					}
					MainWindow.lastItemMainBar = item;
				}
				MainWindow.this.toolBarMain.getShell().getDisplay().timerExec(250, this);
			}
		});

		int nrChars = 5;
		addSeparatorMainBar(2);

		this.itemUserAccount = new ToolItem(this.toolBarMain, SWT.PUSH);
		this.itemUserAccount.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT));
		this.itemUserAccount.setImage(AppImages.getImage64(AppImages.IMG_USER_ACCOUNT));
		this.itemUserAccount.setData(AppImages.IMG_USER_ACCOUNT);
		this.itemUserAccount.addListener(SWT.Selection, this);

		addSeparatorMainBar(nrChars);

		this.itemBackupSet = new ToolItem(this.toolBarMain, SWT.PUSH);
		this.itemBackupSet.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET));
		this.itemBackupSet.setImage(AppImages.getImage64(AppImages.IMG_BACKUP_SET));
		this.itemBackupSet.setData(AppImages.IMG_BACKUP_SET);
		this.itemBackupSet.addListener(SWT.Selection, this);

		addSeparatorMainBar(nrChars);

		this.itemSchedule = new ToolItem(this.toolBarMain, SWT.PUSH);
		this.itemSchedule.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE));
		this.itemSchedule.setImage(AppImages.getImage64(AppImages.IMG_SCHEDULE));
		this.itemSchedule.setData(AppImages.IMG_SCHEDULE);
		this.itemSchedule.addListener(SWT.Selection, this);

		addSeparatorMainBar(nrChars);

		this.itemSettings = new ToolItem(this.toolBarMain, SWT.PUSH);
		this.itemSettings.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS));
		this.itemSettings.setImage(AppImages.getImage64(AppImages.IMG_SETTINGS));
		this.itemSettings.setData(AppImages.IMG_SETTINGS);
		this.itemSettings.addListener(SWT.Selection, this);

		addSeparatorMainBar(nrChars);

		this.itemReports = new ToolItem(this.toolBarMain, SWT.PUSH);
		this.itemReports.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS));
		this.itemReports.setImage(AppImages.getImage64(AppImages.IMG_REPORTS));
		this.itemReports.setData(AppImages.IMG_REPORTS);
		this.itemReports.addListener(SWT.Selection, this);

		addSeparatorMainBar(nrChars);

		this.itemHelp = new ToolItem(this.toolBarMain, SWT.PUSH);
		this.itemHelp.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_HELP));
		this.itemHelp.setImage(AppImages.getImage64(AppImages.IMG_HELP));
		this.itemHelp.setData(AppImages.IMG_HELP);
		this.itemHelp.addListener(SWT.Selection, this);
	}

	private void addSeparatorMainBar(final int nrChars) {
		ToolItem itemSeparator = new ToolItem(this.toolBarMain, SWT.NONE);
		String str = "";
		for (int i = 0; i < nrChars; i++) {
			str += " ";
		}
		itemSeparator.setText(str);
		itemSeparator.setEnabled(false);
	}

	private void changeLanguage() {
		this.shell.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_TITLE));
		this.itemUserAccount.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_USER_ACCOUNT));
		this.itemBackupSet.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP_SET));
		this.itemSchedule.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SCHEDULE));
		this.itemSettings.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_SETTINGS));
		this.itemReports.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_REPORTS));
		this.itemHelp.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_HELP));
		this.itemBachUp.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_BACKUP));
		this.itemRestore.setText(Language.getTextMainWindow(Language.MAIN_WINDOW_TEXT_BUTTON_RESTORE));
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
		BackUpSourceWindow dlg = new BackUpSourceWindow();
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			// TODO action if ok
		}
		// TODO action
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

	/*
	 * backup button action
	 */

	private Set<Filter> setFilter;

	private boolean itemBackUpAction() {

		AdvancedBackupSourceWindow dlg1 = new AdvancedBackupSourceWindow(this.backUp);
		dlg1.open();

		Backup backupTEST = new Backup();
		backupTEST.setName("TEST" + this.backUpRestore.size());

		if (dlg1.getExitChoiceAction() == SWT.OK) {
			this.backUp = dlg1.getResult();
			backupTEST.setBackupset(dlg1.getResult());
			this.backUpRestore.add(backupTEST);
		}
		// BackUpFilterWindow dlg1 = new BackUpFilterWindow(this.setFilter);
		// dlg1.open();
		// if (dlg1.getExitChoiceAction() == SWT.OK) {
		// this.setFilter = dlg1.getResult();
		// System.err.println("SIZE::" + this.setFilter.size());
		// }

		if (MainWindow.backUpFirstRun) {
			BackUpSourceWindow dlg = new BackUpSourceWindow();
			dlg.open();
			if (dlg.getExitChoiceAction() == SWT.OK) {
				// TODO acction if ok
			}
		}
		return false;
	}

	public static void connect() {
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			MainWindow.logger.fatal(e, e);
		}
	}

	public static void sendRequest() {
		try {
			Thread.sleep(20);
		}
		catch (InterruptedException e) {
			MainWindow.logger.fatal(e, e);
		}
	}

	public static void receivingData() {
		try {
			Thread.sleep(20);
		}
		catch (InterruptedException e) {
			MainWindow.logger.fatal(e, e);
		}
	}

	/*
	 * restore button action
	 */
	private boolean itemRestoreAction() {
		try {
			Method[] method = new Method[3];
			String[] text = new String[3];

			try {
				method[0] = MainWindow.class.getMethod("connect");
				text[0] = "Connecting to server..";
				method[1] = MainWindow.class.getMethod("sendRequest");
				text[1] = "Sending request to server..";
				method[2] = MainWindow.class.getMethod("receivingData");
				text[2] = "Receiving data from server..";
			}
			catch (Exception e) {
				MainWindow.logger.fatal(e, e);
				return false;
			}

			// WaitMessageWindow msg = new WaitMessageWindow(
			// this.toolBarBig2.getShell(),
			// true);
			// int opt = msg.open(method, text);
			// if (opt == SWT.CANCEL)
			// return false;

			RestoreWindow dlg = new RestoreWindow(this.restore, this.backUpRestore);
			dlg.open();
			if (dlg.getExitChoiceAction() == SWT.OK) {
				this.restore = dlg.getResult();
			}
			return true;
		}
		catch (Exception e) {
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
				case SWT.Selection:
					if (src == this.itemUserAccount) {
						this.itemUserAccountAction();
					} else if (src == this.itemBackupSet) {
						this.itemBackupSetAction();
					} else if (src == this.itemSchedule) {
						this.itemScheduleAction();
					} else if (src == this.itemSettings) {
						this.itemSettingsAction();
					} else if (src == this.itemReports) {
						this.itemReportsAction();
					} else if (src == this.itemHelp) {
						this.itemHelpAction();
					} else if (src == this.itemBachUp) {
						this.itemBackUpAction();
					} else if (src == this.itemRestore) {
						this.itemRestoreAction();
					}
					break;

				default:
					break;
			}
		}
		catch (Exception e) {
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
