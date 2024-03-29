package net.nettape.client.gui.admin;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 12, 2009
 * @description
 */

public class AppImages {

	public static Logger logger = Logger.getLogger(AppImages.class);

	private static AppImages instance = null;

	private static final String APP_ROOT = System.getProperty("user.dir");
	private static final String IMAGES_DIR = "images";

	private static final String IMAGES_DIR_SKIN_NORMAL = "normal";
	private static final String IMAGES_DIR_SKIN_FOCUS = "focus";

	private static final String IMAGES_DIR_COMPLETE_NORMAL = APP_ROOT
			+ File.separator + IMAGES_DIR + File.separator
			+ IMAGES_DIR_SKIN_NORMAL + File.separator;
	private static final String IMAGES_DIR_COMPLETE_FOCUS = APP_ROOT
			+ File.separator + IMAGES_DIR + File.separator
			+ IMAGES_DIR_SKIN_FOCUS + File.separator;

	private static Hashtable<Program, Image> hashImageCacheProgram = null;

	private static Hashtable<String, Image> hashImagesNormal16;
	private static Hashtable<String, Image> hashImagesNormal24;
	private static Hashtable<String, Image> hashImagesNormal32;
	private static Hashtable<String, Image> hashImagesNormal64;
	private static Hashtable<String, Image> hashImagesNormal128;

	private static Hashtable<String, Image> hashImagesFocus16;
	private static Hashtable<String, Image> hashImagesFocus24;
	private static Hashtable<String, Image> hashImagesFocus32;
	private static Hashtable<String, Image> hashImagesFocus64;
	private static Hashtable<String, Image> hashImagesFocus128;

	private static Hashtable<String, Image> hashImagesMisc;

	public static final String IMG_SCHEDULE = "schedule.png";
	public static final String IMG_HELP = "help.png";
	public static final String IMG_BACKUP_SET = "backup_set.png";
	public static final String IMG_USER_ACCOUNT = "user_account.png";
	public static final String IMG_REPORTS = "reports.png";
	public static final String IMG_SETTINGS = "settings.png";

	public static final String IMG_REPEAT = "repeat.png";
	public static final String IMG_NEXT = "next.png";

	public static final String IMG_ACCEPT = "accept.png";
	public static final String IMG_ADD = "add.png";
	public static final String IMG_EDIT = "edit.png";
	public static final String IMG_USER = "user.png";
	public static final String IMG_REFRESH = "refresh.png";
	public static final String IMG_REMOVE = "remove.png";
	public static final String IMG_CALENDAR_DATE = "calendar_date.png";
	public static final String IMG_MAIL = "mail.png";
	public static final String IMG_FOLDER = "folder.png";
	public static final String IMG_CLOCK = "clock.png";
	public static final String IMG_DATABASE_PROCESS = "database_process.png";
	public static final String IMG_DATABASE_SEARCH = "database_search.png";
	public static final String IMG_DATABASE_ADD = "database_add.png";
	public static final String IMG_SEARCH_ADD = "search_add.png";
	public static final String IMG_LOCK = "lock.png";
	public static final String IMG_FOLDER_FULL_ADD = "folder_full_add.png";
	public static final String IMG_FOLDER_FULL = "folder_full.png";
	public static final String IMG_FOLDER_REMOVE = "folder_remove.png";
	public static final String IMG_FOLDER_PROCESS = "folder_process.png";
	public static final String IMG_FOLDER_PREVIOUS = "folder_previous.png";
	public static final String IMG_FOLDER_DOWN = "folder_down.png";
	public static final String IMG_MOVIE_TRACK = "movie_track.png";
	public static final String IMG_SOUND = "sound.png";
	public static final String IMG_WINDOW = "window.png";
	public static final String IMG_NOTE_EDIT = "note_edit.png";
	public static final String IMG_SEARCH = "search.png";
	public static final String IMG_STOP = "stop.png";
	public static final String IMG_APPLICATION = "application.png";
	public static final String IMG_CD = "cd.png";
	public static final String IMG_HDD = "hdd.png";

	public static final String IMG_INFORMATION = "info.png";
	public static final String IMG_QUESTION = "question.png";
	public static final String IMG_WARNING = "warning.png";
	public static final String IMG_ERROR = "error.png";

	public static final String IMG_MISC_BANNER = "banner.png";
	public static final String IMG_MISC_MAIN = "main.png";
	public static final String IMG_MISC_APPLICATION = "application.png";
	public static final String IMG_MISC_LEFT_SQUARE = "leftSquare.png";
	public static final String IMG_MISC_RIGHT_SQUARE = "rightSquare.png";

	public static final String IMAGES_16X16 = "16x16" + File.separator;
	public static final String IMAGES_24X24 = "24x24" + File.separator;
	public static final String IMAGES_32X32 = "32x32" + File.separator;
	public static final String IMAGES_64X64 = "64x64" + File.separator;
	public static final String IMAGES_128X128 = "128x128" + File.separator;

	private static Image imageNotFound16Normal = null;
	private static Image imageNotFound16Focus = null;
	private static Image imageNotFound24Normal = null;
	private static Image imageNotFound24Focus = null;
	private static Image imageNotFound32Normal = null;
	private static Image imageNotFound32Focus = null;
	private static Image imageNotFound64Normal = null;
	private static Image imageNotFound64Focus = null;
	private static Image imageNotFound128Normal = null;
	private static Image imageNotFound128Focus = null;

	private AppImages() {
		try {
			hashImageCacheProgram = new Hashtable<Program, Image>();
			hashImagesNormal16 = new Hashtable<String, Image>();
			hashImagesNormal24 = new Hashtable<String, Image>();
			hashImagesNormal32 = new Hashtable<String, Image>();
			hashImagesNormal64 = new Hashtable<String, Image>();
			hashImagesNormal128 = new Hashtable<String, Image>();

			hashImagesFocus16 = new Hashtable<String, Image>();
			hashImagesFocus24 = new Hashtable<String, Image>();
			hashImagesFocus32 = new Hashtable<String, Image>();
			hashImagesFocus64 = new Hashtable<String, Image>();
			hashImagesFocus128 = new Hashtable<String, Image>();

			hashImagesMisc = new Hashtable<String, Image>();

			imageNotFound16Normal = getImageNotFound(16,
					16,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			imageNotFound16Focus = getImageNotFound(16,
					16,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			imageNotFound24Normal = getImageNotFound(24,
					24,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			imageNotFound24Focus = getImageNotFound(24,
					24,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			imageNotFound32Normal = getImageNotFound(32,
					32,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			imageNotFound32Focus = getImageNotFound(32,
					32,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			imageNotFound64Normal = getImageNotFound(64,
					64,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			imageNotFound64Focus = getImageNotFound(64,
					64,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			imageNotFound128Normal = getImageNotFound(128,
					128,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			imageNotFound128Focus = getImageNotFound(128,
					128,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
	}

	public static AppImages getInstance() {
		try {
			if (instance == null)
				instance = new AppImages();
			return instance;
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			return null;
		}
	}

	public final static Image resizeImage(Image image, int width, int height) {
		if (image.getImageData().width == width
				&& image.getImageData().height == height)
			return image;
		Image scaled = new Image(Display.getDefault(), width, height);
		Image imgM = new Image(
			Display.getDefault(),
			image.getImageData().getTransparencyMask());
		Image scaledM = new Image(Display.getDefault(), width, height);
		GC gc1 = new GC(scaledM);
		gc1.setAntialias(SWT.ON);
		gc1.setInterpolation(SWT.HIGH);
		gc1.drawImage(imgM,
				0,
				0,
				imgM.getBounds().width,
				imgM.getBounds().height,
				0,
				0,
				width,
				height);
		gc1.dispose();
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image,
				0,
				0,
				image.getBounds().width,
				image.getBounds().height,
				0,
				0,
				width,
				height);
		gc.dispose();
		return new Image(
			Display.getDefault(),
			scaled.getImageData(),
			scaledM.getImageData());
	}

	public final static Image getImage(Image srcImage, int flag) {
		Image img = null;
		try {
			img = new Image(Display.getDefault(), srcImage, flag);
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			img = null;
		}
		return img;
	}

	public static Image loadImage(String fileName) {
		FileInputStream is = null;
		Image img = null;
		try {
			File file = new File(fileName);
			is = new FileInputStream(file);
			img = new Image(Display.getDefault(), is);
			is.close();
		} catch (Exception ex) {
			logger.fatal(ex);
			img = null;
		}
		return img;
	}

	public static Image getImageBySizeNormal(String imageKey, int size) {
		switch (size) {
			case 16:
				return hashImagesNormal16.get(imageKey) != null	? hashImagesNormal16.get(imageKey)
																: imageNotFound16Normal;
			case 32:
				return hashImagesNormal32.get(imageKey) != null	? hashImagesNormal32.get(imageKey)
																: imageNotFound32Normal;
			case 64:
				return hashImagesNormal64.get(imageKey) != null	? hashImagesNormal64.get(imageKey)
																: imageNotFound64Normal;
			case 128:
				return hashImagesNormal128.get(imageKey) != null ? hashImagesNormal128.get(imageKey)
																: imageNotFound128Normal;
			default:
				return hashImagesNormal16.get(imageKey) != null	? hashImagesNormal16.get(imageKey)
																: imageNotFound16Normal;
		}
	}

	public static Image getImage(String imageKey, int flag, int size) {
		return getImage(getImageBySizeNormal(imageKey, size), flag);
	}

	public static Image getImageDisabled(String imageKey, int size) {
		return getImage(getImageBySizeNormal(imageKey, size), SWT.IMAGE_DISABLE);
	}

	public static Image getImageGray(String imageKey, int size) {
		return getImage(getImageBySizeNormal(imageKey, size), SWT.IMAGE_GRAY);
	}

	public static Image getImageMisc(String imageKey) {
		if (hashImagesMisc.get(imageKey)== null)
			hashImagesMisc.put(imageKey, loadImage(APP_ROOT + File.separator + IMAGES_DIR
					+ File.separator + "misc" + File.separator+imageKey));
		return hashImagesMisc.get(imageKey) != null	? hashImagesMisc.get(imageKey)
													: imageNotFound16Normal;
	}

	public static Image getImage16(String imageKey) {
		if (hashImagesNormal16.get(imageKey)== null)
			hashImagesNormal16.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_NORMAL + IMAGES_16X16+imageKey));
		return hashImagesNormal16.get(imageKey) != null	? hashImagesNormal16.get(imageKey)
														: imageNotFound16Normal;
	}

	public static Image getImage16Focus(String imageKey) {
		if (hashImagesFocus16.get(imageKey)== null)
			hashImagesFocus16.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_FOCUS + IMAGES_16X16+imageKey));
		return hashImagesFocus16.get(imageKey) != null	? hashImagesFocus16.get(imageKey)
														: imageNotFound16Focus;
	}

	public static Image getImage24(String imageKey) {
		if (hashImagesNormal24.get(imageKey)== null)
			hashImagesNormal24.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_NORMAL + IMAGES_24X24+imageKey));		
		return hashImagesNormal24.get(imageKey) != null	? hashImagesNormal24.get(imageKey)
														: imageNotFound24Normal;
	}

	public static Image getImage24Focus(String imageKey) {
		if (hashImagesFocus24.get(imageKey)== null)
			hashImagesFocus24.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_FOCUS + IMAGES_24X24+imageKey));
		return hashImagesFocus24.get(imageKey) != null	? hashImagesFocus24.get(imageKey)
														: imageNotFound24Focus;
	}

	public static Image getImage32(String imageKey) {
		if (hashImagesNormal32.get(imageKey)== null)
			hashImagesNormal32.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_NORMAL + IMAGES_32X32+imageKey));		
		return hashImagesNormal32.get(imageKey) != null	? hashImagesNormal32.get(imageKey)
														: imageNotFound32Normal;
	}

	public static Image getImage32Focus(String imageKey) {
		if (hashImagesFocus32.get(imageKey)== null)
			hashImagesFocus32.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_FOCUS + IMAGES_32X32+imageKey));
		return hashImagesFocus32.get(imageKey) != null	? hashImagesFocus32.get(imageKey)
														: imageNotFound32Focus;
	}

	public static Image getImage64(String imageKey) {
		if (hashImagesNormal64.get(imageKey)== null)
			hashImagesNormal64.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_NORMAL + IMAGES_64X64+imageKey));
		return hashImagesNormal64.get(imageKey) != null	? hashImagesNormal64.get(imageKey)
														: imageNotFound64Normal;
	}

	public static Image getImage64Focus(String imageKey) {
		if (hashImagesFocus64.get(imageKey)== null)
			hashImagesFocus64.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_FOCUS + IMAGES_64X64+imageKey));
		return hashImagesFocus64.get(imageKey) != null	? hashImagesFocus64.get(imageKey)
														: imageNotFound64Focus;
	}

	public static Image getImage128(String imageKey) {
		if (hashImagesNormal128.get(imageKey)== null)
			hashImagesNormal128.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_NORMAL + IMAGES_128X128+imageKey));		
		return hashImagesNormal128.get(imageKey) != null ? hashImagesNormal128.get(imageKey)
														: imageNotFound128Normal;
	}

	public static Image getImage128Focus(String imageKey) {
		if (hashImagesFocus128.get(imageKey)== null)
			hashImagesFocus128.put(imageKey, loadImage(IMAGES_DIR_COMPLETE_FOCUS + IMAGES_128X128+imageKey));
		return hashImagesFocus128.get(imageKey) != null	? hashImagesFocus128.get(imageKey)
														: imageNotFound128Focus;
	}

	public static Image getImageNotFound(int width, int height, Color color) {
		try {
			Image img = new Image(Display.getDefault(), width, height);
			GC gc = new GC(img);
			gc.setTextAntialias(SWT.ON);
			gc.setAntialias(SWT.ON);
			gc.setInterpolation(SWT.HIGH);
			gc.setForeground(color);
			gc.setLineWidth(2);
			gc.drawLine(0, 0, width - 1, height - 1);
			gc.drawLine(height - 1, 0, 0, height - 1);
			gc.drawRectangle(1, 1, width - 2, height - 2);

			gc.dispose();
			return new Image(
				Display.getDefault(),
				img.getImageData(),
				img.getImageData());
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			return null;
		}
	}

	public static Image addImage(Image imgSrc, Image imgAdd, int x, int y) {
		try {
			GC gc = new GC(imgSrc);
			gc.setAntialias(SWT.ON);
			gc.setInterpolation(SWT.HIGH);
			gc.drawImage(imgAdd, x, y);
			gc.dispose();
			return new Image(Display.getDefault(), imgSrc.getImageData());
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			return imgSrc;
		}
	}

	public static Image getImageFromProgram(Program program) {
		Image image = hashImageCacheProgram.get(program);
		if (image == null) {
			ImageData imageData = program.getImageData();
			if (imageData != null) {
				image = new Image(
					null,
					imageData,
					imageData.getTransparencyMask());
				hashImageCacheProgram.put(program, image);
			}
		}
		return image;
	}
}