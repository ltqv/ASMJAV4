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

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	private UserDAO dao = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.getRequestDispatcher("/views/changePassword.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("currentUser");


		if (currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String currentPass = req.getParameter("currentPass");
		String newPass = req.getParameter("newPass");
		String confirmPass = req.getParameter("confirmPass");

		try {
			if (!currentUser.getPassword().equals(currentPass)) {
				req.setAttribute("message", "Mật khẩu hiện tại không đúng!");
			} else if (!newPass.equals(confirmPass)) {
				req.setAttribute("message", "Mật khẩu xác nhận không khớp!");
			} else {
		
				currentUser.setPassword(newPass);
				dao.update(currentUser);
				req.setAttribute("message", "Đổi mật khẩu thành công!");
				req.setAttribute("messageType", "success");
			}

		} catch (Exception e) {
			// TODO: handle exception
			req.setAttribute("message", "Lỗi: " + e.getMessage());
		}
		req.getRequestDispatcher("/views/changePassword.jsp").forward(req, resp);
	}

}
