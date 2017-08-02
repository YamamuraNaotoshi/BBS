package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.UserComment;
import bbs.beans.UserMessage;
import bbs.service.CommentService;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String category = request.getParameter("category");
		String toDate = request.getParameter("to_date");
		String fromDate = request.getParameter("from_date");

		List<UserMessage> messages = new MessageService().getMessage(category, fromDate, toDate);
		List<UserComment> comments = new CommentService().getComment();
		List<UserMessage> categorys = new MessageService().getCategory();

		request.setAttribute("checkCategory", category);
		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);
		request.setAttribute("categorys", categorys);
		request.setAttribute("from_date", fromDate);
		request.setAttribute("to_date", toDate);

		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
