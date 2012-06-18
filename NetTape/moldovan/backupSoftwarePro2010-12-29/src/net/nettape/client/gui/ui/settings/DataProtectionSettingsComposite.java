/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 8, 2010
 * @description
 */
package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class DataProtectionSettingsComposite extends AbstractSettingsComposite {

	private Button buttonEnable;
	private Combo comboTimeInterval;
	private Combo comboMinimumIntervat;
	private Button buttonBackUpFile;
	private Button buttonBackUpAllFile;
	private Button buttonBackUpCustom;
	private Button buttonNotBackUp;
	private Group group;

	/**
	 * @param parent
	 * @param title
	 */
	public DataProtectionSettingsComposite(Composite parent, String title) {
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

		this.buttonEnable = new Button(this, SWT.CHECK);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.buttonEnable.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_ENABLE));
		this.buttonEnable.setLayoutData(data);
		this.buttonEnable.addListener(SWT.Selection, this);
		this.group = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.group.setLayout(new GridLayout(3, false));
		this.group.setLayoutData(data);

		new Label(this.group, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_COMBO_TIME));
		this.comboTimeInterval = new Combo(this.group, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.comboTimeInterval.setLayoutData(data);

		new Label(this.group, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_COMBO_MINIMUM));
		this.comboMinimumIntervat = new Combo(this.group, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.comboMinimumIntervat.setLayoutData(data);

		new Label(this.group, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_LABEL_TYPE));
		new Label(this.group, SWT.NONE).setText("");
		new Label(this.group, SWT.NONE).setText("");
		this.buttonBackUpFile = new Button(this.group, SWT.RADIO);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 3;
		this.buttonBackUpFile.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_BACKUP_FILES));
		this.buttonBackUpFile.setLayoutData(data);
		this.buttonBackUpFile.setSelection(true);

		this.buttonBackUpAllFile = new Button(this.group, SWT.RADIO);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 3;
		this.buttonBackUpAllFile.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_BACKUP_ALL_FILES));
		this.buttonBackUpAllFile.setLayoutData(data);

		this.buttonBackUpCustom = new Button(this.group, SWT.RADIO);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 3;
		this.buttonBackUpCustom.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_CUSTOM));
		this.buttonBackUpCustom.setLayoutData(data);

		new Label(this.group, SWT.NONE).setText("");
		this.buttonNotBackUp = new Button(this.group, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.buttonNotBackUp.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_DATA_PROTECTION_BUTTON_DO_NOT_BACKUP));
		this.buttonNotBackUp.setLayoutData(data);

		this.buttonEnable.notifyListeners(SWT.Selection, new Event());
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#populateData()
	 */
	@Override
	protected void populateData() {
		for (int i = 1; i <= 18; i++) {
			this.comboMinimumIntervat.add(i * 10 + " "
					+ Language.getTextGeneral(Language.GENERAL_TEXT_MINUTES));
			this.comboTimeInterval.add(i * 10 + " "
					+ Language.getTextGeneral(Language.GENERAL_TEXT_MINUTES));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#save()
	 */
	@Override
	protected boolean save() {
		return true;
	}

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
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(Event event) {
		if (event.type == SWT.Selection) {
			if (event.widget == this.buttonEnable) {
				WidgetUtils.enableGUI(this.group, this.buttonEnable.getSelection());
			}
		}
	}

}
