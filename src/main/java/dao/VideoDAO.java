package dao;
import java.util.List;
import entity.Video;

public interface VideoDAO {
    List<Video> findAll();
    Video findById(String id);
    void create(Video video);
    void update(Video video);
    void delete(String id);
    
    // MỚI: Lấy danh sách thịnh hành (Top views)
    List<Video> findTopViews(int top);
}