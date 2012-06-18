package net.nettape.client.gui.ui;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;
import net.nettape.client.gui.ui.awindow.AbstractL3Window;
import net.nettape.client.gui.ui.util.WidgetUtils;

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
import org.eclipse.swt.widgets.Widget;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 28, 2009
 * @description
 */

public class BackupScheduleWindow extends AbstractL3Window {

	private static final Logger logger = Logger.getLogger(BackupScheduleWindow.class);

	private Button buttonRunSchedule;
	private Label labelBackup;
	private Button buttonSunday;
	private Button buttonMonday;
	private Button buttonTuesday;
	private Button buttonWednesday;
	private Button buttonThursday;
	private Button buttonFriday;
	private Button buttonSaturday;
	private Group groupTime;
	private Label labelStart;
	private Label labelStop;
	private Button buttonOnCompletion;
	private Button buttonAfter;
	private Label labelHours;
	private Combo comboStartHour;
	private Combo comboStartMinute;
	private Combo comboStop;

	private Group groupDays;

	public BackupScheduleWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_TITLE),
			Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_EXPLAIN),
			AppImages.IMG_SCHEDULE,
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_CALENDAR_DATE));
			this.createGUI();
			this.populateData();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}

	}

	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			// TODO some action

			result = true;
		} catch (Exception e) {
			logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	public void createGUI() {
		try {
			this.buttonRunSchedule = new Button(this.container, SWT.CHECK);
			this.buttonRunSchedule.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_RUN_SCHEDULE));
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) this.container.getLayout()).numColumns;
			this.buttonRunSchedule.addListener(SWT.Selection, this);

			this.groupDays = new Group(this.container, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			this.groupDays.setLayout(new GridLayout(3, false));
			this.groupDays.setLayoutData(data);

			this.labelBackup = new Label(this.groupDays, SWT.NONE);
			this.labelBackup.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_LABEL_BACKUP)
					+ ":");
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = 3;
			this.labelBackup.setLayoutData(data);

			this.buttonSunday = new Button(this.groupDays, SWT.CHECK);
			this.buttonSunday.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_SUNDAY));

			this.buttonMonday = new Button(this.groupDays, SWT.CHECK);
			this.buttonMonday.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_MONDAY));

			this.buttonTuesday = new Button(this.groupDays, SWT.CHECK);
			this.buttonTuesday.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_TUESDAY));

			this.buttonWednesday = new Button(this.groupDays, SWT.CHECK);
			this.buttonWednesday.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_WEDNESDAY));

			this.buttonThursday = new Button(this.groupDays, SWT.CHECK);
			this.buttonThursday.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_THURSDAY));

			this.buttonFriday = new Button(this.groupDays, SWT.CHECK);
			this.buttonFriday.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_FRIDAY));

			this.buttonSaturday = new Button(this.groupDays, SWT.CHECK);
			this.buttonSaturday.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_SATURDAY));

			this.groupTime = new Group(this.container, SWT.NONE);
			// this.groupTime.setText("       "
			// + Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_GROUP_TIME));
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			GridLayout lay = new GridLayout(1, false);
			lay.marginWidth = lay.marginHeight = 0;
			lay.horizontalSpacing = lay.verticalSpacing = 0;
			this.groupTime.setLayout(lay);
			this.groupTime.setLayoutData(data);
			// WidgetUtils.drawImageOnGroup(this.groupTime,
			// AppImages.getImage16(AppImages.IMG_CLOCK));
			this.groupTime.setEnabled(false);

			Composite comp = new Composite(this.groupTime, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) this.groupTime.getLayout()).numColumns;
			comp.setLayoutData(data);
			lay = new GridLayout(2, false);
			comp.setLayout(lay);

			new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_CLOCK));
			new Label(comp, SWT.NONE).setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_GROUP_TIME));

			comp = new Composite(this.groupTime, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = ((GridLayout) this.groupTime.getLayout()).numColumns;
			comp.setLayoutData(data);
			lay = new GridLayout(6, false);
			comp.setLayout(lay);

			this.labelStart = new Label(comp, SWT.NONE);
			this.labelStart.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_LABEL_START)
					+ ":");

			this.comboStartHour = new Combo(comp, SWT.DROP_DOWN);
			data = new GridData();
			data.minimumWidth = 30;
			data.widthHint = 40;
			this.comboStartHour.setLayoutData(data);

			new Label(comp, SWT.NONE).setText(":");
			this.comboStartMinute = new Combo(comp, SWT.DROP_DOWN);
			data = new GridData();
			data.minimumWidth = 30;
			data.widthHint = 40;
			this.comboStartMinute.setLayoutData(data);

			new Label(comp, SWT.NONE).setText("");
			new Label(comp, SWT.NONE).setText("");

			this.labelStop = new Label(comp, SWT.NONE);
			this.labelStop.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_LABEL_STOP)
					+ ":");
			this.buttonOnCompletion = new Button(comp, SWT.RADIO);
			this.buttonOnCompletion.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_ON_COMPLETION));
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = 5;
			this.buttonOnCompletion.setLayoutData(data);
			this.buttonOnCompletion.setSelection(true);

			new Label(comp, SWT.NONE).setText("");
			this.buttonAfter = new Button(comp, SWT.RADIO);
			this.buttonAfter.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_BUTTON_AFTER));
			this.buttonAfter.addListener(SWT.Selection, this);

			this.comboStop = new Combo(comp, SWT.DROP_DOWN);
			data = new GridData();
			data.minimumWidth = 30;
			data.widthHint = 40;
			data.horizontalSpan = 2;
			this.comboStop.setLayoutData(data);
			this.comboStop.setEnabled(false);

			this.labelHours = new Label(comp, SWT.NONE);
			this.labelHours.setText(Language.getTextBackupScheduleWindow(Language.BACKUP_SCHEDULE_WINDOW_LABEL_HOURS));
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void populateData() {
		for (int i = 0; i < 24; i++) {
			this.comboStartHour.add((i < 10 ? "0" : "") + i);
		}
		for (int i = 0; i < 60; i += 5) {
			this.comboStartMinute.add((i < 10 ? "0" : "") + i);
		}
		for (int i = 0; i <= 24; i += 4) {
			this.comboStop.add(i + "");
		}

		// TODO selection on data

		WidgetUtils.enableGUI(this.groupDays,
				this.buttonRunSchedule.getSelection());
		WidgetUtils.enableGUI(this.groupTime,
				this.buttonRunSchedule.getSelection());
	}

	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {
				if (src == this.buttonRunSchedule) {
					WidgetUtils.enableGUI(this.groupDays,
							this.buttonRunSchedule.getSelection());
					WidgetUtils.enableGUI(this.groupTime,
							this.buttonRunSchedule.getSelection());
					if (this.buttonRunSchedule.getSelection())
						this.comboStop.setEnabled(this.buttonAfter.getSelection());
					this.groupTime.redraw();// for image on group
				} else if (src == this.buttonAfter) {
					this.comboStop.setEnabled(this.buttonAfter.getSelection());
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	protected boolean validateData() {
		// TODO Auto-generated method stub
		return true;
	}

	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
