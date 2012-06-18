package net.nettape.client.gui.ui.settings;

import java.io.File;
import java.util.ArrayList;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.AppUtils;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class BackupSourceSettingsComposite extends AbstractSettingsComposite {

	private static final Logger logger = Logger.getLogger(BackupSourceSettingsComposite.class);

	private Button[] buttons = null;

	private Button buttonAdvanced;

	/**
	 * @param parent
	 * @param title
	 */
	public BackupSourceSettingsComposite(Composite parent, String title) {
		super(parent, title);
		createGUI();
		populateData();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(Event event) {
		Widget src = null;
		try {
			src = event.widget;
			if (event.type == SWT.Selection) {
				if (src == this.buttonAdvanced) {
					buttonAdvancedAction();
				}
			}
		} catch (Exception e) {
			BackupSourceSettingsComposite.logger.fatal(e, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#createGUI()
	 */
	@Override
	protected void createGUI() {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			String[] dir = new String[] {
					AppUtils.WINDOWS_SF_DESKTOP, AppUtils.WINDOWS_SF_FAVORITES,
					AppUtils.WINDOWS_SF_MYDOCUMENT, AppUtils.WINDOWS_SF_PROGRAMS };
			this.buttons = new Button[dir.length];

			for (int i = 0; i < dir.length; i++) {
				File file = AppUtils.getSpecialFoldeFileWindows(dir[i]);
				if ((file == null) || !file.exists()) {
					continue;
				}
				this.buttons[i] = new Button(this, SWT.CHECK);
				this.buttons[i].setText(dir[i]);
				this.buttons[i].setData(file);
				Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData data = new GridData(SWT.FILL, SWT.CENTER, Boolean.TRUE, Boolean.FALSE);
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
							if (!files[i].isDirectory()) {
								continue;
							}
							listDirs.add(files[i]);
						}
					}
				}

				this.buttons = new Button[listDirs.size()];
				for (int i = 0; i < listDirs.size(); i++) {
					this.buttons[i] = new Button(this, SWT.CHECK);
					this.buttons[i].setText(listDirs.get(i).getName());
					this.buttons[i].setData(listDirs.get(i));
					Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
					GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
					label.setLayoutData(data);
				}
			} catch (Exception e) {
				BackupSourceSettingsComposite.logger.fatal(e, e);
			}
		} else if (osName.indexOf("UNIX") != -1) {

		} else if (osName.indexOf("Linux") != -1) {

		}

		this.buttonAdvanced = new Button(this, SWT.PUSH);
		GridData data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.widthHint = 90;
		this.buttonAdvanced.setLayoutData(data);
		this.buttonAdvanced.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_ADVANCED));
		this.buttonAdvanced.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonAdvanced,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonAdvanced.addListener(SWT.Selection, this);
	}

	private boolean buttonAdvancedAction() {
		boolean result = false;
		try {
			// AdvancedBackupSourceWindow dlg = new AdvancedBackupSourceWindow(this.backupset);
			// dlg.open();
			// if (dlg.getExitChoiceAction() == SWT.OK) {
			// this.backupset = dlg.getResult();
			// // TODO acction if ok
			// }

			result = true;
		} catch (Exception e) {
			BackupSourceSettingsComposite.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#populateData()
	 */
	@Override
	protected void populateData() {}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#validate()
	 */
	@Override
	protected boolean validate() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#save()
	 */
	@Override
	protected boolean save() {
		return true;
	}
}