package net.nettape.client.gui.ui;

import java.util.*;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.ui.object.Node;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 3, 2009
 * @description
 */

public class ChangePathWindow extends AbstractFilesRemoteView {

	private static final Logger logger = Logger.getLogger(ChangePathWindow.class);

	private ArrayList<TreeMap<String, Node>> listMapNode;
	
	private Node result;

	public ChangePathWindow(final ArrayList<TreeMap<String, Node>> listMapNode) {
		super(Language.getTextChangePathWindow(Language.CHANGE_PATH_WINDOW_TEXT_TITLE),
			AppImages.getImage16(AppImages.IMG_FOLDER_PROCESS));
		try {
			this.listMapNode = listMapNode;
			this.createGUI();
			this.populateData();
		}
		catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			TreeItem item = this.tree.getSelection()[0];
			this.result = (Node) item.getData(); 
			result = true;
		} catch (Exception e) {
			result = false;
			logger.fatal(e, e);
		}
		return result;
	}

	public void createGUI() {
		this.createLeftSash(this.container);
	}

	public void populateData() {
		this.populateTree();
	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.tree) {
					// TODO action
				} else if (arg0.type == SWT.DefaultSelection) {
					if (src == this.tree) {
						// TODO action
					}
				} else if (arg0.type == SWT.Expand) {
					if (src == this.tree) {
						// TODO action
					}
				} else if (arg0.type == SWT.Collapse) {
					if (src == this.tree) {
						// TODO action
					}
				}
			}
		}
		catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected boolean validateData() {
		try {
			if (this.tree.getSelectionCount()<=0) {
				MessageBoxWindow.error(Language.getTextChangePathWindow(Language.CHANGE_PATH_WINDOW_MESSAGE_BOX_TEXT_ONE_ITEM_SELECTED));
				this.tree.forceFocus();
				return false;
			}
		return true;
		} catch (Exception e) {
			logger.fatal(e, e);
			return false;
		}
	}

	public Node getResult() {
		return this.result;
	}

	@Override
	protected void createLeftSash(Composite parent) {
		this.createTree(parent, false);

	}

	protected void createHeader() {	}

	protected void createBottom() {	}

	protected void comboItemsAction() {	}

	protected void comboPageAction() {	}

	protected void treeSelectionAction(int action, Widget widget) {	}

	protected void populateTree() {
		for (int i = 0; i < this.listMapNode.size(); i++) {
			Iterator<Node> iter = this.listMapNode.get(i).values().iterator();
			while (iter.hasNext()) {
				Node node = iter.next();
				this.addTreeItem(null, node);
			}
		}
	}
}
