/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 9, 2010
 * @description
 */
package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ExtraBackupSettingsComposite extends AbstractSettingsComposite {

	private Group groupSystemLogout;
	private Button buttonEnableSystem;
	private Group groupBackup;
	private Group groupOfflineBackUp;
	private Button buttonOfflineBackup;
	private Combo comboDays;
	private Combo comboHours;
	private Combo comboEmailDays;
	private Button buttonShowBackup;

	/**
	 * @param parent
	 * @param title
	 */
	public ExtraBackupSettingsComposite(Composite parent, String title) {
		super(parent, title);
		createGUI();
		populateData();
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#createGUI()
	 */
	@Override
	protected void createGUI() {

		this.groupOfflineBackUp = new Group(this, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupOfflineBackUp.setLayout(new GridLayout(5, false));
		this.groupOfflineBackUp.setLayoutData(data);

		Composite comp = new Composite(this.groupOfflineBackUp, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupOfflineBackUp.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_OFF_LINE));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_OFFLINE));

		this.buttonOfflineBackup = new Button(this.groupOfflineBackUp, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 5;
		this.buttonOfflineBackup.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_ENABLE));
		this.buttonOfflineBackup.setLayoutData(data);
		this.buttonOfflineBackup.addListener(SWT.Selection, this);

		new Label(this.groupOfflineBackUp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_COMBO_BACKUP_INTERVAL));

		this.comboDays = new Combo(this.groupOfflineBackUp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboDays.setLayoutData(data);

		new Label(this.groupOfflineBackUp, SWT.NONE).setText(Language.getTextGeneral(Language.GENERAL_TEXT_DAYS));

		this.comboHours = new Combo(this.groupOfflineBackUp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboHours.setLayoutData(data);

		new Label(this.groupOfflineBackUp, SWT.NONE).setText(Language.getTextGeneral(Language.GENERAL_TEXT_HOURS));

		new Label(this.groupOfflineBackUp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_COMBO_EMAIL_NOTIFICATION));

		this.comboEmailDays = new Combo(this.groupOfflineBackUp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboEmailDays.setLayoutData(data);

		new Label(this.groupOfflineBackUp, SWT.NONE).setText(Language.getTextGeneral(Language.GENERAL_TEXT_DAYS));

		this.buttonShowBackup = new Button(this.groupOfflineBackUp, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 5;
		this.buttonShowBackup.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_SHOW_BACKUP));
		this.buttonShowBackup.setLayoutData(data);
		this.buttonShowBackup.addListener(SWT.Selection, this);

		this.groupSystemLogout = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupSystemLogout.setLayout(new GridLayout(4, false));
		this.groupSystemLogout.setLayoutData(data);

		comp = new Composite(this.groupSystemLogout, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupSystemLogout.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_LOGOUT));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_SYSTEM_LOGOUT));

		this.buttonEnableSystem = new Button(this.groupSystemLogout, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 4;
		this.buttonEnableSystem.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_BUTTON_ENABLE_SYSTEM_LOGOUT));
		this.buttonEnableSystem.setLayoutData(data);
		this.buttonEnableSystem.addListener(SWT.Selection, this);

		this.groupBackup = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupBackup.setLayout(new GridLayout(4, false));
		this.groupBackup.setLayoutData(data);

		comp = new Composite(this.groupBackup, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupBackup.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_BACKUP));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_GROUP_BACKUP));

		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP_LABEL_FILE));

		this.buttonOfflineBackup.notifyListeners(SWT.Selection, new Event());
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#populateData()
	 */
	@Override
	protected void populateData() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#save()
	 */
	@Override
	protected boolean save() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#validate()
	 */
	@Override
	protected boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(Event event) {
		if (event.type == SWT.Selection) {
			if (event.widget == this.buttonOfflineBackup) {
				this.comboDays.setEnabled(this.buttonOfflineBackup.getSelection());
				this.comboEmailDays.setEnabled(this.buttonOfflineBackup.getSelection());
				this.comboHours.setEnabled(this.buttonOfflineBackup.getSelection());
				this.buttonShowBackup.setEnabled(this.buttonOfflineBackup.getSelection());
			}
		}
	}

}
