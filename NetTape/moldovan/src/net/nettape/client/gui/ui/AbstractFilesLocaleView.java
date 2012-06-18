package net.nettape.client.gui.ui;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.FileUtil;
import net.nettape.client.gui.util.StringUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
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

public abstract class AbstractFilesLocaleView extends AbstractFilesWindow {

	private static final Logger logger = Logger.getLogger(AbstractFilesLocaleView.class);

	public AbstractFilesLocaleView(String title, Image image) {
		super(title, image);
		try {

		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected void treeDefaultSelectionAction() {
		final TreeItem[] selection = this.tree.getSelection();
		if (selection != null && selection.length != 0) {
			TreeItem item = selection[0];
			item.setExpanded(true);
			if (item.getParentItem() != null) {
				item.setImage(AppImages.getImage16(AppImages.IMG_FOLDER_FULL));
			}
			this.treeExpandItem(item);
		}
	}

	protected void treeExpandAction(final Widget widget) {
		final TreeItem item = (TreeItem) widget;
		final Image image = (Image) item.getData(TREEITEMDATA_IMAGEEXPANDED);
		if (image != null)
			item.setImage(image);
		this.treeExpandItem(item);
	}

	protected void treeCollapseAction(final Widget widget) {
		final TreeItem item = (TreeItem) widget;
		final Image image = (Image) item.getData(TREEITEMDATA_IMAGECOLLAPSED);
		if (image != null)
			item.setImage(image);
	}

	protected void populateTree() {
		this.shell.setCursor(new Cursor(
			this.shell.getDisplay(),
			SWT.CURSOR_WAIT));
		final File[] roots = FileUtil.getRoots();

		for (int i = 0; i < roots.length; i++) {
			final File masterFile = roots[i];
			TreeItem item = new TreeItem(this.tree, SWT.NULL);
			this.treeInitVolume(item, masterFile);
			new TreeItem(item, SWT.NULL);
		}
		this.shell.setCursor(null);
	}

	protected void treeInitFolder(TreeItem item, File folder) {
		item.setText(folder.getName());
		item.setImage(AppImages.getImage16(AppImages.IMG_FOLDER));
		item.setData(TREEITEMDATA_FILE, folder);
		item.setData(TREEITEMDATA_IMAGEEXPANDED,
				AppImages.getImage16(AppImages.IMG_FOLDER_FULL));
		item.setData(TREEITEMDATA_IMAGECOLLAPSED,
				AppImages.getImage16(AppImages.IMG_FOLDER));
	}

	protected void treeInitVolume(TreeItem item, File volume) {
		try {
			String name = "";
			if (System.getProperty("os.name").indexOf("Windows") != -1) {
				name = FileSystemView.getFileSystemView().getSystemDisplayName(volume);
				if (StringUtil.isEmpty(name)) {
					name = FileSystemView.getFileSystemView().getSystemTypeDescription(volume)
							+ " (" + volume.getPath().replace('\\', ' ') + ")";
				}
				if (StringUtil.isEmpty(name)) {
					name = volume.getPath();
				}
				item.setText(name);
				item.setImage(AppImages.getImage16(AppImages.IMG_HDD));
				item.setData(TREEITEMDATA_FILE, volume);
				item.setData(TREEITEMDATA_IMAGEEXPANDED,
						AppImages.getImage16(AppImages.IMG_HDD));
				item.setData(TREEITEMDATA_IMAGECOLLAPSED,
						AppImages.getImage16(AppImages.IMG_HDD));
			} else {
				this.treeInitFolder(item, volume);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected void treeRefreshItem(TreeItem dirItem, boolean forcePopulate) {
		final File dir = (File) dirItem.getData(TREEITEMDATA_FILE);

		if (!forcePopulate && !dirItem.getExpanded()) {
			if (dirItem.getData(TREEITEMDATA_STUB) != null) {
				WidgetUtils.treeItemRemoveAll(dirItem);
				new TreeItem(dirItem, SWT.NULL);
				dirItem.setData(TREEITEMDATA_STUB, null);
			}
			return;
		}
		dirItem.setData(TREEITEMDATA_STUB, this);

		File[] subFiles = (dir != null)	? FileUtil.getDirectoryList(dir, true)
										: null;
		if (subFiles == null || subFiles.length == 0) {
			WidgetUtils.treeItemRemoveAll(dirItem);
			dirItem.setExpanded(false);
			return;
		}

		TreeItem[] items = dirItem.getItems();
		final File[] masterFiles = subFiles;
		int masterIndex = 0;
		int itemIndex = 0;
		File masterFile = null;
		for (int i = 0; i < items.length; ++i) {
			while ((masterFile == null) && (masterIndex < masterFiles.length)) {
				masterFile = masterFiles[masterIndex++];
				if (!masterFile.isDirectory())
					masterFile = null;
			}

			final TreeItem item = items[i];
			final File itemFile = (File) item.getData(TREEITEMDATA_FILE);
			if ((itemFile == null) || (masterFile == null)) {
				item.dispose();
				continue;
			}
			int compare = FileUtil.compareFiles(masterFile, itemFile);
			if (compare == 0) {
				this.treeRefreshItem(item, false);
				masterFile = null;
				++itemIndex;
			} else if (compare < 0) {
				TreeItem newItem = new TreeItem(dirItem, SWT.NULL, itemIndex);
				newItem.setChecked(dirItem.getChecked());
				this.treeInitFolder(newItem, masterFile);
				new TreeItem(newItem, SWT.NULL);
				masterFile = null;
				++itemIndex;
				--i;
			} else {
				item.dispose();
			}
		}
		while ((masterFile != null) || (masterIndex < masterFiles.length)) {
			if (masterFile != null) {
				TreeItem newItem = new TreeItem(dirItem, SWT.NULL);
				newItem.setChecked(dirItem.getChecked());
				this.treeInitFolder(newItem, masterFile);
				new TreeItem(newItem, SWT.NULL);
				if (masterIndex == masterFiles.length)
					break;
			}
			masterFile = masterFiles[masterIndex++];
			if (!masterFile.isDirectory())
				masterFile = null;
		}
	}

	protected void treeExpandItem(TreeItem item) {
		this.shell.setCursor(new Cursor(
			this.shell.getDisplay(),
			SWT.CURSOR_WAIT));
		final Object stub = item.getData(TREEITEMDATA_STUB);
		if (stub == null)
			this.treeRefreshItem(item, true);
		this.shell.setCursor(null);
	}

}
