package net.nettape.client.gui.util;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.Logger;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Jan 30, 2010
 * @description
 */

public final class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	private FileUtil() {}

	public static int compareFiles(File a, File b) {
		boolean aIsDir = a.isDirectory();
		boolean bIsDir = b.isDirectory();
		if (aIsDir && !bIsDir)
			return -1;
		if (bIsDir && !aIsDir)
			return 1;

		int compare = a.getName().compareToIgnoreCase(b.getName());
		if (compare == 0)
			compare = a.getName().compareTo(b.getName());
		return compare;
	}

	public static void sortMergeFiles(File[] files) {
		sortMergeBlock(files, 0, files.length - 1, new File[files.length]);
	}

	public static void sortMergeBlock(	File[] files,
										int start,
										int end,
										File[] mergeTemp) {
		final int length = end - start + 1;
		if (length < 8) {
			for (int i = end; i > start; --i) {
				for (int j = end; j > start; --j) {
					if (compareFiles(files[j - 1], files[j]) > 0) {
						final File temp = files[j];
						files[j] = files[j - 1];
						files[j - 1] = temp;
					}
				}
			}
			return;
		}
		final int mid = (start + end) / 2;
		sortMergeBlock(files, start, mid, mergeTemp);
		sortMergeBlock(files, mid + 1, end, mergeTemp);
		int x = start;
		int y = mid + 1;
		for (int i = 0; i < length; ++i) {
			if ((x > mid)
					|| ((y <= end) && compareFiles(files[x], files[y]) > 0)) {
				mergeTemp[i] = files[y++];
			} else {
				mergeTemp[i] = files[x++];
			}
		}
		for (int i = 0; i < length; ++i)
			files[i + start] = mergeTemp[i];
	}

	public static File[] getDirectoryList(File file, boolean onlyDir) {
		FileFilter filtru = new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				return false;
			}
		};
		File[] list = onlyDir ? file.listFiles(filtru) : file.listFiles();
		if (list == null)
			return new File[0];
		FileUtil.sortMergeFiles(list);
		return list;
	}

	public static File[] getRoots() {
		try {
			if (System.getProperty("os.name").indexOf("Windows") != -1) {
				File[] roots = File.listRoots();
				return roots;
			}
			File root = new File(File.separator);
			return new File[] {
				root };
		} catch (Exception e) {
			logger.fatal(e, e);
			return new File[0];
		}
	}
	
	public static String getFileExtension(final File file) {
		return getFileExtension(file.getAbsolutePath());
	}

	public static String getFileExtension(final String fileName) {
		if (fileName.lastIndexOf('.') != -1)
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		return "";
	}
	
}
