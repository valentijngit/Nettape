package net.nettape.client.gui.ui;

import java.util.Iterator;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.ui.object.Node;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Jun 21, 2010
 * @description
 */

public abstract class AbstractFilesRemoteView extends AbstractFilesWindow {

	private static final Logger logger = Logger.getLogger(AbstractFilesRemoteView.class);

	public AbstractFilesRemoteView(String title, Image image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	protected void treeDefaultSelectionAction() {
		final TreeItem[] selection = this.tree.getSelection();
		if (selection != null && selection.length != 0) {
			TreeItem item = selection[0];
			item.setExpanded(true);
			item.setImage(AppImages.getImage16(AppImages.IMG_FOLDER_FULL));
		}
	}

	protected void treeExpandAction(final Widget widget) {
		final TreeItem item = (TreeItem) widget;
		final Image image = (Image) item.getData(TREEITEMDATA_IMAGEEXPANDED);
		if (image != null)
			item.setImage(image);
	}

	protected void treeCollapseAction(final Widget widget) {
		final TreeItem item = (TreeItem) widget;
		final Image image = (Image) item.getData(TREEITEMDATA_IMAGECOLLAPSED);
		if (image != null)
			item.setImage(image);
	}
	
	protected void addTreeItem(final TreeItem parent, final Node node) {
		try {
			if (!node.isFolder())
				return;
			final TreeItem item;
			if (parent != null) {
				item = new TreeItem(parent, SWT.NONE);
			} else {
				item = new TreeItem(this.tree, SWT.NONE);
			}
			item.setText(node.getValue());
			item.setImage(AppImages.getImage16(AppImages.IMG_FOLDER));
			item.setData(node);
			item.setData(TREEITEMDATA_IMAGEEXPANDED,
					AppImages.getImage16(AppImages.IMG_FOLDER_FULL));
			item.setData(TREEITEMDATA_IMAGECOLLAPSED, AppImages.getImage16(AppImages.IMG_FOLDER));
			Iterator<Node> iter = node.getChildrens().values().iterator();
			while (iter.hasNext()) {
				Node node2 = iter.next();
				this.addTreeItem(item, node2);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
