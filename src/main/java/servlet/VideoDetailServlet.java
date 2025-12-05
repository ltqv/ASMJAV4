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

        if(video == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        req.setAttribute("video", video);

        req.getRequestDispatcher("/views/VideoDetails.jsp")
           .forward(req, resp);
    }
}
