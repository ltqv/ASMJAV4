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
import jakarta.servlet.http.HttpSession; // Thêm import

@WebServlet({
    "/home",
    "/admin/videoManager",
    "/admin/userManager",
    "/admin/report",
    "/admin/video/list",
    "/admin/video/details" ,
    "/trending",  // <--- THÊM DÒNG NÀY
    "/favorites"// Thêm cho chỉnh sửa/tạo mới
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

        // Lấy và xóa flash message (thông báo) từ session
        HttpSession session = req.getSession();
        String adminMessage = (String) session.getAttribute("adminMessage");
        String adminMessageType = (String) session.getAttribute("adminMessageType");
        if (adminMessage != null) {
            req.setAttribute("message", adminMessage);
            req.setAttribute("messageType", adminMessageType);
            session.removeAttribute("adminMessage");
            session.removeAttribute("adminMessageType");
        }


        // -------------------------
        // XỬ LÝ TRANG ADMIN
        // -------------------------
        if (uri.contains("/admin/")) {

            User user = (User) session.getAttribute("currentUser");

            if (user == null || !Boolean.TRUE.equals(user.getAdmin())) { // Sửa check admin
                resp.sendRedirect(req.getContextPath() + "/login?message=AccessDenied");
                return;
            }

            layout = "/views/adminPageLayout.jsp";
            
            // Lấy danh sách video (cho cả VideoList và VideoManager)
            List<Video> videoList = videoDAO.findAll();
            req.setAttribute("videos", videoList);


            if (uri.contains("/admin/videoManager")) {
                req.setAttribute("page", "/views/admin/VideoManager.jsp");
                // Mặc định hiển thị danh sách nếu không có subpage khác
                if (req.getAttribute("subpage") == null) {
                    req.setAttribute("subpage", "/views/admin/VideoList.jsp");
                }


            } else if (uri.contains("/admin/userManager")) {
                req.setAttribute("page", "/views/admin/UserManager.jsp");

            } else if (uri.contains("/admin/report")) {
                req.setAttribute("page", "/views/admin/Report.jsp");

            } else if (uri.contains("/admin/video/details")) {
                // Trang chi tiết/chỉnh sửa video (Details/Edit/Create)
                String id = req.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    // Chế độ chỉnh sửa
                    Video video = videoDAO.findById(id);
                    req.setAttribute("video", video);
                } else {
                    // Chế độ tạo mới
                    req.setAttribute("video", new Video());
                }
                
                req.setAttribute("page", "/views/admin/VideoManager.jsp");
                req.setAttribute("subpage", "/views/admin/VideoEdit.jsp"); // Dùng VideoEdit.jsp
            
            } else if (uri.contains("/admin/video/list")) {
                // Hiển thị danh sách video (GET request)
                req.setAttribute("page", "/views/admin/VideoManager.jsp");
                req.setAttribute("subpage", "/views/admin/VideoList.jsp");
            }
        } 
        
        // -------------------------
        // XỬ LÝ TRANG NGƯỜI DÙNG
        // -------------------------
        else if (uri.contains("/home")) {
            List<Video> videoList = videoDAO.findAll();
            req.setAttribute("videoList", videoList);
            req.setAttribute("page", "/views/user/home.jsp");
        } 
        // MỚI: Xử lý trang Thịnh hành
        else if (uri.contains("/trending")) {
            List<Video> trendingList = videoDAO.findTopViews(10); // Lấy top 10
            req.setAttribute("videoList", trendingList);
            req.setAttribute("page", "/views/user/home.jsp"); // Tái sử dụng giao diện home
        }
        // MỚI: Xử lý trang Yêu thích
        else if (uri.contains("/favorites")) {
            User user = (User) session.getAttribute("currentUser");
            if (user != null) {
                // Bạn cần thêm import FavoriteDAO
                dao.FavoriteDAO favDao = new dao.impl.FavoriteDAOImpl();
                List<entity.Favorite> favList = favDao.findByUserId(user.getId());
                req.setAttribute("favList", favList);
                req.setAttribute("page", "/views/user/favorites.jsp"); // Trang riêng cho yêu thích
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }

        req.getRequestDispatcher(layout).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}