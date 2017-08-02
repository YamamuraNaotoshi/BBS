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
import javax.servlet.http.HttpSession;

import bbs.beans.User;

@WebFilter(filterName = "DirectEntryFilter", urlPatterns = { "/management", "/management.jsp", "/settings",
		"/settings.jsp", "/signup", "/signup.jsp" })
public class DirectEntryFilter implements Filter {

	public void init(FilterConfig config) {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) req).getSession();
		User directEntry = (User) ((HttpServletRequest) req).getSession().getAttribute("loginUser");
		if (directEntry != null) {
			int directEntryCheck = directEntry.getPosition_id();

			if (directEntryCheck != 1) {
				List<String> messages = new ArrayList<String>();
				messages.add("該当ページへの移動権限がありません");
				session.setAttribute("errorMessages", messages);
				req.getRequestDispatcher("./").forward(req, res);
			} else {
				chain.doFilter(req, res);
			}
		} else {
			List<String> messages = new ArrayList<String>();
			messages.add("ログインしてください");
			session.setAttribute("errorMessages", messages);
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}

}
