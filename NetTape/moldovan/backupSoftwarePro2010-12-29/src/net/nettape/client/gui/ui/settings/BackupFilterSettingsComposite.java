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
import net.nettape.client.gui.ui.PatternWindow;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class BackupFilterSettingsComposite extends AbstractSettingsComposite {

	private static final Logger logger = Logger.getLogger(BackupFilterSettingsComposite.class);

	private Group groupDirectory;
	private Combo comboDirectory;
	private Button buttonChange;
	private Button buttonFile;
	private Button buttonDirectory;
	private Combo comboName;
	private Group groupPattern;
	private Button buttonText;
	private Button buttonUnix;
	private List listPattern;
	private Button buttonRemove;
	private Button buttonAdd;
	private Button buttonProperties;
	private Combo comboText;
	private Group groupFilterMode;
	private Button buttonInclude;
	private Button buttonIncludeEx;
	private Button buttonExclude;
	private Button buttonExcludeIn;
	private Button buttonAddFilter;
	private Button buttonDelFilter;

	/**
	 * @param parent
	 * @param title
	 */
	public BackupFilterSettingsComposite(Composite parent, String title) {
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
		GridLayout lay = new GridLayout(2, false);
		lay.marginWidth = 0;
		lay.marginLeft = lay.marginRight = 0;
		lay.marginTop = lay.marginBottom = 0;
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_COMBO_NAME));
		this.comboName = new Combo(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboName.setLayoutData(data);

		this.groupDirectory = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupDirectory.setLayout(new GridLayout(4, false));
		this.groupDirectory.setLayoutData(data);

		new Label(this.groupDirectory, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_COMBO_TOP_DIRECTORY));
		this.comboDirectory = new Combo(this.groupDirectory, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 2;
		this.comboDirectory.setLayoutData(data);

		this.buttonChange = new Button(this.groupDirectory, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = 90;
		this.buttonChange.setLayoutData(data);
		this.buttonChange.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_CHANGE));
		this.buttonChange.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonChange,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonChange.addListener(SWT.Selection, this);

		new Label(this.groupDirectory, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_LABEL_APPLY_TO));
		this.buttonFile = new Button(this.groupDirectory, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 3;
		this.buttonFile.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_FILE));
		this.buttonFile.setLayoutData(data);

		new Label(this.groupDirectory, SWT.NONE).setText("");
		this.buttonDirectory = new Button(this.groupDirectory, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 3;
		this.buttonDirectory.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_DIRECTORY));
		this.buttonDirectory.setLayoutData(data);

		this.groupPattern = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupPattern.setLayout(new GridLayout(5, false));
		this.groupPattern.setLayoutData(data);

		comp = new Composite(this.groupPattern, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupPattern.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_PATTERN));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_GROUP_PATTERN_MATCHING));

		new Label(this.groupPattern, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_LABEL_TYPE));
		this.buttonText = new Button(this.groupPattern, SWT.RADIO);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonText.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_COMBO_NAME));
		this.buttonText.setLayoutData(data);
		this.buttonText.addListener(SWT.Selection, this);

		new Label(this.groupPattern, SWT.NONE).setText("(");

		this.comboText = new Combo(this.groupPattern, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.horizontalSpan = 1;
		this.comboText.setLayoutData(data);

		new Label(this.groupPattern, SWT.NONE).setText(")");

		new Label(this.groupPattern, SWT.NONE).setText("");
		this.buttonUnix = new Button(this.groupPattern, SWT.RADIO);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 4;
		this.buttonUnix.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTON_UNIX_STYLE));
		this.buttonUnix.setLayoutData(data);
		this.buttonUnix.addListener(SWT.Selection, this);

		new Label(this.groupPattern, SWT.NONE).setText("");
		this.listPattern = new List(this.groupPattern, SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		// data.heightHint = 80;
		data.horizontalSpan = 3;
		data.verticalSpan = 3;
		this.listPattern.setLayoutData(data);
		this.listPattern.addListener(SWT.Selection, this);

		this.buttonAdd = new Button(this.groupPattern, SWT.PUSH);
		this.buttonAdd.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(SWT.LEFT, SWT.TOP, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAdd.setLayoutData(data);
		this.buttonAdd.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAdd,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		new Label(this.groupPattern, SWT.NONE).setText("");

		this.buttonRemove = new Button(this.groupPattern, SWT.PUSH);
		this.buttonRemove.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(SWT.LEFT, SWT.TOP, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonRemove.setLayoutData(data);
		this.buttonRemove.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonRemove,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));

		new Label(this.groupPattern, SWT.NONE).setText("");

		this.buttonProperties = new Button(this.groupPattern, SWT.PUSH);
		this.buttonProperties.setImage(AppImages.getImage16(AppImages.IMG_NOTE_EDIT));
		data = new GridData(SWT.LEFT, SWT.TOP, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonProperties.setLayoutData(data);
		this.buttonProperties.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonProperties,
				AppImages.getImage16(AppImages.IMG_NOTE_EDIT),
				AppImages.getImage16Focus(AppImages.IMG_NOTE_EDIT));

		this.groupFilterMode = new Group(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupFilterMode.setLayout(new GridLayout(4, false));
		this.groupFilterMode.setLayoutData(data);

		comp = new Composite(this.groupFilterMode, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupFilterMode.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_FILTER));
		new Label(comp, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_GROUP_FILTER_MODE));

		this.buttonInclude = new Button(this.groupFilterMode, SWT.RADIO);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonInclude.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_INCLUDE_MATCHED));
		this.buttonInclude.setLayoutData(data);
		this.buttonInclude.addListener(SWT.Selection, this);

		new Label(this.groupFilterMode, SWT.NONE).setText(" (");
		this.buttonIncludeEx = new Button(this.groupFilterMode, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonIncludeEx.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_EXCLUDE_ALL_OTHERS));
		this.buttonIncludeEx.setLayoutData(data);
		new Label(this.groupFilterMode, SWT.NONE).setText(" )");

		this.buttonExclude = new Button(this.groupFilterMode, SWT.RADIO);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonExclude.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_EXCLUDE_MATCHED));
		this.buttonExclude.setLayoutData(data);
		this.buttonExclude.addListener(SWT.Selection, this);
		new Label(this.groupFilterMode, SWT.NONE).setText(" (");
		this.buttonExcludeIn = new Button(this.groupFilterMode, SWT.CHECK);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonExcludeIn.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER_BUTTON_INCLUDE_ALL_OTHERS));
		this.buttonExcludeIn.setLayoutData(data);
		new Label(this.groupFilterMode, SWT.NONE).setText(" )");

		comp = new Composite(this, SWT.NONE);
		data = new GridData(SWT.RIGHT, SWT.TOP, true, false);
		data.horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		lay.marginWidth = 0;
		lay.marginLeft = lay.marginRight = 0;

		comp.setLayout(lay);

		this.buttonAddFilter = new Button(comp, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAddFilter.setLayoutData(data);
		this.buttonAddFilter.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_ADD));
		this.buttonAddFilter.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		WidgetUtils.addImageChangeListener(this.buttonAddFilter,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));
		this.buttonAddFilter.addListener(SWT.Selection, this);

		this.buttonDelFilter = new Button(comp, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.horizontalSpan = 1;
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonDelFilter.setLayoutData(data);
		this.buttonDelFilter.setText(Language.getTextGeneral(Language.GENERAL_BUTTON_REMOVE));
		this.buttonDelFilter.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		WidgetUtils.addImageChangeListener(this.buttonDelFilter,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));
		this.buttonDelFilter.addListener(SWT.Selection, this);

		this.buttonText.setSelection(true);
		this.buttonInclude.setSelection(true);
		this.buttonText.notifyListeners(SWT.Selection, new Event());
		this.buttonInclude.notifyListeners(SWT.Selection, new Event());

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

	private void enableGUI() {
		this.comboText.setEnabled(this.buttonText.getSelection());
		this.listPattern.setEnabled(this.buttonUnix.getSelection());
		this.buttonAdd.setEnabled(this.buttonUnix.getSelection());
		this.buttonRemove.setEnabled(this.buttonUnix.getSelection());
		this.buttonProperties.setEnabled(this.buttonUnix.getSelection());
		this.buttonIncludeEx.setEnabled(this.buttonInclude.getSelection());
		this.buttonExcludeIn.setEnabled(this.buttonExclude.getSelection());
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
				if (src == this.buttonAdd) {
					PatternWindow dlg = new PatternWindow();
					dlg.open();
					if (dlg.getExitChoiceAction() == SWT.OK) {

					}
				} else if (src == this.buttonText) {
					enableGUI();
				} else if (src == this.buttonUnix) {
					enableGUI();
				} else if (src == this.buttonInclude) {
					enableGUI();
				} else if (src == this.buttonExclude) {
					enableGUI();
				}
			}
		} catch (Exception e) {
			BackupFilterSettingsComposite.logger.fatal(e, e);
		}

	}
}
