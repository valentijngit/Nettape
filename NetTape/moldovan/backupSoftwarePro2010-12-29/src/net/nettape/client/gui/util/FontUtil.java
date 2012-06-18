/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 2, 2010
 * @description
 */
package net.nettape.client.gui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

public class FontUtil {

	private FontUtil() {
	}

	public final static Font TAHOMA_BOLD = new Font(Display.getDefault(),
			"Tahoma", 8, SWT.BOLD);

	public final static Font ARIAL_NORMAL_10 = new Font(Display.getDefault(),
			"Arial", 10, SWT.NORMAL);

	public final static Font ARIAL_NORMAL_8 = new Font(Display.getDefault(),
			"Arial", 8, SWT.NORMAL);
	public final static Font ARIAL_NORMAL_18 = new Font(Display.getDefault(),
			"Arial", 18, SWT.NORMAL);

}
