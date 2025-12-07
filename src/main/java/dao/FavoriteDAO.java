package dao;
import java.util.List;
import entity.Favorite;

public interface FavoriteDAO {
    List<Favorite> findByUserId(String userId);
    Favorite findByUserAndVideo(String userId, String videoId);
    void create(Favorite favorite);
    void delete(Long id);
    
    // MỚI: Thống kê mỗi video có bao nhiêu lượt thích
    List<Object[]> getLikeStats(); 
    
    // MỚI: Lấy danh sách người đã thích 1 video cụ thể
    List<Favorite> findByVideoId(String videoId);
}