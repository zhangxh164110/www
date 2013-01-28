package cn.www.utils;

import java.io.InputStream;
import java.util.Properties;

public class VarData {
	public static String fromEmail;
	public static String fromEailName;
	public static String emailPassword;
	public static String subject;
	public static String subject1;
	public static String adminName;
	public static String adminEmail;
	public static String cookieDomain;
	public static String m68Url;
	/**
	 * 保证金冻结天数
	 */
	public static int freezeDays = 100;

	static {
		Properties prop = new Properties();
		InputStream in = VarData.class.getResourceAsStream("/m68.properties");
		try {
			prop.load(in);
			fromEmail = prop.getProperty("fromEmail");
			fromEailName = prop.getProperty("fromEailName");
			emailPassword = prop.getProperty("emailPassword");
			subject = prop.getProperty("subject");
			adminName = prop.getProperty("adminName");
			adminEmail = prop.getProperty("adminEmail");
			subject1 = prop.getProperty("subject1");
			cookieDomain = prop.getProperty("cookieDomain");
			m68Url = prop.getProperty("m68Url");
			freezeDays = Integer.parseInt(prop.getProperty("freezeDays"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
