package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 8, 2010
 * @description
 */
public class BackupScheduleSettingsComposite extends AbstractSettingsComposite {

	private Button buttonEnable;
	private List listSchedule;
	private Button buttonAdd;
	private Button buttonRemove;
	private Button buttonProperties;
	private Composite compButtons;

	public BackupScheduleSettingsComposite(Composite parent, String title) {
		super(parent, title);
		createGUI();
		populateData();
	}

	@Override
	protected void createGUI() {

		this.buttonEnable = new Button(this, SWT.CHECK);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.buttonEnable.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_SCHEDULE_BUTTON_ENABLE));
		this.buttonEnable.setLayoutData(data);
		this.buttonEnable.addListener(SWT.Selection, this);

		this.listSchedule = new List(this, SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		// data.heightHint = 80;
		data.horizontalSpan = 3;
		this.listSchedule.setLayoutData(data);
		this.listSchedule.addListener(SWT.Selection, this);

		this.compButtons = new Composite(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		this.compButtons.setLayoutData(data);
		GridLayout lay = new GridLayout(3, false);
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

		this.buttonEnable.notifyListeners(SWT.Selection, new Event());
	}

	@Override
	protected void populateData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean save() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void handleEvent(Event event) {
		if (event.type == SWT.Selection) {
			if (event.widget == this.buttonEnable) {
				this.listSchedule.setEnabled(this.buttonEnable.getSelection());
				WidgetUtils.enableGUI(this.compButtons, this.buttonEnable.getSelection());
			}
		}
	}

}
