package net.nettape.client.gui.ui;

import java.awt.Color;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import net.nettape.client.Global;
import net.nettape.client.command.LoginCommand;
import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.INIFile;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.connection.Connection;
import net.nettape.object.Constants.ConnectionType;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 22, 2009
 * @description
 */

public class LoginWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(LoginWindow.class);

	private Combo comboServerName;
	private Combo comboConnectionType;
	private Button buttonChange;
	private Button buttonSavePassword;
	private Text textLoginName;
	private Text textPassword;
	
	public Connection connection;


	public LoginWindow(boolean autoLogin) {
		super(SWT.SYSTEM_MODAL,
			Language.getTextLoginWindow(Language.LOGIN_WINDOW_TITLE),
			Language.getTextLoginWindow(Language.LOGIN_WINDOW_EXPLAIN),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_USER));
			this.createGUI();
			this.populateData();
			if(autoLogin)
			{
				if(buttonOKAction()) this.close(true);
			}

		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	@Override
	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			String strTextLoginName = this.textLoginName.getText();
			String strTextPassword = this.textPassword.getText();
			boolean buttonSavePasswordState = this.buttonSavePassword.getSelection();
			
			// TODO save data
			TreeMap<String, String> p;
			p = AppConstants.getSettings().get("LOGIN");
			if(p == null)
			{
				p = new TreeMap<String, String>();
			}
			p.put("ServerName",comboServerName.getText());
			p.put("LoginName", strTextLoginName);
			p.put("SavePassword",new Boolean(buttonSavePasswordState).toString());
			p.put("ConnectionType", Integer.toString(ConnectionType.valueOf(comboConnectionType.getText()).ordinal()));
			if(Global.ClientGUID != null)
			{
				p.put("ClientGUID",Global.ClientGUID);
			}
			if (buttonSavePasswordState)
			{
				p.put("Password", strTextPassword);	
			}
		

			AppConstants.getSettings().put("LOGIN", p);
			INIFile ini = new INIFile(AppConstants.SETTINGS_DIR
					+ "settings.ini");
			ini.setSections(AppConstants.getSettings());
			ini.saveFile();
			
			LoginCommand loginCommand = new LoginCommand(comboServerName.getText(),ConnectionType.valueOf(comboConnectionType.getText()),textLoginName.getText() ,textPassword.getText());
			if(loginCommand.Execute())	
			{
				if(loginCommand != null && loginCommand.GetConnection() != null && loginCommand.GetConnection().IsLoggedIn())
				{
					connection = loginCommand.GetConnection();
					result = true;
				}
				else
				{
					MessageBox messageBox = new MessageBox(shell, SWT.OK);
			        messageBox.setText("Warning");
			        messageBox.setMessage("Login name or password incorrect!");
			        messageBox.open();
					result = false;
				}
			}
			else
			{
				MessageBox messageBox = new MessageBox(shell, SWT.OK);
		        messageBox.setText("Warning");
		        messageBox.setMessage("Could not connect to server!");
		        messageBox.open();
				result = false;
			}

			
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

	@Override
	public void createGUI() {
		try {

			Group groupServerInformation = new Group(this.container, SWT.NONE);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			groupServerInformation.setLayout(new GridLayout(3, false));
			groupServerInformation.setLayoutData(data);

			Composite comp = new Composite(groupServerInformation, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) groupServerInformation.getLayout()).numColumns;
			comp.setLayoutData(data);
			GridLayout lay = new GridLayout(2, false);
			comp.setLayout(lay);
			
			new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_USER));
			new Label(comp, SWT.NONE).setText(Language.getTextLoginWindow(Language.LOGIN_WINDOW_GROUP_SERVER_INFORMATION_TITLE));

			new Label(groupServerInformation, SWT.NONE).setText(Language.getTextLoginWindow(Language.LOGIN_WINDOW_GROUP_SERVER_INFORMATION_TEXT_SERVER_NAME)
					+ ":");

			this.comboServerName = new Combo(groupServerInformation, SWT.BORDER);
			data = new GridData(GridData.FILL, GridData.FILL, true, true);
			data.widthHint = 170;
			this.comboServerName.setLayoutData(data);

			this.comboConnectionType = new Combo(groupServerInformation, SWT.BORDER);
			data = new GridData(GridData.FILL, GridData.FILL, true, true);
			data.widthHint = 170;
			this.comboConnectionType.setLayoutData(data);

			this.buttonChange = new Button(groupServerInformation, SWT.PUSH);
			this.buttonChange.setText("...");
			data = new GridData(GridData.FILL_HORIZONTAL);
			this.buttonChange.setLayoutData(data);
			this.buttonChange.addListener(SWT.Selection, this);
			
			
			Group groupUserInformation = new Group(this.container, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			groupUserInformation.setLayout(new GridLayout(3, false));
			groupUserInformation.setLayoutData(data);

			comp = new Composite(groupUserInformation, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) groupUserInformation.getLayout()).numColumns;
			comp.setLayoutData(data);
			lay = new GridLayout(2, false);
			comp.setLayout(lay);

			new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_USER));
			new Label(comp, SWT.NONE).setText(Language.getTextLoginWindow(Language.LOGIN_WINDOW_GROUP_USER_INFORMATION_TITLE));

			new Label(groupUserInformation, SWT.NONE).setText(Language.getTextLoginWindow(Language.LOGIN_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_NAME)
					+ ":");
			this.textLoginName = new Text(groupUserInformation, SWT.BORDER);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.widthHint = 100;
			this.textLoginName.setLayoutData(data);

			new Label(groupUserInformation, SWT.NONE).setText(" ");

			new Label(groupUserInformation, SWT.NONE).setText(Language.getTextLoginWindow(Language.LOGIN_WINDOW_GROUP_USER_INFORMATION_TEXT_LOGIN_PASSWORD)
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
			this.buttonChange.setText(Language.getTextLoginWindow(Language.LOGIN_WINDOW_GROUP_USER_INFORMATION_BUTTON_CHANGE));
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
			this.buttonSavePassword.setText(Language.getTextLoginWindow(Language.LOGIN_WINDOW_GROUP_USER_INFORMATION_BUTTON_SAVE_PASSWORD));
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			this.buttonSavePassword.setLayoutData(data);



			
		} catch (Exception e) {
			logger.fatal(e, e);
		}

	}

	@Override
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
			String strServerName = "";
			String strConnectionType = "";
			boolean buttonSavePasswordState = false;
			

			// TODO get data for populate
			TreeMap<String, String> p = AppConstants.getSettings().get("LOGIN");
			if(p != null)
			{
				strTextLoginName = p.get("LoginName");
				buttonSavePasswordState = Boolean.parseBoolean(p.get("SavePassword"));
				if(buttonSavePasswordState) strTextPassword = p.get("Password");
				strServerName = p.get("ServerName");
				Integer connectionType = Integer.parseInt(p.get("ConnectionType"));
				strConnectionType = ConnectionType.class.getEnumConstants()[connectionType].name();
			}
			
			
			this.textLoginName.setText(strTextLoginName);
			this.textPassword.setText(strTextPassword);
			
			this.buttonSavePassword.setSelection(buttonSavePasswordState);
			this.comboServerName.setText(strServerName);
			
			this.comboConnectionType.add("SOCKETS");
			this.comboConnectionType.add("HTTP");
			this.comboConnectionType.setText(strConnectionType);
			
			
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
