	package servlet;

import java.io.IOException;
import java.util.List;

import dao.VideoDAO;
import dao.impl.VideoDAOImpl;
import entity.User;
import entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
    "/home",
    "/admin/videoManager",
    "/admin/userManager",
    "/admin/report",
    "/admin/video/list",
    "/admin/video/details"
})
public class Index extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;

    public Index() {
        videoDAO = new VideoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        String layout = "/views/userPageLayout.jsp";

        // -------------------------
        // XỬ LÝ TRANG ADMIN
        // -------------------------
        if (uri.contains("/admin/")) {

            User user = (User) req.getSession().getAttribute("currentUser");

            if (user == null || !user.getAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/login?message=AccessDenied");
                return;
            }

            layout = "/views/adminPageLayout.jsp";

            if (uri.contains("/admin/videoManager")) {
                req.setAttribute("page", "/views/admin/VideoManager.jsp");

            } else if (uri.contains("/admin/userManager")) {
                req.setAttribute("page", "/views/admin/UserManager.jsp");

            } else if (uri.contains("/admin/report")) {
                req.setAttribute("page", "/views/admin/Report.jsp");

            } else if (uri.contains("/admin/video/details")) {
                req.setAttribute("page", "/views/admin/VideoManager.jsp");
                req.setAttribute("subpage", "/views/admin/VideoDetails.jsp");

            } else if (uri.contains("/admin/video/list")) {
                req.setAttribute("page", "/views/admin/VideoManager.jsp");
                req.setAttribute("subpage", "/views/admin/VideoList.jsp");
            }

        } 
        
        // -------------------------
        // XỬ LÝ TRANG NGƯỜI DÙNG
        // -------------------------
        else if (uri.contains("/home")) {

            // Lấy danh sách video
            List<Video> videoList = videoDAO.findAll();

            // Gửi sang view
            req.setAttribute("videoList", videoList);

            req.setAttribute("page", "/views/user/home.jsp");
        }

        req.getRequestDispatcher(layout).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
