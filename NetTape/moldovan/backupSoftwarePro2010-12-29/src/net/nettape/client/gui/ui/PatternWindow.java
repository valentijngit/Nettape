package net.nettape.client.gui.ui;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL2Window;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 28, 2010
 * @description
 */

public class PatternWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(PatternWindow.class);

	private Text textPattern;

	private Group groupPattern;

	public PatternWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextPatternWindow(Language.PATTERN_WINDOW_TITLE),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_PATTERN));
			this.createGUI();
			populateData();
		} catch (Exception ex) {
			PatternWindow.logger.fatal(ex, ex);
		}
	}

	@Override
	protected boolean buttonOKAction() {
		boolean result = false;
		try {
			result = true;
		} catch (Exception e) {
			PatternWindow.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	@Override
	public void createGUI() {

		this.groupPattern = new Group(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.groupPattern.setLayout(new GridLayout(2, false));
		this.groupPattern.setLayoutData(data);

		Composite comp = new Composite(this.groupPattern, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = ((GridLayout) this.groupPattern.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_APPLICATION));
		new Label(comp, SWT.NONE).setText(Language.getTextPatternWindow(Language.PATTERN_WINDOW_GROUP_PATTERN));

		this.textPattern = new Text(this.groupPattern, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		data.widthHint = 200;
		this.textPattern.setLayoutData(data);
		this.textPattern.addListener(SWT.Modify, this);
	}

	public void populateData() {}

	@Override
	public void handleEvent(Event arg0) {
		super.handleEvent(arg0);
		Widget src = null;
		try {
			src = arg0.widget;
			if (arg0.type == SWT.Selection) {}
		} catch (Exception e) {
			PatternWindow.logger.fatal(e, e);
		}
	}

	@Override
	protected boolean validateData() {
		boolean result = false;
		try {
			result = true;
		} catch (Exception e) {
			PatternWindow.logger.fatal(e, e);
			result = false;
		}
		return result;
	}

	public Object getResult() {
		return null;
	}
}