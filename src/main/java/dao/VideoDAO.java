package dao;
import java.util.List;
import entity.Video;
import entity.Share; // Import entity Share

public interface VideoDAO {
    List<Video> findAll();
    Video findById(String id);
    void create(Video video);
    void update(Video video);
    void delete(String id);
    List<Video> findTopViews(int top);
    void incrementViews(String id);
    
    // MỚI: Lấy danh sách chia sẻ của một video cụ thể
    List<Share> findSharesByVideoId(String videoId);
}