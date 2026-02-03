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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserDAO dao = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			User user = new User();
			user.setId(req.getParameter("id"));
			user.setPassword(req.getParameter("password"));
			user.setFullname(req.getParameter("fullname"));
			user.setEmail(req.getParameter("email"));
			user.setAdmin(false);

			if (dao.findById(user.getId()) != null) {
				req.setAttribute("message", "Tên đăng nhập đã được sử dụng!");
				req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
				return;
			}

			if (dao.findByEmail(user.getEmail()) != null) {
				req.setAttribute("message", "Email đã được sử dụng");
				req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
				return;
			}

			dao.create(user);

			// ✅ QUAN TRỌNG
			resp.sendRedirect(req.getContextPath() + "/login");
			return;

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Lỗi đăng ký: " + e.getMessage());
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
		}
	}

}
