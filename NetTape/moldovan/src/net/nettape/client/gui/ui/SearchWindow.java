package net.nettape.client.gui.ui;

import java.util.*;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.ui.object.Node;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.FileUtil;
import net.nettape.client.gui.util.StringUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 2, 2009
 * @description
 */

public class SearchWindow extends AbstractFilesRemoteView {

	private static final Logger logger = Logger.getLogger(SearchWindow.class);

	private Text textLookIn;
	private Button buttonChange;
	private Text textPattern;
	private Combo comboType;
	private Combo comboApplyTo;
	private Button buttonSearchSubFolders;
	private Button buttonMatchCase;

	private Button buttonStop;

	private Button buttonSearch;

	private ProgressBar progressBar;

	private ArrayList<TreeMap<String, Node>> listMapNode;

	private SearchThread searchThread;

	private ArrayList<Node> resultSearchs;

	private AbstractMap<String, Node> result;

	public SearchWindow(final ArrayList<TreeMap<String, Node>> listMapNode) {
		super(Language.getTextSearchWindow(Language.SEARCH_WINDOW_TEXT_TITLE),
			AppImages.getImage16(AppImages.IMG_SEARCH));
		try {
			this.listMapNode = listMapNode;
			createGUI();
			populateData();
			this.result = new TreeMap<String, Node>();
		}
		catch (Exception ex) {
			SearchWindow.logger.fatal(ex, ex);
		}
	}

	@Override
	protected boolean buttonOKAction() {
		return true;
	}

	private boolean buttonChangeAction() {
		ChangePathWindow dlg = new ChangePathWindow(this.listMapNode);
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			Node node = dlg.getResult();
			this.textLookIn.setText(node.getPath());
			this.textLookIn.setData(node);
		}
		return false;
	}

	private boolean buttonSearchAction() {
		try {
			String text = this.textLookIn.getText();
			if (StringUtil.isEmpty(text) || (this.textLookIn.getData() == null)) {
				MessageBoxWindow.error(Language.getTextSearchWindow(Language.SEARCH_WINDOW_MESSAGE_BOX_TEXT_LOOK_IN_DIR));
				this.textLookIn.forceFocus();
				return false;
			}
			text = this.textPattern.getText();
			if (StringUtil.isNotEmpty(text)) {
				ArrayList<String> strIllegalChar = AppConstants.getIlegalCharacters();
				for (int i = 0; i < strIllegalChar.size(); i++) {
					if (text.indexOf(strIllegalChar.get(i)) != -1) {
						MessageBoxWindow.error(Language.getTextSearchWindow(Language.SEARCH_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER)
								+ " ("
								+ StringUtil.getStringFromArrayList(strIllegalChar, " ")
								+ ")");
						this.textPattern.forceFocus();
						return false;
					}
				}

			}

			this.searchThread = new SearchThread(
				(Node) SearchWindow.this.textLookIn.getData(),
				SearchWindow.this.textPattern.getText(),
				SearchWindow.this.comboType.getSelectionIndex(),
				SearchWindow.this.comboApplyTo.getSelectionIndex(),
				SearchWindow.this.buttonSearchSubFolders.getSelection(),
				SearchWindow.this.buttonMatchCase.getSelection());
			this.searchThread.start();
			return true;
		}
		catch (Exception e) {
			SearchWindow.logger.fatal(e, e);
			return false;
		}
	}

	private boolean buttonStopAction() {
		if ((this.searchThread != null) && this.searchThread.isAlive()) {
			this.searchThread.setActive(false);
		}
		this.buttonStop.setEnabled(false);
		this.buttonSearch.setEnabled(true);
		return false;
	}

	@Override
	protected void createHeader() {
		Composite compositeHeader = new Composite(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeHeader.setLayoutData(data);
		GridLayout lay = new GridLayout(4, false);
		compositeHeader.setLayout(lay);

		new Label(compositeHeader, SWT.NONE).setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_TEXT_LOOK_IN));
		new Label(compositeHeader, SWT.NONE).setText(":");
		this.textLookIn = new Text(compositeHeader, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		// data.widthHint = compositeHeader.getBounds().width - 70;
		// data.widthHint = 450;
		this.textLookIn.setLayoutData(data);

		this.buttonChange = new Button(compositeHeader, SWT.PUSH);
		this.buttonChange.setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_BUTTON_CHANGE));
		this.buttonChange.setImage(AppImages.getImage16(AppImages.IMG_REFRESH));
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonChange.setLayoutData(data);
		this.buttonChange.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonChange,
				AppImages.getImage16(AppImages.IMG_REFRESH),
				AppImages.getImage16Focus(AppImages.IMG_REFRESH));
	}

	@Override
	public void createGUI() {

		createHeader();
		createSash();
		createLeftSash(this.sashForm);
		createRightSash(this.sashForm);
		this.sashForm.setWeights(new int[] {
				2, 5 });

		// this.createCompositeItemsPages();
		createBottom();

		this.comboType.add(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_TYPE_CONTAINS));
		this.comboType.add(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_TYPE_EXACT));
		this.comboType.add(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_TYPE_START_WITH));
		this.comboType.add(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_TYPE_ENDS_WITH));
		this.comboApplyTo.add(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_APPLY_TO_FILES_DIRECTORIES));
		this.comboApplyTo.add(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_APPLY_TO_FILES));
		this.comboApplyTo.add(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_APPLY_TO_DIRECTORIES));
		this.comboType.select(0);
		this.comboApplyTo.select(0);
	}

	@Override
	protected void createBottom() {
	// TODO Auto-generated method stub
	}

	@Override
	protected void createLeftSash(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);

		new Label(composite, SWT.NONE).setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_TEXT_PATTERN)
				+ ":");
		new Label(composite, SWT.NONE).setText("");
		this.textPattern = new Text(composite, SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		this.textPattern.setLayoutData(data);

		new Label(composite, SWT.NONE).setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_TYPE)
				+ ":");
		new Label(composite, SWT.NONE).setText("");
		this.comboType = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.FILL, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		this.comboType.setLayoutData(data);

		new Label(composite, SWT.NONE).setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_COMBO_APPLY_TO)
				+ ":");
		new Label(composite, SWT.NONE).setText("");
		this.comboApplyTo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.FILL, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		this.comboApplyTo.setLayoutData(data);

		this.buttonSearchSubFolders = new Button(composite, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		this.buttonSearchSubFolders.setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_BUTTON_SEARCH_SUB_FOLDERS));
		this.buttonSearchSubFolders.setLayoutData(data);

		this.buttonMatchCase = new Button(composite, SWT.CHECK);
		data = new GridData(SWT.FILL, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		this.buttonMatchCase.setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_BUTTON_MATCH_CASE));
		this.buttonMatchCase.setLayoutData(data);

		this.progressBar = new ProgressBar(composite, SWT.INDETERMINATE);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.horizontalSpan = 2;
		this.progressBar.setLayoutData(data);
		this.progressBar.setVisible(false);

		this.buttonSearch = new Button(composite, SWT.PUSH);
		this.buttonSearch.setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_BUTTON_SEARCH));
		this.buttonSearch.setImage(AppImages.getImage16(AppImages.IMG_SEARCH));
		data = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		data.widthHint = 80;
		this.buttonSearch.setLayoutData(data);
		this.buttonSearch.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonSearch,
				AppImages.getImage16(AppImages.IMG_SEARCH),
				AppImages.getImage16Focus(AppImages.IMG_SEARCH));

		this.buttonStop = new Button(composite, SWT.PUSH);
		this.buttonStop.setText(Language.getTextSearchWindow(Language.SEARCH_WINDOW_BUTTON_STOP));
		this.buttonStop.setImage(AppImages.getImage16(AppImages.IMG_STOP));
		data = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		data.widthHint = 80;
		this.buttonStop.setLayoutData(data);
		this.buttonStop.setEnabled(false);
		this.buttonStop.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonStop,
				AppImages.getImage16(AppImages.IMG_STOP),
				AppImages.getImage16Focus(AppImages.IMG_STOP));
	}

	@Override
	public void populateData() {
		if ((this.resultSearchs == null) || (this.resultSearchs.size() <= 0)) {
			return;
		}
		this.table.removeAll();
		int nrItems = Integer.parseInt(this.comboItems.getText());
		int nrPages = this.comboPage.getSelectionIndex();
		for (int i = nrPages * nrItems; (i < nrItems * nrPages + nrItems)
				&& (i < this.resultSearchs.size()); i++) {
			Node node = this.resultSearchs.get(i).cloneNode();
			node.setChecked(false);
			node.setGrayed();
			addItemToTable(node);
		}
	}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonChange) {
					buttonChangeAction();
				} else if (src == this.buttonSearch) {
					buttonSearchAction();
				} else if (src == this.buttonStop) {
					buttonStopAction();
				} else if (src == this.table) {
					if (this.table.getSelectionCount() <= 0) {
						return;
					}
					TableItem item = this.table.getSelection()[0];
					Node node = (Node) item.getData();
					node.setChecked(item.getChecked());
					if (item.getChecked()) {
						this.result.put(node.getPath(), node);
					} else {
						this.result.remove(node.getPath());
					}

				}
			}
		}
		catch (Exception e) {
			SearchWindow.logger.fatal(e, e);
		}
	}

	@Override
	protected boolean validateData() {
		return true;
	}

	@Override
	public AbstractMap<String, Node> getResult() {
		return this.result;
	}

	@Override
	protected void comboItemsAction() {
		this.comboPage.removeAll();
		int nrPages = this.resultSearchs.size() / Integer.parseInt(this.comboItems.getText());
		nrPages++;
		for (int i = 1; i <= nrPages; i++) {
			this.comboPage.add(i + " / " + nrPages);
		}
		if (this.comboPage.getSelectionIndex() < 0) {
			this.comboPage.select(0);
		}
		populateData();
	}

	@Override
	protected void comboPageAction() {
		populateData();
	}

	@Override
	protected void treeSelectionAction(int action, Widget widget) {}

	@Override
	protected void populateTree() {}

	private void addItemToTable(final Node node) {
		try {
			this.table.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					TableItem item = new TableItem(SearchWindow.this.table, SWT.NONE);
					item.setText(0, node.getPath());
					item.setText(2,
							node.isFolder()	? Language.getText(Language.KEY_GENERAL,
													Language.GENERAL_TEXT_FILETYPE_FOLDER)
											: FileUtil.getFileExtension(node.getValue()).toUpperCase()
													+ " "
													+ Language.getText(Language.KEY_GENERAL,
															Language.GENERAL_TEXT_FILETYPE_UNKNOWN));
					item.setImage(AppImages.getImage16(node.isFolder()	? AppImages.IMG_FOLDER
																		: AppImages.IMG_WINDOW));
					item.setChecked(node.getChecked());
					item.setGrayed(node.getGrayed());
					item.setData(node);
				}
			});
		}
		catch (Exception e) {
			SearchWindow.logger.fatal(e, e);
		}
	}

	private class SearchThread extends Thread {

		private boolean isActive = true;

		private final Node lookInNode;
		private final String patternStr;
		private final int type;
		private final int applyTo;
		private final boolean searchSubFolder;
		private final boolean matchCase;

		public SearchThread(final Node lookInNode,
							final String patternStr,
							final int type,
							final int applyTo,
							final boolean searchSubFolder,
							final boolean matchCase) {
			this.lookInNode = lookInNode;
			this.patternStr = patternStr;
			this.type = type;
			this.applyTo = applyTo;
			this.searchSubFolder = searchSubFolder;
			this.matchCase = matchCase;

		}

		@Override
		public void run() {
			try {
				if (SearchWindow.this.shell.isDisposed()) {
					return;
				}
				SearchWindow.this.shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						SearchWindow.this.progressBar.setVisible(true);
						SearchWindow.this.buttonSearch.setEnabled(false);
						SearchWindow.this.buttonStop.setEnabled(true);
						SearchWindow.this.table.removeAll();
					}
				});
				while (this.isActive) {
					if (!this.isActive) {
						break;
					}
					SearchWindow.this.resultSearchs = new ArrayList<Node>();

					searchTree(this.lookInNode, this.searchSubFolder);

					if (SearchWindow.this.shell.isDisposed()) {
						return;
					}
					SearchWindow.this.shell.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							int nrPages = SearchWindow.this.resultSearchs.size()
									/ Integer.parseInt(SearchWindow.this.comboItems.getText());
							nrPages++;
							SearchWindow.this.comboPage.removeAll();
							for (int i = 1; i <= nrPages; i++) {
								SearchWindow.this.comboPage.add(i + " / " + nrPages);
							}
							if (SearchWindow.this.comboPage.getSelectionIndex() < 0) {
								SearchWindow.this.comboPage.select(0);
							}
						}
					});
					break;
				}
				if (SearchWindow.this.shell.isDisposed()) {
					return;
				}
				SearchWindow.this.shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						populateData();
						SearchWindow.this.buttonSearch.setEnabled(true);
						SearchWindow.this.buttonStop.setEnabled(false);
						SearchWindow.this.progressBar.setVisible(false);
					}
				});
			}
			catch (Exception e) {
				SearchWindow.logger.fatal(e, e);
			}
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}

		private void searchTree(Node node, boolean searchSub) {
			Iterator<Node> iter = node.getChildrens().values().iterator();
			while (iter.hasNext()) {
				Node node2 = iter.next();
				if (searchSub && node2.isFolder() && node2.hasChildrends()) {
					searchTree(node2, searchSub);
				} else {
					if (findNode(node2, this.patternStr, this.type, this.applyTo, this.matchCase)) {
						SearchWindow.this.resultSearchs.add(node2);
					}
				}
			}
		}

		private boolean findNode(Node node, String patternS, int typeP, int apply, boolean matchC) {
			if ((apply == 1) && node.isFolder()) {
				return false;
			}
			if ((apply == 2) && !node.isFolder()) {
				return false;
			}
			String pattern = patternS;
			String value = node.getValue();
			if (!matchC) {
				pattern = pattern.toLowerCase();
				value = value.toLowerCase();
			}
			if (typeP == 0) {// contains
				if (!value.contains(pattern)) {
					return false;
				}
			} else if (typeP == 1) {// exact
				if (!value.equals(pattern)) {
					return false;
				}
			} else if (typeP == 2) {// start
				if (!value.startsWith(pattern)) {
					return false;
				}
			} else if (typeP == 3) {// ends
				if (!value.endsWith(pattern)) {
					return false;
				}
			}
			return true;
		}

	}

}
