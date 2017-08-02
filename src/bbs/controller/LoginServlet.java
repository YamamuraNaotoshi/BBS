package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;
import bbs.service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");

		List<String> messages = new ArrayList<String>();
		LoginService loginService = new LoginService();
		User user = loginService.login(login_id, password);

		HttpSession session = request.getSession();

		if (user != null) {
			if (user.getIs_working() == 0) {
				messages.add("このアカウントは停止中です");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("login_id", login_id);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				session.setAttribute("loginUser", user);
				response.sendRedirect("./");
			}
		} else {
			messages.add("ログインに失敗しました。");
			session.setAttribute("errorMessages", messages);
			request.setAttribute("login_id", login_id);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

}
