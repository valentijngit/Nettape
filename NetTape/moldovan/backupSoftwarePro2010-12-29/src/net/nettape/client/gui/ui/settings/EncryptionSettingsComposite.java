package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.admin.Language;

import org.apache.log4j.Logger;
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
public class EncryptionSettingsComposite extends AbstractSettingsComposite {

	private static final Logger logger = Logger.getLogger(EncryptionSettingsComposite.class);

	private Text textEncryptingKey;
	private Button buttonMaskEncryptingKey;
	private Text textAlgorithm;
	private Text textMode;
	private Text textKeyLength;

	/**
	 * @param parent
	 * @param title
	 */
	public EncryptionSettingsComposite(Composite parent, String title) {
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
		Group groupEncryption = new Group(this, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		groupEncryption.setLayout(new GridLayout(3, false));
		groupEncryption.setLayoutData(data);

		new Label(groupEncryption, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_ALGORITHM));
		this.textAlgorithm = new Text(groupEncryption, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textAlgorithm.setLayoutData(data);

		new Label(groupEncryption, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_MODE));
		this.textMode = new Text(groupEncryption, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textMode.setLayoutData(data);

		new Label(groupEncryption, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_KEY_LENGTH));
		this.textKeyLength = new Text(groupEncryption, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.textKeyLength.setLayoutData(data);

		new Label(groupEncryption, SWT.NONE).setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_TEXT_ENCRYPTING_KEY));

		this.textEncryptingKey = new Text(groupEncryption, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.textEncryptingKey.setLayoutData(data);
		new Label(groupEncryption, SWT.NONE).setText("");
		this.buttonMaskEncryptingKey = new Button(groupEncryption, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.buttonMaskEncryptingKey.setText(Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION_BUTTON_MASK_ENCRYPTING_KEY));
		this.buttonMaskEncryptingKey.setLayoutData(data);
		this.buttonMaskEncryptingKey.addListener(SWT.Selection, this);

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
		Widget src = null;
		try {
			src = event.widget;
			if (event.type == SWT.Selection) {

				if (src == this.buttonMaskEncryptingKey) {
					this.textEncryptingKey.setEchoChar(this.buttonMaskEncryptingKey.getSelection()	? '*'
																									: '\0');
				}

			}
		} catch (Exception e) {
			EncryptionSettingsComposite.logger.fatal(e, e);
		}

	}

}
