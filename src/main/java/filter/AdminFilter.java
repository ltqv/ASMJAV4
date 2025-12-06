package filter;

import java.io.IOException;

import entity.User; // Đảm bảo import đúng entity User
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/admin/*"}) // Áp dụng cho mọi URL bắt đầu bằng /admin
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Lấy User từ Session
        User user = (User) req.getSession().getAttribute("user");

        if (user != null && user.getAdmin()) {
            // Đã đăng nhập và là Admin: Cho phép truy cập
            chain.doFilter(request, response);
        } else {
            // Không phải Admin hoặc chưa đăng nhập: Chặn lại
            req.getSession().setAttribute("message", "Bạn cần đăng nhập với tài khoản Admin để truy cập.");
            req.getSession().setAttribute("messageType", "danger");
            resp.sendRedirect(req.getContextPath() + "/login"); 
        }
    }

    @Override public void init(FilterConfig filterConfig) throws ServletException {}
    @Override public void destroy() {}
}