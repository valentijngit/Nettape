package net.nettape.client.gui.ui;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 22, 2009
 * @description
 */

public class UserProfileWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(UserProfileWindow.class);

	private Button buttonChange;
	private Button buttonSavePassword;
	private Text textContactName;
	private Text textContactEmail;
	private Combo comboTimeZone;
	private Text textLoginName;
	private Text textPassword;

	public UserProfileWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_TITLE),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_USER));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			String strTextLoginName = this.textLoginName.getText();
			String strTextPassword = this.textPassword.getText();
			boolean buttonSavePasswordState = this.buttonSavePassword.getSelection();
			String strTextName = this.textContactName.getText();
			String strTextEmail = this.textContactEmail.getText();
			String strComboTimeZone = this.comboTimeZone.getText();
			int indexComboTimeZone = this.comboTimeZone.getSelectionIndex();

			// TODO save data

			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private boolean buttonChangeAction() {
		// TODO action
		return false;
	}

	public void createGUI() {
		try {
			Group groupUserInformation = new Group(this.container, SWT.NONE);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			groupUserInformation.setLayout(new GridLayout(3, false));
			groupUserInformation.setLayoutData(data);

			Composite comp = new Composite(groupUserInformation, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) groupUserInformation.getLayout()).numColumns;
			comp.setLayoutData(data);
			GridLayout lay = new GridLayout(2, false);
			comp.setLayout(lay);

			new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_USER));
			new Label(comp, SWT.NONE).setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TITLE));

			new Label(groupUserInformation, SWT.NONE).setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_NAME)
					+ ":");
			this.textLoginName = new Text(groupUserInformation, SWT.BORDER);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.widthHint = 100;
			this.textLoginName.setLayoutData(data);

			new Label(groupUserInformation, SWT.NONE).setText(" ");

			new Label(groupUserInformation, SWT.NONE).setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_PASSWORD)
					+ ":");
			this.textPassword = new Text(groupUserInformation, SWT.PASSWORD
					| SWT.BORDER);
			data = new GridData(
				GridData.FILL,
				GridData.VERTICAL_ALIGN_BEGINNING,
				false,
				false);
			data.widthHint = 100;
			this.textPassword.setLayoutData(data);

			this.buttonChange = new Button(groupUserInformation, SWT.PUSH);
			this.buttonChange.setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_BUTTON_CHANGE));
			this.buttonChange.setImage(AppImages.getImage16(AppImages.IMG_REFRESH));
			data = new GridData(GridData.FILL_HORIZONTAL);
			this.buttonChange.setLayoutData(data);
			this.buttonChange.addListener(SWT.Selection, this);
			WidgetUtils.addImageChangeListener(this.buttonChange,
					AppImages.getImage16(AppImages.IMG_REFRESH),
					AppImages.getImage16Focus(AppImages.IMG_REFRESH));

			new Label(groupUserInformation, SWT.NONE).setText("");

			this.buttonSavePassword = new Button(
				groupUserInformation,
				SWT.CHECK);
			this.buttonSavePassword.setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_USER_INFORMATION_BUTTON_SAVE_PASSWORD));
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			this.buttonSavePassword.setLayoutData(data);

			Group groupContact = new Group(this.container, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			groupContact.setLayout(new GridLayout(3, false));
			groupContact.setLayoutData(data);

			comp = new Composite(groupContact, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) groupContact.getLayout()).numColumns;
			comp.setLayoutData(data);
			lay = new GridLayout(2, false);
			comp.setLayout(lay);

			new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_MAIL));
			new Label(comp, SWT.NONE).setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_CONTACT_TITLE));

			new Label(groupContact, SWT.NONE).setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_CONTACT_TEXT_NAME)
					+ ":");
			this.textContactName = new Text(groupContact, SWT.BORDER);
			data = new GridData(GridData.FILL, GridData.FILL, true, true);
			data.widthHint = 170;
			data.horizontalSpan = 2;
			this.textContactName.setLayoutData(data);

			new Label(groupContact, SWT.NONE).setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_CONTACT_TEXT_EMAIL)
					+ ":");
			this.textContactEmail = new Text(groupContact, SWT.BORDER);
			data = new GridData(GridData.FILL, GridData.FILL, false, false);
			data.widthHint = 170;
			data.horizontalSpan = 2;
			this.textContactEmail.setLayoutData(data);

			Group groupTimeZone = new Group(this.container, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			groupTimeZone.setLayout(new GridLayout(1, false));
			groupTimeZone.setLayoutData(data);

			comp = new Composite(groupTimeZone, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) groupTimeZone.getLayout()).numColumns;
			comp.setLayoutData(data);
			lay = new GridLayout(2, false);
			comp.setLayout(lay);

			new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_CLOCK));
			new Label(comp, SWT.NONE).setText(Language.getTextUserProfileWindow(Language.USER_PROFILE_WINDOW_GROUP_TIME_ZONE_TITLE));

			this.comboTimeZone = new Combo(groupTimeZone, SWT.BORDER);
			data = new GridData(GridData.FILL, GridData.FILL, true, true);
			data.widthHint = 170;
			this.comboTimeZone.setLayoutData(data);

			for (int i = -11; i < 12; i++) {
				this.comboTimeZone.add("GMT " + i + ":00");
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}

	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonChange) {
					this.buttonChangeAction();
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void populateData() {
		try {
			String strTextLoginName = "";
			String strTextPassword = "";
			boolean buttonSavePasswordState = false;
			String strTextName = "";
			String strTextEmail = "";

			String strComboTimeZone = "";

			// TODO get data for populate

			this.textLoginName.setText(strTextLoginName);
			this.textPassword.setText(strTextPassword);
			this.textContactName.setText(strTextName);
			this.textContactEmail.setText(strTextEmail);
			this.buttonSavePassword.setSelection(buttonSavePasswordState);
			this.comboTimeZone.select(this.comboTimeZone.indexOf(strComboTimeZone));
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected boolean validateData() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
