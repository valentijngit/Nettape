package net.nettape.client.gui.util;

import java.util.*;

import org.apache.log4j.Logger;

/***
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Jan 30, 2010
 * @description
 */

public final class StringUtil {

	private static final Logger logger = Logger.getLogger(StringUtil.class);

	private StringUtil() {}

	public static boolean isEmpty(final String str) {
		return (str == null) || (str.length() < 1);
	}

	public static boolean isNotEmpty(final String str) {
		return !StringUtil.isEmpty(str);
	}

	public static boolean isEmpty(final StringBuffer buff) {
		return (buff == null) || StringUtil.isEmpty(buff.toString());
	}

	public static boolean isNotEmpty(StringBuffer buff) {
		return !StringUtil.isEmpty(buff);
	}

	public static boolean isEmpty(String[] array) {
		return (array == null) || (array.length < 1);
	}

	public static boolean isNotEmpty(String[] array) {
		return !StringUtil.isEmpty(array);
	}

	public static String capitalizeCharAtIdx(String str, int idx) {
		if (StringUtil.isEmpty(str) || (str.length() < idx)) {
			return str;
		}
		return str.replaceFirst(String.valueOf(str.charAt(idx)),
				(String.valueOf(str.charAt(idx))).toUpperCase());
	}

	public static String decapitalizeCharAtIdx(String str, int idx) {
		if (StringUtil.isEmpty(str) || (str.length() < idx)) {
			return str;
		}
		return str.replaceFirst(String.valueOf(str.charAt(idx)),
				(String.valueOf(str.charAt(idx))).toLowerCase(Locale.ENGLISH));
	}

	/**
	 * @param theCar
	 *            The String used as support
	 * @param theNewColor
	 *            The char(s) to be inserted in
	 * @param theOldColor
	 *            The char(s) to be replaced by new ones.
	 * @return the converted string
	 */
	public static String convertString(String theCar_param, String theOldColor, String theNewColor) {
		String theCar = theCar_param;
		if (StringUtil.isEmpty(theCar) || StringUtil.isEmpty(theNewColor)
				|| StringUtil.isEmpty(theOldColor)) {
			return theCar;
		}
		StringBuffer tmpString = new StringBuffer();
		int pos = -1, oldPos = 0;
		do {
			pos = theCar.indexOf(theOldColor, oldPos);
			oldPos = pos;
			if (pos >= 0) {
				tmpString.append(theCar.substring(0, pos));
				tmpString.append(theNewColor);
				oldPos += theNewColor.length();
				tmpString.append(theCar.substring(pos + theOldColor.length()));
				theCar = tmpString.toString();

				tmpString.delete(0, tmpString.length());
			}
		} while (pos >= 0);
		return theCar;
	}

	public static final String getDBString(String text) {
		String result = null;
		try {
			result = text;

			result = StringUtil.convertString(result, "\\", "\\\\");
			result = StringUtil.convertString(result, "\"", "\\\"");
			result = StringUtil.convertString(result, "'", "''");
			result = StringUtil.convertString(result, "'", "\'");

		} catch (Exception exc) {
			result = "";
		}
		return result;
	}

	public static final String getSQLString(StringBuffer text) {
		return StringUtil.isNotEmpty(text) ? StringUtil.getDBString(text.toString()) : "";
	}

	public static final String getSQLString(String text) {
		return StringUtil.isNotEmpty(text) ? StringUtil.getDBString(text) : "";
	}

	/**
	 * @return widget suggested size, so that widget will still pe capable to render all the
	 *         associated text
	 */
	public static final String[] splitStrByDelimAndLength(	String strParam,
															String delim,
															int blockSize) {
		String[] result = null;
		TreeMap<Integer, String> mapStr = new TreeMap<Integer, String>();
		try {
			StringTokenizer t = new StringTokenizer(strParam, delim, Boolean.TRUE);
			StringBuffer buff = new StringBuffer();
			int k = 0;
			while (t.hasMoreTokens()) {
				String next = t.nextToken();
				buff.append(next);
				if (buff.length() >= blockSize) {
					mapStr.put(Integer.valueOf(k++), buff.toString());
					buff = new StringBuffer();
				} else if (strParam.endsWith(next)) {
					mapStr.put(Integer.valueOf(k++), buff.toString());
					buff = new StringBuffer();
				}
				result = new String[mapStr.size()];
				int i = 0;
				for (Iterator<String> iter = mapStr.values().iterator(); iter.hasNext();) {
					result[i++] = iter.next();
				}
			}
		} catch (Exception exc) {
			StringUtil.logger.fatal(exc, exc);
			return null;
		}
		return result;
	}

	public static final ArrayList<String> splitStrByDelim(String strParam, String delim) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			StringTokenizer t = new StringTokenizer(strParam, delim, Boolean.TRUE);
			while (t.hasMoreTokens()) {
				String next = t.nextToken();
				result.add(next);
			}
		} catch (Exception exc) {
			StringUtil.logger.fatal(exc, exc);
			return null;
		}
		return result;
	}

	public static String getStringFromArrayList(ArrayList<String> stringList, String sepapator) {
		String str = "";
		for (int i = 0; i < stringList.size(); i++) {
			if (str.length() > 0) {
				str += sepapator;
			}
			str += stringList.get(i);
		}
		return str;
	}

	public static String wrapText(final String text, final int nrCharsOnline) {
		String[] details = StringUtil.splitStrByDelimAndLength(text, " ", nrCharsOnline);
		String str = "";
		for (int i = 0; i < details.length; i++) {
			str += details[i].trim();
			if (i < details.length - 1) {
				str += System.getProperty("line.separator");
			}
		}
		return str;
	}

}
