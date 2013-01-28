package cn.www.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

@SuppressWarnings("unchecked")
public class CommUtils {
	static {
		ConvertUtils.register(new DateConverter2(), Date.class);
	}

	/**
	 * String编码转换
	 * 
	 * @param originalString
	 *            源字符串
	 * @param orginalFormat
	 *            源格式
	 * @param objectFormat
	 *            要转换成的格式
	 */
	public static String charsetFormat(String originalString, String orginalFormat, String objectFormat) {
		String objectString = null;
		if (!isValidStr(originalString)) {
			return "";
		}
		try {
			if (objectFormat != null) {
				objectString = new String(originalString.trim().getBytes(orginalFormat), objectFormat);
			} else {
				objectString = new String(originalString.trim().getBytes(orginalFormat));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return objectString;
	}

	/**
	 * 判断是否有效字符串
	 */
	public static boolean isValidStr(String str) {
		if (str == null || str.equalsIgnoreCase("") || str.equalsIgnoreCase("null")) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 仅供订舱交易模块跟财务模块校验用
	 * @param token
	 * @return
	 */
	public static String tokenEncryption(String token) {
		int i = (new Random()).nextInt(1000);
		return MD5.toMD5(token + i) + String.valueOf(i + 156);
	}

	/**
	 * 判断是否为正确数字
	 */
	public static boolean isCorrectNumber(String srcDateStr) {
		if (isValidStr(srcDateStr)) {
			try {
				Double.parseDouble(srcDateStr);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为正确数字
	 */
	public static boolean isCorrectInt(String srcDateStr) {
		if (isValidStr(srcDateStr)) {
			try {
				Integer.parseInt(srcDateStr);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 删除最后一个字符
	 */
	public static String deleteLastChar(String srcDateStr) {
		if (isValidStr(srcDateStr)) {
			srcDateStr = srcDateStr.substring(0, srcDateStr.length() - 1);
		}
		return srcDateStr;
	}

	public static String safeTrim(String str) {
		return str != null ? str.trim() : str;
	}

	/**
	 * 修复org.apache.commons.beanutils.BeanUtils中Date类型字段的值为空时报错bug
	 */
	public static void copyProperties(Object dest, Object orig) throws Exception {
		try {
			Field[] fields = orig.getClass().getDeclaredFields();
			List<String> list = new ArrayList<String>();
			for (Field field : fields) {
				if (field.getType() == Date.class) {
					String name = field.getName();
					Date date = (Date) getTheObject(orig, name);
					if (date == null) {
						list.add(name);
						setTheObject(orig, name, new Object[] { new Date() }, new Class[] { Date.class });
					}
				}
			}
			BeanUtils.copyProperties(dest, orig);
			for (String string : list) {
				setTheObject(orig, string, new Object[] { null }, new Class[] { Date.class });
				setTheObject(dest, string, new Object[] { null }, new Class[] { Date.class });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 格式数字最少有两个小数点
	 * 
	 * @param number
	 * @return
	 */
	public static String twoFractionDigits(Number number) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("0.00");
		return df.format(number);
	}

	@SuppressWarnings("rawtypes")
	private static void setTheObject(Object obj, String field, Object[] values, Class[] paramclazz) throws Exception {
		Class clazz = obj.getClass();
		Method method = clazz.getDeclaredMethod("set" + field.substring(0, 1).toUpperCase() + field.substring(1),
				paramclazz);
		method.invoke(obj, values);
	}

	@SuppressWarnings("rawtypes")
	private static Object getTheObject(Object obj, String field) throws Exception {
		Class clazz = obj.getClass();
		Method method = clazz.getDeclaredMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1),
				new Class[] {});
		return method.invoke(obj, new Object[] {});
	}

}
