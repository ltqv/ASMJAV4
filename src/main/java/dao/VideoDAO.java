package dao;

import java.util.List;
import entity.Video;

public interface VideoDAO {
    List<Video> findAll(); // <--- bắt buộc có method này
    Video findById(String id);
    void create(Video video);
    void update(Video video);
    void delete(String id);
}
