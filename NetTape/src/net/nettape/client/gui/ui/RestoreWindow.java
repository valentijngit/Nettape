package net.nettape.client.gui.ui;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

import net.nettape.client.Global;
import net.nettape.client.MessageHandler;
import net.nettape.client.command.ReceiveBackupsCommand;
import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.object.Node;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.FileUtil;
import net.nettape.dal.object.*;
import net.nettape.object.BackupWithItems;
import net.nettape.object.Constants;
import net.nettape.object.RestoreItem;
import net.nettape.object.RestoreWithBackupItems;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 31, 2009
 * @description
 */

public class RestoreWindow extends AbstractFilesRemoteView {

	private static final Logger logger = Logger.getLogger(RestoreWindow.class);

	private Button buttonShowFiles;
	private Button buttonShowAllFiles;
	private Button buttonFilter;
	private Combo comboDate;
	private Combo comboType;
	private Button buttonOriginalLocation;
	private Button buttonAlternativeLocation;
	private Text textAlternativeLocation;
	private Button buttonChange;
	private Button buttonRestoreFile;
	private Button buttonSearch;
	private Button buttonOverwrite;

	private Label labelAllFolders;

	private Set<Filter> filterSet;

	private RestoreWithBackupItems restore;

	private Set<BackupWithItems> backups;

	// private Hashtable<String, Backupsetitem> hashItems;

	private ArrayList<TreeMap<String, Node>> listMapItems;
	
	public boolean errors = false;
	public boolean relogin = false;

	public RestoreWindow(RestoreWithBackupItems restore, Set<BackupWithItems> backups) {
		this();
		this.restore = restore;
		this.backups = backups;

	}
	public void open()
	{
		try
		{
			GetBackupsWindow getBackupsWindow = new GetBackupsWindow(this.shell,true);
			this.backups = getBackupsWindow.open();
			this.errors = getBackupsWindow.errors;
			this.relogin = getBackupsWindow.relogin;
			if(!this.errors)
			{
				populateData();
				super.open();
			}
		}
		catch(Exception ex)
		{
			this.errors = true;
		}
	}
	public RestoreWindow() {
		super(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_TEXT_TITLE),
				Language.getTextRestoreWindow(Language.RESTORE_WINDOW_TEXT_EXPLAIN),
			AppImages.getImage16(AppImages.IMG_FOLDER_PREVIOUS), AppImages.IMG_REPEAT);
		try {
			createGUI();
			
		}
		catch (Exception ex) {
			RestoreWindow.logger.fatal(ex, ex);
		}
	}

	@Override
	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			if (this.restore == null) {
				this.restore = new RestoreWithBackupItems();
				this.restore.restore = new Restore();
			}

			ArrayList<Node> listResult = new ArrayList<Node>();

			for (int i = 0; i < this.listMapItems.size(); i++) {
				TreeMap<String, Node> list = this.listMapItems.get(i);
				Iterator<Node> iter = list.values().iterator();

				while (iter.hasNext()) {
					Node node = iter.next();
					listResult.addAll(node.getCheckedListNode());
				}
			}
			HashSet<RestoreItem> items = new HashSet<RestoreItem>();
			for (int j = 0; j < listResult.size(); j++) {
				Node n = listResult.get(j);
				Restoreitem item = new Restoreitem();
				item.setRestore(this.restore.restore);
				item.setDatetime(Calendar.getInstance().getTime());
				item.setPath(n.getPath());
				RestoreItem restoreItem = new RestoreItem(item,n.getBackupitem());
				items.add(restoreItem);
			}
			this.restore.restoreItems = items;
			this.restore.restore.setRestoreitems(items);
			this.restore.restore.setDatetime(Calendar.getInstance().getTime());
			this.restore.restore.setRestorepermissions(this.buttonRestoreFile.getSelection());
			if(this.buttonAlternativeLocation.getSelection())
			{
				this.restore.restore.setRestorepath(this.textAlternativeLocation.getText());
			}
			this.restore.restore.setOverwrite(this.buttonOverwrite.getSelection());

			if (this.filterSet != null) {
				for(Filter filter: filterSet)
				{
					filter.setRestore(this.restore.restore);
				}
			}
			this.restore.restore.setFilters(filterSet);
			result = true;
		}
		catch (Exception e) {
			result = false;
			RestoreWindow.logger.fatal(e, e);
		}
		return result;
	}

	private boolean buttonChangeAction() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean buttonFilterAction() {
		RestoreFilterWindow dlg = new RestoreFilterWindow(this.filterSet);
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			this.filterSet = dlg.getResult();
		}
		return false;
	}

	private boolean buttonSearchAction() {
		ArrayList<TreeMap<String, Node>> list = new ArrayList<TreeMap<String, Node>>();
		if (this.buttonShowAllFiles.getSelection()) {
			for (int i = 0; i < this.listMapItems.size(); i++) {
				list.add(this.listMapItems.get(i));
			}
		} else {
			if (this.comboDate.getSelectionIndex() >= 0) {
			list.add(this.listMapItems.get(this.comboDate.getSelectionIndex()));
			}
		}
		SearchWindow dlg = new SearchWindow(list);
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			AbstractMap<String, Node> result = dlg.getResult();
			Iterator<Node> iterRes = result.values().iterator();
			while (iterRes.hasNext()) {
				Node node = iterRes.next();
				int indexSTART = 0;
				int indexSTOP = 0;
				if (!this.buttonShowAllFiles.getSelection()) {
					indexSTART = this.comboDate.getSelectionIndex();
					indexSTOP = indexSTART + 1;
				} else {
					indexSTART = 0;
					indexSTOP = this.listMapItems.size();
				}
				for (int i = indexSTART; i < indexSTOP; i++) {
					TreeMap<String, Node> listTree = this.listMapItems.get(i);
					Iterator<Node> iterator = listTree.values().iterator();
					while (iterator.hasNext()) {
						Node node2 = iterator.next();
						node2.ckeckNode(node);
						// node2.setGrayed();
						this.listMapItems.get(i).put(node2.getValue(), node2);
					}
				}
			}
			populateTree();
			if (this.tree.getItemCount() > 0) {
				TreeItem item0 = this.tree.getItem(0);
				if (item0 != null) {
					Event event = new Event();
					event.item = item0;
					this.tree.notifyListeners(SWT.Selection, event);
					this.tree.select(item0);
				}
				checkTree();
			}
		}
		return false;
	}

	private void checkNode(Node node) {

	}

	@Override
	protected void createHeader() {
		Composite compositeHeader = new Composite(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeHeader.setLayoutData(data);
		compositeHeader.setLayout(new GridLayout(6, false));
		this.buttonShowFiles = new Button(compositeHeader, SWT.RADIO);
		this.buttonShowFiles.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_SHOW_FILES));
		this.buttonShowFiles.addListener(SWT.Selection, this);
		this.buttonShowFiles.setSelection(true);

		new Label(compositeHeader, SWT.NONE).setText(":");
		this.comboDate = new Combo(compositeHeader, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		data.widthHint = 80;
		this.comboDate.setLayoutData(data);
		this.comboDate.addListener(SWT.Selection, this);

		this.comboType = new Combo(compositeHeader, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		data.widthHint = 80;
		this.comboType.setLayoutData(data);

		this.buttonShowAllFiles = new Button(compositeHeader, SWT.RADIO);
		this.buttonShowAllFiles.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_SHOW_ALL_FILES));
		this.buttonShowAllFiles.addListener(SWT.Selection, this);

		this.buttonFilter = new Button(compositeHeader, SWT.PUSH);
		this.buttonFilter.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_FILTER));
		this.buttonFilter.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonFilter.setLayoutData(data);
		this.buttonFilter.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonFilter,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));
	}

	@Override
	protected void createBottom() {
		Group groupRestore = new Group(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, true);
		groupRestore.setLayoutData(data);
		groupRestore.setLayout(new GridLayout(4, false));

		Composite comp = new Composite(groupRestore, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) groupRestore.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_FOLDER_DOWN));
		new Label(comp, SWT.NONE).setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_GROUP_RESTORE));

		new Label(groupRestore, SWT.NONE).setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_LABEL_ORIGINAL_LOCATION));
		new Label(groupRestore, SWT.NONE).setText("");
		new Label(groupRestore, SWT.NONE).setText("");
		new Label(groupRestore, SWT.NONE).setText("");

		this.buttonOriginalLocation = new Button(groupRestore, SWT.RADIO);
		this.buttonOriginalLocation.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_ORIGINAL_LOCATION));
		this.buttonOriginalLocation.setSelection(true);
		this.buttonOriginalLocation.addListener(SWT.Selection, this);
		new Label(groupRestore, SWT.NONE).setText("");
		new Label(groupRestore, SWT.NONE).setText("");
		new Label(groupRestore, SWT.NONE).setText("");

		this.buttonAlternativeLocation = new Button(groupRestore, SWT.RADIO);
		this.buttonAlternativeLocation.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_ALTERNATIVE_LOCATION));
		this.buttonAlternativeLocation.addListener(SWT.Selection, this);
		new Label(groupRestore, SWT.NONE).setText(":");

		this.textAlternativeLocation = new Text(groupRestore, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 200;
		this.textAlternativeLocation.setLayoutData(data);
		this.buttonChange = new Button(groupRestore, SWT.PUSH);
		data = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonChange.setLayoutData(data);
		this.buttonChange.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_CHANGE));
		this.buttonChange.setImage(AppImages.getImage16(AppImages.IMG_REFRESH));
		this.buttonChange.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonChange,
				AppImages.getImage16(AppImages.IMG_REFRESH),
				AppImages.getImage16Focus(AppImages.IMG_REFRESH));

		this.textAlternativeLocation.setEnabled(this.buttonAlternativeLocation.getSelection());
		this.buttonChange.setEnabled(this.buttonAlternativeLocation.getSelection());

		Composite compDelete = new Composite(this.container, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		compDelete.setLayoutData(data);
		compDelete.setLayout(new GridLayout(3, false));

		this.buttonOverwrite = new Button(compDelete, SWT.CHECK);
		data = new GridData(SWT.RIGHT, SWT.FILL, true, true);
		this.buttonOverwrite.setLayoutData(data);
		this.buttonOverwrite.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_OVERWRITE));

		this.buttonRestoreFile = new Button(compDelete, SWT.CHECK);
		data = new GridData(SWT.RIGHT, SWT.FILL, true, true);
		this.buttonRestoreFile.setLayoutData(data);
		this.buttonRestoreFile.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_RESTORE_FILE));
	}

	@Override
	public void createGUI() {
		createHeader();
		createSash();
		createLeftSash(this.sashForm);
		createRightSash(this.sashForm);
		this.sashForm.setWeights(new int[] {
				2, 5 });
		createBottom();

		this.buttonSearch = new Button(this.compositeExtraButtons, SWT.PUSH);
		GridData data = new GridData(SWT.LEFT, SWT.FILL, false, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonSearch.setLayoutData(data);
		this.buttonSearch.setText(Language.getTextRestoreWindow(Language.RESTORE_WINDOW_BUTTON_SEARCH));
		this.buttonSearch.setImage(AppImages.getImage16(AppImages.IMG_SEARCH));
		this.buttonSearch.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonSearch,
				AppImages.getImage16(AppImages.IMG_SEARCH),
				AppImages.getImage16Focus(AppImages.IMG_SEARCH));
	}

	@Override
	protected void createLeftSash(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = gridLayout.marginWidth = 2;
		gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0;
		composite.setLayout(gridLayout);

		this.labelAllFolders = new Label(composite, SWT.BORDER);
		this.labelAllFolders.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL));

		this.tree = new Tree(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE
				| SWT.CHECK);
		this.tree.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		this.tree.addListener(SWT.Selection, this);
		this.tree.addListener(SWT.DefaultSelection, this);
		this.tree.addListener(SWT.Expand, this);
		this.tree.addListener(SWT.Collapse, this);
	}

	
	public void populateData() {
		try {

			if (this.backups == null) {
				return;
			}
			if (this.restore != null) {
				this.buttonRestoreFile.setSelection(this.restore.restore.getRestorepermissions());
				filterSet = this.restore.restore.getFilters();
				 
			}
			// if (this.restore == null) return;
			this.listMapItems = new ArrayList<TreeMap<String, Node>>();
			for (int i = 0; i < this.backups.size(); i++) {
				TreeMap<String, Node> itm = new TreeMap<String, Node>();
				this.listMapItems.add(itm);
			}
			Iterator<BackupWithItems> iterBackup = this.backups.iterator();
			int index = 0;
			while (iterBackup.hasNext()) {
				BackupWithItems backupWithItems = iterBackup.next();
				this.comboDate.add(backupWithItems.backup.getName());
				Iterator<Backupitem> iter = backupWithItems.backupitems.iterator();
				while (iter.hasNext()) {
					Backupitem itm = iter.next();
					addItemsToMap(index, itm);
				}
				index++;
			}
			if ((this.restore != null) && (this.restore.restore.getRestoreitems() != null)) {
				Iterator<Restoreitem> restoreItems = this.restore.restore.getRestoreitems().iterator();
				while (restoreItems.hasNext()) {
					Restoreitem restoreitem = restoreItems.next();
					Node node = new Node();
					node.setChecked(true);
					node.setPath(restoreitem.getPath());
					for (int i = 0; i < this.listMapItems.size(); i++) {
						TreeMap<String, Node> listTree = this.listMapItems.get(i);
						Iterator<Node> iterator = listTree.values().iterator();
						while (iterator.hasNext()) {
							Node node2 = iterator.next();
							node2.ckeckNode(node);
							node2.setGrayed();
							this.listMapItems.get(i).put(node2.getValue(), node2);
						}
					}
				}
			}

			Event eventC = new Event();
			this.comboDate.select(0);
			eventC.item = this.comboDate;
			this.comboDate.notifyListeners(SWT.Selection, eventC);
		}
		catch (Exception e) {
			RestoreWindow.logger.fatal(e, e);
		}
	}

	protected void populateTree(final int index) {
		Iterator<Node> iter = this.listMapItems.get(index).values().iterator();
		while (iter.hasNext()) {
			Node node = iter.next();
			addTreeItem(null, node);
		}
	}

	@Override
	protected void populateTree() {
		if (this.comboDate.getSelectionIndex() < 0)
			return;
		this.tree.removeAll();
		this.table.removeAll();
		int indexSTART = 0;
		int indexSTOP = 0;
		if (!this.buttonShowAllFiles.getSelection()) {
			indexSTART = this.comboDate.getSelectionIndex();
			indexSTOP = indexSTART + 1;
		} else {
			indexSTART = 0;
			indexSTOP = this.listMapItems.size();
		}
		for (int i = indexSTART; i < indexSTOP; i++) {
			populateTree(i);
		}
	}

	private void addItemsToMap(int index, Backupitem item) {
		try {
			StringTokenizer token = new StringTokenizer(item.getPath(), File.separator);
			String[] str = new String[token.countTokens()];
			int j = 0;
			while (token.hasMoreElements()) {
				String object = token.nextToken();
				str[j++] = object;
			}

			Node node = new Node();
			node.setValue(str[0]);
			Node p = node;
			for (int i = 1; i < str.length; i++) {
				Node node1 = new Node();
				node1.setParent(node);
				node1.setValue(str[i]);
				if (i == str.length - 1) {
					node1.setFolder(item.getIsfolder());
					node1.setBackupitem(item);
				}
				p.addChild(node1);
				p.setFolder(Boolean.TRUE);
				p = node1;
			}
			if ((node.getChildrens().size() > 0) || (node.getParent() == null)) {
				node.setFolder(Boolean.TRUE);
			}
			if (this.listMapItems.get(index).get(node.getValue()) == null) {
				this.listMapItems.get(index).put(node.getValue(), node);
			} else {
				Iterator<Node> iter = this.listMapItems.get(index).get(node.getValue()).getChildrens().values().iterator();
				while (iter.hasNext()) {
					node.addChild(iter.next());
				}
				this.listMapItems.get(index).put(node.getValue(), node);
			}
		}
		catch (Exception e) {
			RestoreWindow.logger.fatal(e, e);
		}
	}

	private void checkTree() {
		TreeItem[] items = this.tree.getItems();
		for (int i = 0; i < items.length; i++) {
			checkTreeItem(items[i]);
		}
	}

	private void checkTreeItem(TreeItem item) {
		Node node = (Node) item.getData();
		item.setChecked(node.getChecked());
		item.setGrayed(node.getGrayed());
		TreeItem items[] = item.getItems();
		for (int i = 0; i < items.length; i++) {
			checkTreeItem(items[i]);
		}
	}

	private void addItemsToTable(Node node) {
		try {

			if ((node == null) || !node.isFolder()) {
				return;
			}
			this.table.removeAll();
			if (node.getChildrens().size() == 0) {
				return;
			}
			Iterator<Node> iter = node.getChildrens().values().iterator();

			int index = this.comboPage.getSelectionIndex();
			int nrPages = node.getChildrens().size() / Integer.parseInt(this.comboItems.getText());
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
			TreeMap<String, Node> treeDirs = new TreeMap<String, Node>();
			TreeMap<String, Node> treeFiles = new TreeMap<String, Node>();

			while (iter.hasNext()) {
				Node node2 = iter.next();
				if (node2.isFolder()) {
					treeDirs.put(node2.getValue(), node2);
				} else {
					treeFiles.put(node2.getValue(), node2);
				}
			}
			int count = 0;
			iter = treeDirs.values().iterator();
			while (iter.hasNext()) {
				final Node node2 = iter.next();
				if (++count < nrItems * nrPages) {
					continue;
				}
				this.table.getDisplay().asyncExec(new Runnable() {
					
					public void run() {
						TableItem item = new TableItem(RestoreWindow.this.table, SWT.NONE);
						item.setText(0, node2.getValue());
						item.setText(2, Language.getText(Language.KEY_GENERAL,
								Language.GENERAL_TEXT_FILETYPE_FOLDER));
						item.setImage(AppImages.getImage16(AppImages.IMG_FOLDER));
						item.setChecked(node2.getChecked());
						item.setGrayed(node2.getGrayed());
						item.setData(node2);
					}
				});

				if (count > nrItems * nrPages + nrItems) {
					break;
				}
			}
			if (count > nrItems * nrPages + nrItems) {
				return;
			}
			iter = treeFiles.values().iterator();
			while (iter.hasNext()) {
				final Node node2 = iter.next();
				if (++count < nrItems * nrPages) {
					continue;
				}
				this.table.getDisplay().asyncExec(new Runnable() {
					
					public void run() {
						TableItem item = new TableItem(RestoreWindow.this.table, SWT.NONE);
						item.setText(0, node2.getValue());
						item.setText(2, FileUtil.getFileExtension(node2.getValue()).toUpperCase()
								+ " "
								+ Language.getText(Language.KEY_GENERAL,
										Language.GENERAL_TEXT_FILETYPE_UNKNOWN));
						item.setImage(AppImages.getImage16(AppImages.IMG_WINDOW));
						item.setChecked(node2.getChecked());
						item.setGrayed(node2.getGrayed());
						item.setData(node2);
					}
				});
				if (count > nrItems * nrPages + nrItems) {
					break;
				}
			}
		}
		catch (Exception e) {
			RestoreWindow.logger.fatal(e, e);
		}
	}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonFilter) {
					buttonFilterAction();
				} else if (src == this.buttonSearch) {
					buttonSearchAction();
				} else if (src == this.buttonChange) {
					buttonChangeAction();
				} else if (src == this.buttonShowFiles) {
					this.comboDate.setEnabled(this.buttonShowFiles.getSelection());
					this.comboType.setEnabled(this.buttonShowFiles.getSelection());
					comboDateAction();
				} else if (src == this.buttonShowAllFiles) {
					this.comboDate.setEnabled(this.buttonShowFiles.getSelection());
					this.comboType.setEnabled(this.buttonShowFiles.getSelection());
					populateTree();
				} else if (src == this.buttonOriginalLocation) {
					this.textAlternativeLocation.setEnabled(this.buttonAlternativeLocation.getSelection());
					this.buttonChange.setEnabled(this.buttonAlternativeLocation.getSelection());
				} else if (src == this.buttonAlternativeLocation) {
					this.textAlternativeLocation.setEnabled(this.buttonAlternativeLocation.getSelection());
					this.buttonChange.setEnabled(this.buttonAlternativeLocation.getSelection());
				} else if (src == this.comboDate) {
					comboDateAction();
				} else if (src == this.table) {
					if (arg0.detail == SWT.CHECK) {
						TreeItem treeItem = this.tree.getSelection()[0];
						Node parent = (Node) treeItem.getData();

						TableItem item = this.table.getSelection()[0];
						Node node = (Node) item.getData();
						node.setChecked(item.getChecked());
						parent.setChild(node.getValue(), node);
						parent.setGrayed();
						treeItem.setData(parent);

						TreeItem parentItem = treeItem.getParentItem();
						node = parent;
						while (parentItem != null) {
							Node nodeP = (Node) parentItem.getData();
							nodeP.setChild(node.getValue(), node);
							nodeP.setGrayed();
							parentItem.setData(nodeP);
							parentItem = parentItem.getParentItem();
							node = nodeP;
						}
						checkTree();
					}
				}
			} else if (arg0.type == SWT.DefaultSelection) {
				if (src == this.table) {
					if (this.table.getSelectionCount() <= 0) {
						return;
					}
					TableItem item = this.table.getSelection()[0];
					Node node = (Node) item.getData();
					TreeItem treeItem = this.tree.getSelection()[0];
					TreeItem[] items = treeItem.getItems();
					for (int i = 0; i < items.length; i++) {
						Node treeNode = (Node) items[i].getData();
						if (treeNode.getValue().equals(node.getValue())) {
							Event event = new Event();
							event.item = items[i];
							this.tree.notifyListeners(SWT.Selection, event);
							this.tree.select(items[i]);
							break;
						}
					}
				}
			} else if (arg0.type == SWT.Expand) {
				if (src == this.tree) {
					checkTree();
				}
			}
		}
		catch (Exception e) {
			RestoreWindow.logger.fatal(e, e);
		}
	}

	@Override
	protected boolean validateData() {
		return true;
	}

	public RestoreWithBackupItems getResult() {
		return this.restore;
	}

	@Override
	protected void comboItemsAction() {
		if (this.tree.getSelectionCount() <= 0) {
			return;
		}
		TreeItem item = this.tree.getSelection()[0];
		Node node = (Node) item.getData();
		addItemsToTable(node);
	}

	@Override
	protected void comboPageAction() {
		if (this.tree.getSelectionCount() <= 0) {
			return;
		}
		TreeItem item = this.tree.getSelection()[0];
		Node node = (Node) item.getData();
		addItemsToTable(node);
	}

	@Override
	protected void treeSelectionAction(int action, Widget widget) {
		TreeItem item = (TreeItem) widget;
		if (action == SWT.CHECK) {
			this.tree.select(item);
			Node node = (Node) item.getData();
			node.setChecked(item.getChecked());
			item.setData(node);
			this.table.removeAll();
			TreeItem parent = item.getParentItem();
			while (parent != null) {
				Node nodeP = (Node) parent.getData();
				nodeP.setChild(node.getValue(), node);
				nodeP.setGrayed();
				parent.setData(nodeP);
				parent = parent.getParentItem();
				node = nodeP;
			}
		}
		addItemsToTable((Node) item.getData());
		checkTree();
	}

	private void comboDateAction() {
		this.tree.removeAll();
		this.table.removeAll();
		if (this.comboDate.getSelectionIndex() < 0) {
			return;
		}
		populateTree(this.comboDate.getSelectionIndex());
		if (this.tree.getItemCount() > 0) {
			TreeItem item0 = this.tree.getItem(0);
			if (item0 != null) {
				Event event = new Event();
				event.item = item0;
				this.tree.notifyListeners(SWT.Selection, event);
				this.tree.select(item0);
			}
			checkTree();
		}
	}

}