package net.nettape.client.gui.ui;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;
import net.nettape.client.gui.ui.settings.*;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.StringUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 24, 2009
 * @description
 */

public class BackupSettingsWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(BackupSettingsWindow.class);

	static ToolItem lastItemMainBar = null;
	static ToolItem lastItemBar1 = null;
	static ToolItem lastItemBar2 = null;

	private final String[] toolBarTexts = new String[] {
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_GENERAL),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_SOURCE),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_SCHEDULE),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_ENCRYPTION),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_CONTINOUS_DATA_PROTECTION),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_IN_FILE_DELTA),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_RETENTION_POLICY),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_BACKUP_FILTER),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_COMMAND_LINE_TOOL),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_EXTRA_BACKUP),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_LOCAL_COPY),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_OPTIONS) };

	private final String[] toolBarImages = new String[] {
			AppImages.IMG_SETTINS_GENERAL, AppImages.IMG_SETTINS_BACKUP_SOURCE,
			AppImages.IMG_SETTINS_BACKUP_SCHEDULE, AppImages.IMG_SETTINS_ENCRYPTION,
			AppImages.IMG_SETTINS_DATA_PROTECTION, AppImages.IMG_SETTINS_IN_FILE_DELTA,
			AppImages.IMG_SETTINS_RETENCION_POLICY, AppImages.IMG_SETTINS_BACKUP_FILTER,
			AppImages.IMG_SETTINS_COMMANDLINE_TOOL, AppImages.IMG_SETTINS_EXTRA_BACKUP,
			AppImages.IMG_SETTINS_LOCAL_COPY, AppImages.IMG_SETTINS_OPTIONS };

	private final AbstractSettingsComposite[] settingsComposite = new AbstractSettingsComposite[this.toolBarImages.length];
	private final Class<? extends AbstractSettingsComposite>[] settingsCompositeClass = new Class[] {
			GeneralSettingsComposite.class, BackupSourceSettingsComposite.class,
			BackupScheduleSettingsComposite.class, EncryptionSettingsComposite.class,
			DataProtectionSettingsComposite.class, InFileDeltaSettingsComposite.class,
			RetentionPolicySettingsComposite.class, BackupFilterSettingsComposite.class,
			CommandLineToolSettingsComposite.class, ExtraBackupSettingsComposite.class,
			LocalCopySettingsComposite.class, OptionsSettingsComposite.class };

	private ToolBar toolBar;

	private ViewForm viewForm;

	private Combo comboBackupSet;

	private Button buttonAdd;

	private Button buttonRemove;

	private static final String DATA_INDEX = "index";

	public BackupSettingsWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_TEXT_TITLE),
			Language.getTextBackupSettingsWindow(Language.BACKUP_SETTINGS_WINDOW_TEXT_EXPLAIN),
			AppImages.IMG_SETTINGS,
			600,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_PROCESS));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			BackupSettingsWindow.logger.fatal(ex, ex);
		}
	}

	@Override
	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			for (int i = 0; i < this.settingsComposite.length; i++) {
				this.toolBar.getItem(i).setSelection(false); // to deselect all
			}
			for (int i = 0; i < this.settingsComposite.length; i++) {
				if (this.settingsComposite[i] == null) {
					continue;
				}
				if (!this.settingsComposite[i].saveData()) {
					this.toolBar.getItem(i).setSelection(true);// to select the one with problem
					this.toolBar.getItem(i).notifyListeners(SWT.Selection, new Event());
					return false;
				}
			}
			result = true;
		} catch (Exception e) {
			BackupSettingsWindow.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	@Override
	public void createGUI() {
		Composite comp = new Composite(this.container, SWT.NONE);
		GridLayout lay = new GridLayout(2, Boolean.FALSE);
		lay.marginWidth = 0;
		comp.setLayout(lay);

		Composite comp1 = new Composite(comp, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 1;
		comp1.setLayoutData(data);
		lay = new GridLayout(3, false);
		comp1.setLayout(lay);

		this.comboBackupSet = new Combo(comp1, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		data.horizontalSpan = 1;
		this.comboBackupSet.setLayoutData(data);

		this.buttonAdd = new Button(comp1, SWT.PUSH);
		this.buttonAdd.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.buttonAdd.setLayoutData(data);
		this.buttonAdd.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAdd,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		this.buttonRemove = new Button(comp1, SWT.PUSH);
		this.buttonRemove.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(SWT.LEFT, SWT.TOP, false, false);
		this.buttonRemove.setLayoutData(data);
		this.buttonRemove.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonRemove,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));

		Label label = new Label(comp1, SWT.SEPARATOR | SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.TOP, true, true);
		data.horizontalSpan = 3;
		label.setLayoutData(data);

		this.toolBar = new ToolBar(comp1, SWT.FLAT | SWT.VERTICAL | SWT.RIGHT);
		this.toolBar.addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ToolItem item = BackupSettingsWindow.this.toolBar.getItem(new Point(
					event.x,
					event.y));
				if (BackupSettingsWindow.lastItemBar1 != item) {
					if ((BackupSettingsWindow.lastItemBar1 != null)
							&& (BackupSettingsWindow.lastItemBar1.getData() != null)) {
						BackupSettingsWindow.lastItemBar1.setImage(AppImages.getImage32((String) BackupSettingsWindow.lastItemBar1.getData()));
					}
					BackupSettingsWindow.lastItemBar1 = item;
					if ((BackupSettingsWindow.lastItemBar1 != null)
							&& (BackupSettingsWindow.lastItemBar1.getData() != null)) {
						BackupSettingsWindow.lastItemBar1.setImage(AppImages.getImage32Focus((String) BackupSettingsWindow.lastItemBar1.getData()));
					}
				}
			}
		});
		// toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		this.toolBar.getShell().getDisplay().timerExec(250, new Runnable() {
			@Override
			public void run() {
				if (BackupSettingsWindow.this.toolBar.isDisposed()) {
					return;
				}
				Point pt = BackupSettingsWindow.this.toolBar.getShell().getDisplay().map(null,
						BackupSettingsWindow.this.toolBar,
						BackupSettingsWindow.this.toolBar.getShell().getDisplay().getCursorLocation());
				ToolItem item = BackupSettingsWindow.this.toolBar.getItem(pt);
				if (BackupSettingsWindow.lastItemBar1 != item) {
					if ((BackupSettingsWindow.lastItemBar1 != null)
							&& (BackupSettingsWindow.lastItemBar1.getData() != null)) {
						BackupSettingsWindow.lastItemBar1.setImage(AppImages.getImage32((String) BackupSettingsWindow.lastItemBar1.getData()));
					}
					BackupSettingsWindow.lastItemBar1 = item;
				}
				BackupSettingsWindow.this.toolBar.getShell().getDisplay().timerExec(250, this);
			}
		});

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 3;
		// data.widthHint = 230;
		this.toolBar.setLayoutData(data);

		// ToolItem separator = new ToolItem(this.toolBar, SWT.SEPARATOR| SWT.VERTICAL);
		// separator.setWidth(200);

		for (int i = 0; i < this.toolBarTexts.length; i++) {
			ToolItem item = new ToolItem(this.toolBar, SWT.FLAT | SWT.RADIO);
			item.setText(StringUtil.padRight(this.toolBarTexts[i], 30));
			item.setImage(AppImages.getImage32(this.toolBarImages[i]));
			item.setData(this.toolBarImages[i]);
			item.setData(BackupSettingsWindow.DATA_INDEX, i);
			item.addListener(SWT.Selection, this);
		}

		this.viewForm = new ViewForm(comp, SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 500;
		data.horizontalSpan = ((GridLayout) this.container.getLayout()).numColumns;
		this.viewForm.setLayoutData(data);

		this.toolBar.getItem(0).setSelection(true);
		this.toolBar.getItem(0).notifyListeners(SWT.Selection, new Event());

	}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src instanceof ToolItem) {
					ToolItem item = (ToolItem) src;
					int index = (Integer) item.getData(BackupSettingsWindow.DATA_INDEX);
					toolItemAction(index);
				}
				// if (src == this.buttonMaskEncryptingKey) {
				// this.textEncryptingKey.setEchoChar(this.buttonMaskEncryptingKey.getSelection() ?
				// '*'
				// : '\0');
				// } else if (src == this.buttonChange) {
				// this.buttonChangeAction();
				// }
			}
		} catch (Exception e) {
			BackupSettingsWindow.logger.fatal(e, e);
		}
	}

	private void toolItemAction(int index) {
		if ((index < 0) || (index >= this.toolBarTexts.length)) {
			return;
		}
		if (this.settingsComposite[index] == null) {
			try {
				this.settingsComposite[index] = this.settingsCompositeClass[index].getConstructor(new Class[] {
						Composite.class, String.class }).newInstance(new Object[] {
						this.viewForm, this.toolBarTexts[index] });
			} catch (Exception e) {
				BackupSettingsWindow.logger.fatal(e, e);
			}
		}
		this.viewForm.setContent(this.settingsComposite[index]);
		this.shell.pack();
	}

	@Override
	public void populateData() {
		// for (int i = 1; i <= 15; i++) {
		// this.comboRecycleBin.add(i + "");
		// }
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean validateData() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
