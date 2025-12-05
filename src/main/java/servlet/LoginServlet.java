package servlet;

import java.io.IOException;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserDAO dao = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		try {
			User user = dao.findById(id);
			if (user == null) {
				req.setAttribute("message", "Tài khoản không tồn tại");
			} else if (!user.getPassword().equals(password)) {
				req.setAttribute("message", "Sai mật khẩu!");
			} else {
				// Đăng nhập thành công lưu vào session
				HttpSession session = req.getSession();
				session.setAttribute("currentUser", user);

				// Phân quyền admin và user
				if (Boolean.TRUE.equals(user.getAdmin())) {
					resp.sendRedirect(req.getContextPath() + "/admin/videoManager");
				} else {
					resp.sendRedirect(req.getContextPath() + "/home");
				}
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			req.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
		}
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}
}
