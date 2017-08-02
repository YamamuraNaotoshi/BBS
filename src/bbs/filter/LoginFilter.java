package bbs.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;
import bbs.service.LoginService;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
// Filterインターフェースを実装します
public class LoginFilter implements Filter {
	public void init(FilterConfig conf) throws ServletException {
	}

	// doFilterメソッドにフィルタ処理を記載
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String path = ((HttpServletRequest) req).getRequestURI();
		if (!path.matches(".*/login.*") && !path.matches(".+.css")) {
			HttpSession session = ((HttpServletRequest) req).getSession();
			User checkUser = (User) session.getAttribute("loginUser");

			if (checkUser != null) {
				String login_id = checkUser.getLogin_id().toString();
				LoginService checkLogin = new LoginService();
				User user = checkLogin.filter(login_id);

				if (user.getIs_working() == 0) {
					List<String> messages = new ArrayList<String>();
					messages.add("このアカウントは停止中です");
					session.setAttribute("errorMessages", messages);
					((HttpServletResponse) res).sendRedirect("login.jsp");
					session.invalidate();
					return;
				} else {
					session.setAttribute("loginUser", user);
					chain.doFilter(req, res);
				}
			} else {
				((HttpServletResponse) res).sendRedirect("login.jsp");
				return;
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}
}
