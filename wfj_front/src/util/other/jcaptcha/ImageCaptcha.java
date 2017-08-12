package util.other.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageCaptcha extends HttpServlet {

	/**
	 * 生成验证码图片
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	protected void doGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		genernateCaptchaImage(httpServletRequest, httpServletResponse);
	}
	
	
	/**
	 * 生成验证码图片.
	 */
	public static BufferedImage genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
//		ServletOutputStream out = response.getOutputStream();
		String captchaId = request.getSession(true).getId();
		BufferedImage challenge = (BufferedImage)  CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId, request.getLocale());
//		ImageIO.write(challenge, "jpg", out);
//		out.flush();
//		out.close();
		return challenge;
	}
}