package net.nettape.client.gui.ui;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.StringUtil;
import net.nettape.dal.object.Criteria;
import net.nettape.dal.object.Filter;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 8, 2009
 * @description
 */

public class BackUpFilterWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(BackUpFilterWindow.class);

	private Combo comboApplyTo;
	private Combo comboOption;
	private Button buttonPlus;
	private Button buttonMinus;
	private Text textFileNameExtension;
	private Button buttonAdd;
	private Button buttonRemove;
	private List listExtension;
	private Group groupFileNameExtension;
	private Set<Filter> setFilter;

	private final AbstractList<String> listApplyTo = new ArrayList<String>();
	private final AbstractList<AbstractList<Criteria>> listCriteria = new ArrayList<AbstractList<Criteria>>();
	private final AbstractList<Boolean> listInclude = new ArrayList<Boolean>();

	public BackUpFilterWindow(Set<Filter> setFilter) {
		this();
		this.setFilter = setFilter;
		this.populateData();
	}

	public BackUpFilterWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_TEXT_TITLE),
			Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_TEXT_EXPLAIN),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_SEARCH));
			this.createGUI();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			this.setFilter = new HashSet<Filter>();
			for (int i = 0; i < this.listApplyTo.size(); i++) {
				Filter filter = new Filter();
				filter.setToppath(this.listApplyTo.get(i));
				filter.setInclude(this.listInclude.get(i));
				filter.setApplytofiles(true);
				filter.setApplytofolders(true);
				Set<Criteria> setCriteria = new HashSet<Criteria>();
				for (int j = 0; j < this.listCriteria.get(i).size(); j++) {
					Criteria criteria = this.listCriteria.get(i).get(j);
					criteria.setFilter(filter);
					criteria.setType(new Short((short) 0));
					setCriteria.add(criteria);
				}
				filter.setCriterias(setCriteria);
				this.setFilter.add(filter);
			}
			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private boolean validateAddCriteria() {
		String text = this.textFileNameExtension.getText();
		if (StringUtil.isEmpty(text))
			return false;
		ArrayList<String> strIllegalChar = AppConstants.getIlegalCharacters();
		for (int i = 0; i < strIllegalChar.size(); i++) {
			if (text.indexOf(strIllegalChar.get(i)) != -1) {
				MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER)
						+ " ("
						+ StringUtil.getStringFromArrayList(strIllegalChar, " ")
						+ ")");
				this.textFileNameExtension.forceFocus();
				return false;
			}
		}
		if (text.indexOf(".") == -1) {
			MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT));
			this.textFileNameExtension.forceFocus();
			return false;
		}
		for (int i = 0; i < this.listExtension.getItemCount(); i++) {
			if (this.listExtension.getItem(i).intern() == this.textFileNameExtension.getText().intern()) {
				MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATTERN_DUPLICATE));
				this.textFileNameExtension.forceFocus();
				return false;
			}
		}
		return true;
	}

	private boolean buttonAddAction() {
		if (!this.validateAddCriteria())
			return false;

		Criteria criteria = new Criteria();
		criteria.setPattern(this.textFileNameExtension.getText());
		criteria.setMatchcase(true);
		criteria.setType((short)1);
		this.listExtension.add(criteria.getPattern());
		this.textFileNameExtension.setText("");
		this.listCriteria.get(this.comboApplyTo.getSelectionIndex()).add(criteria);
		return true;
	}

	private boolean buttonRemoveAction() {
		if (this.listExtension.getSelectionCount() <= 0
				|| this.listExtension.getItemCount() <= 0)
			return false;
		this.listCriteria.get(this.comboApplyTo.getSelectionIndex()).remove(this.listExtension.getSelectionIndex());
		this.listExtension.remove(this.listExtension.getSelectionIndex());
		return false;
	}

	private boolean buttonMinusAction() {
		if (this.comboApplyTo.getSelectionIndex() >= 0) {
			int index = this.comboApplyTo.getSelectionIndex();
			this.listApplyTo.remove(index);
			this.listCriteria.remove(index);
			this.listInclude.remove(index);
			this.comboApplyTo.remove(index);
			this.comboApplyTo.setText("");
			this.comboApplyTo.select(0);
			this.comboApplyTo.notifyListeners(SWT.Selection, new Event());
		}
		return true;
	}

	private boolean buttonPlusAction() {
		SelectDirectoryFilterWindow dlg = new SelectDirectoryFilterWindow();
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			String selectedDirectory = dlg.getResult();
			if (this.comboApplyTo.indexOf(selectedDirectory) != -1) {
				MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATH_DUPLICATE));
				return false;
			}
			this.comboApplyTo.add(selectedDirectory);
			this.comboApplyTo.select(this.comboApplyTo.indexOf(selectedDirectory));
			this.listApplyTo.add(selectedDirectory);
			this.listCriteria.add(new ArrayList<Criteria>());
			this.listInclude.add(this.comboOption.getSelectionIndex() == 0);
			this.comboApplyTo.notifyListeners(SWT.Selection, new Event());
		}
		return true;
	}

	public void createGUI() {
		Composite compositeHeader = new Composite(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeHeader.setLayoutData(data);
		compositeHeader.setLayout(new GridLayout(4, false));
		new Label(compositeHeader, SWT.NONE).setText(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_COMBO_APPLY_TO)
				+ ":");

		this.comboApplyTo = new Combo(compositeHeader, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 180;
		this.comboApplyTo.setLayoutData(data);
		this.comboApplyTo.addListener(SWT.Selection, this);

		this.buttonPlus = new Button(compositeHeader, SWT.PUSH);
		this.buttonPlus.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(GridData.FILL_HORIZONTAL);
		this.buttonPlus.setLayoutData(data);
		this.buttonPlus.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonPlus,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		this.buttonMinus = new Button(compositeHeader, SWT.PUSH);
		this.buttonMinus.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(GridData.FILL_HORIZONTAL);
		this.buttonMinus.setLayoutData(data);
		this.buttonMinus.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonMinus,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));

		this.groupFileNameExtension = new Group(this.container, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.groupFileNameExtension.setLayout(new GridLayout(2, false));
		this.groupFileNameExtension.setLayoutData(data);

		Composite comp = new Composite(this.groupFileNameExtension, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) this.groupFileNameExtension.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_APPLICATION));
		new Label(comp, SWT.NONE).setText(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_GROUP_FILE_NAME_EXTENSION));

		this.textFileNameExtension = new Text(
			this.groupFileNameExtension,
			SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 200;
		this.textFileNameExtension.setLayoutData(data);
		this.textFileNameExtension.addListener(SWT.Modify, this);

		this.buttonAdd = new Button(this.groupFileNameExtension, SWT.PUSH);
		this.buttonAdd.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAdd.setLayoutData(data);
		this.buttonAdd.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAdd,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		this.listExtension = new List(this.groupFileNameExtension, SWT.BORDER
				| SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 200;
		data.heightHint = 200;
		this.listExtension.setLayoutData(data);
		this.listExtension.addListener(SWT.Selection, this);

		this.buttonRemove = new Button(this.groupFileNameExtension, SWT.PUSH);
		this.buttonRemove.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(SWT.LEFT, SWT.TOP, true, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonRemove.setLayoutData(data);
		this.buttonRemove.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonRemove,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));

		this.comboOption = new Combo(this.groupFileNameExtension, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 180;
		this.comboOption.setLayoutData(data);
		this.comboOption.add(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_COMBO_OPTION_1));
		this.comboOption.add(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_COMBO_OPTION_2));
		this.comboOption.select(0);
		this.comboOption.addListener(SWT.Selection, this);
		this.enableGUI();
	}

	public void populateData() {
		if (this.setFilter == null)
			return;
		Iterator<Filter> iterFilter = this.setFilter.iterator();
		while (iterFilter.hasNext()) {
			Filter filter = iterFilter.next();
			this.listApplyTo.add(filter.getToppath());
			this.comboApplyTo.add(filter.getToppath());
			this.listInclude.add(filter.getInclude());
			this.listCriteria.add(new ArrayList<Criteria>());
			Iterator<Criteria> iterCriteria = filter.getCriterias().iterator();
			while (iterCriteria.hasNext()) {
				Criteria criteria = iterCriteria.next();
				this.listCriteria.get(this.listCriteria.size() - 1).add(criteria);
			}
		}
		this.comboApplyTo.select(0);
		this.comboApplyTo.notifyListeners(SWT.Selection, new Event());
		this.enableGUI();
	}

	private void enableGUI() {
		if (this.shell.isDisposed())
			return;// window closed
		if (this.comboApplyTo.getSelectionIndex() < 0) {
			this.buttonMinus.setEnabled(false);
			this.textFileNameExtension.setEnabled(false);
			this.buttonAdd.setEnabled(false);
			this.comboOption.setEnabled(false);
			this.listExtension.setEnabled(false);
		} else {
			this.buttonMinus.setEnabled(true);
			this.textFileNameExtension.setEnabled(true);
			this.buttonAdd.setEnabled(true);
			this.comboOption.setEnabled(true);
			this.listExtension.setEnabled(true);
		}
		if (StringUtil.isEmpty(this.textFileNameExtension.getText())) {
			this.buttonAdd.setEnabled(false);
		} else {
			this.buttonAdd.setEnabled(true);
		}
		if (this.listExtension.getSelectionIndex() < 0) {
			this.buttonRemove.setEnabled(false);
		} else {
			this.buttonRemove.setEnabled(true);
		}
	}

	private void comboApplyToAction() {
		try {
			this.listExtension.removeAll();
			if (this.comboApplyTo.getSelectionIndex() < 0)
				return;
			for (int i = 0; i < this.listCriteria.get(this.comboApplyTo.getSelectionIndex()).size(); i++) {
				Criteria criteria = this.listCriteria.get(this.comboApplyTo.getSelectionIndex()).get(i);
				if (criteria == null)
					continue;
				this.listExtension.add(criteria.getPattern());
			}
			this.comboOption.select(this.listInclude.get(this.comboApplyTo.getSelectionIndex())	? 0
																								: 1);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonPlus) {
					this.buttonPlusAction();
				} else if (src == this.buttonMinus) {
					this.buttonMinusAction();
				} else if (src == this.buttonAdd) {
					this.buttonAddAction();
				} else if (src == this.buttonRemove) {
					this.buttonRemoveAction();
				} else if (src == this.comboApplyTo) {
					this.comboApplyToAction();
				} else if (src == this.comboOption) {
					this.listInclude.add(this.comboApplyTo.getSelectionIndex(),
							this.comboOption.getSelectionIndex() == 0);
				}
			}
			this.enableGUI();// put here for all listeners
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected boolean validateData() {
		boolean result = false;
		try {
			if (this.listApplyTo.isEmpty()) {
				MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_ONE_FILTER));
				this.buttonPlus.forceFocus();
				return false;
			}
			for (int i = 0; i < this.listApplyTo.size(); i++) {
				File f = new File(this.listApplyTo.get(i));
				if (!f.exists()) {
					MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATH_NOT_EXIST));
					this.comboApplyTo.select(i);
					this.comboApplyTo.notifyListeners(SWT.Selection,
							new Event());
					this.comboApplyTo.forceFocus();
					return false;
				}
				if (this.listCriteria.get(i).isEmpty()) {
					MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATTERN_EMPTY));
					this.comboApplyTo.select(i);
					this.comboApplyTo.notifyListeners(SWT.Selection,
							new Event());
					this.textFileNameExtension.forceFocus();
					return false;
				}
			}
			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	public Set<Filter> getResult() {
		return this.setFilter;
	}
}
