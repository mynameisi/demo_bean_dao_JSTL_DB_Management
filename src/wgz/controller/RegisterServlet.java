package wgz.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wgz.model.UserBean;
import wgz.model.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String password = request.getParameter("password");
		logger.debug("password: " + password);
		String email = request.getParameter("email");
		logger.debug("email: " + email);
		UserBean user = new UserBean();

		user.setPassword(password);
		user.setEmail(email);
		user.setUsername(email);// 默认用户名和邮箱相同
		boolean regisitered = false;
		String msg = "";
		try {
			regisitered = UserDAO.register(user);
		} catch (SQLIntegrityConstraintViolationException e) {
			msg = "Email重复，请更改Email";
			e.printStackTrace();
		} catch (SQLException e) {
			msg = "数据库异常，请联系网站管理员";
			e.printStackTrace();// 这里如果修改mgs，就把上面的覆盖了
		} finally {
			if (regisitered) {
				msg = "注册成功";
			} else if (msg == null) {
				msg = "由于未知原因导致无法加入用户，请联系网站管理员";
			}
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", msg);
			rd.forward(request, response);
		}

	}
}
