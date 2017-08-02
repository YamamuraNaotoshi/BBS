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

import bbs.beans.Comment;
import bbs.beans.User;
import bbs.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("./").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		Comment comment = new Comment();

		comment.setMessage_id(Integer.parseInt(request.getParameter("messageid")));
		comment.setUser_name(user.getName());
		comment.setBody(request.getParameter("body"));
		comment.setUser_id(user.getId());
		comment.setBranch_id(user.getBranch_id());
		comment.setPosition_id(user.getPosition_id());

		if (isValid(request, messages) == true) {

			new CommentService().register(comment);
			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("makeComment", comment);
			response.sendRedirect("./");

		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String body = request.getParameter("body");

		if (StringUtils.isEmpty(body) == true) {
			messages.add("コメントを入力してください");
		} else if (StringUtils.isBlank(body)) {
			messages.add("コメントを入力してください");
		}
		if (500 < body.length()) {
			messages.add("コメントを500文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
