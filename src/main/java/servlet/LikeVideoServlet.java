package servlet;

import java.io.IOException;
import java.sql.Date;
import dao.FavoriteDAO;
import dao.impl.FavoriteDAOImpl;
import dao.impl.VideoDAOImpl;
import entity.Favorite;
import entity.User;
import entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/video/like")
public class LikeVideoServlet extends HttpServlet {
    private FavoriteDAO favDao = new FavoriteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");
        String videoId = req.getParameter("id");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Favorite favorite = favDao.findByUserAndVideo(user.getId(), videoId);
        
        if (favorite == null) {
            // Chưa like -> Tạo mới
            favorite = new Favorite();
            favorite.setUser(user);
            Video video = new VideoDAOImpl().findById(videoId);
            favorite.setVideo(video);
            favorite.setLikeDate(new Date(System.currentTimeMillis()));
            favDao.create(favorite);
        } else {
            // Đã like -> Xóa (Unlike)
            favDao.delete(favorite.getId());
        }
        
        resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId);
    }
}