/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 2, 2010
 * @description
 */
package net.nettape.client.gui.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorUtil {

	private ColorUtil() {}

	public static final Color COLOR_WHITE = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);

	public static final Color COLOR_BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);

	public static final Color COLOR_LIGHT_BLUE = new Color(Display.getDefault(), new RGB(
		213,
		230,
		248));

	public static final Color COLOR_DARK_BLUE = new Color(
		Display.getDefault(),
		new RGB(10, 36, 106));
}
