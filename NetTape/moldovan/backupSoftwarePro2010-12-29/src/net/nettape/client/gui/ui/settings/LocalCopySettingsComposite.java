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

public class LocalCopySettingsComposite extends AbstractSettingsComposite {

	private Group groupCopyTo;
	private Text textCopyTo;
	private Button buttonChange;
	private Group groupRetencionPolicy;
	private Button buttonRemoveDeleted;
	private Combo comboRemoveDeleted1;
	private Combo comboRemoveDeleted2;
	private Group groupOffsiteBackup;
	private Button buttonSkip;
	private Button buttonEnable;

	/**
	 * @param parent
	 * @param title
	 */
	public LocalCopySettingsComposite(Composite parent, String title) {
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
		data.horizontalSpan = 1;
		this.buttonEnable.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_ENABLE_COPY));
		this.buttonEnable.setLayoutData(data);
		this.buttonEnable.addListener(SWT.Selection, this);

		this.groupCopyTo = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupCopyTo.setLayout(new GridLayout(4, false));
		this.groupCopyTo.setLayoutData(data);

		Composite comp = new Composite(this.groupCopyTo, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupCopyTo.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_COPY_TO));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_COPY));

		this.textCopyTo = new Text(this.groupCopyTo, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 3;
		this.textCopyTo.setLayoutData(data);

		this.buttonChange = new Button(this.groupCopyTo, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonChange.setLayoutData(data);
		this.buttonChange.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_CHANGE));
		this.buttonChange.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonChange,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonChange.addListener(SWT.Selection, this);

		this.groupRetencionPolicy = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupRetencionPolicy.setLayout(new GridLayout(4, false));
		this.groupRetencionPolicy.setLayoutData(data);

		comp = new Composite(this.groupRetencionPolicy, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupRetencionPolicy.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_RETENCION_POLICY));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_RETENCION));

		this.buttonRemoveDeleted = new Button(this.groupRetencionPolicy, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 4;
		this.buttonRemoveDeleted.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_REMOVE));
		this.buttonRemoveDeleted.setLayoutData(data);
		this.buttonRemoveDeleted.addListener(SWT.Selection, this);

		new Label(this.groupRetencionPolicy, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_COMBO_KEEP_DELETED));

		this.comboRemoveDeleted1 = new Combo(this.groupRetencionPolicy, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboRemoveDeleted1.setLayoutData(data);

		this.comboRemoveDeleted2 = new Combo(this.groupRetencionPolicy, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboRemoveDeleted2.setLayoutData(data);

		this.groupOffsiteBackup = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupOffsiteBackup.setLayout(new GridLayout(4, false));
		this.groupOffsiteBackup.setLayoutData(data);

		comp = new Composite(this.groupOffsiteBackup, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupOffsiteBackup.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_OFFSITE));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_GROUP_OFFSITE));

		this.buttonSkip = new Button(this.groupOffsiteBackup, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 4;
		this.buttonSkip.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY_BUTTON_SKIP));
		this.buttonSkip.setLayoutData(data);
		this.buttonSkip.addListener(SWT.Selection, this);

		this.buttonEnable.notifyListeners(SWT.Selection, new Event());
		this.buttonRemoveDeleted.notifyListeners(SWT.Selection, new Event());
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
			if (event.widget == this.buttonEnable) {
				WidgetUtils.enableGUI(this.groupCopyTo, this.buttonEnable.getSelection());
				WidgetUtils.enableGUI(this.groupOffsiteBackup, this.buttonEnable.getSelection());
				WidgetUtils.enableGUI(this.groupRetencionPolicy, this.buttonEnable.getSelection());
				this.buttonRemoveDeleted.notifyListeners(SWT.Selection, new Event());
			} else if (event.widget == this.buttonRemoveDeleted) {
				this.comboRemoveDeleted1.setEnabled(this.buttonRemoveDeleted.getSelection());
				this.comboRemoveDeleted2.setEnabled(this.buttonRemoveDeleted.getSelection());
			}
		}
	}
}