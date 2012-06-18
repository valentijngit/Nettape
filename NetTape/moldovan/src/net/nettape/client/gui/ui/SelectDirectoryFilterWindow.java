package net.nettape.client.gui.ui;

import java.io.File;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 8, 2009
 * @description
 */

public class SelectDirectoryFilterWindow extends AbstractFilesLocaleView {

	private static final Logger logger = Logger.getLogger(SelectDirectoryFilterWindow.class);

	private String selectedDirectory = "";

	public SelectDirectoryFilterWindow() {
		super(Language.getTextSelectDirectoryWindow(Language.SELECT_DIRECTORY_WINDOW_TEXT_TITLE),
			AppImages.getImage16(AppImages.IMG_DATABASE_ADD));
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_ADD));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	public void createGUI() {
		this.createLeftSash(this.container);
	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.tree) {
					final TreeItem[] selection = this.tree.getSelection();
					if (selection != null && selection.length != 0) {
						TreeItem item = selection[0];
						File file = (File) item.getData(TREEITEMDATA_FILE);
						if (file != null)
							this.selectedDirectory = file.getAbsolutePath();
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			final TreeItem[] selection = this.tree.getSelection();
			if (selection != null && selection.length != 0) {
				TreeItem item = selection[0];
				File file = (File) item.getData(TREEITEMDATA_FILE);
				if (file != null)
					this.selectedDirectory = file.getAbsolutePath();
			}
			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	public void populateData() {
		this.populateTree();
	}

	protected boolean validateData() {
		return true;
	}

	public String getResult() {
		return this.selectedDirectory;
	}

	protected void createLeftSash(Composite parent) {
		this.createTree(parent, false);
	}

	protected void createHeader() {}

	protected void createBottom() {}

	protected void comboItemsAction() {}

	protected void comboPageAction() {}

	protected void treeSelectionAction(int action, Widget widget) {}
}