package net.nettape.client.gui.ui;

import java.util.LinkedList;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;
import net.nettape.object.BackupSet;

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
public class BackupLogWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(BackupLogWindow.class);
	private Combo comboItems;
	private Combo comboPage;
	private Table table;

	protected final int[] tableWidths = new int[] {
			100, 400, 200 };
	protected final int[] tableAligns = new int[] {
			SWT.LEFT, SWT.LEFT, SWT.CENTER };

	protected final String[] tableTitles = new String[] {
			Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_TABLE_COLUMN_TYPE),
			Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_TABLE_COLUMN_LOG),
			Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_TABLE_COLUMN_TIME) };
	private Combo comboLog;
	private Combo comboShow;
	private Combo comboBackupSet;

	private LinkedList<BackupSet> backupSetList;
	public BackupLogWindow(LinkedList<BackupSet> backupSetList) {
		super(SWT.SYSTEM_MODAL | SWT.RESIZE | SWT.MAX,
			Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_TITLE),
			Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_EXPLAIN),
			AppImages.IMG_REPORTS,
			400,
			200);
		try {
			this.backupSetList = backupSetList;
			this.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_PROCESS));
			this.createGUI();
			this.buttonOK.dispose();
			populateData();
		} catch (Exception ex) {
			BackupLogWindow.logger.fatal(ex, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettapepro.client.gui.ui.awindow.IWindow#createGUI()
	 */
	@Override
	public void createGUI() {
		Composite composite = new Composite(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
		composite.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		lay.marginWidth = 0;
		composite.setLayout(lay);
		new Label(composite, SWT.NONE).setText(Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_COMBO_BACKUP_SET)
				+ ":");
		this.comboBackupSet = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.comboBackupSet.setLayoutData(data);
		this.comboBackupSet.addListener(SWT.Selection, this);

		Label label = new Label(this.container, SWT.SEPARATOR | SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.BOTTOM, true, false);
		data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
		label.setLayoutData(data);
		createCompositeLogShow(this.container);
		createTable(this.container);
		createCompositeItemsPages(this.container);
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettapepro.client.gui.ui.awindow.IWindow#populateData()
	 */
	public void populateData() {
		for(BackupSet item : this.backupSetList)
		{
			this.comboBackupSet.add(item.backupset.getName());
		}
		this.comboLog.add("Backup");
		this.comboLog.add("Backup errors");
		this.comboLog.add("Restore");
		this.comboLog.add("Restore errors");

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

	protected void createCompositeItemsPages(Composite parent) {
		Composite compositeItems = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
		compositeItems.setLayoutData(data);
		GridLayout lay = new GridLayout(4, false);
		lay.marginWidth = 0;
		compositeItems.setLayout(lay);
		new Label(compositeItems, SWT.NONE).setText(Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_COMBO_LOGS_PER_PAGE)
				+ ":");
		this.comboItems = new Combo(compositeItems, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		data.widthHint = 80;
		this.comboItems.setLayoutData(data);
		this.comboItems.addListener(SWT.Selection, this);
		this.comboItems.add("50");
		this.comboItems.add("100");
		this.comboItems.add("200");
		this.comboItems.add("500");
		this.comboItems.add("1000");
		this.comboItems.select(0);

		Label label = new Label(compositeItems, SWT.NONE);
		label.setText(Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_COMBO_PAGE) + ":");
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		label.setLayoutData(data);
		this.comboPage = new Combo(compositeItems, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		data.widthHint = 80;
		this.comboPage.setLayoutData(data);
		this.comboPage.addListener(SWT.Selection, this);
	}

	protected void createCompositeLogShow(Composite parent) {
		Composite compositeItems = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
		compositeItems.setLayoutData(data);
		GridLayout lay = new GridLayout(4, false);
		lay.marginWidth = 0;
		compositeItems.setLayout(lay);
		new Label(compositeItems, SWT.NONE).setText(Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_COMBO_LOG)
				+ ":");
		this.comboLog = new Combo(compositeItems, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		data.widthHint = 80;
		this.comboLog.setLayoutData(data);
		this.comboLog.addListener(SWT.Selection, this);

		Label label = new Label(compositeItems, SWT.NONE);
		label.setText(Language.getTextBackupLogWindow(Language.BACKUP_LOG_WINDOW_COMBO_PAGE) + ":");
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		label.setLayoutData(data);
		this.comboShow = new Combo(compositeItems, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		data.widthHint = 80;
		this.comboShow.setLayoutData(data);
		this.comboShow.addListener(SWT.Selection, this);
	}

	protected void createTable(Composite composite) {
		this.table = new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI
				| SWT.FULL_SELECTION | SWT.CHECK);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 300;
		this.table.setLayoutData(data);

		for (int i = 0; i < this.tableTitles.length; ++i) {
			TableColumn column = new TableColumn(this.table, SWT.NONE);
			column.setText(this.tableTitles[i]);
			column.setWidth(this.tableWidths[i]);
			column.setAlignment(this.tableAligns[i]);
		}
		this.table.setHeaderVisible(true);
		this.table.addListener(SWT.DefaultSelection, this);
		this.table.addListener(SWT.Selection, this);
	}

	private void comboItemsAction() {

	}

	private void comboPageAction() {

	}

	private void comboLogAction() {
		
	}

	private void comboShowAction() {

	}

	private void comboBackupSetAction() {

	}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			switch (arg0.type) {
				case SWT.Selection:
					if (src == this.comboItems) {
						this.comboItemsAction();
					} else if (src == this.comboPage) {
						this.comboPageAction();
					} else if (src == this.comboLog) {
						comboLogAction();
					} else if (src == this.comboShow) {
						comboShowAction();
					} else if (src == this.comboBackupSet) {
						comboBackupSetAction();
					}
					break;
			}
		} catch (Exception e) {
			BackupLogWindow.logger.fatal(e, e);
		}
	}
}