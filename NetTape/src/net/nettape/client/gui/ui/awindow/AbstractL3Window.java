/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 3, 2010
 * @description
 */
package net.nettape.client.gui.ui.awindow;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.ui.util.ColorUtil;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.client.gui.util.FontUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 3, 2010
 * @description
 */
public abstract class AbstractL3Window extends AbstractL2Window {

	/**
	 * 
	 */
	public AbstractL3Window() {
		super(SWT.NONE);
	}

	/**
	 * @param style
	 */
	public AbstractL3Window(int style) {
		this(style, "", 0, 0);
	}

	/**
	 * @param title
	 */
	public AbstractL3Window(String title) {
		this(SWT.NONE, title, 0, 0);
	}

	/**
	 * @param style
	 * @param title
	 * @param width
	 * @param height
	 */
	public AbstractL3Window(int style, String title, int width, int height) {
		this(style, title, "", "", width, height);
	}

	/**
	 * @param style
	 * @param title
	 * @param explain
	 * @param width
	 * @param height
	 */
	public AbstractL3Window(int style,
							String title,
							String details,
							String detailsImg,
							int width,
							int height) {
		super(style, title, width, height);
		createCompositeDetails(title, details, detailsImg);
	}

	private void createCompositeDetails(String title, String details, String detailsImg) {
		GridData data;

		((GridLayout) this.shell.getLayout()).marginWidth = 0;
		((GridLayout) this.shell.getLayout()).marginHeight = 0;
		((GridLayout) this.shell.getLayout()).verticalSpacing = 0;
		// shell.setBackground(ColorUtil.COLOR_BLACK);

		Label separator = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.END, Boolean.TRUE, Boolean.FALSE);
		data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
		separator.setLayoutData(data);
		separator.moveAbove(this.container);

		Composite bigUpperComp = new Composite(this.shell, SWT.NONE);
		GridLayout lay = new GridLayout(2, Boolean.FALSE);
		lay.marginWidth = 0;
		lay.marginLeft = 8;
		lay.marginRight = 5;
		lay.marginTop = 0;
		lay.marginBottom = 0;
		bigUpperComp.setLayout(lay);
		data = new GridData(SWT.FILL, SWT.TOP, Boolean.TRUE, Boolean.TRUE);
		data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
		bigUpperComp.setLayoutData(data);
		// bigUpperComp.setBackground(ColorUtil.COLOR_WHITE);
		bigUpperComp.setBackgroundMode(SWT.INHERIT_FORCE);
		WidgetUtils.renderXEfect(bigUpperComp, ColorUtil.COLOR_WHITE, ColorUtil.COLOR_LIGHT_BLUE);
		// bigUpperComp.setBackgroundImage(AppImages.getImageMiscByName(AppImages.IMG_MISC_HTML_FRUNZE5));
		bigUpperComp.moveAbove(this.container);

		Label labelBigText = new Label(bigUpperComp, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.TOP, Boolean.TRUE, Boolean.TRUE);
		labelBigText.setLayoutData(data);
		labelBigText.setFont(FontUtil.ARIAL_NORMAL_18);
		labelBigText.setAlignment(SWT.LEFT);
		labelBigText.setText(title);

		Label labelBigImage = new Label(bigUpperComp, SWT.NONE);
		data = new GridData(SWT.END, SWT.CENTER, Boolean.FALSE, Boolean.FALSE);
		data.verticalSpan = 2;
		labelBigImage.setLayoutData(data);
		if(!detailsImg.equals(""))
		{
			labelBigImage.setImage(AppImages.getImage64(detailsImg));
		}
		StyledText textDetails = new StyledText(bigUpperComp, SWT.WRAP);
		textDetails.setEditable(Boolean.FALSE);
		textDetails.setAlignment(SWT.FILL);
		data = new GridData(SWT.FILL, SWT.TOP, Boolean.TRUE, Boolean.TRUE);
		textDetails.setLayoutData(data);
		textDetails.setEnabled(Boolean.FALSE);
		textDetails.setFont(FontUtil.ARIAL_NORMAL_8);
		textDetails.setForeground(ColorUtil.COLOR_BLACK);
		textDetails.setText(details);
		textDetails.setMargins(3, 0, 0, 0);

		separator = new Label(this.shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.END, Boolean.TRUE, Boolean.FALSE);
		data.horizontalSpan = ((GridLayout) this.shell.getLayout()).numColumns;
		separator.setLayoutData(data);
		separator.moveAbove(this.container);
	}

}
