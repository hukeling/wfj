package basic.action;
import javax.servlet.http.Cookie;

import util.action.BaseAction;
@SuppressWarnings("serial")
public class CookieAction extends BaseAction {
	private String check;
	public String Cookie() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userCookie".equals(cookie.getName())) {
					String value = cookie.getValue();
					String[] valueArr = value.split("_");
					request.setAttribute("loginName", valueArr[0]);
					request.setAttribute("password", valueArr[1]);
					request.setAttribute("check", valueArr[2]);
				}
			}
		}
		return SUCCESS;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
}
