package net.nettape.client.gui.ui.custom;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.util.StringUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 4, 2010
 * @description
 */
public class XButton extends Composite implements Listener {

	private static final Font fontTitle = new Font(Display.getDefault(), "Arial", 8, SWT.BOLD);
	private static final Font fontDetails = new Font(Display.getDefault(), "Arial", 7, SWT.NORMAL);
	private static final Cursor cursorHand = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
	private Label labelImage;
	private Label labelTitle;
	private Label labelDetails;

	private String title;
	private String details;
	private int nrCharsOnLine;

	private String image;

	private boolean showBigButtons;

	/**
	 * @param parent
	 * @param style
	 */
	public XButton(Composite parent, boolean showBigButtons) {
		super(parent, SWT.NONE);
		this.showBigButtons = showBigButtons;
		createGUI();
	}

	private void createGUI() {
		if (this.showBigButtons) {
			super.addListener(SWT.MouseEnter, this);
			super.addListener(SWT.MouseExit, this);
			super.addListener(SWT.MouseUp, this);
		}

		GridLayout lay = new GridLayout(2, false);
		// setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		lay.marginTop = 0;
		lay.marginBottom = 0;
		lay.marginLeft = 0;
		lay.marginRight = 0;
		setLayout(lay);
		GridData data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		data.horizontalSpan = 1;
		if (this.showBigButtons) {
			data.heightHint = 109;
		} else {
			data.heightHint = 82;
		}
		data.widthHint = 334;
		setLayoutData(data);
		setBackgroundMode(SWT.INHERIT_FORCE);

		data = new GridData(SWT.CENTER, SWT.CENTER, false, true);
		data.heightHint = 72;
		data.widthHint = 92;
		this.labelImage = new Label(this, SWT.NONE);
		this.labelImage.setLayoutData(data);
		this.labelImage.setAlignment(SWT.CENTER);
		this.labelImage.addListener(SWT.MouseEnter, this);
		this.labelImage.addListener(SWT.MouseExit, this);

		Composite comp = new Composite(this, SWT.NONE);

		// comp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		lay = new GridLayout(1, false);
		lay.marginWidth = 0;
		lay.marginTop = 0;
		lay.marginBottom = 0;
		lay.marginLeft = 0;
		lay.marginRight = 0;
		comp.setLayout(lay);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		comp.setLayoutData(gd);
		this.labelTitle = new Label(comp, SWT.NONE);
		// this.labelTitle.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.labelTitle.setLayoutData(gd);
		this.labelTitle.setFont(XButton.fontTitle);
		this.labelTitle.addListener(SWT.MouseEnter, this);
		this.labelTitle.addListener(SWT.MouseExit, this);

		this.labelDetails = new Label(comp, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.labelDetails.setLayoutData(gd);
		this.labelDetails.setFont(XButton.fontDetails);
		this.labelDetails.addListener(SWT.MouseEnter, this);
		this.labelDetails.addListener(SWT.MouseExit, this);
	}

	@Override
	public void addListener(int eventType, Listener listener) {
		super.addListener(eventType, listener);
		this.labelImage.addListener(eventType, listener);
		this.labelTitle.addListener(eventType, listener);
		this.labelDetails.addListener(eventType, listener);
	}

	@Override
	public boolean equals(Object arg0) {
		return (this == arg0) || (this.labelImage == arg0) || (this.labelTitle == arg0)
				|| (this.labelDetails == arg0);
	}

	public void handleEvent(Event event) {
		switch (event.type) {
			case SWT.MouseEnter:
				setCursor(XButton.cursorHand);
				this.labelImage.setImage(AppImages.getImage64Focus(this.image));
				if (this.showBigButtons) {
					setBackgroundImage(AppImages.getImageMisc(AppImages.IMG_MISC_ROUND_RECTANGLE_BIG));
				} else {
					this.labelImage.setBackgroundImage(AppImages.getImageMisc(AppImages.IMG_MISC_ROUND_RECTANGLE_SMALL));
				}
				break;
			case SWT.MouseExit:
				setCursor(null);
				this.labelImage.setImage(AppImages.getImage64(this.image));
				if (this.showBigButtons) {
					setBackgroundImage(null);
				} else {
					this.labelImage.setBackgroundImage(null);
				}
				setBackgroundImage(null);
				break;
		}
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
		this.labelImage.setImage(AppImages.getImage64(this.image));
	}

	public boolean isShowBigButtons() {
		return this.showBigButtons;
	}

	public void setShowBigButtons(boolean showBigButtons) {
		this.showBigButtons = showBigButtons;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.labelTitle.setText(this.title);
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
		this.labelDetails.setText(StringUtil.wrapText(this.details, this.nrCharsOnLine));
	}

	public int getNrCharsOnLine() {
		return this.nrCharsOnLine;
	}

	public void setNrCharsOnLine(int nrCharsOnLine) {
		this.nrCharsOnLine = nrCharsOnLine;
	}
}
