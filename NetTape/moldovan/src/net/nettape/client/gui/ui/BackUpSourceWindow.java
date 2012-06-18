package net.nettape.client.gui.ui;

import java.io.File;
import java.util.ArrayList;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.AppUtils;
import net.nettape.dal.object.Backupset;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 6, 2009
 * @description
 */

public class BackUpSourceWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(BackUpSourceWindow.class);

	private Button[] buttons = null;

	private Button buttonAdvanced;
	
	private Backupset backupset;

	public BackUpSourceWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextBackupSourceWindow(Language.BACKUP_SOURCE_WINDOW_TEXT_TITLE),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_FOLDER_PROCESS));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			ArrayList<String> listDir = new ArrayList<String>();
			for (int i = 0; i < this.buttons.length; i++) {
				if (!this.buttons[i].getSelection())
					continue;
				File file = (File) this.buttons[i].getData();
				if (file != null)
					listDir.add(file.getAbsolutePath());
			}

			// TODO returning of selected dirs
			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private boolean buttonAdvancedAction() {
		boolean result = false;
		try {
			AdvancedBackupSourceWindow dlg = new AdvancedBackupSourceWindow(this.backupset);
			dlg.open();
			if (dlg.getExitChoiceAction() == SWT.OK) {
				this.backupset = dlg.getResult();
				// TODO acction if ok
			}

			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	public void createGUI() {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			String[] dir = new String[] {
					AppUtils.WINDOWS_SF_DESKTOP, AppUtils.WINDOWS_SF_FAVORITES,
					AppUtils.WINDOWS_SF_MYDOCUMENT,
					AppUtils.WINDOWS_SF_PROGRAMS };
			this.buttons = new Button[dir.length];

			for (int i = 0; i < dir.length; i++) {
				File file = AppUtils.getSpecialFoldeFileWindows(dir[i]);
				if (file == null || !file.exists())
					continue;
				this.buttons[i] = new Button(this.container, SWT.CHECK);
				this.buttons[i].setText(dir[i]);
				this.buttons[i].setData(file);
				Label label = new Label(this.container, SWT.SEPARATOR
						| SWT.HORIZONTAL);
				GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
				label.setLayoutData(data);
			}

		} else if (osName.indexOf("MacOS") != -1) {
			String strDirs = System.getProperty("user.home");
			try {
				File f = new File(strDirs);
				ArrayList<File> listDirs = new ArrayList<File>();
				if (f.exists()) {
					File[] files = f.listFiles();
					if (files != null) {
						for (int i = 0; i < files.length; i++) {
							if (!files[i].isDirectory())
								continue;
							listDirs.add(files[i]);
						}
					}
				}

				this.buttons = new Button[listDirs.size()];
				for (int i = 0; i < listDirs.size(); i++) {
					this.buttons[i] = new Button(this.container, SWT.CHECK);
					this.buttons[i].setText(listDirs.get(i).getName());
					this.buttons[i].setData(listDirs.get(i));
					Label label = new Label(this.container, SWT.SEPARATOR
							| SWT.HORIZONTAL);
					GridData data = new GridData(
						SWT.FILL,
						SWT.CENTER,
						true,
						true);
					label.setLayoutData(data);
				}
			} catch (Exception e) {
				logger.fatal(e, e);
			}
		} else if (osName.indexOf("UNIX") != -1) {

		} else if (osName.indexOf("Linux") != -1) {

		}

		this.buttonAdvanced = new Button(this.container, SWT.PUSH);
		GridData data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		data.widthHint = 80;
		this.buttonAdvanced.setLayoutData(data);
		this.buttonAdvanced.setText(Language.getTextBackupSourceWindow(Language.BACKUP_SOURCE_WINDOW_BUTTON_ADVANCE));
		this.buttonAdvanced.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonAdvanced,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonAdvanced.addListener(SWT.Selection, this);

	}

	public void populateData() {
	// TODO Auto-generated method stub

	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonAdvanced) {
					this.buttonAdvancedAction();
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
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
