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

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	private UserDAO dao = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.getRequestDispatcher("/views/forgotPassword.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String email = req.getParameter("email");
		String id = req.getParameter("id");

		try {
			User user = dao.findById(id);
			if (user == null) {
				req.setAttribute("message", "Tên đăng nhập không tồn tại");
			} else if (!user.getAdmin().equals(email)) {
				req.setAttribute("message", "Email không khớp với tài khoản");
			} else {
				req.setAttribute("message", "Mật khẩu của bạn là: " + user.getPassword());
				req.setAttribute("messageType", "success");
			}
		} catch (Exception e) {
		
			e.printStackTrace();
			req.setAttribute("message", "Lỗi: " + e.getMessage());
		}
		req.getRequestDispatcher("/views/forgotPassword.jsp").forward(req, resp);
	}
}
