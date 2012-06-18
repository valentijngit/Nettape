package net.nettape.client.gui.ui.awindow;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 22, 2009
 * @description
 */

public abstract class AbstractL2Window extends AbstractL1Window {

	private static final Logger logger = Logger.getLogger(AbstractL2Window.class);

	protected Button buttonOK;
	protected Button buttonCancel;

	protected Composite container;
	protected Composite container2;

	protected int exitChoiceAction;

	protected Composite compositeExtraButtons;

	public AbstractL2Window() {
		this(SWT.NONE);
	}

	public AbstractL2Window(int style) {
		this(style, "", 0, 0);
	}

	public AbstractL2Window(String title) {
		this(SWT.NONE, title, 0, 0);
	}
	public AbstractL2Window(int style, String title, int width, int height) {
		this(style, title, "", width, height);
	}

	public AbstractL2Window(int style, String title, String explain, int width, int height) {
		super(null, style, title, width, height);
		try {
			Canvas canvas = new Canvas(shell, SWT.NONE);
			canvas.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
			data.heightHint = 0;
			canvas.setLayoutData(data);
			
			Label label_2 = new Label(canvas, SWT.NONE);
			label_2.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			label_2.setFont(new Font(Display.getCurrent(),"Arial",18,SWT.NORMAL));
			label_2.setBounds(10, 7, 450, 29);
			label_2.setText(title);
			
			Label label_1 = new Label(canvas, SWT.NONE);
			label_1.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			label_1.setBounds(13, 35, 450, 13);
			label_1.setText(explain);

			this.container = new Composite(this.shell, SWT.NONE);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
			this.container.setLayoutData(data);
			GridLayout lay = new GridLayout(1, false);
			lay.marginTop = 5;
			lay.marginWidth = 10;
			lay.marginHeight = 0;
			lay.marginBottom = 5;
			lay.verticalSpacing = 5;
			lay.horizontalSpacing = 5;

			this.container.setLayout(lay);
			
			
			this.container2 = new Composite(this.shell, SWT.NONE);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
			this.container2.setLayoutData(data);
			lay = new GridLayout(1, false);
			lay.marginTop = 5;
			lay.marginWidth = 0;
			lay.marginHeight = 0;
			lay.marginBottom = 5;
			lay.verticalSpacing = 5;
			lay.horizontalSpacing = 5;

			this.container2.setLayout(lay);

			Label label = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
			label.setLayoutData(data);
			this.createCompositeExtraButtons();
			this.createCompositeSave();
			this.shell.setDefaultButton(this.buttonOK);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	private void createCompositeSave() {
		Composite compSave = new Composite(this.shell, SWT.NONE);
		GridLayout lay = new GridLayout(3, false);
		lay.marginBottom = 10;
		lay.marginTop = 10;
		compSave.setLayout(lay);
		GridData data = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		data.horizontalSpan = 1;

		compSave.setLayoutData(data);

		this.buttonOK = new Button(compSave, SWT.PUSH);
		this.buttonOK.setText(Language.getText(Language.KEY_GENERAL,
				Language.GENERAL_TEXT_BUTTON_OK));
		this.buttonOK.setImage(AppImages.getImage16(AppImages.IMG_ACCEPT));
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonOK.setLayoutData(data);
		this.buttonOK.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonOK,
				AppImages.getImage16(AppImages.IMG_ACCEPT),
				AppImages.getImage16Focus(AppImages.IMG_ACCEPT));

		this.buttonCancel = new Button(compSave, SWT.PUSH);
		this.buttonCancel.setText(Language.getText(Language.KEY_GENERAL,
				Language.GENERAL_TEXT_BUTTON_CANCEL));
		this.buttonCancel.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = AppConstants.BUTTON_WIDTH;
		this.buttonCancel.setLayoutData(data);
		this.buttonCancel.addListener(SWT.Selection, this);
		WidgetUtils.addImageChangeListener(this.buttonCancel,
				AppImages.getImage16(AppImages.IMG_REMOVE),
				AppImages.getImage16Focus(AppImages.IMG_REMOVE));
	}

	private void createCompositeExtraButtons() {
		this.compositeExtraButtons = new Composite(this.shell, SWT.NONE);

		GridData data = new GridData(SWT.LEFT, SWT.NONE, true, true);
		data.horizontalSpan = 1;
		this.compositeExtraButtons.setLayoutData(data);

		new Label(this.compositeExtraButtons, SWT.NONE);
		GridLayout lay = new GridLayout(2, false);
		this.compositeExtraButtons.setLayout(lay);
	}

	abstract public void createGUI();

	abstract protected boolean buttonOKAction();

	abstract protected boolean validateData();

	public void handleEvent(Event event) {
		Widget src = null;
		try {
			src = event.widget;
			if (event.type == SWT.Selection) {
				if (src == this.buttonOK) {
					if (this.validateData()) {
						if (this.buttonOKAction()) {
							this.close(true);
						}
					}
				} else if (src == this.buttonCancel) {
					this.close(false);
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void close(boolean flag) {
		this.exitChoiceAction = (flag ? SWT.OK : SWT.CLOSE);
		this.shell.close();
	}

	/**
	 * @return SWT.OK or SWT.CLOSE
	 */
	public int getExitChoiceAction() {
		return this.exitChoiceAction;
	}

}
