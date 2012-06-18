package net.nettape.client.gui.ui;

import java.util.*;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.dal.object.Criteria;
import net.nettape.dal.object.Filter;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.List;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Mar 27, 2010
 * @description
 */

public class RestoreFilterWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(BackUpFilterWindow.class);

	private Button buttonAdd;
	private Button buttonRemove;
	private List listExtension;
	private Group groupFileNameExtension;
	private Filter filter;

	private final AbstractList<Criteria> listCriteria = new ArrayList<Criteria>();

	public RestoreFilterWindow(final Filter setFilter) {
		this();
		this.filter = setFilter;
		this.populateData();
	}

	public RestoreFilterWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextRestoreFilterWindow(Language.RESTORE_FILTER_WINDOW_TEXT_TITLE),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_DATABASE_SEARCH));
			this.createGUI();
		} catch (Exception ex) {
			RestoreFilterWindow.logger.fatal(ex, ex);
		}
	}

	@Override
	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			if (this.filter == null) {
				this.filter = new Filter();
			}
			Set<Criteria> setCriteria = new HashSet<Criteria>();
			for (int j = 0; j < this.listCriteria.size(); j++) {
				Criteria criteria = this.listCriteria.get(j);
				criteria.setFilter(this.filter);
				setCriteria.add(criteria);
			}
			this.filter.setCriterias(setCriteria);
			result = true;
		} catch (Exception e) {
			RestoreFilterWindow.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private boolean buttonAddAction() {
		RestoreFilterCriteriaWindow dlg = new RestoreFilterCriteriaWindow(null);
		dlg.open();
		if (dlg.getExitChoiceAction() == SWT.OK) {
			Criteria criteria = dlg.getResult();
			this.listCriteria.add(criteria);
			this.listExtension.add(this.getStrLine(criteria));
		}
		return false;
	}

	private boolean buttonRemoveAction() {
		if ((this.listExtension.getSelectionCount() <= 0)
				|| (this.listExtension.getItemCount() <= 0)) {
			return false;
		}
		this.listExtension.remove(this.listExtension.getSelectionIndex());
		return false;
	}

	@Override
	public void createGUI() {
		this.groupFileNameExtension = new Group(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.groupFileNameExtension.setLayout(new GridLayout(2, false));
		this.groupFileNameExtension.setLayoutData(data);

		Composite comp = new Composite(this.groupFileNameExtension, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) this.groupFileNameExtension.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_APPLICATION));
		new Label(comp, SWT.NONE).setText(Language.getTextRestoreFilterWindow(Language.RESTORE_FILTER_WINDOW_GROUP_FILE_NAME_EXTENSION));

		this.buttonAdd = new Button(this.groupFileNameExtension, SWT.PUSH);
		this.buttonAdd.setImage(AppImages.getImage16(AppImages.IMG_ADD));
		data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonAdd.setLayoutData(data);
		this.buttonAdd.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonAdd,
				AppImages.getImage16(AppImages.IMG_ADD),
				AppImages.getImage16Focus(AppImages.IMG_ADD));

		this.buttonRemove = new Button(this.groupFileNameExtension, SWT.PUSH);
		this.buttonRemove.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonRemove.setLayoutData(data);
		this.buttonRemove.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonRemove,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));

		this.listExtension = new List(this.groupFileNameExtension, SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 300;
		data.heightHint = 150;
		data.horizontalSpan = 2;
		this.listExtension.setLayoutData(data);
		this.listExtension.addListener(SWT.Selection, this);

		this.enableGUI();
	}

	public void populateData() {
		if (this.filter == null) {
			return;
		}
		Iterator<Criteria> iterCriteria = this.filter.getCriterias().iterator();
		while (iterCriteria.hasNext()) {
			Criteria criteria = iterCriteria.next();
			this.listCriteria.add(criteria);
			this.listExtension.add(this.getStrLine(criteria));
		}
		this.enableGUI();
	}

	private void enableGUI() {
		if (this.shell.isDisposed()) {
			return;// window closed
		}
		if (this.listExtension.getSelectionIndex() < 0) {
			this.buttonRemove.setEnabled(false);
		} else {
			this.buttonRemove.setEnabled(true);
		}
	}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {

				if (src == this.buttonAdd) {
					this.buttonAddAction();
				} else if (src == this.buttonRemove) {
					this.buttonRemoveAction();
				}
			}

			this.enableGUI();// put here for all listeners
		} catch (Exception e) {
			RestoreFilterWindow.logger.fatal(e, e);
		}
	}

	@Override
	protected boolean validateData() {
		boolean result = false;
		try {
			for (int i = 0; i < this.listCriteria.size(); i++) {
				// if (this.listCriteria.get(i).isEmpty()) {
				// MessageBoxWindow.error(Language.getTextBackUpFilterWindow(Language.BACKUP_FILTER_WINDOW_MESSAGE_BOX_TEXT_PATTERN_EMPTY));
				// return false;
				// }
			}
			result = true;
		} catch (Exception e) {
			RestoreFilterWindow.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	private String getTypeStr(Short type) {
		try {
			String[] strCombo = new String[] {
					Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_EXACT),
					Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_START_WITH),
					Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_ENDS_WITH) };
			return strCombo[type - 1];
		} catch (Exception e) {
			RestoreFilterWindow.logger.fatal(e, e);
			return "";
		}
	}

	private String getStrLine(Criteria criteria) {
		return criteria.getPattern()
				+ " | "
				+ this.getTypeStr(criteria.getType())
				+ (criteria.getMatchCase()	? " | "
													+ Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_BUTTON_MATCH_CASE)
											: "");
	}

	public Filter getResult() {
		return this.filter;
	}
}