package cn.www.utils;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

public class CookieTag extends ComponentTagSupport {
	private static final long serialVersionUID = -6007128491359224635L;
	private String value;

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new Cookie(arg0);
	}

	protected void populateParams() {
		super.populateParams();
		Cookie cookie = (Cookie) component;
		cookie.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
