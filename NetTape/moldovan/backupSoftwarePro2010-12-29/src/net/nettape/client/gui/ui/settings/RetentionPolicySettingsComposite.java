/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 8, 2010
 * @description
 */
package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.RetencionPolicyWindow;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class RetentionPolicySettingsComposite extends AbstractSettingsComposite {

	private List listRetencion;
	private Composite compButtons;
	private Button buttonAdd;
	private Button buttonRemove;
	private Button buttonProperties;
	private Button buttonKeepDeleted;
	private Combo comboDeleted1;
	private Combo comboDeleted2;
	private Button buttonAdvanced;
	private Button buttonRemoveRetencion;

	/**
	 * @param parent
	 * @param title
	 */
	public RetentionPolicySettingsComposite(Composite parent, String title) {
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

		Composite comp = new Composite(this, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(3, false);
		comp.setLayout(lay);

		this.buttonKeepDeleted = new Button(comp, SWT.RADIO);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonKeepDeleted.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_KEEP_DELETE));
		this.buttonKeepDeleted.setLayoutData(data);
		this.buttonKeepDeleted.addListener(SWT.Selection, this);
		this.buttonKeepDeleted.setSelection(true);

		this.comboDeleted1 = new Combo(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboDeleted1.setLayoutData(data);

		this.comboDeleted2 = new Combo(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboDeleted2.setLayoutData(data);

		this.buttonAdvanced = new Button(comp, SWT.RADIO);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 3;
		this.buttonAdvanced.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_ADVANCED));
		this.buttonAdvanced.setLayoutData(data);
		this.buttonAdvanced.addListener(SWT.Selection, this);

		this.listRetencion = new List(this, SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		// data.heightHint = 80;
		data.horizontalSpan = 3;
		this.listRetencion.setLayoutData(data);
		this.listRetencion.addListener(SWT.Selection, this);

		this.buttonRemoveRetencion = new Button(this, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonRemoveRetencion.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_RETENCION_POLICY_BUTTON_REMOVE));
		this.buttonRemoveRetencion.setLayoutData(data);

		this.compButtons = new Composite(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		this.compButtons.setLayoutData(data);
		lay = new GridLayout(3, false);
		lay.marginWidth = 0;
		lay.marginLeft = lay.marginRight = 0;
		lay.marginTop = lay.marginBottom = 0;
		this.compButtons.setLayout(lay);

		this.buttonAdd = new Button(this.compButtons, SWT.PUSH);
		this.buttonAdd.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(SWT.RIGHT, SWT.TOP, true, false);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAdd.setLayoutData(data);
		this.buttonAdd.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAdd,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		new Label(this, SWT.NONE).setText("");

		this.buttonRemove = new Button(this.compButtons, SWT.PUSH);
		this.buttonRemove.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(SWT.RIGHT, SWT.TOP, false, false);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonRemove.setLayoutData(data);
		this.buttonRemove.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonRemove,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));

		this.buttonProperties = new Button(this.compButtons, SWT.PUSH);
		this.buttonProperties.setImage(AppImages.getImage16(AppImages.IMG_NOTE_EDIT));
		data = new GridData(SWT.RIGHT, SWT.TOP, false, false);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonProperties.setLayoutData(data);
		this.buttonProperties.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonProperties,
				AppImages.getImage16(AppImages.IMG_NOTE_EDIT),
				AppImages.getImage16Focus(AppImages.IMG_NOTE_EDIT));

		this.buttonKeepDeleted.notifyListeners(SWT.Selection, new Event());

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
			if (event.widget == this.buttonKeepDeleted) {
				this.comboDeleted1.setEnabled(this.buttonKeepDeleted.getSelection());
				this.comboDeleted2.setEnabled(this.buttonKeepDeleted.getSelection());
				WidgetUtils.enableGUI(this.compButtons, this.buttonAdvanced.getSelection());
				this.listRetencion.setEnabled(this.buttonAdvanced.getSelection());
				this.buttonRemoveRetencion.setEnabled(this.buttonAdvanced.getSelection());

			} else if (event.widget == this.buttonAdvanced) {
				this.comboDeleted1.setEnabled(this.buttonKeepDeleted.getSelection());
				this.comboDeleted2.setEnabled(this.buttonKeepDeleted.getSelection());
				WidgetUtils.enableGUI(this.compButtons, this.buttonAdvanced.getSelection());
				this.listRetencion.setEnabled(this.buttonAdvanced.getSelection());
				this.buttonRemoveRetencion.setEnabled(this.buttonAdvanced.getSelection());
			} else if (event.widget == this.buttonAdd) {
				RetencionPolicyWindow dlg = new RetencionPolicyWindow();
				dlg.open();
				if (dlg.getExitChoiceAction() == SWT.OK) {

				}
			}
		}
	}
}