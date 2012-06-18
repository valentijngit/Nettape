package net.nettape.client.gui.ui;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Dec 29, 2010
 * @description
 */
public class ChangePasswordWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(ChangePasswordWindow.class);

	private Text textOldPassword;

	private Text textNewPassword;

	private Text textConfirmPassword;

	public ChangePasswordWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextChangePasswordWindow(Language.CHANGE_PASSWORD_WINDOW_TEXT_TITLE),
			Language.getTextChangePasswordWindow(Language.CHANGE_PASSWORD_WINDOW_TEXT_EXPLAIN),
			AppImages.IMG_LOCK,
			600,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_LOCK));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			ChangePasswordWindow.logger.fatal(ex, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettapepro.client.gui.ui.awindow.IWindow#createGUI()
	 */
	@Override
	public void createGUI() {
		new Label(this.container, SWT.NONE).setText(Language.getTextChangePasswordWindow(Language.CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_OLD_PASSWORD));
		this.textOldPassword = new Text(this.container, SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.textOldPassword.setLayoutData(data);
		this.textOldPassword.setEchoChar('*');

		new Label(this.container, SWT.NONE).setText(Language.getTextChangePasswordWindow(Language.CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_NEW_PASSWORD));
		this.textNewPassword = new Text(this.container, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.textNewPassword.setLayoutData(data);
		this.textNewPassword.setEchoChar('*');

		new Label(this.container, SWT.NONE).setText(Language.getTextChangePasswordWindow(Language.CHANGE_PASSWORD_WINDOW_TEXT_BUTTON_CONFIRM_PASSWORD));
		this.textConfirmPassword = new Text(this.container, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 2;
		this.textConfirmPassword.setLayoutData(data);
		this.textConfirmPassword.setEchoChar('*');
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettapepro.client.gui.ui.awindow.IWindow#populateData()
	 */
	public void populateData() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see net.nettapepro.client.gui.ui.awindow.IWindow#getResult()
	 */
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettapepro.client.gui.ui.awindow.AbstractL2Window#buttonOKAction()
	 */
	@Override
	protected boolean buttonOKAction() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettapepro.client.gui.ui.awindow.AbstractL2Window#validateData()
	 */
	@Override
	protected boolean validateData() {
		// TODO Auto-generated method stub
		return false;
	}
}