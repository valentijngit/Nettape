package net.nettape.client.gui.ui;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.StringUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Dec 29, 2010
 * @description
 */
public class ServersWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(ServersWindow.class);

	private Group groupServers;
	private Text textServers;
	private Button buttonAdd;
	private List listServers;
	private Button buttonRemove;

	public ServersWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextServersWindow(Language.SERVERS_WINDOW_TEXT_TITLE),
			Language.getTextServersWindow(Language.SERVERS_WINDOW_TEXT_EXPLAIN),
			AppImages.IMG_DATABASE_ADD,
			600,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_ADD));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			ServersWindow.logger.fatal(ex, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.IWindow#createGUI()
	 */
	@Override
	public void createGUI() {
		this.groupServers = new Group(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.groupServers.setLayout(new GridLayout(2, false));
		this.groupServers.setLayoutData(data);

		Composite comp = new Composite(this.groupServers, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) this.groupServers.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_DATABASE_ADD));
		new Label(comp, SWT.NONE).setText(Language.getTextServersWindow(Language.SERVERS_WINDOW_TEXT_GROUP_SERVERS));

		this.textServers = new Text(this.groupServers, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 200;
		this.textServers.setLayoutData(data);
		this.textServers.addListener(SWT.Modify, this);

		this.buttonAdd = new Button(this.groupServers, SWT.PUSH);
		this.buttonAdd.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAdd.setLayoutData(data);
		this.buttonAdd.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAdd,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		this.listServers = new List(this.groupServers, SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 200;
		data.heightHint = 200;
		this.listServers.setLayoutData(data);
		this.listServers.addListener(SWT.Selection, this);

		this.buttonRemove = new Button(this.groupServers, SWT.PUSH);
		this.buttonRemove.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(SWT.LEFT, SWT.TOP, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonRemove.setLayoutData(data);
		this.buttonRemove.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonRemove,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));
		this.enableGUI();
	}

	private void enableGUI() {
		if (this.shell.isDisposed()) {
			return;// window closed
		}
		if (StringUtil.isEmpty(this.textServers.getText())) {
			this.buttonAdd.setEnabled(false);
		} else {
			this.buttonAdd.setEnabled(true);
		}
		if (this.listServers.getSelectionIndex() < 0) {
			this.buttonRemove.setEnabled(false);
		} else {
			this.buttonRemove.setEnabled(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.IWindow#populateData()
	 */
	@Override
	public void populateData() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.IWindow#getResult()
	 */
	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.AbstractL2Window#buttonOKAction()
	 */
	@Override
	protected boolean buttonOKAction() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.AbstractL2Window#validateData()
	 */
	@Override
	protected boolean validateData() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean buttonAddAction() {
		if (!this.validateAddCriteria()) {
			return false;
		}
		this.listServers.add(this.textServers.getText());
		this.textServers.setText("");
		return true;
	}

	private boolean buttonRemoveAction() {
		if ((this.listServers.getSelectionCount() <= 0) || (this.listServers.getItemCount() <= 0)) {
			return false;
		}
		this.listServers.remove(this.listServers.getSelectionIndex());
		return false;
	}

	private boolean validateAddCriteria() {
		String text = this.textServers.getText();
		if (StringUtil.isEmpty(text)) {
			return false;
		}

		for (int i = 0; i < this.listServers.getItemCount(); i++) {
			if (this.listServers.getItem(i).intern() == this.textServers.getText().intern()) {
				MessageBoxWindow.error(Language.getTextServersWindow(Language.SERVERS_WINDOW_MESSAGE_BOX_TEXT_DUPLICATE_SERVER));
				this.textServers.forceFocus();
				return false;
			}
		}
		return true;
	}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonAdd) {
					this.buttonAddAction();
				} else if (src == this.buttonRemove) {
					this.buttonRemoveAction();
				}
			}
			this.enableGUI();
		} catch (Exception e) {
			ServersWindow.logger.fatal(e, e);
		}
	}
}