/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Nov 28, 2010
 * @description
 */
package net.nettape.client.gui.util;

import net.nettape.client.gui.admin.Language;

import org.apache.log4j.Logger;

public class DateUtil {

	public static Logger logger = Logger.getLogger(DateUtil.class);

	private static DateUtil instance = null;

	public static final String SUNDAY = Language.getTextGeneral(Language.GENERAL_SUNDAY);
	public static final String MONDAY = Language.getTextGeneral(Language.GENERAL_MONDAY);
	public static final String TUESDAY = Language.getTextGeneral(Language.GENERAL_TUESDAY);
	public static final String WEDNESDAY = Language.getTextGeneral(Language.GENERAL_WEDNESDAY);
	public static final String THURSDAY = Language.getTextGeneral(Language.GENERAL_THURSDAY);
	public static final String FRIDAY = Language.getTextGeneral(Language.GENERAL_FRIDAY);
	public static final String SATURDAY = Language.getTextGeneral(Language.GENERAL_SATURDAY);

	public static final String[] DAYS_WEEKLY = new String[] {
			DateUtil.SUNDAY, DateUtil.MONDAY, DateUtil.TUESDAY, DateUtil.WEDNESDAY,
			DateUtil.THURSDAY, DateUtil.FRIDAY, DateUtil.SATURDAY };

	public static final String JANUARY = Language.getTextGeneral(Language.GENERAL_JANUARY);
	public static final String FEBRUARY = Language.getTextGeneral(Language.GENERAL_FEBRUARY);
	public static final String MARCH = Language.getTextGeneral(Language.GENERAL_MARCH);
	public static final String APRIL = Language.getTextGeneral(Language.GENERAL_APRIL);
	public static final String MAY = Language.getTextGeneral(Language.GENERAL_MAY);
	public static final String JUNE = Language.getTextGeneral(Language.GENERAL_JUNE);
	public static final String JULY = Language.getTextGeneral(Language.GENERAL_JULY);
	public static final String AUGUST = Language.getTextGeneral(Language.GENERAL_AUGUST);
	public static final String SEPTEMBER = Language.getTextGeneral(Language.GENERAL_SEPTEMBER);
	public static final String OCTOBER = Language.getTextGeneral(Language.GENERAL_OCTOBER);
	public static final String NOVEMBER = Language.getTextGeneral(Language.GENERAL_NOVEMBER);
	public static final String DECEMBER = Language.getTextGeneral(Language.GENERAL_DECEMBER);

	public static final String[] MONTHS = new String[] {
			DateUtil.JANUARY, DateUtil.FEBRUARY, DateUtil.MARCH, DateUtil.APRIL, DateUtil.MAY,
			DateUtil.JUNE, DateUtil.JULY, DateUtil.AUGUST, DateUtil.SEPTEMBER, DateUtil.OCTOBER,
			DateUtil.NOVEMBER, DateUtil.DECEMBER };

	private DateUtil() {}

	public static DateUtil getInstance() {
		try {
			if (DateUtil.instance == null) {
				DateUtil.instance = new DateUtil();
			}
			return DateUtil.instance;
		} catch (Exception ex) {
			DateUtil.logger.fatal(ex, ex);
			return null;
		}
	}
}