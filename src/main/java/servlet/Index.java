package servlet;

import java.io.IOException;
import java.util.List;

import dao.FavoriteDAO;
import dao.UserDAO;
import dao.VideoDAO;
import dao.impl.FavoriteDAOImpl;
import dao.impl.UserDAOImpl;
import dao.impl.VideoDAOImpl;
import entity.User;
import entity.Video;
import entity.Favorite;
import entity.Share;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet({
    "/home",
    "/admin/videoManager",
    "/admin/userManager",
    "/admin/report",
    "/admin/video/list",
    "/admin/video/details",
    "/trending",
    "/favorites"

})
public class Index extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO = new VideoDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();
    private FavoriteDAO favDAO = new FavoriteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        String layout = "/views/userPageLayout.jsp";
        
        // --- Xử lý thông báo (Flash Message) ---
        HttpSession session = req.getSession();
        String adminMessage = (String) session.getAttribute("adminMessage");
        String adminMessageType = (String) session.getAttribute("adminMessageType");
        if (adminMessage != null) {
            req.setAttribute("message", adminMessage);
            req.setAttribute("messageType", adminMessageType);
            session.removeAttribute("adminMessage");
            session.removeAttribute("adminMessageType");
        }

        // ============================================
        //              KHU VỰC ADMIN
        // ============================================
        if (uri.contains("/admin/")) {
            User user = (User) session.getAttribute("currentUser");
            if (user == null || !Boolean.TRUE.equals(user.getAdmin())) {
                resp.sendRedirect(req.getContextPath() + "/login?message=AccessDenied");
                return;
            }

            layout = "/views/adminPageLayout.jsp";

            // 1. QUẢN LÝ VIDEO (VIDEO MANAGER)
            if (uri.contains("/admin/video")) {
                List<Video> videoList = videoDAO.findAll();
                req.setAttribute("videos", videoList);
                
                if (uri.contains("videoManager") || uri.contains("video/list")) {
                    req.setAttribute("page", "/views/admin/VideoManager.jsp");
                    // Mặc định load list nếu không có subpage
                    if(req.getAttribute("subpage") == null) {
                        req.setAttribute("subpage", "/views/admin/VideoList.jsp");
                    }
                } 
                else if (uri.contains("video/details")) {
                    String id = req.getParameter("id");
                    if (id != null && !id.isEmpty()) {
                        Video v = videoDAO.findById(id);
                        req.setAttribute("video", v);
                    } else {
                        req.setAttribute("video", new Video());
                    }
                    req.setAttribute("page", "/views/admin/VideoManager.jsp");
                    req.setAttribute("subpage", "/views/admin/VideoEdit.jsp");
                }
            } 
            
            // 2. QUẢN LÝ NGƯỜI DÙNG (USER MANAGER)
            else if (uri.contains("/admin/userManager")) {
                List<User> users = userDAO.findAll();
                req.setAttribute("users", users);
                req.setAttribute("page", "/views/admin/UserManager.jsp");
            } 
            
            // 3. BÁO CÁO THỐNG KÊ (REPORT)
            else if (uri.contains("/admin/report")) {
                String type = req.getParameter("type");
                String videoId = req.getParameter("id");
                
                // Mặc định hiển thị thống kê tổng hợp Like
                if (type == null) {
                    List<Object[]> likeStats = favDAO.getLikeStats();
                    req.setAttribute("likeStats", likeStats);
                }
                
                // Chi tiết: Xem ai đã like video này
                else if ("likes".equals(type) && videoId != null) {
                    List<Favorite> favUsers = favDAO.findByVideoId(videoId);
                    req.setAttribute("favUsers", favUsers);
                    req.setAttribute("videoTitle", videoDAO.findById(videoId).getTitle());
                    req.setAttribute("showTab", "likes");
                }
                
                // Chi tiết: Xem ai đã share video này (Người gửi & Người nhận)
                else if ("shares".equals(type) && videoId != null) {
                    List<Share> shareList = videoDAO.findSharesByVideoId(videoId);
                    req.setAttribute("shareList", shareList);
                    req.setAttribute("videoTitle", videoDAO.findById(videoId).getTitle());
                    req.setAttribute("showTab", "shares");
                }
                
                req.setAttribute("page", "/views/admin/Report.jsp");
            }
        } 
        
        // ============================================
        //              KHU VỰC USER
        // ============================================
        else if (uri.contains("/home")) {
            List<Video> videoList = videoDAO.findAll();
            req.setAttribute("videoList", videoList);
            req.setAttribute("page", "/views/user/home.jsp");
        } 
        else if (uri.contains("/trending")) {
            List<Video> trendingList = videoDAO.findTopViews(10);
            req.setAttribute("videoList", trendingList);
            req.setAttribute("page", "/views/user/home.jsp");
        } 
        else if (uri.contains("/favorites")) {
            User user = (User) session.getAttribute("currentUser");
            if (user != null) {
                List<Favorite> favList = favDAO.findByUserId(user.getId());
                req.setAttribute("favList", favList);
                req.setAttribute("page", "/views/user/favorites.jsp");
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