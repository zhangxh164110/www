package cn.www.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import cn.www.utils.ImageUtil;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class XheditorUploadAction extends ActionSupport {
	private File filedata;

	private String filedataContentType;

	private String filedataFileName;

	private String err;

	private String msg;

	private String message;

	private String fileExt = "jpg,jpeg,gif,bmp,png";
	
	public String uploadPicture() throws Exception {
		String subFilePath = ImageUtil.getFolderDir();
		HttpServletRequest request = ServletActionContext.getRequest();
		String flag = request.getParameter("flag");
		String saveRealFilePath = "";
		if( flag!=null && flag.equals("1")){
			saveRealFilePath = ServletActionContext.getServletContext().getRealPath("")+File.separator +"images"+File.separator+"a" +subFilePath;
		}else{
			saveRealFilePath = ServletActionContext.getServletContext().getRealPath("")+File.separator +"images"+File.separator+"u" +subFilePath;
		}
		File fileDir = new File(saveRealFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File savefile;
		filedataFileName = ImageUtil.genFileName(filedataFileName);
		savefile = new File(saveRealFilePath + filedataFileName);
		FileUtils.copyFile(filedata, savefile);
		if( flag!=null && flag.equals("1")){
			msg = "images/a" +subFilePath + filedataFileName;
		}else{
			msg = "images/u" +subFilePath + filedataFileName;
		}
		err = "";
		printInfo(err, msg);
		return SUCCESS;
	}

	public void printInfo(String err, String newFileName) {
		message = "{\"err\":\"" + err + "\",\"msg\":\"" + newFileName + "\"}";
	}

	public String uploadJsp() {
		return SUCCESS;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}