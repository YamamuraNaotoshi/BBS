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

import bbs.beans.Branch;
import bbs.beans.Position;
import bbs.beans.User;
import bbs.service.BranchService;
import bbs.service.PositionService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Branch> branchs = new BranchService().getBranch();
		request.setAttribute("branchs", branchs);
		List<Position> positions = new PositionService().getPosition();
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = new User();

		user.setLogin_id(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
		user.setPosition_id(Integer.parseInt(request.getParameter("position_id")));
		user.setIs_working(1);

		if (isValid(request, messages) == true) {

			new UserService().register(user);
			response.sendRedirect("management");
			return;

		} else {

			List<Branch> branchs = new BranchService().getBranch();
			request.setAttribute("branchs", branchs);

			List<Position> positions = new PositionService().getPosition();
			request.setAttribute("positions", positions);

			session.setAttribute("errorMessages", messages);
			request.setAttribute("makeUser", user);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		int branch_id = Integer.parseInt(request.getParameter("branch_id"));
		int position_id = Integer.parseInt(request.getParameter("position_id"));
		boolean loginIdCheck = new UserService().idCheck(login_id, 0);

		if (!loginIdCheck) {
			messages.add("ログインIDが重複しています");
		}

		if (StringUtils.isEmpty(login_id) == true) {
			messages.add("ログインIDを入力してください");
		} else {
			if (!login_id.matches("[a-zA-Z0-9]+")) {
				messages.add("ログインIDは半角英数字で入力してください");
			}
			if (6 > login_id.length() || 20 < login_id.length()) {
				messages.add("ログインIDは6文字以上20文字以下で入力してください");
			}
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (password.length() != 0 && (6 > password.length() || 20 < password.length())) {
			messages.add("パスワードは6文字以上20文字以下で入力してください");
		}
		if (!password.equals(password2)) {
			messages.add("パスワードが一致していません");
		}
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名称を入力してください");
		}
		if (10 < name.length()) {
			messages.add("名称は10文字以下です");
		}
		if (branch_id == 1 && (position_id != 1 && position_id != 2)) {
			messages.add("支店と部署の組み合わせが不正です");
		}
		if (branch_id != 1 && (position_id == 1 || position_id == 2)) {
			messages.add("支店と部署の組み合わせが不正です");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
