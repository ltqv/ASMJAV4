package servlet;

import java.io.IOException;
import dao.VideoDAO;
import dao.impl.VideoDAOImpl;
import dao.FavoriteDAO;
import dao.impl.FavoriteDAOImpl;
import entity.Video;
import entity.User;
import entity.Favorite;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/video/detail")
public class VideoDetailServlet extends HttpServlet {
    private VideoDAO videoDAO = new VideoDAOImpl();
    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String id = req.getParameter("id");
        Video video = videoDAO.findById(id);

        if (video != null) {
            // 1. Tăng view (SỬA ĐỔI - Sử dụng atomic update và reload)
            try {
                // Tăng view một cách an toàn (atomic update)
                videoDAO.incrementViews(video.getId()); 
                
                // Tải lại đối tượng Video để có view count mới nhất
                video = videoDAO.findById(id); 
            } catch (Exception e) {
                // In ra lỗi nhưng vẫn tiếp tục hiển thị video
                e.printStackTrace();
            }

            // 2. Kiểm tra user đã like chưa (nếu đã đăng nhập)
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("currentUser");
            boolean isLiked = false;
            if (user != null) {
                Favorite fav = favoriteDAO.findByUserAndVideo(user.getId(), video.getId());
                if (fav != null) isLiked = true;
            }
            req.setAttribute("isLiked", isLiked);
            req.setAttribute("video", video);
            
            // Chuyển hướng
            req.setAttribute("page", "/views/user/video-detail.jsp");
            req.getRequestDispatcher("/views/userPageLayout.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}