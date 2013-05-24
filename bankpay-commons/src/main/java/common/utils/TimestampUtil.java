package common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间计算工具
 * 
 * @author qizhen.lu 2012-8-21
 */
public class TimestampUtil {
	/** 日期模式：年-月-日 */
	public static final String DATE_PATTERN_NORMAL = "yyyy-MM-dd";
	/** 日期模式：年月日 */
	public static final String DATE_PATTERN_DAYS = "yyyyMMdd";
	/** 日期模式：时分秒 */
	public static final String DATE_PATTERN_TIMES = "HHmmss";
	/** 日期模式：年月日时分秒 */
	public static final String DATE_PATTERN_TIMESTAMP = "yyyyMMddHHmmss";
	/** 日期模式：人类可读 年-月-日 时:分:秒 */
	public static final String DATE_PATTERN_HUMAN_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
	/** 日期模式：年月日 */
	private static final SimpleDateFormat DATE_FORMAT_DAYS = new SimpleDateFormat(DATE_PATTERN_DAYS);
	/** 日期模式：时分秒 */
	private static final SimpleDateFormat DATE_FORMAT_TIMES = new SimpleDateFormat(DATE_PATTERN_TIMES);
	/** 日期模式：年月日时分秒 */
	private static final SimpleDateFormat DATE_FORMAT_TIMESTAMP = new SimpleDateFormat(DATE_PATTERN_TIMESTAMP);
	/** 日期模式：人类可读 年-月-日 时:分:秒 */
	private static final SimpleDateFormat DATE_FORMAT_HUMAN_TIMESTAMP = new SimpleDateFormat(
			DATE_PATTERN_HUMAN_TIMESTAMP);
	/** 日期模式：年月日时分秒毫秒 */
	public static final String DATE_PATTERN_TIMESTAMP_MS = "yyyyMMddHHmmsssss";

	/** 日期模式：年月日时分 */
	public static final String FOTMAT_EXECUTION_DATE = "yyyyMMddHHmm";

	/**
	 * 判断传入time stamp是否为今天或某天（xDays天之前）。0表示当天，1表示当天与昨天。 是则返回true,否则返回false
	 * 
	 * @param time
	 * @param xDays
	 * @return
	 */
	public static boolean inXDays(Date time, int xDays) {
		Date today = new Date();

		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(today);
		beginCal.add(Calendar.DATE, -1 - xDays);
		beginCal.set(Calendar.HOUR_OF_DAY, 0);
		beginCal.set(Calendar.MINUTE, 0);
		beginCal.set(Calendar.SECOND, 0);
		Date begin = beginCal.getTime();

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(today);
		endCal.add(Calendar.DATE, 1);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		Date end = endCal.getTime();

		return time.after(begin) && time.before(end);
	}

	/**
	 * 判断传入time stamp是否超过X分钟，是返回true，否则返回flase
	 * 
	 * @param time
	 * @param xDays
	 * @return
	 */
	public static boolean greatThanXMinutes(Date time, int xMinutes) {
		Date today = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.MINUTE, -xMinutes);
		Date date = calendar.getTime();

		return time.before(date);
	}

	/**
	 * 将日期转换成想要返回的格式.
	 * 
	 * @param curStamp
	 * @param format
	 * @return
	 */
	public static String formatDate(Date curStamp, String format) {
		SimpleDateFormat sim = new SimpleDateFormat(format);
		return sim.format(curStamp);
	}

	/**
	 * 将日期转换成年月日的格式.
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDays(Date time) {
		return DATE_FORMAT_DAYS.format(time);
	}

	/**
	 * 将日期转换成时分秒的格式.
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTimes(Date time) {
		return DATE_FORMAT_TIMES.format(time);
	}

	/**
	 * 将日期转换成年月日时分秒的格式.
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTimestamp(Date time) {
		return DATE_FORMAT_TIMESTAMP.format(time);
	}

	/**
	 * 将日期转换成人类可读 年-月-日 时:分:秒 的格式.
	 * 
	 * @param time
	 * @return
	 */
	public static String formatHumanTimestamp(Date time) {
		return DATE_FORMAT_HUMAN_TIMESTAMP.format(time);
	}

	/**
	 * 功能描述: <br>
	 * 格式化日期为年月日时分格式
	 * 
	 * @param iTime
	 *            日期
	 * @return 长整型
	 * @since 8.7
	 */
	public static Long formatDate2Long(Date iTime) {
		Date time = iTime;
		if (null == time) {
			time = new Date();
		}
		String executeDateStr = TimestampUtil.formatDate(time, FOTMAT_EXECUTION_DATE);
		return Long.parseLong(executeDateStr);
	}

}
