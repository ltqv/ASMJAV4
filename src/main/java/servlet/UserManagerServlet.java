package servlet;

import java.io.IOException;
import java.util.List;
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/user/list") 
public class UserManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO dao = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Tải danh sách người dùng
        List<User> userList = dao.findAll();
        req.setAttribute("users", userList);
        
        // Chuyển tiếp tới trang quản lý (đặt trong subpage của VideoManager/AdminLayout)
        req.setAttribute("page", "/views/admin/VideoManager.jsp"); 
        req.setAttribute("subpage", "/views/admin/UserList.jsp"); // Tạo JSP này ở bước tiếp theo
        req.getRequestDispatcher("/views/adminPageLayout.jsp").forward(req, resp);
    }
    // Thêm doPost để xử lý xóa/sửa sau này nếu cần
}