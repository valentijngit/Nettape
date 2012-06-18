/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 9, 2010
 * @description
 */
package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class OptionsSettingsComposite extends AbstractSettingsComposite {

	private Group groupTemp;
	private Text textTemp;
	private Button buttonChange;
	private Group groupVolume;
	private Button buttonEnableVolume;
	private Group groupAdvanced;
	private Combo comboTransfer;
	private Button buttonFilePermision;

	/**
	 * @param parent
	 * @param title
	 */
	public OptionsSettingsComposite(Composite parent, String title) {
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
		this.groupTemp = new Group(this, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupTemp.setLayout(new GridLayout(4, false));
		this.groupTemp.setLayoutData(data);

		Composite comp = new Composite(this.groupTemp, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupTemp.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_TEMPORARY_DIR));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_TEMPORARY_DIRECTORY));

		this.textTemp = new Text(this.groupTemp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 3;
		this.textTemp.setLayoutData(data);

		this.buttonChange = new Button(this.groupTemp, SWT.PUSH);
		data = new GridData(SWT.CENTER, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonChange.setLayoutData(data);
		this.buttonChange.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_CHANGE));
		this.buttonChange.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonChange,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonChange.addListener(SWT.Selection, this);

		this.groupVolume = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupVolume.setLayout(new GridLayout(4, false));
		this.groupVolume.setLayoutData(data);

		comp = new Composite(this.groupVolume, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupVolume.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_VOLUME));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_VOLUME_SHADOW));

		this.buttonEnableVolume = new Button(this.groupVolume, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 4;
		this.buttonEnableVolume.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_BUTTON_ENABLE_VOLUME));
		this.buttonEnableVolume.setLayoutData(data);
		this.buttonEnableVolume.addListener(SWT.Selection, this);

		Label label = new Label(this.groupVolume, SWT.NONE);
		label.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_LABEL_VOLUME_EXPLICATION));
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 4;
		label.setLayoutData(data);

		this.groupAdvanced = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupAdvanced.setLayout(new GridLayout(4, false));
		this.groupAdvanced.setLayoutData(data);

		comp = new Composite(this.groupAdvanced, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupAdvanced.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_ADVANCED_OPTION));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_GROUP_ADVANCED));

		new Label(this.groupAdvanced, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_COMBO_TRANSFER_BLOCK));
		this.comboTransfer = new Combo(this.groupAdvanced, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.comboTransfer.setLayoutData(data);

		new Label(this.groupAdvanced, SWT.NONE).setText(Language.getTextGeneral(Language.GENERAL_TEXT_KBYTES));

		this.buttonFilePermision = new Button(this.groupAdvanced, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 4;
		this.buttonFilePermision.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS_BUTTON_BACKUP_FILE));
		this.buttonFilePermision.setLayoutData(data);
		this.buttonFilePermision.addListener(SWT.Selection, this);

	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#populateData()
	 */
	@Override
	protected void populateData() {

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

	}

}
