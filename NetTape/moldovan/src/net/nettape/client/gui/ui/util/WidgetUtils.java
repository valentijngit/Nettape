package net.nettape.client.gui.ui.util;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;

public final class WidgetUtils {

	private static final Logger logger = Logger.getLogger(WidgetUtils.class);

	private WidgetUtils() {}

	public static Button createButton(	Composite shell,
										int style,
										int width,
										String text,
										String toolTip,
										SelectionAdapter sel) {
		try {
			Button button = new Button(shell, style);
			button.setText(text);
			button.setToolTipText(toolTip);
			GridData data = new GridData();
			data.widthHint = width;
			button.setLayoutData(data);
			if (sel != null)
				button.addSelectionListener(sel);
			return button;
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			return null;
		}
	}

	/**
	 * enable or disable components from composite/group
	 * 
	 * @param comp
	 * @param flag
	 */
	public static void enableGUI(Composite comp, boolean flag) {
		try {
			if ((comp == null) || comp.isDisposed()) {
				return;
			}
			if (!(comp instanceof Shell)) {
				comp.setEnabled(flag);
			}
			Control[] childrens = comp.getChildren();
			if (childrens != null) {
				for (int i = 0; i < childrens.length; i++) {
					if (!childrens[i].isDisposed()) {
						if (!(childrens[i] instanceof Label)) {
							childrens[i].setEnabled(flag);
						}
					}
					if (childrens[i].isDisposed()) {
						continue;
					}
					Control[] ch = null;
					if (childrens[i] instanceof Composite) {
						ch = ((Composite) childrens[i]).getChildren();
						if ((ch != null) && (ch.length > 0)) {
							WidgetUtils.enableGUI((Composite) childrens[i],
									flag);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public static void drawImageOnGroup(final Group group, final Image image) {
		try {
			if ((group == null) || group.isDisposed()) {
				return;
			}
			group.addListener(SWT.Paint, new Listener() {
				public void handleEvent(final Event event) {
					if ((image != null) && !image.isDisposed()) {
						if (group.getBounds().width >= 10 + image.getBounds().width) {
							event.gc.drawImage(image, 10, 0);
						}
					}
				}
			});
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	public static void addImageChangeListener(	final Button button,
												final Image imgNormal,
												final Image imgFocus) {
		Listener listenToMe;
		try {
			if ((button == null) || button.isDisposed()) {
				return;
			}
			listenToMe = new Listener() {
				public void handleEvent(final Event event) {
					if (event.type == SWT.MouseEnter) {
						button.setImage(imgFocus);
					} else if (event.type == SWT.MouseExit) {
						button.setImage(imgNormal);
					}
				}
			};
			button.addListener(SWT.MouseEnter, listenToMe);
			button.addListener(SWT.MouseExit, listenToMe);
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	public static void addImageChangeListener(	final ToolItem item,
												final Image imgNormal,
												final Image imgFocus) {
		Listener listenToMe;
		try {
			if ((item == null) || item.isDisposed()) {
				return;
			}
			listenToMe = new Listener() {
				public void handleEvent(final Event event) {
					if (event.type == SWT.MouseEnter) {
						item.setImage(imgFocus);
					} else if (event.type == SWT.MouseExit) {
						item.setImage(imgNormal);
					}
				}
			};
			item.addListener(SWT.MouseEnter, listenToMe);
			item.addListener(SWT.MouseExit, listenToMe);
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	public static void selectAllTableElements(Table table, boolean state) {
		if (table == null || table.isDisposed()) {
			return;
		}
		TableItem item = null;
		try {
			for (int i = 0; i < table.getItemCount(); i++) {
				item = table.getItem(i);
				if (item != null && item.getChecked() != state) {
					item.setChecked(state);
					item = null;
				}
			}
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		} finally {
			item = null;
		}
	}

	public static void selectParentTree(TreeItem item) {
		try {
			TreeItem parent = item.getParentItem();
			if (parent == null)
				return;
			TreeItem[] items = parent.getItems();
			boolean checked = false;
			for (int i = 0; i < items.length; i++) {
				if (items[i].getChecked()) {
					checked = true;
					break;
				}
			}
			parent.setChecked(checked);
			selectParentTree(parent);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public static void centerInDisplay(final Composite parent) {
		try {
			if ((parent == null) || parent.isDisposed()) {
				return;
			}
			parent.setLocation((Display.getDefault().getBounds().width - parent.getBounds().width) / 2,
					(Display.getDefault().getBounds().height - parent.getBounds().height) / 2);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public static Point centerInWindow(	final Rectangle parentSize,
										final Rectangle childSize) {
		Point childLocation = new Point(0, 0);
		try {
			childLocation.x = parentSize.x
					+ ((parentSize.width - childSize.width)) / 2;
			if (childLocation.x < 0) {
				childLocation.x = 0;
			} else if (childLocation.x + childSize.width > Display.getDefault().getBounds().width) {
				childLocation.x = Display.getDefault().getBounds().width
						- childSize.width;
			}
			childLocation.y = parentSize.y
					+ ((parentSize.height - childSize.height)) / 2;
			if (childLocation.y < 0) {
				childLocation.y = 0;
			} else if (childLocation.y + childSize.height > Display.getDefault().getBounds().height) {
				childLocation.y = Display.getDefault().getBounds().height
						- childSize.height;
			}
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
		return childLocation;
	}

	public static void checkTreePath(TreeItem item, boolean checked, boolean grayed) {
		if (item == null)
			return;
		boolean gray = grayed;
		boolean check = checked;
		if (gray) {
			check = true;
		} else {
			int index = 0;
			TreeItem[] items = item.getItems();
			while (index < items.length) {
				TreeItem child = items[index];
				if (child.getGrayed()
				|| check != child.getChecked()) {
					check = gray = true;
					break;
				}
				index++;
			}
		}
		item.setChecked(check);
		item.setGrayed(gray);
		checkTreePath(item.getParentItem(), check, gray);
	}
	public static void checkTreeItems(TreeItem item, boolean checked) {
	    item.setGrayed(false);
	    item.setChecked(checked);
	    TreeItem[] items = item.getItems();
	    for (int i = 0; i < items.length; i++) {
	        checkTreeItems(items[i], checked);
	    }
	}
	
	public static void treeItemRemoveAll(TreeItem treeItem) {
		final TreeItem[] children = treeItem.getItems();
		for (int i = 0; i < children.length; ++i) {
			children[i].dispose();
		}
	}

}
