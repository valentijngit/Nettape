package net.nettape.client.gui.ui;

import java.util.Hashtable;

import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;
import net.nettape.client.gui.ui.object.FileState;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 7, 2009
 * @description
 */

public abstract class AbstractFilesWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(AbstractFilesWindow.class);

	protected Combo comboItems;
	protected Combo comboPage;
	protected Tree tree;
	protected Table table;

	protected final int[] tableWidths = new int[] {
			150, 80, 100, 150 };
	protected final int[] tableAligns = new int[] {
			SWT.LEFT, SWT.RIGHT, SWT.LEFT, SWT.LEFT };

	protected final String[] tableTitles = new String[] {
			Language.getTextFilesWindow(Language.FILES_WINDOW_TABLE_NAME),
			Language.getTextFilesWindow(Language.FILES_WINDOW_TABLE_SIZE),
			Language.getTextFilesWindow(Language.FILES_WINDOW_TABLE_TYPE),
			Language.getTextFilesWindow(Language.FILES_WINDOW_TABLE_MODIFIED) };

	protected SashForm sashForm;

	protected static final String TREEITEMDATA_FILE = "TreeItem.file";
	protected static final String TREEITEMDATA_IMAGEEXPANDED = "TreeItem.imageExpanded";
	protected static final String TREEITEMDATA_IMAGECOLLAPSED = "TreeItem.imageCollapsed";
	protected static final String TREEITEMDATA_STUB = "TreeItem.stub";
	protected static final String TABLEITEMDATA_FILE = "TableItem.file";

	Hashtable<String, FileState> hashFiles = new Hashtable<String, FileState>();

	public AbstractFilesWindow(String title, String explain, Image image, String imageName) {
		super(SWT.SYSTEM_MODAL, title, explain, imageName, 400, 200);
		try {
			this.shell.setImage(image);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected void createCompositeItemsPages(Composite parent) {
		Composite compositeItems = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
		compositeItems.setLayoutData(data);
		GridLayout lay = new GridLayout(4, false);
		lay.marginWidth = 0;
		compositeItems.setLayout(lay);
		new Label(compositeItems, SWT.NONE).setText(Language.getTextFilesWindow(Language.FILES_WINDOW_COMBO_ITEMS)
				+ ":");
		this.comboItems = new Combo(compositeItems, SWT.DROP_DOWN
				| SWT.READ_ONLY);
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
		label.setText(Language.getTextFilesWindow(Language.FILES_WINDOW_COMBO_PAGE)
				+ ":");
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		label.setLayoutData(data);
		this.comboPage = new Combo(compositeItems, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		data = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		data.widthHint = 80;
		this.comboPage.setLayoutData(data);
		this.comboPage.addListener(SWT.Selection, this);
	}

	protected void createRightSash(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = gridLayout.marginWidth = 2;
		gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0;
		composite.setLayout(gridLayout);
		this.table = new Table(composite, SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.MULTI | SWT.FULL_SELECTION | SWT.CHECK);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
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
		this.createCompositeItemsPages(composite);
	}

	protected abstract void createLeftSash(Composite parent);

	protected abstract void createHeader();

	protected abstract void createBottom();

	protected abstract void comboItemsAction();

	protected abstract void comboPageAction();

	protected abstract void treeDefaultSelectionAction();

	protected abstract void treeSelectionAction(final int action,
												final Widget widget);

	protected abstract void treeExpandAction(final Widget widget);

	protected abstract void treeCollapseAction(final Widget widget);

	protected abstract void populateTree();

	protected void createSash() {
		this.sashForm = new SashForm(this.container, SWT.NONE);
		this.sashForm.setOrientation(SWT.HORIZONTAL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gridData.horizontalSpan = 3;
		gridData.heightHint = 300;
		this.sashForm.setLayoutData(gridData);
	}

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
					} else if (src == this.table) {
						this.table.setSelection((TableItem) arg0.item);
					} else if (src == this.tree) {
						this.treeSelectionAction(arg0.detail, arg0.item);
					}
					break;
				case SWT.DefaultSelection:
					if (src == this.tree) {
						this.treeDefaultSelectionAction();
					}
					break;
				case SWT.Expand:
					if (src == this.tree) {
						this.treeExpandAction(arg0.item);
					}
					break;
				case SWT.Collapse:
					if (src == this.tree) {
						this.treeCollapseAction(arg0.item);
					}
					break;
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected void createTree(Composite composite, boolean check) {
		this.tree = new Tree(composite, SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.SINGLE | (check ? SWT.CHECK : SWT.NONE));
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		this.tree.setLayoutData(data);
		this.tree.addListener(SWT.Selection, this);
		this.tree.addListener(SWT.DefaultSelection, this);
		this.tree.addListener(SWT.Expand, this);
		this.tree.addListener(SWT.Collapse, this);
	}

}