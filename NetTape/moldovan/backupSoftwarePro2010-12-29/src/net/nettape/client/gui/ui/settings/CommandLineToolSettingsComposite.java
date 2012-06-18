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
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class CommandLineToolSettingsComposite extends AbstractSettingsComposite {

	private Group groupPreBackup;
	private Combo comboName;
	private Button buttonChangePre;
	private Text textWorkDirectoryPre;
	private Text textCommandPre;
	private Button buttonAddPre;
	private Button buttonDelPre;
	private Group groupPostBackup;
	private Combo comboNamePost;
	private Text textWorkDirectoryPost;
	private Button buttonChangePost;
	private Text textCommandPost;
	private Button buttonAddPost;

	public CommandLineToolSettingsComposite(Composite parent, String title) {
		super(parent, title);
		createGUI();
		populateData();
	}

	@Override
	protected void createGUI() {
		this.groupPreBackup = new Group(this, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupPreBackup.setLayout(new GridLayout(4, false));
		this.groupPreBackup.setLayoutData(data);

		Composite comp = new Composite(this.groupPreBackup, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupPreBackup.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_GLOBE));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_GROUP_PRE_BACKUP));

		new Label(this.groupPreBackup, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_COMBO_NAME));
		this.comboName = new Combo(this.groupPreBackup, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 3;
		this.comboName.setLayoutData(data);

		new Label(this.groupPreBackup, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_WORKING_DIRECTORY));
		this.textWorkDirectoryPre = new Text(this.groupPreBackup, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 2;
		this.textWorkDirectoryPre.setLayoutData(data);

		this.buttonChangePre = new Button(this.groupPreBackup, SWT.PUSH);
		data = new GridData(SWT.CENTER, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonChangePre.setLayoutData(data);
		this.buttonChangePre.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_CHANGE));
		this.buttonChangePre.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonChangePre,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonChangePre.addListener(SWT.Selection, this);

		new Label(this.groupPreBackup, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_COMMAND));
		this.textCommandPre = new Text(this.groupPreBackup, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 3;
		this.textCommandPre.setLayoutData(data);

		new Label(this.groupPreBackup, SWT.NONE).setText("");
		new Label(this.groupPreBackup, SWT.NONE).setText("");

		this.buttonAddPre = new Button(this.groupPreBackup, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAddPre.setLayoutData(data);
		this.buttonAddPre.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_ADD));
		this.buttonAddPre.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		WidgetUtils.addImageChangeListener(this.buttonAddPre,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));
		this.buttonAddPre.addListener(SWT.Selection, this);

		this.buttonDelPre = new Button(this.groupPreBackup, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonDelPre.setLayoutData(data);
		this.buttonDelPre.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_REMOVE));
		this.buttonDelPre.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		WidgetUtils.addImageChangeListener(this.buttonDelPre,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));
		this.buttonDelPre.addListener(SWT.Selection, this);

		this.groupPostBackup = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupPostBackup.setLayout(new GridLayout(4, false));
		this.groupPostBackup.setLayoutData(data);

		comp = new Composite(this.groupPostBackup, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupPostBackup.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_GLOBE));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_GROUP_POST_BACKUP));

		new Label(this.groupPostBackup, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_COMBO_NAME));
		this.comboNamePost = new Combo(this.groupPostBackup, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 3;
		this.comboNamePost.setLayoutData(data);

		new Label(this.groupPostBackup, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_WORKING_DIRECTORY));
		this.textWorkDirectoryPost = new Text(this.groupPostBackup, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 2;
		this.textWorkDirectoryPost.setLayoutData(data);

		this.buttonChangePost = new Button(this.groupPostBackup, SWT.PUSH);
		data = new GridData(SWT.CENTER, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonChangePost.setLayoutData(data);
		this.buttonChangePost.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_CHANGE));
		this.buttonChangePost.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonChangePre,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonChangePost.addListener(SWT.Selection, this);

		new Label(this.groupPostBackup, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL_TEXT_COMMAND));
		this.textCommandPost = new Text(this.groupPostBackup, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 3;
		this.textCommandPost.setLayoutData(data);

		new Label(this.groupPostBackup, SWT.NONE).setText("");
		new Label(this.groupPostBackup, SWT.NONE).setText("");

		this.buttonAddPost = new Button(this.groupPostBackup, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAddPost.setLayoutData(data);
		this.buttonAddPost.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_ADD));
		this.buttonAddPost.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		WidgetUtils.addImageChangeListener(this.buttonAddPost,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));
		this.buttonAddPost.addListener(SWT.Selection, this);

		this.buttonDelPre = new Button(this.groupPostBackup, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonDelPre.setLayoutData(data);
		this.buttonDelPre.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_REMOVE));
		this.buttonDelPre.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		WidgetUtils.addImageChangeListener(this.buttonDelPre,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));
		this.buttonDelPre.addListener(SWT.Selection, this);

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
		// TODO Auto-generated method stub

	}
}