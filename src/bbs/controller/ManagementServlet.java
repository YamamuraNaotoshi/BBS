package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.Branch;
import bbs.beans.Position;
import bbs.beans.User;
import bbs.service.BranchService;
import bbs.service.PositionService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/management" })
public class ManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<User> users = new UserService().getUser();
		request.setAttribute("users", users);

		List<Branch> branchs = new BranchService().getBranch();
		request.setAttribute("branchs", branchs);

		List<Position> positions = new PositionService().getPosition();
		request.setAttribute("positions", positions);

		request.getRequestDispatcher("/management.jsp").forward(request, response);
	}

}
