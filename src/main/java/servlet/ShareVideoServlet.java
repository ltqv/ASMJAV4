package servlet;

import java.io.IOException;
import java.util.Properties;

import dao.impl.VideoDAOImpl;
import entity.Video;

// --- CÁC IMPORT QUAN TRỌNG ĐÃ SỬA ---
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
// -------------------------------------

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/video/share")
public class ShareVideoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Xử lý tiếng Việt cho request
        req.setCharacterEncoding("UTF-8");
        
        String emailTo = req.getParameter("recipientEmail"); // Kiểm tra lại name bên form JSP (recipientEmail hay email?)
        if (emailTo == null) emailTo = req.getParameter("email");
        
        String videoId = req.getParameter("videoId");
        
        if(emailTo == null || videoId == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Video video = new VideoDAOImpl().findById(videoId);
        // Tạo link tuyệt đối để người nhận bấm vào được
        String videoLink = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() 
                           + req.getContextPath() + "/video/detail?id=" + videoId;

        // Cấu hình gửi mail
        // LƯU Ý: Bạn cần thay bằng email thật và mật khẩu ứng dụng thật của bạn
        final String username = "testjavaxapui@gmail.com"; 
        final String password = "rvrujwir vxbojemj";    

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        // Sử dụng jakarta.mail.Authenticator (Đã sửa import)
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            
            // Tiêu đề email
            message.setSubject("Chia sẻ video: " + video.getTitle());
            
            // Nội dung email (Hỗ trợ HTML)
            String content = "<h3>Xin chào!</h3>"
                    + "<p>Bạn nhận được một video thú vị từ hệ thống LTQV: <b>" + video.getTitle() + "</b></p>"
                    + "<p>Xem ngay tại: <a href='" + videoLink + "'>" + videoLink + "</a></p>"
                    + "<br><img src='" + video.getPoster() + "' width='300' />";
            
            message.setContent(content, "text/html; charset=UTF-8");

            Transport.send(message);
            req.setAttribute("message", "Đã gửi email thành công!");
            req.setAttribute("messageType", "success");
            
        } catch (MessagingException e) {
            e.printStackTrace();
            req.setAttribute("message", "Lỗi gửi mail: " + e.getMessage());
            req.setAttribute("messageType", "danger");
        }
        
        // Trả về trang chi tiết video
        req.setAttribute("video", video);
        req.setAttribute("page", "/views/user/video-detail.jsp");
        req.getRequestDispatcher("/views/userPageLayout.jsp").forward(req, resp);
    }
}