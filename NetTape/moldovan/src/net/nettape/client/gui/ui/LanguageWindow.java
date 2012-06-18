package net.nettape.client.gui.ui;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.INIFile;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.util.StringUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 16, 2009
 * @description
 */

public class LanguageWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(LanguageWindow.class);

	private TreeMap<String, TreeMap<String, String>> language;

	private String fileName;

	private Text textLanguageName;

	private Tree treeWindows;

	private ViewForm formItems;

	private Hashtable<String, ScrolledComposite> hashComposite;

	public LanguageWindow(String fileName) {
		super(SWT.SYSTEM_MODAL,
			Language.getTextSettingsWindow(Language.SETTINGS_WINDOW_TEXT_TITLE),
			600,
			200);
		try {
			if (fileName == null || fileName.length() == 0) {
				this.setImage(AppImages.getImage16(AppImages.IMG_ADD));
				Language.getInstance();
				this.language = Language.getDefaultLanguage();
			} else {
				this.setImage(AppImages.getImage16(AppImages.IMG_NOTE_EDIT));
				INIFile ini = new INIFile(AppConstants.LNG_DIR + fileName);
				ini.loadFile();
				this.language = ini.getSections();
			}
			this.fileName = fileName;
			this.hashComposite = new Hashtable<String, ScrolledComposite>();
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		try {
			TreeMap<String, TreeMap<String, String>> languageToSave = (TreeMap<String, TreeMap<String, String>>) this.language.clone();

			Enumeration<String> enumS = this.hashComposite.keys();
			while (enumS.hasMoreElements()) {
				String section = enumS.nextElement();
				ScrolledComposite scrComp = this.hashComposite.get(section);
				Composite comp = (Composite) scrComp.getChildren()[0];
				Control[] controls = comp.getChildren();
				TreeMap<String, String> properties = new TreeMap<String, String>();
				for (int j = 0; j < controls.length; j++) {
					if (!(controls[j] instanceof Text))
						continue;
					properties.put((String) controls[j].getData(),
							((Text) controls[j]).getText());
				}
				languageToSave.put(section, properties);
			}
			logger.info(languageToSave.size());
			INIFile ini = new INIFile(AppConstants.LNG_DIR
					+ this.textLanguageName.getText() + ".lng");
			ini.setSections(languageToSave);
			ini.saveFile();
			return true;
		} catch (Exception e) {
			logger.fatal(e, e);
			return false;
		}
	}

	public void createGUI() {
		new Label(this.container, SWT.NONE).setText(Language.getTextLanguageWindow(Language.LANGUAGE_WINDOW_TEXT_LANGUAGE_NAME));
		this.textLanguageName = new Text(this.container, SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.widthHint = 200;
		this.textLanguageName.setText(this.fileName.length() > 4 ? this.fileName.substring(0,
																		this.fileName.length() - 4)
																: "");
		this.textLanguageName.setLayoutData(data);

		Composite comp = new Composite(this.container, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		lay.marginWidth = 0;

		comp.setLayout(lay);

		this.treeWindows = new Tree(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 210;
		data.heightHint = 200;
		data.horizontalSpan = 1;
		this.treeWindows.setLayoutData(data);
		this.treeWindows.addListener(SWT.Selection, this);

		this.formItems = new ViewForm(comp, SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 450;
		data.horizontalSpan = 1;
		this.formItems.setLayoutData(data);

	}

	public ScrolledComposite addWindowComposite(String title,
												TreeMap<String, String> prop) {
		try {
			ScrolledComposite scrollComposite = new ScrolledComposite(
				this.formItems,
				SWT.V_SCROLL | SWT.BORDER);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.widthHint = 350;
			data.horizontalSpan = 1;
			scrollComposite.setLayoutData(data);
			GridLayout lay = new GridLayout(1, false);
			lay.marginLeft = lay.marginRight = 0;
			scrollComposite.setLayout(lay);

			Composite composite = new Composite(scrollComposite, SWT.NONE);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			data.horizontalSpan = 3;
			composite.setLayoutData(data);
			lay = new GridLayout(4, false);
			lay.marginLeft = lay.marginRight = 0;
			composite.setLayout(lay);
			composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			scrollComposite.setContent(composite);
			scrollComposite.setMinSize(400, 200);
			scrollComposite.getVerticalBar().setIncrement(scrollComposite.getVerticalBar().getIncrement() * 5);

			Set<String> keys = Language.getDefaultLanguage().get(title).keySet();
			Iterator<String> iter = keys.iterator();
			while (iter.hasNext()) {
				String string = iter.next();
				String value = prop.get(string);
				if (value == null)
					value = Language.getDefaultLanguage().get(title).get(string);
				this.addItem(composite, title, string, value);
				composite.setSize(composite.computeSize(SWT.DEFAULT,
						SWT.DEFAULT));
				composite.layout();
			}
			return scrollComposite;
		} catch (Exception e) {
			logger.fatal(e, e);
			return null;
		}
	}

	public void addItem(Composite parent,
						String sectionTitle,
						String title,
						String value) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(title);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 180;
		label.setLayoutData(data);

		Text text = new Text(parent, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 200;
		data.horizontalSpan = 2;
		text.setText(value);
		text.setLayoutData(data);
		text.setData(title);
		Label labelTooltip = new Label(parent, SWT.NONE);
		labelTooltip.setImage(AppImages.getImage16(AppImages.IMG_HELP));
		labelTooltip.setToolTipText("Default language text:\r\n"
				+ Language.getDefaultLanguage().get(sectionTitle).get(title));

	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		try {
			if (arg0.type == SWT.Selection) {
				if (arg0.widget == this.treeWindows) {
					if (this.treeWindows.getSelectionCount() <= 0)
						return;
					TreeItem item = this.treeWindows.getSelection()[0];
					String strWin = (String) item.getData();
					ScrolledComposite scr = this.hashComposite.get(strWin);
					if (scr == null)
						scr = this.addWindowComposite(strWin,
								this.language.get(strWin));
					this.hashComposite.put(strWin, scr);
					this.formItems.setContent(scr);
					// System.err.println(this.hashComposite.size());
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void populateData() {
		try {
			Set<String> setWindows = this.language.keySet();
			Iterator<String> iterWindows = setWindows.iterator();
			while (iterWindows.hasNext()) {
				String string = iterWindows.next();
				TreeItem item = new TreeItem(this.treeWindows, SWT.NONE);
				item.setText(string);
				item.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_PROCESS));
				item.setData(string);
			}

			this.treeWindows.select(this.treeWindows.getItem(0));
			this.treeWindows.notifyListeners(SWT.Selection, new Event());
			this.treeWindows.forceFocus();
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected boolean validateData() {
		try {
			if (StringUtil.isEmpty(this.textLanguageName.getText())) {
				MessageBoxWindow.error(Language.getTextLanguageWindow(Language.LANGUAGE_WINDOW_MESSAGE_BOX_LANGUAGE_NAME_EMPTY));
				this.textLanguageName.forceFocus();
				return false;
			}
			Enumeration<String> enumS = this.hashComposite.keys();
			while (enumS.hasMoreElements()) {
				String section = enumS.nextElement();
				ScrolledComposite scrComp = this.hashComposite.get(section);
				Composite comp = (Composite) scrComp.getChildren()[0];
				Control[] controls = comp.getChildren();
				for (int j = 0; j < controls.length; j++) {
					if (!(controls[j] instanceof Text))
						continue;
					String str = ((Text) controls[j]).getText();
					if (StringUtil.isEmpty(str)) {
						MessageBoxWindow.error(Language.getTextLanguageWindow(Language.LANGUAGE_WINDOW_MESSAGE_BOX_TEXT_EMPTY));
						TreeItem item = this.getItem(section);
						if (item != null) {
							this.treeWindows.select(item);
							this.treeWindows.notifyListeners(SWT.Selection,
									new Event());
						}
						controls[j].forceFocus();
						return false;
					}
				}
			}

		} catch (Exception e) {
			logger.fatal(e, e);
			return false;
		}
		return true;
	}

	private TreeItem getItem(String key) {
		TreeItem[] items = this.treeWindows.getItems();
		for (int i = 0; i < items.length; i++) {
			if (((String) items[i].getData()).intern() == key.intern())
				return items[i];
		}
		return null;
	}

	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
