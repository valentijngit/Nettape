package net.nettape.client.gui.ui;

import java.io.File;
import java.text.DateFormat;
import java.util.*;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.ui.object.FileState;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.FileUtil;
import net.nettape.client.gui.util.StringUtil;
import net.nettape.dal.object.*;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 8, 2009
 * @description directory population base on
 *              http://www.ibm.com/developerworks/opensource/library/os-jws/
 */
public class AdvancedBackupSourceWindow extends AbstractFilesLocaleView {

	private static final Logger logger = Logger.getLogger(AdvancedBackupSourceWindow.class);

	private Label labelAllFolders;

	private Button buttonBackUpFilter;

	private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
			DateFormat.MEDIUM);

	private File currentDirectory = null;
	volatile File workerStateDir = null;

	private final TreeMap<String, FileState> treeFiles = new TreeMap<String, FileState>();

	private Backupset backUpSet;

	Set<Filter> setFilters = null;

	public AdvancedBackupSourceWindow(Backupset backUp) {
		this();
		this.backUpSet = backUp;
		if (this.backUpSet == null) {
			this.backUpSet = new Backupset();
		}
		this.populateData();
	}

	public AdvancedBackupSourceWindow() {
		super(Language.getTextAdvancedBackupSourceWindow(Language.ADVANCED_BACKUP_SOURCE_WINDOW_TEXT_TITLE),
			AppImages.getImage16(AppImages.IMG_FOLDER));
		try {
			this.createGUI();
		} catch (Exception e) {
			AdvancedBackupSourceWindow.logger.fatal(e, e);
		}
	}

	@Override
	public void createGUI() {
		this.createSash();
		this.createLeftSash(this.sashForm);
		this.createRightSash(this.sashForm);
		this.sashForm.setWeights(new int[] {
				2, 5 });
		// this.createCompositeItemsPages();
		this.createBottom();

		this.buttonBackUpFilter = new Button(this.compositeExtraButtons, SWT.PUSH);
		GridData data = new GridData(SWT.LEFT, SWT.FILL, false, true);
		// data.widthHint = 80;
		this.buttonBackUpFilter.setLayoutData(data);
		this.buttonBackUpFilter.setText(Language.getTextAdvancedBackupSourceWindow(Language.ADVANCED_BACKUP_SOURCE_WINDOW_BUTTON_BACKUP_FILTER));
		this.buttonBackUpFilter.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_SEARCH));
		this.buttonBackUpFilter.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonBackUpFilter,
				AppImages.getImage16(AppImages.IMG_DATABASE_SEARCH),
				AppImages.getImage16Focus(AppImages.IMG_DATABASE_SEARCH));
	}

	@Override
	protected void createHeader() {}

	@Override
	protected void createBottom() {}

	@Override
	protected void createLeftSash(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = gridLayout.marginWidth = 2;
		gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0;
		composite.setLayout(gridLayout);
		this.labelAllFolders = new Label(composite, SWT.BORDER);
		this.labelAllFolders.setText(Language.getTextAdvancedBackupSourceWindow(Language.ADVANCED_BACKUP_SOURCE_WINDOW_LABEL_ALLFOLDERS));
		this.labelAllFolders.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL));
		this.createTree(composite, true);
	}

	private boolean setGrayed(File file) {
		return this.treeFiles.get(file.getAbsolutePath()).getGrayed();
	}

	private void checkAllParents(File file) {
		String parent = file.getParent();
		while (StringUtil.isNotEmpty(parent)) {
			File fileParent = new File(parent);
			if (!fileParent.exists()) {
				return;
			}

			File[] files = fileParent.listFiles();
			int nr = 0;
			int nrGray = 0;
			for (int i = 0; i < files.length; i++) {
				if ((this.treeFiles.get(files[i].getAbsolutePath()) == null)
						|| (!this.treeFiles.get(files[i].getAbsolutePath()).getChecked())) {
					nr++;
				}
				if ((this.treeFiles.get(files[i].getAbsolutePath()) != null)
						&& this.treeFiles.get(files[i].getAbsolutePath()).getGrayed()) {
					nrGray++;
				}
			}
			FileState fS = this.treeFiles.get(fileParent.getAbsolutePath());
			if (fS == null) {
				fS = new FileState();
				fS.setFile(fileParent);
			}
			if (nrGray > 0) {
				fS.setChecked(true);
				fS.setGrayed(true);
			} else if (nr == 0) {// there are all checked
				fS.setChecked(true);
				fS.setGrayed(false);
			} else if (nr == files.length) {// if none are checked
				fS.setChecked(false);
				fS.setGrayed(false);
			} else {// if not all are checked
				fS.setChecked(true);
				fS.setGrayed(true);
			}
			this.treeFiles.put(fS.getFile().getAbsolutePath(), fS);
			parent = fileParent.getParent();
		}
	}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			switch (arg0.type) {
				case SWT.Selection:// for click
					if (src == this.tree) {

					} else if (src == this.buttonBackUpFilter) {
						this.buttonUpFilterAction();
					} else if (src == this.table) {
						if (arg0.detail == SWT.CHECK) {
							TableItem item = (TableItem) arg0.item;
							this.table.setSelection(item);
							File file = (File) item.getData(AbstractFilesWindow.TABLEITEMDATA_FILE);
							this.addFileToHash(file, item.getChecked() && !item.getGrayed());
							this.addAllChildren(file, item.getChecked() && !item.getGrayed());
							this.checkFileChildren(file, item.getChecked() && !item.getGrayed());
							this.checkAllParents(file);
							this.checkTree();
						}
					}
					break;
				case SWT.DefaultSelection:
					if (src == this.table) {
						final TableItem[] items = this.table.getSelection();
						if ((items == null) || (items.length == 0)) {
							return;
						}
						File file = (File) items[0].getData(AbstractFilesWindow.TABLEITEMDATA_FILE);
						if (file == null) {
							return;
						}

						this.addFileToHash(file, items[0].getChecked() && !items[0].getGrayed());
						this.addAllChildren(file, items[0].getChecked() && !items[0].getGrayed());
						this.checkFileChildren(file, items[0].getChecked() && !items[0].getGrayed());
						this.checkAllParents(file);
						this.checkTree();

						this.workerStateDir = file;
						if (file.isDirectory()) {
							this.notifySelectedDirectory(file);
						}

					}
					break;
				case SWT.Expand:
					if (src == this.tree) {
						this.tree.select((TreeItem) arg0.item);
						this.tree.notifyListeners(SWT.Selection, new Event());
						this.checkTree();
					}
			}
		} catch (Exception e) {
			AdvancedBackupSourceWindow.logger.fatal(e, e);
		}
	}

	@Override
	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			this.backUpSet = new Backupset();
			Set<Backupsetitem> backUpSetItems = new HashSet<Backupsetitem>();
			TreeMap<String, FileState> treeTemp = (TreeMap<String, FileState>) this.treeFiles.clone();

			Iterator<String> iter = treeTemp.keySet().iterator();

			while (iter.hasNext()) {// remove all file unchecked or grayed
				FileState fileS = treeTemp.get(iter.next());
				if (!fileS.getChecked() || (fileS.getChecked() && fileS.getGrayed())) {
					this.treeFiles.remove(fileS.getFile().getAbsolutePath());
				}
			}

			treeTemp = (TreeMap<String, FileState>) this.treeFiles.clone();

			iter = treeTemp.keySet().iterator();

			while (iter.hasNext()) {// remove all files if the parent is checked
				String key = iter.next();
				FileState fS = treeTemp.get(key);
				if (fS.getChecked() && !fS.getGrayed() && fS.getFile().isDirectory()) {
					Iterator<String> tempKeys = treeTemp.keySet().iterator();
					while (tempKeys.hasNext()) {
						String string = tempKeys.next();
						ArrayList<String> strings = StringUtil.splitStrByDelim(string,
								File.separator);
						ArrayList<String> keys = StringUtil.splitStrByDelim(key, File.separator);
						if ((string.indexOf(key) != -1) && (strings.size() > keys.size())
								&& (!string.equalsIgnoreCase(key))) {
							this.treeFiles.remove(string);
						}
					}
				}
			}

			iter = this.treeFiles.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				FileState fileS = this.treeFiles.get(key);
				Backupsetitem item = new Backupsetitem();
				item.setPath(fileS.getFile().getAbsolutePath());
				item.setIsfolder(fileS.getFile().isDirectory());
				item.setBackupset(this.backUpSet);
				backUpSetItems.add(item);
			}
			this.backUpSet.setBackupsetitems(backUpSetItems);
			Iterator<Filter> iterFilters = this.setFilters.iterator();
			HashSet<Filter> hashNew = new HashSet<Filter>();
			while (iterFilters.hasNext()) {
				Filter filter = iterFilters.next();
				filter.setBackupset(this.backUpSet);
				hashNew.add(filter);
			}
			this.backUpSet.setFilters(hashNew);
			result = true;
		} catch (Exception e) {
			AdvancedBackupSourceWindow.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private boolean buttonUpFilterAction() {
		boolean result = false;
		try {
			BackUpFilterWindow dlg = new BackUpFilterWindow(this.setFilters);
			dlg.open();
			if (dlg.getExitChoiceAction() == SWT.OK) {
				this.setFilters = dlg.getResult();
			}
			result = true;
		} catch (Exception e) {
			AdvancedBackupSourceWindow.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	void workerExecute() {
		final File[] dirList;
		// Clear existing information
		this.shell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				AdvancedBackupSourceWindow.this.table.removeAll();
			}
		});

		dirList = FileUtil.getDirectoryList(AdvancedBackupSourceWindow.this.workerStateDir, false);
		int index = this.comboPage.getSelectionIndex();
		int nrPages = dirList.length / Integer.parseInt(this.comboItems.getText());
		nrPages++;
		this.comboPage.removeAll();
		for (int i = 1; i <= nrPages; i++) {
			this.comboPage.add(i + " / " + nrPages);
		}
		this.comboPage.select(index);
		if (this.comboPage.getSelectionIndex() < 0) {
			this.comboPage.select(0);
		}

		int nrItems = Integer.parseInt(this.comboItems.getText());
		nrPages = this.comboPage.getSelectionIndex();
		for (int i = nrItems * nrPages; (i < dirList.length) && (i < nrItems * nrPages + nrItems); i++) {
			this.workerAddFileDetails(dirList[i]);
			if (!this.shell.isDisposed()) {
				this.shell.getDisplay().readAndDispatch();// prevent blocking the interface
			}
		}

	}

	private void notifySelectedDirectory(File dir) {
		if (dir == null) {
			return;
		}
		if ((this.currentDirectory != null) && dir.equals(this.currentDirectory)) {
			return;
		}
		this.currentDirectory = dir;
		this.workerExecute();

		Vector<File> /* of File */path = new Vector<File>();
		// Build a stack of paths from the root of the tree
		while (dir != null) {
			path.add(dir);
			dir = dir.getParentFile();
		}
		// Recursively expand the tree to get to the specified directory
		TreeItem[] items = this.tree.getItems();
		TreeItem lastItem = null;
		for (int i = path.size() - 1; i >= 0; --i) {
			final File pathElement = path.elementAt(i);

			TreeItem item = null;
			for (int k = 0; k < items.length; ++k) {
				item = items[k];
				if (item.isDisposed()) {
					continue;
				}
				final File itemFile = (File) item.getData(AbstractFilesWindow.TREEITEMDATA_FILE);
				if ((itemFile != null) && itemFile.equals(pathElement)) {
					break;
				}
			}
			if (item == null) {
				break;
			}
			lastItem = item;
			if ((i != 0) && !item.getExpanded()) {
				this.treeExpandItem(item);
				item.setExpanded(true);
				if (item.getParentItem() != null) {
					item.setImage(AppImages.getImage16(AppImages.IMG_FOLDER_FULL));
				}
			}
			items = item.getItems();
		}
		this.tree.setSelection((lastItem != null) ? new TreeItem[] {
			lastItem } : new TreeItem[0]);
	}

	private void workerAddFileDetails(final File file) {
		final String nameString = file.getName();
		final String dateString = AdvancedBackupSourceWindow.dateFormat.format(new Date(
			file.lastModified()));
		final String sizeString;
		final String typeString;
		Image iconImage = null;

		if (file.isDirectory()) {
			typeString = Language.getText(Language.KEY_GENERAL,
					Language.GENERAL_TEXT_FILETYPE_FOLDER);
			sizeString = "";
			iconImage = AppImages.getImage16(AppImages.IMG_FOLDER);
		} else {
			sizeString = ((file.length() + 512) / 1024) + " "
					+ Language.getText(Language.KEY_GENERAL, Language.GENERAL_TEXT_FILESIZE_KB);

			int dot = nameString.lastIndexOf('.');
			if (dot != -1) {
				String extension = nameString.substring(dot);
				Program program = Program.findProgram(extension);
				if (program != null) {
					typeString = program.getName();
					iconImage = AppImages.getImageFromProgram(program);
				} else {
					typeString = extension.toUpperCase();
					iconImage = AppImages.getImage16(AppImages.IMG_WINDOW);
				}
			} else {
				typeString = Language.getText(Language.KEY_GENERAL,
						Language.GENERAL_TEXT_FILETYPE_NONE);
				iconImage = AppImages.getImage16(AppImages.IMG_WINDOW);
			}
		}
		final String[] strings = new String[] {
				nameString, sizeString, typeString, dateString };
		final Image image = iconImage;
		if (this.shell.isDisposed()) {
			return;
		}
		if (this.table.isDisposed()) {
			return;
		}
		this.shell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				TableItem tableItem = new TableItem(AdvancedBackupSourceWindow.this.table, 0);
				tableItem.setText(strings);
				tableItem.setImage(image);
				tableItem.setChecked(AdvancedBackupSourceWindow.this.setCheck(file));
				tableItem.setGrayed(AdvancedBackupSourceWindow.this.setGrayed(file));
				tableItem.setData(AbstractFilesWindow.TABLEITEMDATA_FILE, file);
			}
		});
	}

	private boolean setCheck(File file) {
		FileState fState = this.treeFiles.get(file.getAbsolutePath());
		if (fState == null) {
			return false;
		}
		return fState.getChecked();
	}

	private void addAllChildren(File file, boolean checked) {
		if (file.isFile()) {
			return;
		}
		File[] files = file.listFiles();
		if ((files == null) || (files.length == 0)) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			if (this.treeFiles.get(files[i].getAbsolutePath()) != null) {
				continue;
			}
			FileState fState = new FileState();
			fState.setFile(files[i]);
			fState.setChecked(checked);
			this.treeFiles.put(files[i].getAbsolutePath(), fState);
		}
	}

	private void addFileToHash(final File file, final boolean checked) {
		if (file == null) {
			return;
		}
		FileState fState = new FileState();
		fState.setFile(file);
		fState.setChecked(checked);
		this.treeFiles.put(file.getAbsolutePath(), fState);
	}

	private void checkFileChildren(final File file, final boolean state) {
		if (file.isFile()) {
			return;
		}
		Iterator<String> key = this.treeFiles.keySet().iterator();
		while (key.hasNext()) {
			String string = key.next();
			String str = string + File.separator;
			ArrayList<String> strings = StringUtil.splitStrByDelim(string, File.separator);
			ArrayList<String> keys = StringUtil.splitStrByDelim(file.getAbsolutePath(),
					File.separator);
			if ((string.indexOf(file.getAbsolutePath()) != -1) && (strings.size() > keys.size())) {

				// if ((str.indexOf(file.gqetAbsolutePath()) != -1)
				// && (string.length() > file.getAbsolutePath().length())) {
				this.treeFiles.get(string).setChecked(state);
				this.treeFiles.get(string).setGrayed(false);
			}
		}
	}

	private void checkTree() {
		TreeItem[] items = this.tree.getItems();
		for (int i = 0; i < items.length; i++) {
			this.checkTreeItem(items[i]);
		}
	}

	private void checkTreeItem(TreeItem item) {
		File file = (File) item.getData(AbstractFilesWindow.TREEITEMDATA_FILE);
		if (file == null) {
			return;
		}
		FileState fS = this.treeFiles.get(file.getAbsolutePath());
		if (fS == null) {
			fS = new FileState();
			fS.setFile(file);
		}
		item.setChecked(fS.getChecked());
		item.setGrayed(fS.getGrayed());
		TreeItem items[] = item.getItems();
		for (int i = 0; i < items.length; i++) {
			this.checkTreeItem(items[i]);
		}
	}

	private void checkTable() {
		TableItem[] items = this.table.getItems();
		for (int i = 0; i < items.length; i++) {
			File file = (File) items[i].getData(AbstractFilesWindow.TABLEITEMDATA_FILE);
			if (file == null) {
				return;
			}
			FileState fS = this.treeFiles.get(file.getAbsolutePath());
			items[i].setChecked(fS.getChecked());
			items[i].setGrayed(fS.getGrayed());
		}
	}

	@Override
	public void populateData() {
		try {
			this.populateTree();
			TreeItem items[] = this.tree.getItems();
			for (int i = 0; i < items.length; i++) {
				File file = (File) items[i].getData(AbstractFilesWindow.TREEITEMDATA_FILE);
				FileState fS = new FileState();
				fS.setFile(file);
				fS.setChecked(false);
				fS.setGrayed(false);
				this.treeFiles.put(file.getAbsolutePath(), fS);
			}

			this.setFilters = this.backUpSet.getFilters();
			Iterator<Backupsetitem> iterBackUp = this.backUpSet.getBackupsetitems().iterator();
			while (iterBackUp.hasNext()) {
				Backupsetitem backupsetitem = iterBackUp.next();
				File file = new File(backupsetitem.getPath());
				if (!file.exists()) {
					continue;
				}
				FileState fS = new FileState();
				fS.setFile(file);
				fS.setChecked(true);
				this.treeFiles.put(file.getAbsolutePath(), fS);
				if (StringUtil.isEmpty(file.getParent())) {
					continue;
				}
				File parent = new File(file.getParent());
				while ((parent != null) && parent.exists()) {
					fS = new FileState();
					fS.setFile(parent);
					fS.setChecked(true);
					fS.setGrayed(true);
					this.treeFiles.put(parent.getAbsolutePath(), fS);
					if (StringUtil.isNotEmpty(parent.getParent())) {
						parent = new File(parent.getParent());
					} else {
						parent = null;
					}
				}
			}

			TreeItem item0 = this.tree.getItem(0);
			if (item0 != null) {
				Event event = new Event();
				event.item = item0;
				this.tree.notifyListeners(SWT.Selection, event);
			}
			this.checkTree();
		} catch (Exception e) {
			AdvancedBackupSourceWindow.logger.fatal(e, e);
		}
	}

	@Override
	protected boolean validateData() {
		try {
			if ((this.treeFiles == null) || (this.treeFiles.size() == 0)) {
				MessageBoxWindow.error(Language.getTextAdvancedBackupSourceWindow(Language.ADVANCED_BACKUP_SOURCE_WINDOW_MESSAGE_BOX_ONE_FILE));
				this.table.forceFocus();
				return false;
			}
			Iterator<FileState> iter = this.treeFiles.values().iterator();
			int nrFileUnchecked = 0;
			while (iter.hasNext()) {
				FileState fileState = iter.next();
				if (!fileState.getChecked()) {
					nrFileUnchecked++;
				}
			}
			if (nrFileUnchecked == this.treeFiles.size()) {
				MessageBoxWindow.error(Language.getTextAdvancedBackupSourceWindow(Language.ADVANCED_BACKUP_SOURCE_WINDOW_MESSAGE_BOX_ONE_FILE));
				this.table.forceFocus();
				return false;
			}
			return true;
		} catch (Exception e) {
			AdvancedBackupSourceWindow.logger.fatal(e, e);
			return false;
		}
	}

	@Override
	public Backupset getResult() {
		return this.backUpSet;
	}

	@Override
	protected void comboItemsAction() {
		this.workerExecute();
	}

	@Override
	protected void comboPageAction() {
		this.workerExecute();
	}

	@Override
	protected void treeSelectionAction(final int action, final Widget widget) {
		TreeItem item = (TreeItem) widget;
		if (action == SWT.CHECK) {
			this.tree.select(item);
			File file = (File) item.getData(AbstractFilesWindow.TREEITEMDATA_FILE);
			this.addFileToHash(file, item.getChecked());
			this.addAllChildren((File) item.getData(AbstractFilesWindow.TREEITEMDATA_FILE),
					item.getChecked() && !item.getGrayed());
			this.checkFileChildren(file, item.getChecked() && !item.getGrayed());
			this.checkAllParents(file);
			this.checkTable();

		}
		final TreeItem[] selection = this.tree.getSelection();
		if ((selection != null) && (selection.length != 0)) {
			item = selection[0];
			File file = (File) item.getData(AbstractFilesWindow.TREEITEMDATA_FILE);
			this.addAllChildren(file, item.getChecked() && !item.getGrayed());
			this.workerStateDir = file;
			this.notifySelectedDirectory(file);
		}
		this.checkTree();
	}
}
