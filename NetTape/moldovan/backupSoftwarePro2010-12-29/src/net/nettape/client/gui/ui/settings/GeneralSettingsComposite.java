/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 1, 2010
 * @description
 */
package net.nettape.client.gui.ui.settings;

import java.io.File;
import java.util.TreeMap;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.*;
import net.nettape.client.gui.ui.LanguageWindow;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 1, 2010
 * @description
 */
public class GeneralSettingsComposite extends AbstractSettingsComposite {

	private static final Logger logger = Logger.getLogger(GeneralSettingsComposite.class);

	private Text textName;
	private Text textType;
	private Text textDomain;
	private Text textUserName;
	private Text textPassword;
	private Combo comboLanguage;
	private Button buttonAddLanguage;
	private Button buttonModLanguage;
	private Button buttonDelLanguage;

	/**
	 * @param parent
	 * @param style
	 */
	public GeneralSettingsComposite(Composite parent, String title) {
		super(parent, title);
		createGUI();
		populateData();
	}

	@Override
	protected void createGUI() {

		Group groupBackupSet = new Group(this, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.NONE, true, false);
		groupBackupSet.setLayout(new GridLayout(3, false));
		groupBackupSet.setLayoutData(data);

		Composite comp = new Composite(groupBackupSet, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) groupBackupSet.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_BACKUP_SET));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_BACKUP_SET));

		new Label(groupBackupSet, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_NAME));
		this.textName = new Text(groupBackupSet, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textName.setLayoutData(data);

		new Label(groupBackupSet, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_TYPE));
		this.textType = new Text(groupBackupSet, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textType.setLayoutData(data);

		Group groupUserAuth = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		groupUserAuth.setLayout(new GridLayout(3, false));
		groupUserAuth.setLayoutData(data);

		comp = new Composite(groupUserAuth, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) groupUserAuth.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_USER_AUTH));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_USER_AUTHENTICATION_FOR_WINDOWS));

		new Label(groupUserAuth, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_DOMAIN));
		this.textDomain = new Text(groupUserAuth, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textDomain.setLayoutData(data);

		new Label(groupUserAuth, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_USER_NAME));
		this.textUserName = new Text(groupUserAuth, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textUserName.setLayoutData(data);

		new Label(groupUserAuth, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_TEXT_PASSWORD));
		this.textPassword = new Text(groupUserAuth, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textPassword.setLayoutData(data);

		Group groupLanguage = new Group(this, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		groupLanguage.setLayoutData(data);
		lay = new GridLayout(5, false);
		groupLanguage.setLayout(lay);

		Composite comp1 = new Composite(groupLanguage, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) groupLanguage.getLayout()).numColumns;
		comp1.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp1.setLayout(lay);

		new Label(comp1, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_USER));
		new Label(comp1, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_GROUP_LANGUAGE));

		this.comboLanguage = new Combo(groupLanguage, SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		this.comboLanguage.setLayoutData(data);
		this.comboLanguage.addListener(SWT.Selection, this);

		this.buttonAddLanguage = new Button(groupLanguage, SWT.PUSH);
		this.buttonAddLanguage.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData();
		this.buttonAddLanguage.setLayoutData(data);
		this.buttonAddLanguage.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAddLanguage,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		this.buttonModLanguage = new Button(groupLanguage, SWT.PUSH);
		this.buttonModLanguage.setImage(AppImages.getImage16(AppImages.IMG_NOTE_EDIT));
		data = new GridData();
		this.buttonModLanguage.setLayoutData(data);
		this.buttonModLanguage.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonModLanguage,
				AppImages.getImage16(AppImages.IMG_NOTE_EDIT),
				AppImages.getImage16Focus(AppImages.IMG_NOTE_EDIT));

		this.buttonDelLanguage = new Button(groupLanguage, SWT.PUSH);
		this.buttonDelLanguage.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData();
		this.buttonDelLanguage.setLayoutData(data);
		this.buttonDelLanguage.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonDelLanguage,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));
	}

	private void populateComboLanguage() {
		try {
			this.comboLanguage.removeAll();
			this.comboLanguage.add(AppConstants.DEFAULT_LANGUAGE);// for default
																	// language
																	// set
			this.comboLanguage.select(0);
			this.buttonDelLanguage.setEnabled(false);
			this.buttonModLanguage.setEnabled(false);

			File dirLanguage = new File(AppConstants.LNG_DIR);
			File[] files = dirLanguage.listFiles();
			if ((files == null) || (files.length == 0)) {
				return;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().indexOf(".lng") != -1) {
					this.comboLanguage.add(files[i].getName().substring(0,
							files[i].getName().length() - 4));
				}
			}
			TreeMap<String, String> p = AppConstants.getSettings().get("SETTINGS");
			if (p != null) {
				String language = p.get("language");
				language = language.indexOf(".lng") >= 0 ? language.substring(0,
						language.length() - 4) : language;// condition for
															// DEFAULT
															// LANGUAGE
				int index = this.comboLanguage.indexOf(language);
				if (index > 0) {
					this.comboLanguage.select(index);
				}
			}
			this.buttonDelLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																							: false);
			this.buttonModLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																							: false);
		} catch (Exception e) {
			GeneralSettingsComposite.logger.fatal(e, e);
		}
	}

	@Override
	public void handleEvent(Event event) {
		Widget src = null;
		try {
			src = event.widget;
			if (event.type == SWT.Selection) {
				if (src == this.buttonAddLanguage) {
					LanguageWindow dlg = new LanguageWindow("");
					dlg.open();
					if (dlg.getExitChoiceAction() == SWT.OK) {
						this.populateComboLanguage();
					}

				} else if (src == this.buttonModLanguage) {
					LanguageWindow dlg = new LanguageWindow(this.comboLanguage.getText() + ".lng");
					dlg.open();
					if (dlg.getExitChoiceAction() == SWT.OK) {
						this.populateComboLanguage();
					}
				} else if (src == this.buttonDelLanguage) {
					if (MessageBoxWindow.question(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_DELETE_LANGUAGE)) == SWT.NO) {
						return;
					}
					File file = new File(AppConstants.LNG_DIR + this.comboLanguage.getText()
							+ ".lng");
					if (!file.exists()) {
						return;
					}
					if (!file.delete()) {
						MessageBoxWindow.error(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_DELETE_EROR_LANGUAGE));
						return;
					}
					populateComboLanguage();
				} else if (src == this.comboLanguage) {
					this.buttonDelLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																									: false);
					this.buttonModLanguage.setEnabled(this.comboLanguage.getSelectionIndex() != 0	? true
																									: false);
				}
			}
		} catch (Exception e) {
			GeneralSettingsComposite.logger.fatal(e, e);
		}
	}

	@Override
	protected boolean validate() {
		if (this.comboLanguage.getSelectionIndex() < 0) {
			MessageBoxWindow.error(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL_MESSAGE_BOX_VALIDATE_LANGUAGE));
			this.comboLanguage.forceFocus();
			return false;
		}
		return true;
	}

	@Override
	protected boolean save() {
		boolean result = false;
		try {
			if (this.comboLanguage.getSelectionIndex() == 0) {
				Language.getInstance().activateDefaultLanguage();
				GeneralSettingsComposite.logger.info("default language acctivated.");
			} else {
				Language.getInstance().setLanguage(this.comboLanguage.getText() + ".lng");
				GeneralSettingsComposite.logger.info("set language - "
						+ this.comboLanguage.getText());
			}
			TreeMap<String, String> p = new TreeMap<String, String>();
			p.put("language", this.comboLanguage.getText() + ".lng");
			AppConstants.getSettings().put("SETTINGS", p);

			INIFile ini = new INIFile(AppConstants.SETTINGS_DIR + "settings.ini");
			ini.setSections(AppConstants.getSettings());
			ini.saveFile();

			// TODO save for rest of data

			result = true;
		} catch (Exception e) {
			GeneralSettingsComposite.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	@Override
	protected void populateData() {
		populateComboLanguage();
	}
}
