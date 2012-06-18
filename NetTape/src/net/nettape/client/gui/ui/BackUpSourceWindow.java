package net.nettape.client.gui.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.AppUtils;
import net.nettape.client.gui.util.FileUtil;
import net.nettape.dal.object.Backupset;
import net.nettape.dal.object.Backupsetitem;
import net.nettape.object.BackupSet;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 6, 2009
 * @description
 */

public class BackUpSourceWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(BackUpSourceWindow.class);

	private Button[] buttons = null;

	private Button buttonAdvanced;
	
	private BackupSet backupSet;

	private Button buttonCompression;
	
	private Button buttonEncryption;

	public BackUpSourceWindow(BackupSet backupSet) {
		super(SWT.SYSTEM_MODAL,
			Language.getTextBackupSourceWindow(Language.BACKUP_SOURCE_WINDOW_TEXT_TITLE),
			Language.getTextBackupSourceWindow(Language.BACKUP_SOURCE_WINDOW_TEXT_EXPLAIN),
			AppImages.IMG_BACKUP_SET,
			400,
			200);
		try {
			this.backupSet = backupSet;
			this.setImage(AppImages.getImage16(AppImages.IMG_FOLDER_PROCESS));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		boolean result = true;
		try
		{
			result = readData();
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private boolean buttonAdvancedAction() {
		boolean result = false;
		try {
			readData();
			AdvancedBackupSourceWindow dlg = new AdvancedBackupSourceWindow(this.backupSet);
			dlg.open();
			if (dlg.getExitChoiceAction() == SWT.OK) {
				this.backupSet = dlg.getResult();
				populateData();
				// TODO acction if ok
			}

			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	public void createGUI() {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			String[] dir = new String[] {
					AppUtils.WINDOWS_SF_DESKTOP, AppUtils.WINDOWS_SF_FAVORITES,
					AppUtils.WINDOWS_SF_MYDOCUMENT,
					AppUtils.WINDOWS_SF_PROGRAMS };
			this.buttons = new Button[dir.length];

			for (int i = 0; i < dir.length; i++) {
				File file = AppUtils.getSpecialFoldeFileWindows(dir[i]);
				if (file == null || !file.exists())
					continue;
				this.buttons[i] = new Button(this.container, SWT.CHECK);
				this.buttons[i].setText(dir[i]);
				this.buttons[i].setData(file.getAbsolutePath().toLowerCase());
				Label label = new Label(this.container, SWT.SEPARATOR
						| SWT.HORIZONTAL);
				GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
				label.setLayoutData(data);
			}

		} else if (osName.indexOf("MacOS") != -1) {
			String strDirs = System.getProperty("user.home");
			try {
				File f = new File(strDirs);
				ArrayList<File> listDirs = new ArrayList<File>();
				if (f.exists()) {
					File[] files = f.listFiles();
					if (files != null) {
						for (int i = 0; i < files.length; i++) {
							if (!files[i].isDirectory())
								continue;
							listDirs.add(files[i]);
						}
					}
				}

				this.buttons = new Button[listDirs.size()];
				for (int i = 0; i < listDirs.size(); i++) {
					this.buttons[i] = new Button(this.container, SWT.CHECK);
					this.buttons[i].setText(listDirs.get(i).getName());
					this.buttons[i].setData(listDirs.get(i).getAbsolutePath());
					Label label = new Label(this.container, SWT.SEPARATOR
							| SWT.HORIZONTAL);
					GridData data = new GridData(
						SWT.FILL,
						SWT.CENTER,
						true,
						true);
					label.setLayoutData(data);
				}
			} catch (Exception e) {
				logger.fatal(e, e);
			}
		} else if (osName.indexOf("UNIX") != -1) {

		} else if (osName.indexOf("Linux") != -1) {

		}
		Group groupSettings = new Group(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupSettings.setLayout(new GridLayout(2, false));
		groupSettings.setLayoutData(data);

		this.buttonEncryption = new Button(groupSettings, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.buttonEncryption.setText(Language.getTextBackupSourceWindow(Language.BACKUP_SOURCE_WINDOW_BUTTON_ENCRYPT_FILES));
		this.buttonEncryption.setLayoutData(data);
		this.buttonEncryption.addListener(SWT.Selection, this);

		this.buttonCompression = new Button(groupSettings, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 3;
		this.buttonCompression.setText(Language.getTextBackupSourceWindow(Language.BACKUP_SOURCE_WINDOW_BUTTON_COMPRESSION));
		this.buttonCompression.setLayoutData(data);

		this.buttonAdvanced = new Button(this.container, SWT.PUSH);
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		data.widthHint = 174;
		this.buttonAdvanced.setLayoutData(data);
		this.buttonAdvanced.setText(Language.getTextBackupSourceWindow(Language.BACKUP_SOURCE_WINDOW_BUTTON_ADVANCE));
		this.buttonAdvanced.setImage(AppImages.getImage16(AppImages.IMG_NEXT));
		WidgetUtils.addImageChangeListener(this.buttonAdvanced,
				AppImages.getImage16(AppImages.IMG_NEXT),
				AppImages.getImage16Focus(AppImages.IMG_NEXT));
		this.buttonAdvanced.addListener(SWT.Selection, this);

	}
	
	public boolean readData()
	{
		String osName = System.getProperty("os.name");
		boolean result = true;
		try {
			ArrayList<String> listDir = new ArrayList<String>();
			for (int i = 0; i < this.buttons.length; i++) {
				if (!this.buttons[i].getSelection())
					continue;
				String file = (String) this.buttons[i].getData();
				if (file != null)
					listDir.add(file);
			}

			// add the items to the backupset
			Set<Backupsetitem> backupsetitems;
			backupsetitems = this.backupSet.backupsetitems;
			Set<Backupsetitem> backupsetitemsToRemove = new HashSet<Backupsetitem>();
			for(int i=0; i<this.buttons.length; i++)
			{
				inner:
				for(Backupsetitem backupsetitem : this.backupSet.backupsetitems)
				{
					if (osName.indexOf("Windows") != -1) 
					{
						if(backupsetitem.getPath().toLowerCase().equals(((String)(this.buttons[i].getData()))))
						{
							backupsetitemsToRemove.add(backupsetitem);
						}
					}
					else
					{
						if(backupsetitem.getPath().equals(((String)(this.buttons[i].getData()))))
						{
							backupsetitemsToRemove.add(backupsetitem);
						}
					}
				}
			}
			for(Backupsetitem backupsetitem : backupsetitemsToRemove)
			{
				this.backupSet.backupsetitems.remove(backupsetitem);
			}
			for (String item : listDir)
			{
				Backupsetitem backupsetitem = new Backupsetitem();
				backupsetitem.setBackupset(this.backupSet.backupset);
				backupsetitem.setPath(item);
				backupsetitem.setRoot(FileUtil.getRoot(item));
				backupsetitem.setIsfolder(new File(item).isDirectory());
				backupsetitems.add(backupsetitem);
			}
			this.backupSet.backupset.setEnablecompression(this.buttonCompression.getSelection());
			this.backupSet.backupset.setEnableencryption(this.buttonEncryption.getSelection());
		}
		catch(Exception ex)
		{
			result = false;
		}
		return result;
	}

	public void populateData() {
		String osName = System.getProperty("os.name");
		for(int i=0; i<this.buttons.length; i++)
		{
			this.buttons[i].setSelection(false);
			inner:
			for(Backupsetitem backupsetitem : this.backupSet.backupsetitems)
			{
				if (osName.indexOf("Windows") != -1) 
				{
					if(backupsetitem.getPath().toLowerCase().equals(((String)(this.buttons[i].getData()))))
					{
						this.buttons[i].setSelection(true);
						break inner;
					}
				}
				else
				{
					if(backupsetitem.getPath().equals(((File)(this.buttons[i].getData())).getPath()))
					{
						this.buttons[i].setSelection(true);
						break inner;
					}
				}
			}
		}
		
		this.buttonCompression.setSelection(this.backupSet.backupset.getEnablecompression());
		this.buttonEncryption.setSelection(this.backupSet.backupset.getEnableencryption());

	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonAdvanced) {
					this.buttonAdvancedAction();
				}
			}
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
		return this.backupSet;
	}

}
