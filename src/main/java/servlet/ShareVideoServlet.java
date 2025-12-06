package servlet;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import entity.User;
import entity.Video;
import dao.impl.VideoDAOImpl;

@WebServlet("/video/share")
public class ShareVideoServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailTo = req.getParameter("email");
        String videoId = req.getParameter("videoId");
        
        if(emailTo == null || videoId == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Video video = new VideoDAOImpl().findById(videoId);
        String videoLink = "http://localhost:8080" + req.getContextPath() + "/video/detail?id=" + videoId;

        // Cấu hình gửi mail
        final String username = "YOUR_EMAIL@gmail.com"; // Thay email của bạn
        final String password = "YOUR_APP_PASSWORD";    // Thay mật khẩu ứng dụng

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Chia sẻ video: " + video.getTitle());
            message.setText("Xin chào,\n\nBạn nhận được một video thú vị: " + video.getTitle() 
                    + "\nXem ngay tại: " + videoLink);

            Transport.send(message);
            req.setAttribute("message", "Đã gửi email thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
            req.setAttribute("message", "Lỗi gửi mail: " + e.getMessage());
        }
        
        req.setAttribute("video", video);
        req.setAttribute("page", "/views/user/video-detail.jsp");
        req.getRequestDispatcher("/views/userPageLayout.jsp").forward(req, resp);
    }
}