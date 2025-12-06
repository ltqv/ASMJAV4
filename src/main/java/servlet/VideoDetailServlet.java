package servlet;

import entity.Video;
import java.io.IOException;

import dao.VideoDAO;
import dao.impl.VideoDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/video/detail")
public class VideoDetailServlet extends HttpServlet {
	
	
    private VideoDAO videoDAO = new VideoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String id = req.getParameter("id");

        if(id == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Video video = videoDAO.findById(id);
        
        // Tăng lượt xem (Optional: Bạn có thể thêm logic tăng views ở đây, 
        // nhưng tôi sẽ giữ logic cơ bản để tránh làm phức tạp thêm)

        req.setAttribute("video", video);

        // Sử dụng layout chung của người dùng
        req.setAttribute("page", "/views/user/video-detail.jsp");
        req.getRequestDispatcher("/views/userPageLayout.jsp")
           .forward(req, resp);
    }
}