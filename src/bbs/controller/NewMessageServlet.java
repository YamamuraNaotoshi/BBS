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

import org.apache.commons.lang.StringUtils;

import bbs.beans.Message;
import bbs.beans.User;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/message" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();

		message.setSubject(request.getParameter("subject"));
		message.setCategory(request.getParameter("category"));
		message.setBody(request.getParameter("body"));
		message.setUser_id(user.getId());
		message.setUser_name(user.getName());
		message.setBranch_id(user.getBranch_id());
		message.setPosition_id(user.getPosition_id());

		if (isValid(request, messages) == true) {

			new MessageService().register(message);
			response.sendRedirect("./");
			return;

		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("makeMessage", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);

		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String subject = request.getParameter("subject");
		String category = request.getParameter("category");
		String body = request.getParameter("body");

		if (StringUtils.isEmpty(subject) == true) {
			messages.add("件名を入力してください");
		}
		if (30 < subject.length()) {
			messages.add("件名を30文字以下で入力してください");
		}

		if (StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリーを入力してください");
		}
		if (10 < category.length()) {
			messages.add("カテゴリーを10文字以下で入力してください");
		}

		if (StringUtils.isEmpty(body) == true) {
			messages.add("本文を入力してください");
		}
		if (1000 < body.length()) {
			messages.add("本文を1000文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
