/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 29, 2010
 * @description
 */
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
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 29, 2010
 * @description
 */
public class RetencionPolicyWindow extends AbstractL2Window {

	private static final Logger logger = Logger.getLogger(RetencionPolicyWindow.class);

	private Group groupType;

	private Text textName;

	private Combo comboNumber;

	private Combo combo1;

	public RetencionPolicyWindow() {
		super(SWT.SYSTEM_MODAL,
			Language.getTextRetencionPolicyWindow(Language.RETENCION_POLICY_WINDOW_TITLE),
			400,
			200);
		try {
			this.setImage(AppImages.getImage16(AppImages.IMG_RETENCION_POLICY));
			this.createGUI();
			populateData();
		} catch (Exception ex) {
			RetencionPolicyWindow.logger.fatal(ex, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.IWindow#populateData()
	 */
	@Override
	public void populateData() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.IWindow#getResult()
	 */
	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.AbstractL2Window#createGUI()
	 */
	@Override
	public void createGUI() {
		Composite comp = new Composite(this.container, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.container.getLayout()).numColumns;
		comp.setLayoutData(data);
		GridLayout lay = new GridLayout(2, false);
		lay.marginWidth = 0;
		lay.marginLeft = lay.marginRight = 0;
		lay.marginTop = lay.marginBottom = 0;
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setText(Language.getTextRetencionPolicyWindow(Language.RETENCION_POLICY_WINDOW_TEXT_NAME));
		this.textName = new Text(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.textName.setLayoutData(data);

		this.groupType = new Group(this.container, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		this.groupType.setLayout(new GridLayout(4, false));
		this.groupType.setLayoutData(data);

		comp = new Composite(this.groupType, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.groupType.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setImage(AppImages.getImage16(AppImages.IMG_TYPE));
		new Label(comp, SWT.NONE).setText(Language.getTextRetencionPolicyWindow(Language.RETENCION_POLICY_WINDOW_GROUP_TYPE));

		new Label(this.groupType, SWT.NONE).setText("");
		this.combo1 = new Combo(this.groupType, SWT.BORDER);
		data = new GridData(SWT.RIGHT, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		this.combo1.setLayoutData(data);

		Label label = new Label(this.groupType, SWT.NONE);
		label.setText(Language.getTextRetencionPolicyWindow(Language.RETENCION_POLICY_WINDOW_LABEL_TYPE_EXPLICATION));
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		comp = new Composite(this.container, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = ((GridLayout) this.container.getLayout()).numColumns;
		comp.setLayoutData(data);
		lay = new GridLayout(2, false);
		lay.marginWidth = 0;
		lay.marginLeft = lay.marginRight = 0;
		lay.marginTop = lay.marginBottom = 0;
		comp.setLayout(lay);

		new Label(comp, SWT.NONE).setText(Language.getTextRetencionPolicyWindow(Language.RETENCION_POLICY_WINDOW_COMBO_NUMBER_SNAPSHOTS));
		this.comboNumber = new Combo(comp, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalSpan = 1;
		this.comboNumber.setLayoutData(data);
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.AbstractL2Window#buttonOKAction()
	 */
	@Override
	protected boolean buttonOKAction() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.nettape.client.gui.ui.awindow.AbstractL2Window#validateData()
	 */
	@Override
	protected boolean validateData() {
		// TODO Auto-generated method stub
		return false;
	}

}
