/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 24, 2010
 * @description
 */
package net.nettape.client.gui.ui;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.util.DateUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class AdvancedInFileDeltaWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(AdvancedInFileDeltaWindow.class);

	private Group groupWeekly;
	private Group groupYearly;
	private Button buttonDays;
	private Combo comboDays;
	private Button buttonDayOfWeek;
	private Combo comboIndex;
	private Combo comboDayOfWeek;

	private Button[] weeklyButtons;
	private Combo[] weeklyCombo;

	private Button[] yearlyButtons;
	private Combo[] yearlyCombo;

	public AdvancedInFileDeltaWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextAdvancedInFileDeltaWindow(Language.ADVANCED_IN_FILE_DELTA_WINDOW_TITLE),
			0,
			0);
		this.shell.setImage(AppImages.getImage16(AppImages.IMG_IN_FILE));
		createGUI();
		populateData();
	}

	@Override
	public void populateData() {

	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createGUI() {
		this.groupWeekly = new Group(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupWeekly.setLayout(new GridLayout(4, false));
		this.groupWeekly.setLayoutData(data);

		Composite comp = new Composite(this.groupWeekly, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupWeekly.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_WEEKLY));
		new Label(comp, SWT.NONE).setText(Language.getTextAdvancedInFileDeltaWindow(Language.ADVANCED_IN_FILE_DELTA_WINDOW_GROUP_WEEKLY));

		this.weeklyButtons = new Button[DateUtil.DAYS_WEEKLY.length];
		this.weeklyCombo = new Combo[DateUtil.DAYS_WEEKLY.length];
		for (int i = 0; i <= this.weeklyButtons.length / 2; i++) {
			this.weeklyButtons[i] = new Button(this.groupWeekly, SWT.CHECK);
			data = new GridData(SWT.NONE, SWT.CENTER, false, false);
			data.horizontalSpan = 1;
			this.weeklyButtons[i].setText(DateUtil.DAYS_WEEKLY[i]);
			this.weeklyButtons[i].setLayoutData(data);
			final int z = i;
			this.weeklyButtons[i].addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					AdvancedInFileDeltaWindow.this.weeklyCombo[z].setEnabled(AdvancedInFileDeltaWindow.this.weeklyButtons[z].getSelection());
				}
			});
			this.weeklyCombo[i] = new Combo(this.groupWeekly, SWT.BORDER);
			data = new GridData(SWT.FILL, SWT.NONE, true, false);
			data.horizontalSpan = 1;
			this.weeklyCombo[i].setLayoutData(data);
			this.weeklyCombo[i].setEnabled(this.weeklyButtons[i].getSelection());
			int y = i + this.weeklyButtons.length / 2 + 1;
			if (y >= this.weeklyButtons.length) {
				break;
			}
			this.weeklyButtons[y] = new Button(this.groupWeekly, SWT.CHECK);
			data = new GridData(SWT.NONE, SWT.CENTER, false, false);
			data.horizontalSpan = 1;
			this.weeklyButtons[y].setText(DateUtil.DAYS_WEEKLY[y]);
			this.weeklyButtons[y].setLayoutData(data);
			final int zz = y;
			this.weeklyButtons[y].addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					AdvancedInFileDeltaWindow.this.weeklyCombo[zz].setEnabled(AdvancedInFileDeltaWindow.this.weeklyButtons[zz].getSelection());
				}
			});
			this.weeklyCombo[y] = new Combo(this.groupWeekly, SWT.BORDER);
			data = new GridData(SWT.FILL, SWT.NONE, true, false);
			data.horizontalSpan = 1;
			this.weeklyCombo[y].setLayoutData(data);
			this.weeklyCombo[y].setEnabled(this.weeklyButtons[y].getSelection());
		}

		this.groupYearly = new Group(this.container, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupYearly.setLayout(new GridLayout(4, false));
		this.groupYearly.setLayoutData(data);

		comp = new Composite(this.groupYearly, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupYearly.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_YEARLY));
		new Label(comp, SWT.NONE).setText(Language.getTextAdvancedInFileDeltaWindow(Language.ADVANCED_IN_FILE_DELTA_WINDOW_GROUP_YEARLY));

		this.yearlyButtons = new Button[DateUtil.MONTHS.length];
		this.yearlyCombo = new Combo[DateUtil.MONTHS.length];
		for (int i = 0; i < this.yearlyButtons.length / 2; i++) {
			this.yearlyButtons[i] = new Button(this.groupYearly, SWT.CHECK);
			data = new GridData(SWT.NONE, SWT.CENTER, false, false);
			data.horizontalSpan = 1;
			this.yearlyButtons[i].setText(DateUtil.MONTHS[i]);
			this.yearlyButtons[i].setLayoutData(data);
			final int z = i;
			this.yearlyButtons[i].addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					AdvancedInFileDeltaWindow.this.yearlyCombo[z].setEnabled(AdvancedInFileDeltaWindow.this.yearlyButtons[z].getSelection());
				}
			});
			this.yearlyCombo[i] = new Combo(this.groupYearly, SWT.BORDER);
			data = new GridData(SWT.FILL, SWT.NONE, true, false);
			data.horizontalSpan = 1;
			this.yearlyCombo[i].setLayoutData(data);
			this.yearlyCombo[i].setEnabled(this.yearlyButtons[i].getSelection());
			int y = i + this.yearlyButtons.length / 2;
			if (y >= this.yearlyButtons.length) {
				break;
			}
			this.yearlyButtons[y] = new Button(this.groupYearly, SWT.CHECK);
			data = new GridData(SWT.NONE, SWT.CENTER, false, false);
			data.horizontalSpan = 1;
			this.yearlyButtons[y].setText(DateUtil.MONTHS[y]);
			this.yearlyButtons[y].setLayoutData(data);
			final int zz = y;
			this.yearlyButtons[y].addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					AdvancedInFileDeltaWindow.this.yearlyCombo[zz].setEnabled(AdvancedInFileDeltaWindow.this.yearlyButtons[zz].getSelection());
				}
			});
			this.yearlyCombo[y] = new Combo(this.groupYearly, SWT.BORDER);
			data = new GridData(SWT.FILL, SWT.NONE, true, false);
			data.horizontalSpan = 1;
			this.yearlyCombo[y].setLayoutData(data);
			this.yearlyCombo[y].setEnabled(this.yearlyButtons[y].getSelection());
		}

		comp = new Composite(this.groupYearly, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupYearly.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(3, false);
		comp.setLayout(lay);

		this.buttonDays = new Button(comp, SWT.RADIO);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonDays.setText(Language.getTextGeneral(Language.GENERAL_TEXT_DAY));
		this.buttonDays.setLayoutData(data);
		this.buttonDays.addListener(SWT.Selection, this);
		this.buttonDays.setSelection(true);

		this.comboDays = new Combo(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboDays.setLayoutData(data);

		new Label(comp, SWT.NONE).setText("");

		this.buttonDayOfWeek = new Button(comp, SWT.RADIO);
		data = new GridData(SWT.NONE, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		this.buttonDayOfWeek.setLayoutData(data);
		this.buttonDayOfWeek.addListener(SWT.Selection, this);

		this.comboIndex = new Combo(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboIndex.setLayoutData(data);

		this.comboDayOfWeek = new Combo(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboDayOfWeek.setLayoutData(data);

		this.buttonDays.notifyListeners(SWT.Selection, new Event());
	}

	@Override
	protected boolean buttonOKAction() {
		return false;
	}

	@Override
	protected boolean validateData() {
		return false;
	}

	@Override
	public void handleEvent(Event event) {
		super.handleEvent(event);
		Widget src = null;
		try {
			src = event.widget;
			if (event.type == SWT.Selection) {
				if (src == this.buttonDays) {
					this.comboDays.setEnabled(this.buttonDays.getSelection());
					this.comboDayOfWeek.setEnabled(this.buttonDayOfWeek.getSelection());
					this.comboIndex.setEnabled(this.buttonDayOfWeek.getSelection());
				} else if (src == this.buttonDayOfWeek) {
					this.comboDays.setEnabled(this.buttonDays.getSelection());
					this.comboDayOfWeek.setEnabled(this.buttonDayOfWeek.getSelection());
					this.comboIndex.setEnabled(this.buttonDayOfWeek.getSelection());
				}
			}
		} catch (Exception e) {
			AdvancedInFileDeltaWindow.logger.fatal(e, e);
		}
	}

}
