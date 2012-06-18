package net.nettape.client.gui.ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Window;
import java.io.File;

import net.nettape.client.gui.Version;

import org.apache.log4j.Logger;


/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 21, 2009
 * @description
 */

public class SplashWindow extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 738569264158741385L;

	public static Logger logger = Logger.getLogger(SplashWindow.class);

	private FontMetrics fontMetrics;
	private Window window;
	private Image image;
	private Image offscreenImg;
	private Graphics offscreenGfx;
	private String version;

	private static String imgLocation = System.getProperty("user.dir")
			+ File.separator + "images" + File.separator + "misc"
			+ File.separator + "splash.jpg";

	public SplashWindow() {

	}

	public void open() {
		try {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.setBackground(Color.white);

			Font font = new Font("Dialog", Font.PLAIN, 15);
			this.setFont(font);
			this.fontMetrics = this.getFontMetrics(font);

			try {
				this.image = this.getToolkit().getImage(imgLocation);
			} catch (Exception exc) {
				logger.fatal(exc, exc);
			}

			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(this.image, 0);

			this.version = "Version: " + Version.APP_VERSION;
			try {
				tracker.waitForAll();
			} catch (Exception e) {
				logger.fatal(e, e);
			}

			this.window = new Window(new Frame());
			Dimension screen = this.getToolkit().getScreenSize();
			Dimension size = new Dimension(
				this.image.getWidth(this),
				this.image.getHeight(this));
			this.window.setSize(size);

			this.window.setLayout(new BorderLayout());
			this.window.add(BorderLayout.CENTER, this);

			this.window.setLocation((screen.width - size.width) / 2,
					(screen.height - size.height) / 2);
			this.window.validate();
			this.window.setVisible(true);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public synchronized void paint(Graphics g) {
		Dimension size = this.getSize();

		if (this.offscreenImg == null) {
			this.offscreenImg = this.createImage(size.width, size.height);
			this.offscreenGfx = this.offscreenImg.getGraphics();
			Font font = new Font("dialog", Font.PLAIN | Font.BOLD, 12);
			this.offscreenGfx.setFont(font);
		}

		this.offscreenGfx.drawImage(this.image, 0, 0, this);

		if (this.version != null) {
			int horizontalCoord = (this.getWidth() - this.fontMetrics.stringWidth(this.version));
			int verticalCoord = this.getHeight() - this.fontMetrics.getHeight();

			((Graphics2D) this.offscreenGfx).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			((Graphics2D) this.offscreenGfx).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			((Graphics2D) this.offscreenGfx).setRenderingHint(RenderingHints.KEY_DITHERING,
					RenderingHints.VALUE_DITHER_ENABLE);
			((Graphics2D) this.offscreenGfx).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
					RenderingHints.VALUE_COLOR_RENDER_QUALITY);

			this.offscreenGfx.setColor(Color.BLACK);
			this.offscreenGfx.drawString(this.version,
					horizontalCoord,
					verticalCoord);
		}
		g.drawImage(this.offscreenImg, 0, 0, this);
	}

	public void close() {
		try {
			this.window.dispose();
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		System.gc();
	}

}