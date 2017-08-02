package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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

@WebServlet(urlPatterns = { "/settings" })
@MultipartConfig(maxFileSize = 10000)
public class SettingsServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();
		String userid = request.getParameter("id");

		if (StringUtils.isEmpty(request.getParameter("id"))) {
			messages.add("不正なアクセスです");
			request.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("management").forward(request, response);
			return;
		} else {
			if (!userid.matches("[0-9]+")) {
				messages.add("不正なアクセスです");
				request.setAttribute("errorMessages", messages);
				request.getRequestDispatcher("management").forward(request, response);
				return;
			}
		}
		User editUser = new UserService().getUser(Integer.parseInt(userid));
		if (editUser == null) {
			messages.add("不正なアクセスです");
			request.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("management").forward(request, response);
			return;
		}
		List<Branch> branchs = new BranchService().getBranch();
		List<Position> positions = new PositionService().getPosition();

		request.setAttribute("editUser", editUser);
		request.setAttribute("branchs", branchs);
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		User editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		if (isValid(request, messages) == true) {
			boolean passwordChanged = false;
			if (!StringUtils.isEmpty(request.getParameter("password"))) {
				editUser.setPassword(request.getParameter("password"));
				passwordChanged = true;
			}
			new UserService().update(editUser, passwordChanged);
			response.sendRedirect("management");
		} else {

			List<Branch> branchs = new BranchService().getBranch();
			request.setAttribute("branchs", branchs);
			List<Position> positions = new PositionService().getPosition();
			request.setAttribute("positions", positions);
			session.setAttribute("errorMessages", messages);
			request.setAttribute("editUser", editUser);
			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request) throws IOException, ServletException {

		User editUser = new UserService().getUser(Integer.parseInt(request.getParameter("editUserId")));

		editUser.setLogin_id(request.getParameter("login_id"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setPosition_id(Integer.parseInt(request.getParameter("position_id")));
		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		int id = Integer.parseInt(request.getParameter("editUserId"));
		String login_id = request.getParameter("login_id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		int branch_id = Integer.parseInt(request.getParameter("branch_id"));
		int position_id = Integer.parseInt(request.getParameter("position_id"));
		boolean loginIdCheck = new UserService().idCheck(login_id, id);

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
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名称を入力してください");
		}
		if (name.length() > 10) {
			messages.add("名称は10文字以下です");
		}

		if (password.length() != 0 && (6 > password.length() || 20 < password.length())) {
			messages.add("パスワードは6文字以上20文字以下で入力してください");
		}
		if (!password.equals(password2)) {
			messages.add("パスワードが一致していません");
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
