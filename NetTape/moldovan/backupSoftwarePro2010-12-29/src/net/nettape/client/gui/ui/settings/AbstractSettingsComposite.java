/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 1, 2010
 * @description
 */
package net.nettape.client.gui.ui.settings;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.util.FontUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 1, 2010
 * @description
 */
public abstract class AbstractSettingsComposite extends Composite implements Listener {

	/**
	 * @param parent
	 * @param style
	 */
	public AbstractSettingsComposite(Composite parent, String title) {
		super(parent, SWT.NONE);
		this.setLayout(new GridLayout(1, Boolean.FALSE));
		GridData data = new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE);

		this.setLayoutData(data);

		Composite comp = new Composite(this, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		lay.marginWidth = lay.marginHeight = 0;
		comp.setLayout(lay);

		Button buttonRemove = new Button(comp, SWT.PUSH);
		buttonRemove.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
		buttonRemove.setVisible(false);

		Label labelBigText = new Label(comp, SWT.NONE);
		data = new GridData(SWT.LEFT, SWT.CENTER, Boolean.TRUE, Boolean.FALSE);
		// data.heightHint = 30;
		labelBigText.setLayoutData(data);
		labelBigText.setFont(FontUtil.ARIAL_NORMAL_10);
		labelBigText.setText(title);

		Label separator = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.TOP, Boolean.TRUE, Boolean.FALSE);
		data.horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		separator.setLayoutData(data);
	}

	protected abstract void createGUI();

	protected abstract void populateData();

	protected abstract boolean validate();

	protected abstract boolean save();

	public boolean saveData() {
		if (validate()) {
			return save();
		}
		return Boolean.FALSE;
	}

}
