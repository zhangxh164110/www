package cn.www.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.www.iservice.CommonManager;
import cn.www.jdo.User;
import cn.www.utils.CommUtils;
import cn.www.utils.CookieUtil;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unused")
public class BaseAction extends ActionSupport{

	private CommonManager commonManager;
	public CommonManager getCommonManager() {
		return commonManager;
	}
	/**
	 * 打印到前台
	 * @param obj
	 */
	public void outputData(Object obj){
		outputData(obj, "html/text;charset=utf-8");
	}
	/**
	 * 打印到前台
	 * @param obj
	 */
	public void outputData(Object obj, String contentType){
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try{
			response.getWriter().print(obj);
			response.getWriter().flush();
			response.getWriter().close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	/**
	 * 获取Request
	 * @return
	 */
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 获取Response
	 * @return
	 */
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	/**
	 * 获取登入用户
	 */
	public User getLoginUser(){
		String id = CookieUtil.getCookieValue(ServletActionContext.getRequest(), "c_userid");
		if(CommUtils.isCorrectNumber(id)){
			User user = commonManager.findEntityByPK(User.class, Long.parseLong(CookieUtil.getCookieValue(ServletActionContext.getRequest(), "c_userid")));
			return user;
		}else{
			return null;
		}
	}
	

	/**
	 * 加用户入Cookie
	 *
	 */
	public void addUserToCookie(User user, Integer times){
		HttpServletRequest request = ServletActionContext.getRequest();
		String strTime = CookieUtil.getCookieValue(request, "www_times");
		if(CommUtils.isCorrectNumber(strTime) && times == null){
			times = Integer.valueOf(strTime);
		}
		CookieUtil.addCookie(ServletActionContext.getResponse(), "www_username", user.getUserName(), "/", times);
		CookieUtil.addCookie(ServletActionContext.getResponse(), "www_role", String.valueOf(user.getRole()), "/", times);
		CookieUtil.addCookie(ServletActionContext.getResponse(), "www_userid", String.valueOf(user.getId()), "/", times);
		CookieUtil.addCookie(ServletActionContext.getResponse(), "www_times", String.valueOf(times), "/", times);
	}
	
	

	/**
	 * 清空Cookie
	 *
	 */
	public void clearUserByCookie(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Cookie[] cookies = request.getCookies();

		if(cookies != null){
			for(int i = 0; i < cookies.length; i++){
				String name = cookies[i].getName();
				cookies[i].setMaxAge(0);
				cookies[i].setPath("/");
				response.addCookie(cookies[i]);
			}
		}
	}
	


  
    /**
     * 获取浏览器版本
     * @return
     */
    public  String getBrowser(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
	    if(UserAgent!=null){
	        if (UserAgent.indexOf("msie") >=0 ) return "IE";
	        if (UserAgent.indexOf("firefox") >= 0) return "FF";
	        if (UserAgent.indexOf("safari") >= 0) return "SF";
	    }
	    return null;
    }
    /**
     * 下载文件到客户端
     * @param file 文件
     * @param filename 客户端保存的文件名
     * @throws IOException
     */
    public void downloadFile(File file, String filename) throws IOException{
    	HttpServletResponse response =getResponse();
		InputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		response.reset();
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		if("FF".equals(getBrowser())){
			//针对火狐浏览器处理
			filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		}else{
			filename = URLEncoder.encode(filename, "UTF-8");
		}
		response.setHeader("Content-disposition", "attachment;filename=" + filename);
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
    }

    /**
     * 下载文件到客户端
     * @param filePath 文件的绝对路径
     * @param filename 客户端保存的文件名
     * @throws IOException
     */
    public void downloadFile(String filePath, String filename) throws IOException{
    	File file = new File(filePath);
    	this.downloadFile(file, filename);
    }
}
