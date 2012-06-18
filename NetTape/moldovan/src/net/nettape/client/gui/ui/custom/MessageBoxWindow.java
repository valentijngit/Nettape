package net.nettape.client.gui.ui.custom;

import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.awindow.AbstractL1Window;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.StringUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Jan 14, 2010
 * @description custom message box
 */

public class MessageBoxWindow extends AbstractL1Window {

	private static final Logger logger = Logger.getLogger(MessageBoxWindow.class);

	private Composite compButtons;
	private Button button1, button2;
	private Label labelImg;
	private int exitChoiceAction = SWT.NO;
	boolean isInfo, isError, isQuestion, isWarning;
	private int windowStyle;
	private String title;
	private String message;

	/**
	 * @param style
	 *            one of this option: SWT.ICON_INFORMATION, SWT.ICON_ERROR, SWT.ICON_QUESTION,
	 *            SWT.ICON_WARNING
	 * @param title
	 *            window title
	 * @param message
	 *            window message
	 */
	private MessageBoxWindow(int style, String title, String message) {
		super(SWT.APPLICATION_MODAL);
		try {
			this.windowStyle = style;
			this.isInfo = this.windowStyle == SWT.ICON_INFORMATION;
			this.isError = this.windowStyle == SWT.ICON_ERROR;
			this.isQuestion = this.windowStyle == SWT.ICON_QUESTION;
			this.isWarning = this.windowStyle == SWT.ICON_WARNING;
			this.message = message;
			this.title = title;
			this.shell.setText(this.title);
			this.createGUI();
			this.setImages();
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	public static int information(String message) {
		return showMessage(SWT.ICON_INFORMATION,
				Language.getText(Language.KEY_GENERAL,
						Language.GENERAL_TEXT_MESSAGE_TITLE_INFO),
				message);
	}

	public static int error(String message) {
		return showMessage(SWT.ICON_ERROR,
				Language.getText(Language.KEY_GENERAL,
						Language.GENERAL_TEXT_MESSAGE_TITLE_ERROR),
				message);
	}

	public static int warning(String message) {
		return showMessage(SWT.ICON_WARNING,
				Language.getText(Language.KEY_GENERAL,
						Language.GENERAL_TEXT_MESSAGE_TITLE_WARNING),
				message);
	}

	public static int question(String message) {
		return showMessage(SWT.ICON_QUESTION,
				Language.getText(Language.KEY_GENERAL,
						Language.GENERAL_TEXT_MESSAGE_TITLE_QUESTION),
				message);
	}

	private static int showMessage(int style, String title, String message) {
		MessageBoxWindow msg = new MessageBoxWindow(style, title, message);
		msg.open();
		return msg.getExitChoiceAction();
	}

	private void setImages() {
		try {
			if (this.isInfo) {
				this.labelImg.setImage(AppImages.getImage32(AppImages.IMG_INFORMATION));
				this.shell.setImage(AppImages.getImage16(AppImages.IMG_INFORMATION));
			} else if (this.isQuestion) {
				this.labelImg.setImage(AppImages.getImage32(AppImages.IMG_QUESTION));
				this.shell.setImage(AppImages.getImage16(AppImages.IMG_QUESTION));
			} else if (this.isWarning) {
				this.labelImg.setImage(AppImages.getImage32(AppImages.IMG_WARNING));
				this.shell.setImage(AppImages.getImage16(AppImages.IMG_WARNING));
			} else if (this.isError) {
				this.labelImg.setImage(AppImages.getImage32(AppImages.IMG_ERROR));
				this.shell.setImage(AppImages.getImage16(AppImages.IMG_ERROR));
			}
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	public void handleEvent(Event e) {
		try {
			if (e.type == SWT.Selection) {
				if (e.widget instanceof Button) {
					this.close(((Integer) e.widget.getData()).intValue());
				}
			}
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	private void close(int state) {
		this.exitChoiceAction = state;
		this.shell.close();
	}

	/**
	 * @returns one of the : SWT.OK, SWT.YES, SWT.NO
	 */
	public int getExitChoiceAction() {
		return this.exitChoiceAction;
	}

	public void createGUI() {
		int numColsCompButtons = 0;
		int blockSize = 0;
		Label msg;
		try {
			if (this.isInfo || this.isError || this.isWarning) {
				blockSize = 1;
			} else if (this.isQuestion) {
				blockSize = 2;
			} else {
				this.shell.dispose();
				return;
			}
			String[] str = StringUtil.splitStrByDelimAndLength(this.message,
					" ",
					blockSize == 1 ? blockSize * 50 : blockSize * 30);
			int nr_labels = 0;
			if (str == null) {
				nr_labels = 1;
			} else {
				nr_labels = str.length;
			}

			this.labelImg = new Label(this.shell, SWT.CENTER);
			GridData gData = new GridData(SWT.CENTER, SWT.TOP, false, false);
			gData.widthHint = 60;
			gData.heightHint = 60;
			gData.verticalSpan = nr_labels + 2;
			this.labelImg.setLayoutData(gData);

			new Label(this.shell, SWT.NONE);
			GridData data = null;

			for (int i = 0; i < nr_labels; i++) {
				msg = new Label(this.shell, SWT.NONE);
				if (nr_labels == 1) {
					msg.setAlignment(SWT.CENTER);
				} else {
					msg.setAlignment(SWT.LEFT_TO_RIGHT);
				}
				msg.setText(str == null ? this.message : str[i]);
				data = new GridData(GridData.FILL_HORIZONTAL);
				data.widthHint = msg.getText().length() * 5 + 20;
				msg.setLayoutData(data);
			}
			msg = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			data = new GridData(GridData.FILL_HORIZONTAL);
			msg.setLayoutData(data);

			if (this.isInfo || this.isError || this.isWarning) {
				numColsCompButtons = 1;
			} else if (this.isQuestion) {
				numColsCompButtons = 2;
			}
			if (numColsCompButtons > 0) {
				this.compButtons = new Composite(this.shell, SWT.NONE);
				this.compButtons.setLayout(new GridLayout(
					numColsCompButtons,
					Boolean.FALSE));
				data = new GridData(SWT.END, SWT.CENTER, true, true);
				data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
				data.minimumWidth = (AppConstants.BUTTON_WIDTH * numColsCompButtons) + 2;
				this.compButtons.setLayoutData(data);

				this.button1 = new Button(this.compButtons, SWT.PUSH);

				this.button1.setImage(AppImages.getImage16(AppImages.IMG_ACCEPT));
				data = new GridData(
					SWT.END,
					SWT.NONE,
					Boolean.FALSE,
					Boolean.FALSE);
				data.widthHint = AppConstants.BUTTON_WIDTH;
				this.button1.setLayoutData(data);
				this.button1.addListener(SWT.Selection, this);
				WidgetUtils.addImageChangeListener(this.button1,
						AppImages.getImage16(AppImages.IMG_ACCEPT),
						AppImages.getImage16Focus(AppImages.IMG_ACCEPT));
				if (this.isInfo || this.isError || this.isWarning) {
					this.button1.setText(Language.getText(Language.KEY_GENERAL,
							Language.GENERAL_TEXT_BUTTON_OK));
					this.button1.setData(new Integer(SWT.OK));

				} else if (this.isQuestion) {
					this.button1.setText(Language.getText(Language.KEY_GENERAL,
							Language.GENERAL_TEXT_BUTTON_YES));
					this.button1.setData(new Integer(SWT.YES));

					this.button2 = new Button(this.compButtons, SWT.PUSH);
					this.button2.setText(Language.getText(Language.KEY_GENERAL,
							Language.GENERAL_TEXT_BUTTON_NO));
					this.button2.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
					data = new GridData(
						SWT.END,
						SWT.NONE,
						Boolean.FALSE,
						Boolean.FALSE);
					data.widthHint = AppConstants.BUTTON_WIDTH;
					this.button2.setLayoutData(data);
					this.button2.addListener(SWT.Selection, this);
					this.button2.setData(new Integer(SWT.NO));
					WidgetUtils.addImageChangeListener(this.button2,
							AppImages.getImage16(AppImages.IMG_REMOVE),
							AppImages.getImage16Focus(AppImages.IMG_REMOVE));
				}
			}
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	public void populateData() {}

	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}