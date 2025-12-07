package servlet;

import java.io.IOException;

import dao.VideoDAO;
import dao.impl.VideoDAOImpl;
import entity.User;
import entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;
@WebServlet("/admin/video/crud")
public class VideoCrudServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VideoDAO dao = new VideoDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Đảm bảo request là UTF-8
        req.setCharacterEncoding("UTF-8");

        // Kiểm tra quyền Admin
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !Boolean.TRUE.equals(currentUser.getAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/login?message=AccessDenied");
            return;
        }

        String action = req.getParameter("action");
        String videoId = req.getParameter("id");
        
        try {
            switch (action) {
                case "create":
                    createVideo(req);
                    break;
                case "update":
                    updateVideo(req, videoId);
                    break;
                case "delete":
                    deleteVideo(req, videoId);
                    break;
                default:
                    throw new Exception("Hành động không hợp lệ.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("adminMessage", "Lỗi: " + e.getMessage());
            session.setAttribute("adminMessageType", "danger");
        }

        // Chuyển hướng về trang danh sách sau khi xử lý CRUD
        resp.sendRedirect(req.getContextPath() + "/admin/video/list");
    }

    private void createVideo(HttpServletRequest req) throws Exception {
        Video video = new Video();
        video.setId(req.getParameter("id"));
        
        // Kiểm tra ID có tồn tại chưa
        if (dao.findById(video.getId()) != null) {
            throw new Exception("ID video đã được sử dụng!");
        }
        
        bindVideo(req, video, "create");
        video.setViews(0); // Mặc định views khi tạo mới
        dao.create(video);
        
        req.getSession().setAttribute("adminMessage", "Tạo video mới thành công!");
        req.getSession().setAttribute("adminMessageType", "success");
    }

    private void updateVideo(HttpServletRequest req, String id) throws Exception {
        Video video = dao.findById(id);
        if (video == null) {
            throw new Exception("Không tìm thấy video để cập nhật.");
        }
        
        bindVideo(req, video, "update");
        dao.update(video);
        
        req.getSession().setAttribute("adminMessage", "Cập nhật video thành công!");
        req.getSession().setAttribute("adminMessageType", "success");
    }

 // Thêm tham số HttpServletRequest req
    private void deleteVideo(HttpServletRequest req, String id) throws Exception {
        Video video = dao.findById(id);
        if (video == null) {
             throw new Exception("Không tìm thấy video để xoá.");
        }
        // Xoá mềm	
        video.setActive(false);
        dao.update(video); 

        // Bây giờ dòng này sẽ hết lỗi đỏ
        req.getSession().setAttribute("adminMessage", "Xoá video thành công (đã set Active = false).");
        req.getSession().setAttribute("adminMessageType", "success");
    }
    
    // Phương thức chung để lấy dữ liệu từ request và gán vào entity
 // 1. Phương thức lấy dữ liệu từ form và xử lý tự động
    private void bindVideo(HttpServletRequest req, Video video, String action) {
        String title = req.getParameter("title");
        String rawLink = req.getParameter("link"); // Link gốc người dùng paste vào
        String poster = req.getParameter("poster");
        String description = req.getParameter("description");
        
        // Xử lý Active
        String activeParam = req.getParameter("active");
        boolean active = activeParam != null && activeParam.equals("true");

        // --- LOGIC MỚI: TỰ ĐỘNG XỬ LÝ LINK VÀ POSTER ---
        if (rawLink != null && !rawLink.isEmpty()) {
            String youtubeId = getYouTubeId(rawLink);
            
            if (youtubeId != null && !youtubeId.isEmpty()) {
                // Tự động chuyển thành link Embed để xem được trên web
                video.setLink("https://www.youtube.com/embed/" + youtubeId);
                
                // Nếu người dùng KHÔNG nhập poster, hoặc muốn tự động lấy poster từ Youtube
                // (Ưu tiên: Nếu ô poster trống thì tự lấy. Nếu muốn luôn tự lấy thì bỏ check rỗng)
                if (poster == null || poster.trim().isEmpty()) {
                    // Lấy ảnh thumbnail chất lượng cao nhất (maxresdefault)
                    video.setPoster("https://img.youtube.com/vi/" + youtubeId + "/maxresdefault.jpg");
                } else {
                    video.setPoster(poster);
                }
            } else {
                // Nếu không nhận diện được ID Youtube, giữ nguyên link cũ
                video.setLink(rawLink);
                video.setPoster(poster);
            }
        }
        // ------------------------------------------------

        video.setTitle(title);
        video.setDescription(description);
        video.setActive(active);
    }
    
    private String getYouTubeId(String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}