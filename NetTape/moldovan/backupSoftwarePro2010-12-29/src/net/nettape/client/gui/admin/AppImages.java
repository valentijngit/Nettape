package net.nettape.client.gui.admin;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
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

	private static final String IMAGES_DIR_COMPLETE_NORMAL = AppImages.APP_ROOT + File.separator
			+ AppImages.IMAGES_DIR + File.separator + AppImages.IMAGES_DIR_SKIN_NORMAL
			+ File.separator;
	private static final String IMAGES_DIR_COMPLETE_FOCUS = AppImages.APP_ROOT + File.separator
			+ AppImages.IMAGES_DIR + File.separator + AppImages.IMAGES_DIR_SKIN_FOCUS
			+ File.separator;

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

	public static final String IMG_SETTINS_GENERAL = "settings_general.png";
	public static final String IMG_SETTINS_RETENCION_POLICY = "settings_retencionpolicy.png";
	public static final String IMG_SETTINS_OPTIONS = "settings_options.png";
	public static final String IMG_SETTINS_LOCAL_COPY = "settings_localcopy.png";
	public static final String IMG_SETTINS_IN_FILE_DELTA = "settings_infiledelta.png";
	public static final String IMG_SETTINS_EXTRA_BACKUP = "settings_extrabackup.png";
	public static final String IMG_SETTINS_ENCRYPTION = "settings_encryption.png";
	public static final String IMG_SETTINS_DATA_PROTECTION = "settings_dataprotection.png";
	public static final String IMG_SETTINS_COMMANDLINE_TOOL = "settings_commandlinetool.png";
	public static final String IMG_SETTINS_BACKUP_SOURCE = "settings_backupsource.png";
	public static final String IMG_SETTINS_BACKUP_SCHEDULE = "settings_backupschedule.png";
	public static final String IMG_SETTINS_BACKUP_FILTER = "settings_backupfilter.png";
	public static final String IMG_YEARLY = "yearly.png";
	public static final String IMG_WEEKLY="weekly.png";
	public static final String IMG_TYPE="type.png";
	public static final String IMG_OFFSITE="offsite.png";
	public static final String IMG_RETENCION_POLICY="retencion_policy.png";
	public static final String IMG_COPY_TO="copy_to.png";
	public static final String IMG_BACKUP="backup.png";
	public static final String IMG_LOGOUT="logout.png";
	public static final String IMG_OFF_LINE="off_line.png";
	public static final String IMG_ADVANCED_OPTION="advanced_option.png";
	public static final String IMG_VOLUME="volume.png";
	public static final String IMG_TEMPORARY_DIR="temporary_dir.png";
	public static final String IMG_FILTER="filter.png";
	public static final String IMG_PATTERN="pattern.png";
	public static final String IMG_UPLOAD="upload.png";
	public static final String IMG_IN_FILE="in_file.png";
	public static final String IMG_APPLY_TO="apply_to.png";
	public static final String IMG_GLOBE="globe.png";
	public static final String IMG_USER_AUTH="user_auth.png";
//	public static final String IMG_BACKUP_SET="backup_set.png";
	

	public static final String IMG_MISC_BANNER = "banner.png";
	public static final String IMG_MISC_MAIN = "main.png";
	public static final String IMG_MISC_APPLICATION = "application.png";
	public static final String IMG_MISC_LEFT_SQUARE = "leftSquare.png";
	public static final String IMG_MISC_RIGHT_SQUARE = "rightSquare.png";
	public static final String IMG_MISC_ROUND_RECTANGLE_SMALL = "back2.png";
	public static final String IMG_MISC_ROUND_RECTANGLE_BIG = "back1.png";

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
			AppImages.hashImageCacheProgram = new Hashtable<Program, Image>();
			AppImages.hashImagesNormal16 = new Hashtable<String, Image>();
			AppImages.hashImagesNormal24 = new Hashtable<String, Image>();
			AppImages.hashImagesNormal32 = new Hashtable<String, Image>();
			AppImages.hashImagesNormal64 = new Hashtable<String, Image>();
			AppImages.hashImagesNormal128 = new Hashtable<String, Image>();

			AppImages.hashImagesFocus16 = new Hashtable<String, Image>();
			AppImages.hashImagesFocus24 = new Hashtable<String, Image>();
			AppImages.hashImagesFocus32 = new Hashtable<String, Image>();
			AppImages.hashImagesFocus64 = new Hashtable<String, Image>();
			AppImages.hashImagesFocus128 = new Hashtable<String, Image>();

			AppImages.hashImagesMisc = new Hashtable<String, Image>();

			AppImages.imageNotFound16Normal = AppImages.getImageNotFound(16,
					16,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			AppImages.imageNotFound16Focus = AppImages.getImageNotFound(16,
					16,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			AppImages.imageNotFound24Normal = AppImages.getImageNotFound(24,
					24,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			AppImages.imageNotFound24Focus = AppImages.getImageNotFound(24,
					24,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			AppImages.imageNotFound32Normal = AppImages.getImageNotFound(32,
					32,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			AppImages.imageNotFound32Focus = AppImages.getImageNotFound(32,
					32,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			AppImages.imageNotFound64Normal = AppImages.getImageNotFound(64,
					64,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			AppImages.imageNotFound64Focus = AppImages.getImageNotFound(64,
					64,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			AppImages.imageNotFound128Normal = AppImages.getImageNotFound(128,
					128,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
			AppImages.imageNotFound128Focus = AppImages.getImageNotFound(128,
					128,
					Display.getDefault().getSystemColor(SWT.COLOR_RED));
		} catch (Exception ex) {
			AppImages.logger.fatal(ex, ex);
		}
	}

	public static AppImages getInstance() {
		try {
			if (AppImages.instance == null) {
				AppImages.instance = new AppImages();
			}
			return AppImages.instance;
		} catch (Exception ex) {
			AppImages.logger.fatal(ex, ex);
			return null;
		}
	}

	public final static Image resizeImage(Image image, int width, int height) {
		if ((image.getImageData().width == width) && (image.getImageData().height == height)) {
			return image;
		}
		Image scaled = new Image(Display.getDefault(), width, height);
		Image imgM = new Image(Display.getDefault(), image.getImageData().getTransparencyMask());
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
		return new Image(Display.getDefault(), scaled.getImageData(), scaledM.getImageData());
	}

	public final static Image getImage(Image srcImage, int flag) {
		Image img = null;
		try {
			img = new Image(Display.getDefault(), srcImage, flag);
		} catch (Exception ex) {
			AppImages.logger.fatal(ex, ex);
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
			AppImages.logger.fatal(ex);
			img = imageNotFound16Normal;
		}
		return img;
	}

	public static Image getImageBySizeNormal(String imageKey, int size) {
		switch (size) {
			case 16:
				return AppImages.hashImagesNormal16.get(imageKey) != null	? AppImages.hashImagesNormal16.get(imageKey)
																			: AppImages.imageNotFound16Normal;
			case 32:
				return AppImages.hashImagesNormal32.get(imageKey) != null	? AppImages.hashImagesNormal32.get(imageKey)
																			: AppImages.imageNotFound32Normal;
			case 64:
				return AppImages.hashImagesNormal64.get(imageKey) != null	? AppImages.hashImagesNormal64.get(imageKey)
																			: AppImages.imageNotFound64Normal;
			case 128:
				return AppImages.hashImagesNormal128.get(imageKey) != null	? AppImages.hashImagesNormal128.get(imageKey)
																			: AppImages.imageNotFound128Normal;
			default:
				return AppImages.hashImagesNormal16.get(imageKey) != null	? AppImages.hashImagesNormal16.get(imageKey)
																			: AppImages.imageNotFound16Normal;
		}
	}

	public static Image getImage(String imageKey, int flag, int size) {
		return AppImages.getImage(AppImages.getImageBySizeNormal(imageKey, size), flag);
	}

	public static Image getImageDisabled(String imageKey, int size) {
		return AppImages.getImage(AppImages.getImageBySizeNormal(imageKey, size), SWT.IMAGE_DISABLE);
	}

	public static Image getImageGray(String imageKey, int size) {
		return AppImages.getImage(AppImages.getImageBySizeNormal(imageKey, size), SWT.IMAGE_GRAY);
	}

	public static Image getImageMisc(String imageKey) {
		if (AppImages.hashImagesMisc.get(imageKey) == null) {
			AppImages.hashImagesMisc.put(imageKey,
					AppImages.loadImage(AppImages.APP_ROOT + File.separator + AppImages.IMAGES_DIR
							+ File.separator + "misc" + File.separator + imageKey));
		}
		return AppImages.hashImagesMisc.get(imageKey) != null	? AppImages.hashImagesMisc.get(imageKey)
																: AppImages.imageNotFound16Normal;
	}

	public static Image getImage16(String imageKey) {
		if (AppImages.hashImagesNormal16.get(imageKey) == null) {
			AppImages.hashImagesNormal16.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_NORMAL
							+ AppImages.IMAGES_16X16 + imageKey));
		}
		return AppImages.hashImagesNormal16.get(imageKey) != null	? AppImages.hashImagesNormal16.get(imageKey)
																	: AppImages.imageNotFound16Normal;
	}

	public static Image getImage16Focus(String imageKey) {
		if (AppImages.hashImagesFocus16.get(imageKey) == null) {
			AppImages.hashImagesFocus16.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_FOCUS
							+ AppImages.IMAGES_16X16 + imageKey));
		}
		return AppImages.hashImagesFocus16.get(imageKey) != null ? AppImages.hashImagesFocus16.get(imageKey)
																: AppImages.imageNotFound16Focus;
	}

	public static Image getImage24(String imageKey) {
		if (AppImages.hashImagesNormal24.get(imageKey) == null) {
			AppImages.hashImagesNormal24.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_NORMAL
							+ AppImages.IMAGES_24X24 + imageKey));
		}
		return AppImages.hashImagesNormal24.get(imageKey) != null	? AppImages.hashImagesNormal24.get(imageKey)
																	: AppImages.imageNotFound24Normal;
	}

	public static Image getImage24Focus(String imageKey) {
		if (AppImages.hashImagesFocus24.get(imageKey) == null) {
			AppImages.hashImagesFocus24.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_FOCUS
							+ AppImages.IMAGES_24X24 + imageKey));
		}
		return AppImages.hashImagesFocus24.get(imageKey) != null ? AppImages.hashImagesFocus24.get(imageKey)
																: AppImages.imageNotFound24Focus;
	}

	public static Image getImage32(String imageKey) {
		if (AppImages.hashImagesNormal32.get(imageKey) == null) {
			AppImages.hashImagesNormal32.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_NORMAL
							+ AppImages.IMAGES_32X32 + imageKey));
		}
		return AppImages.hashImagesNormal32.get(imageKey) != null	? AppImages.hashImagesNormal32.get(imageKey)
																	: AppImages.imageNotFound32Normal;
	}

	public static Image getImage32Focus(String imageKey) {
		if (AppImages.hashImagesFocus32.get(imageKey) == null) {
			AppImages.hashImagesFocus32.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_FOCUS
							+ AppImages.IMAGES_32X32 + imageKey));
		}
		return AppImages.hashImagesFocus32.get(imageKey) != null ? AppImages.hashImagesFocus32.get(imageKey)
																: AppImages.imageNotFound32Focus;
	}

	public static Image getImage64(String imageKey) {
		if (AppImages.hashImagesNormal64.get(imageKey) == null) {
			AppImages.hashImagesNormal64.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_NORMAL
							+ AppImages.IMAGES_64X64 + imageKey));
		}
		return AppImages.hashImagesNormal64.get(imageKey) != null	? AppImages.hashImagesNormal64.get(imageKey)
																	: AppImages.imageNotFound64Normal;
	}

	public static Image getImage64Focus(String imageKey) {
		if (AppImages.hashImagesFocus64.get(imageKey) == null) {
			AppImages.hashImagesFocus64.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_FOCUS
							+ AppImages.IMAGES_64X64 + imageKey));
		}
		return AppImages.hashImagesFocus64.get(imageKey) != null ? AppImages.hashImagesFocus64.get(imageKey)
																: AppImages.imageNotFound64Focus;
	}

	public static Image getImage128(String imageKey) {
		if (AppImages.hashImagesNormal128.get(imageKey) == null) {
			AppImages.hashImagesNormal128.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_NORMAL
							+ AppImages.IMAGES_128X128 + imageKey));
		}
		return AppImages.hashImagesNormal128.get(imageKey) != null	? AppImages.hashImagesNormal128.get(imageKey)
																	: AppImages.imageNotFound128Normal;
	}

	public static Image getImage128Focus(String imageKey) {
		if (AppImages.hashImagesFocus128.get(imageKey) == null) {
			AppImages.hashImagesFocus128.put(imageKey,
					AppImages.loadImage(AppImages.IMAGES_DIR_COMPLETE_FOCUS
							+ AppImages.IMAGES_128X128 + imageKey));
		}
		return AppImages.hashImagesFocus128.get(imageKey) != null	? AppImages.hashImagesFocus128.get(imageKey)
																	: AppImages.imageNotFound128Focus;
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
			return new Image(Display.getDefault(), img.getImageData(), img.getImageData());
		} catch (Exception ex) {
			AppImages.logger.fatal(ex, ex);
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
			AppImages.logger.fatal(ex, ex);
			return imgSrc;
		}
	}

	public static Image getImageFromProgram(Program program) {
		Image image = AppImages.hashImageCacheProgram.get(program);
		if (image == null) {
			ImageData imageData = program.getImageData();
			if (imageData != null) {
				image = new Image(null, imageData, imageData.getTransparencyMask());
				AppImages.hashImageCacheProgram.put(program, image);
			}
		}
		return image;
	}
}