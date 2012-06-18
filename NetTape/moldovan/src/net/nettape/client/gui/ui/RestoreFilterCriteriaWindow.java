package net.nettape.client.gui.ui;

import java.util.ArrayList;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.custom.MessageBoxWindow;
import net.nettape.client.gui.util.StringUtil;
import net.nettape.dal.object.Criteria;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class RestoreFilterCriteriaWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(RestoreFilterCriteriaWindow.class);
	private Button buttonMatchCase;
	private Text textPattern;
	private Combo comboType;

	private Criteria criteria;

	public RestoreFilterCriteriaWindow(Criteria criteria) {
		this();
		this.criteria = criteria;
		this.populateData();
	}

	public RestoreFilterCriteriaWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_WINDOW_TEXT_TITLE),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_SEARCH_ADD));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	protected boolean buttonOKAction() {
		if (this.criteria == null)
			this.criteria = new Criteria();
		this.criteria.setPattern(this.textPattern.getText());
		this.criteria.setMatchCase(this.buttonMatchCase.getSelection());
		this.criteria.setType((short) (this.comboType.getSelectionIndex()+1));
		return true;
	}

	public void createGUI() {
		Group groupPattern = new Group(this.container, SWT.NONE);
		groupPattern.setText(Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_GROUP_PATTERN));
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, true);
		groupPattern.setLayoutData(data);
		groupPattern.setLayout(new GridLayout(7, false));

		new Label(groupPattern, SWT.NONE).setText(Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_TEXT_PATTERN));
		new Label(groupPattern, SWT.NONE).setText(":");

		this.textPattern = new Text(groupPattern, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 200;
		this.textPattern.setLayoutData(data);

		new Label(groupPattern, SWT.NONE).setText(Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TYPE));
		new Label(groupPattern, SWT.NONE).setText(":");

		this.comboType = new Combo(groupPattern, SWT.DROP_DOWN | SWT.READ_ONLY);

		this.buttonMatchCase = new Button(groupPattern, SWT.CHECK);
		this.buttonMatchCase.setText(Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_BUTTON_MATCH_CASE));

		String[] strCombo = new String[] {
				Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_EXACT),
				Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_START_WITH),
				Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_COMBO_TEXT_ENDS_WITH) };
		for (int i = 0; i < strCombo.length; i++) {
			this.comboType.add(strCombo[i]);
		}
		this.comboType.select(0);
	}

	public void populateData() {
		if (this.criteria == null)
			return;
		this.textPattern.setText(this.criteria.getPattern());
		this.comboType.select(this.criteria.getType() - 1);
		this.buttonMatchCase.setSelection(this.criteria.getMatchCase());
	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
	}

	protected boolean validateData() {
		String text = this.textPattern.getText();
		if (StringUtil.isEmpty(text)) {
			MessageBoxWindow.error(Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_PATTERN_EMPTY));
			this.textPattern.forceFocus();
			return false;
		}

		ArrayList<String> strIllegalChar = AppConstants.getIlegalCharacters();
		for (int i = 0; i < strIllegalChar.size(); i++) {
			if (text.indexOf(strIllegalChar.get(i)) != -1) {
				MessageBoxWindow.error(Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_ILLEGAL_CHARACTER)
						+ " ("
						+ StringUtil.getStringFromArrayList(strIllegalChar, " ")
						+ ")");
				this.textPattern.forceFocus();
				return false;
			}
		}

		if (text.indexOf(".") == -1) {
			MessageBoxWindow.error(Language.getTextRestoreFilterCriteriaWindow(Language.RESTORE_FILTER_CRITERIA_WINDOW_MESSAGE_BOX_TEXT_ONE_DOT));
			this.textPattern.forceFocus();
			return false;
		}
		return true;
	}

	public Criteria getResult() {
		return this.criteria;
	}

}