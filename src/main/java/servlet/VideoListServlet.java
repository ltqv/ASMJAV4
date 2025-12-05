package servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.VideoDAO;
import dao.impl.VideoDAOImpl;
import entity.Video;

@WebServlet("/video/*")
public class VideoListServlet extends HttpServlet {

    private VideoDAO dao = new VideoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo(); // /list hoặc /detail

        if (path == null || path.equals("/list")) {
            showList(req, resp);
        } else if (path.equals("/detail")) {
            showDetail(req, resp);
        } else {
            resp.sendError(404, "Không tồn tại đường dẫn: " + path);
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Video> list = dao.findAll();
        req.setAttribute("videos", list);

        req.getRequestDispatcher("/views/video-list.jsp").forward(req, resp);
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(400, "Thiếu id video");
            return;
        }

        Video video = dao.findById(id);
        if (video == null) {
            resp.sendError(404, "Không tìm thấy video!");
            return;
        }

        req.setAttribute("video", video);
        req.getRequestDispatcher("/views/video-detail.jsp").forward(req, resp);
    }
}
