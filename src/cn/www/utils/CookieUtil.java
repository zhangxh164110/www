package cn.www.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class CookieUtil {
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String path,int cookieMaxAge) {
		try {
			Cookie c = new Cookie(cookieName, URLEncoder.encode(cookieValue, "utf-8"));
			c.setMaxAge(cookieMaxAge);
			c.setPath(path);
			response.addCookie(c);
		} catch (Exception ex) {
		}
	}

	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String path,int cookieMaxAge, String domain) {
		try {
			Cookie c = new Cookie(cookieName, URLEncoder.encode(cookieValue, "utf-8"));
			c.setMaxAge(cookieMaxAge);
			c.setPath(path);
			if(CommUtils.isValidStr(domain)) c.setDomain(domain);
			response.addCookie(c);
		} catch (Exception ex) {
		}
	}

	public static void addCookie(HttpServletResponse response, Cookie cookie) {
		try {
			response.addCookie(cookie);
		} catch (Exception e) {
		}
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		String cookieValue = "";
		try {
			Cookie c[] = request.getCookies();
			if (c != null) {
				for (int n = 0; n < c.length; n++) {
					if (c[n].getName().equals(cookieName)) {
						cookieValue = URLDecoder.decode(c[n].getValue(), "utf-8");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cookieValue;
	}

	public static String GetPageContent(String pageURL) {
		String pageContent = "";
		BufferedReader in = null;
		InputStreamReader isr = null;
		InputStream is = null;
		HttpURLConnection huc = null;
		try {
			URL url = new URL(pageURL);
			huc = (HttpURLConnection) url.openConnection();
			is = huc.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			in = new BufferedReader(isr);
			String line = null;
			while (((line = in.readLine()) != null)) {
				if (line.length() == 0)
					continue;
				pageContent += line;
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				is.close();
				isr.close();
				in.close();
				huc.disconnect();
			} catch (Exception e) {
			}
		}
		return pageContent;
	}

	/**
	 * 下载网络文件
	 */
	public static boolean downloadFile(String url, String fileName) {
		try {
			URL httpurl = new URL(url);
			File f = new File(fileName);
			FileUtils.copyURLToFile(httpurl, f);
			return true;
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return false;
	}
}
