/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 8, 2010
 * @description
 */
package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.AdvancedInFileDeltaWindow;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class InFileDeltaSettingsComposite extends AbstractSettingsComposite {

	private Button buttonEnable;
	private Group groupBlockSize;
	private Combo comboDefault;
	private Group groupApplyTo;
	private Group groupType;
	private Button buttonAdvanced;
	private Group groupUpload;
	private Button buttonNoDelta;
	private Combo comboNoDelta;
	private Button buttonDeltaRatio;
	private Combo comboDeltaRatio;

	/**
	 * @param parent
	 * @param title
	 */
	public InFileDeltaSettingsComposite(Composite parent, String title) {
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
		this.buttonEnable.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_ENABLE));
		this.buttonEnable.setLayoutData(data);
		this.buttonEnable.addListener(SWT.Selection, this);
		this.groupBlockSize = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupBlockSize.setLayout(new GridLayout(4, false));
		this.groupBlockSize.setLayoutData(data);
		// WidgetUtils.drawImageOnGroup(groupEncryption,
		// AppImages.getImage16(AppImages.IMG_LOCK));

		new Label(this.groupBlockSize, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_BLOCK_SIZE));
		this.comboDefault = new Combo(this.groupBlockSize, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.comboDefault.setLayoutData(data);
		new Label(this.groupBlockSize, SWT.NONE).setText(Language.getTextGeneral(Language.GENERAL_TEXT_BYTES));

		this.groupApplyTo = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupApplyTo.setLayout(new GridLayout(4, false));
		this.groupApplyTo.setLayoutData(data);

		Composite comp = new Composite(this.groupApplyTo, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupApplyTo.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_APPLY_TO));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_APPLY_TO));

		new Label(this.groupApplyTo, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_DEFAULT));
		this.comboDefault = new Combo(this.groupApplyTo, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.comboDefault.setLayoutData(data);
		new Label(this.groupApplyTo, SWT.NONE).setText(Language.getTextGeneral(Language.GENERAL_TEXT_BYTES));

		this.groupType = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupType.setLayout(new GridLayout(4, false));
		this.groupType.setLayoutData(data);

		comp = new Composite(this.groupType, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupType.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_IN_FILE));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_IN_FILE_DELTA));

		new Label(this.groupType, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_COMBO_DEFAULT));
		this.comboDefault = new Combo(this.groupType, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 3;
		this.comboDefault.setLayoutData(data);

		this.buttonAdvanced = new Button(this.groupType, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 4;
		data.widthHint = 90;
		this.buttonAdvanced.setLayoutData(data);
		this.buttonAdvanced.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_ADVANCED));
		this.buttonAdvanced.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonAdvanced,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonAdvanced.addListener(SWT.Selection, this);

		this.groupUpload = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupUpload.setLayout(new GridLayout(4, false));
		this.groupUpload.setLayoutData(data);

		comp = new Composite(this.groupUpload, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupType.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_UPLOAD));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_GROUP_UPLOAD_FILE));

		Label label = new Label(this.groupUpload, SWT.NONE);
		label.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_LABEL_OR));
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		data.verticalSpan = 2;
		label.setLayoutData(data);

		this.buttonNoDelta = new Button(this.groupUpload, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 1;
		this.buttonNoDelta.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_NO_DELTA));
		this.buttonNoDelta.setLayoutData(data);
		this.buttonNoDelta.addListener(SWT.Selection, this);

		this.comboNoDelta = new Combo(this.groupUpload, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.comboNoDelta.setLayoutData(data);

		this.buttonDeltaRatio = new Button(this.groupUpload, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 1;
		this.buttonDeltaRatio.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_BUTTON_DELTA_RATIO));
		this.buttonDeltaRatio.setLayoutData(data);
		this.buttonDeltaRatio.addListener(SWT.Selection, this);

		this.comboDeltaRatio = new Combo(this.groupUpload, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboDeltaRatio.setLayoutData(data);
		new Label(this.groupUpload, SWT.NONE).setText("%");

		label = new Label(this.groupUpload, SWT.NONE);
		label.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_LABEL_EXPLICATION));
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 4;

		System.err.println(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA_LABEL_EXPLICATION));
		label.setLayoutData(data);
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.settings.AbstractSettingsComposite#populateData()
	 */
	@Override
	protected void populateData() {
		WidgetUtils.enableGUI(this.groupApplyTo, this.buttonEnable.getSelection());
		WidgetUtils.enableGUI(this.groupBlockSize, this.buttonEnable.getSelection());
		WidgetUtils.enableGUI(this.groupType, this.buttonEnable.getSelection());
		WidgetUtils.enableGUI(this.groupUpload, this.buttonEnable.getSelection());
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
				WidgetUtils.enableGUI(this.groupApplyTo, this.buttonEnable.getSelection());
				WidgetUtils.enableGUI(this.groupBlockSize, this.buttonEnable.getSelection());
				WidgetUtils.enableGUI(this.groupType, this.buttonEnable.getSelection());
				WidgetUtils.enableGUI(this.groupUpload, this.buttonEnable.getSelection());
			} else if (event.widget == this.buttonAdvanced) {
				AdvancedInFileDeltaWindow dlg = new AdvancedInFileDeltaWindow();
				dlg.open();
				if (dlg.getExitChoiceAction() == SWT.OK) {

				}
			}
		}
	}

}
