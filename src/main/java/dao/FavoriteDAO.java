package dao;
import java.util.List;
import entity.Favorite;

public interface FavoriteDAO {
    List<Favorite> findByUserId(String userId); // Lấy danh sách video đã like của user
    Favorite findByUserAndVideo(String userId, String videoId); // Kiểm tra xem đã like chưa
    void create(Favorite favorite);
    void delete(Long id);
}