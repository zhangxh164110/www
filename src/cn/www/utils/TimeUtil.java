package cn.www.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
	private static final Calendar cal = Calendar.getInstance();
	private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM");

	/**
	 * 根据语言和格式，得到相应的格式
	 * 
	 * @param locale
	 *            如:new Locale("en","US")
	 * @param formatStr
	 *            如:"yyyy-MM-dd" 或者 "dd MMM" 等
	 * @return
	 */
	public final static String getDateStringByLocale(Locale locale, String formatStr, Date date) {
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr, symbols);
		return formatter.format(date);
	}

	/**
	 * 当前时间
	 */
	public final static Date currentTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 返回比当前日期早num天的日期
	 */
	public static String beforeDate(int num) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE) - num);
		return simpledateformat.format(cal.getTime());
	}

	/**
	 * 返回比当前日期早num天的日期
	 */
	public static Date beforeCurrentDate(int num) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE) - num);
		try {
			return simpledateformat.parse(simpledateformat.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回比当前日期num个月后的日期 num<0时是num个月前的日期
	 */
	public static Date beforeCurrentMonth(int num) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, num);
		try {
			return simpledateformat.parse(simpledateformat.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前的时间的隔num个月的日期
	 */
	public static String beforePreviousMouth(int num) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if (num == 1) {
			if (cal.get(Calendar.MONTH) == 0) {
				cal.set(cal.get(Calendar.YEAR) - 1, 11, cal.get(Calendar.DATE));
			} else {
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, cal.get(Calendar.DATE));
			}
		} else {
			if (cal.get(Calendar.MONTH) == 0 || cal.get(Calendar.MONTH) == 1) {
				cal.set(cal.get(Calendar.YEAR) - 1, cal.get(Calendar.MONTH + 12 - num), cal.get(Calendar.DATE));
			} else {
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - num, cal.get(Calendar.DATE));
			}
		}
		return simpledateformat.format(cal.getTime());
	}

	/**
	 * 日期转换字符串
	 */
	public final static String getYYYYMMDD(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		return simpledateformat.format(date);
	}

	/**
	 * 日期转换字符串
	 */
	public final static String getYYMM(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyMM");
		return simpledateformat.format(date);
	}

	/**
	 * 日期转换字符串
	 */
	public final static String getCustom(Date date, String format) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		return simpledateformat.format(date);
	}

	/**
	 * 时间转换字符串
	 */
	public final static String getYYYYMMDDHHMMSS(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		return simpledateformat.format(date);
	}

	/**
	 * 时间转换字符串
	 */
	public final static String getYYYY_MM_DDHHMM(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return simpledateformat.format(date);
	}

	/**
	 * 时间转换字符串
	 */
	public final static String getMM_DDHHMM(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM-dd HH:mm");
		return simpledateformat.format(date);
	}

	/**
	 * 时间转换字符串
	 */
	public final static String getYYYY_MM_DD(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		return simpledateformat.format(date);
	}

	/**
	 * 时间转换字符串
	 */
	public final static String getYYYY_MM(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM");
		return simpledateformat.format(date);
	}

	/**
	 * 取出年
	 */
	public final static String getYY(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		return simpledateformat.format(date);
	}

	/**
	 * 取出月
	 */
	public final static String getMM(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM");
		return simpledateformat.format(date);
	}

	/**
	 * 取出日
	 */
	public final static String getDD(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd");
		return simpledateformat.format(date);
	}

	/**
	 * 字符串转时间(当月第一天)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getDateFromStr(int year, int month) {
		cal.set(year, month - 1, 1, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 字符串转时间(当月第一天)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getDateFromStr(String dateStr) {
		String strs[] = dateStr.split("-");
		cal.set(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]) - 1, 1, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 字符串转换时间
	 */
	public static final Date controlTime(String dateString) {
		Date date = null;
		try {
			String s1 = dateString.substring(0, 4);
			String s2 = "";
			String s3 = "";
			String s4 = dateString.substring(5);
			int i = s4.indexOf("-");
			if (i == 1) {
				s2 = s4.substring(0, 1);
				s3 = s4.substring(2);
			} else {
				s2 = s4.substring(0, 2);
				s3 = s4.substring(3, 5);
			}
			if (dateString.length() >= 16) {
				cal.set((new Integer(s1)).intValue(), (new Integer(s2)).intValue() - 1, (new Integer(s3)).intValue(),
						new Integer(dateString.substring(11, 13)), new Integer(dateString.substring(14, 16)), 0);
				date = new Date(cal.getTime().getTime());
			} else {
				cal.set((new Integer(s1)).intValue(), (new Integer(s2)).intValue() - 1, (new Integer(s3)).intValue());
				date = cal.getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static final Date toTime(String dateString) {
		String s1 = dateString.substring(0, 4);
		String s2 = "";
		String s3 = "";
		String s4 = dateString.substring(5);
		int i = s4.indexOf("-");
		if (i == 1) {
			s2 = s4.substring(0, 1);
			s3 = s4.substring(2);
		} else {
			s2 = s4.substring(0, 2);
			s3 = s4.substring(3, 5);
		}
		cal.set((new Integer(s1)).intValue(), (new Integer(s2)).intValue() - 1, (new Integer(s3)).intValue(), 0, 0, 0);
		Date date = cal.getTime();
		return date;
	}

	/**
	 * 字符串转换时间
	 */
	public static final Date controlTime1(String dateString) {
		String s1 = dateString.substring(0, 4);
		String s2 = "";
		String s3 = "";
		String s4 = dateString.substring(5);
		int i = s4.indexOf("-");
		if (i == 1) {
			s2 = s4.substring(0, 1);
			s3 = s4.substring(2);
		} else {
			s2 = s4.substring(0, 2);
			s3 = s4.substring(3, 5);
		}
		cal.set((new Integer(s1)).intValue(), (new Integer(s2)).intValue() - 1, (new Integer(s3)).intValue(), 0, 0, 0);
		Date date = cal.getTime();
		return date;
	}

	/**
	 * 字符串转换时间
	 */
	public static final java.sql.Date controlTime2(String dateString) {
		String s1 = dateString.substring(0, 4);
		String s2 = "";
		String s3 = "";
		String s4 = dateString.substring(5);
		int i = s4.indexOf("-");
		if (i == 1) {
			s2 = s4.substring(0, 1);
			s3 = s4.substring(2);
		} else {
			s2 = s4.substring(0, 2);
			s3 = s4.substring(3, 5);
		}
		cal.set((new Integer(s1)).intValue(), (new Integer(s2)).intValue() - 1, (new Integer(s3)).intValue(),
				new Integer(dateString.substring(11, 13)), new Integer(dateString.substring(14, 16)), new Integer(
						dateString.substring(17, 19)));
		java.sql.Date date = new java.sql.Date(cal.getTime().getTime());
		return date;
	}

	/**
	 * 字符串转换时间
	 */
	public static final Date controlTime3(String dateString) {
		String s1 = dateString.substring(0, 4);
		String s2 = "";
		String s3 = "";
		String s4 = dateString.substring(5);
		int i = s4.indexOf("-");
		if (i == 1) {
			s2 = s4.substring(0, 1);
			s3 = s4.substring(2);
		} else {
			s2 = s4.substring(0, 2);
			s3 = s4.substring(3, 5);
		}
		cal.set((new Integer(s1)).intValue(), (new Integer(s2)).intValue() - 1, (new Integer(s3)).intValue(),
				new Integer(dateString.substring(11, 13)), new Integer(dateString.substring(14, 16)), new Integer(
						dateString.substring(17, 19)));
		Date date = cal.getTime();
		return date;
	}

	/**
	 * 取年份
	 */
	public static final String getYear(Date date) {
		cal.setTime(date);
		return String.valueOf(cal.get(1));
	}

	/**
	 * 取月份
	 */
	public static final String getMonth(Date date) {
		String s = "";
		cal.setTime(date);
		if (cal.get(2) < 9)
			s = "0";
		return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1);
	}

	/**
	 * 取月份
	 */
	public static final String getMonthNumber(Date date) {
		@SuppressWarnings("unused")
		String s = "";
		cal.setTime(date);
		if (cal.get(2) < 9)
			s = "0";
		return String.valueOf(cal.get(2) + 1);
	}

	/**
	 * 取日
	 */
	public static final String getDay(Date date) {
		String s = "";
		String s1 = "";
		cal.setTime(date);
		if (cal.get(2) < 9)
			s = "0";
		if (cal.get(5) < 10)
			s1 = "0";
		return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1) + s1 + String.valueOf(cal.get(5));
	}

	/**
	 * 取星期X
	 */
	public static final String getWeek(Date date) {
		String s = "";
		cal.setTime(date);
		if (cal.get(3) < 10)
			s = "0";
		return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(3));
	}

	/**
	 * 取季节
	 */
	public static final String getSeason(Date date) {
		cal.setTime(date);
		int i = cal.get(2);
		byte byte0 = 1;
		if (i >= 3 && i <= 5)
			byte0 = 2;
		if (i >= 6 && i <= 8)
			byte0 = 3;
		if (i >= 9 && i <= 11)
			byte0 = 4;
		return String.valueOf(cal.get(1)) + "0" + String.valueOf(byte0);
	}

	/**
	 * 去现在完整时间字符串
	 */
	public static final String getNowFormatTimeString() {
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 转换完整的时间字符串
	 */
	public static final String getFormatTimeString(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 根据输入的日期，求出这个月有多少天
	 */
	public static final int getDaysInMonth(int year, int mon) {
		java.util.GregorianCalendar date = new java.util.GregorianCalendar(year, mon - 1, 1);
		return (date.getActualMaximum(Calendar.DATE));
	}

	/**
	 * 根据输入的日期，求出这个月有多少天
	 */
	public static final int getDaysInMonth(Date d) {
		int year = Integer.parseInt(getYear(d));
		int mon = Integer.parseInt(getMonthNumber(d));
		java.util.GregorianCalendar date = new java.util.GregorianCalendar(year, mon - 1, 1);
		return (date.getActualMaximum(Calendar.DATE));
	}

	/**
	 * 计算两个日期之间的天数
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(oDate);

		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();

		Long days = (l2 - l1) / (24 * 60 * 60 * 1000);
		return days.intValue();
	}

	/**
	 * 计算两个日期之间的小时差
	 */
	public static int hourOfTwo(Date fDate, Date oDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(oDate);

		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();

		Long minute = (l2 - l1) / (60 * 60 * 1000);
		return minute.intValue();
	}

	/**
	 * 计算两个日期之间的分钟差
	 */
	public static int minutesOfTwo(Date fDate, Date oDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(oDate);

		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();

		Long secode = (l2 - l1) / (60 * 1000);
		return secode.intValue();
	}

	/**
	 * 取出月 取出的值为 1,2...11,12 这样的
	 */
	public final static String getM(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("M");
		return simpledateformat.format(date);
	}

	public static final Date dateByInput(int time, Date date) {
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(date);
		calNow.add(Calendar.DATE, time);// 当前日期前前7天
		Date daNow = calNow.getTime();
		return daNow;
	}

	public static final Date beforYear(int beforNum, Date date) {
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(date);
		calNow.add(Calendar.YEAR, 0 - beforNum);// 当前日期前前7天
		Date daNow = calNow.getTime();
		return daNow;
	}

	public static final Date beforMonth(int beforNum, Date date) {
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(date);
		calNow.add(Calendar.MONTH, 0 - beforNum);// 当前日期前前7天
		Date daNow = calNow.getTime();
		return daNow;
	}

	/**
	 * 将毫秒数l 转换为 00：00：00格式
	 */
	public final static String formatHMS(long l) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
		String ff = formatter.format(l);
		return ff;
	}

	/**
	 * 根据日期得到年份和月份之间的所有年份和月份
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("finally")
	public final static List<String> getDates(String startDate, String endDate) {
		List<String> list = new ArrayList<String>();
		try {
			Calendar startDay = Calendar.getInstance();
			startDay.setTime(FORMATTER.parse(startDate));
			Calendar endDay = Calendar.getInstance();
			endDay.setTime(FORMATTER.parse(endDate));
			// 给出的日期开始日比终了日大则不执行打印
			if (startDay.compareTo(endDay) > 0) {
				return list;
			} else if (startDay.compareTo(endDay) == 0) {
				list.add(FORMATTER.format(startDay.getTime()).toString());
				return list;
			} else {
				// 现在打印中的日期
				Calendar currentPrintDay = startDay;
				list.add(FORMATTER.format(currentPrintDay.getTime()).toString());
				while (true) {
					// 日期加一
					currentPrintDay.add(Calendar.DATE, 1);
					// 日期加一后判断是否达到终了日，达到则终止打印
					if (currentPrintDay.compareTo(endDay) == 0) {
						break;
					}
					if (!list.get(list.size() - 1).toString()
							.equals(FORMATTER.format(currentPrintDay.getTime()).toString())) {
						list.add(FORMATTER.format(currentPrintDay.getTime()).toString());
					}
				}
				list.add(FORMATTER.format(endDay.getTime()).toString());
				return list;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			return list;
		}
	}

	public static java.sql.Date utildateToSqldate(java.util.Date utildate) {
		if (utildate == null) {
			return null;
		}
		GregorianCalendar da = new GregorianCalendar();
		da.setTime(utildate);
		java.sql.Date sqldate = new java.sql.Date(da.getTimeInMillis());
		return sqldate;
	}

	/**
	 * 判定日期是否过期
	 */
	public static boolean dateIsPass(Date oDate) {
		Date d1 = new Date();
		return d1.after(oDate);
	}

	/**
	 * 判断今日是否在beginDate和endDate之间
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean toDayIsBetweenDates(Date beginDate, Date endDate) {
		Calendar today = Calendar.getInstance();
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		begin.setTime(beginDate);
		end.setTime(endDate);
		// 如果beginDate<=今天<=endDate
		if ((begin.equals(today) || begin.before(today)) && (end.equals(today) || end.after(today))) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判定日期是否过期(更精确)
	 */
	public static int dateTimeIsPass(Date oDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(oDate);
		return c1.compareTo(c2);
	}

	/**
	 * 返回比指定日期早num天的日期
	 * 
	 * @param date
	 *            指定日前
	 * @param num
	 *            多少天
	 */
	public static Date beforeCurrentDate(Date date, int num) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE) - num);
		try {
			return simpledateformat.parse(simpledateformat.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 指定格式格式化日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public final static String formatDate(Date date, String format) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		return simpledateformat.format(date);
	}

}
