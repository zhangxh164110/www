package cn.www.utils;

import cn.www.utils.CommUtils;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Component;

public class Cookie extends Component {
	private String value;

	public Cookie(ValueStack arg0) {
		super(arg0);
	}

	@Override
	public boolean start(Writer writer) {
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean result = super.start(writer);
		try {
			if (CommUtils.isValidStr(value)) {
				value = CookieUtil.getCookieValue(request, value);
			}
			writer.write(value != null ? value : "");
		} catch (IOException ex) {
			Logger.getLogger(Cookie.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
