// src/main/java/dao/impl/VideoDAOImpl.java

package dao.impl;
import java.util.List;
import entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.XJPA;
import dao.VideoDAO;
// ... (loại bỏ các import trùng lặp)

public class VideoDAOImpl implements VideoDAO {

    // ❌ XÓA DÒNG NÀY: private EntityManager em = XJPA.getEntityManager(); 

    @Override
    public List<Video> findAll() {
        EntityManager em = XJPA.getEntityManager(); // ✅ Lấy EM mới
        try {
            // Optional but safe: Xóa cache của Persistence Context để đảm bảo data mới nhất
            em.clear(); 
            String jpql = "SELECT v FROM Video v WHERE v.active = true";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            return query.getResultList();
        } finally {
            em.close(); // ✅ Đóng EM
        }
    }

    @Override
    public Video findById(String id) {
        EntityManager em = XJPA.getEntityManager(); // ✅ Lấy EM mới
        try {
            return em.find(Video.class, id);
        } finally {
            em.close(); // ✅ Đóng EM
        }
    }
    
    // Sửa các phương thức còn lại để sử dụng EM cục bộ (local EM)
    
    @Override
    public void create(Video video) {
        EntityManager em = XJPA.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(video);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Video video) {
        EntityManager em = XJPA.getEntityManager();
    	EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(video);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String id) {
        EntityManager em = XJPA.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Video v = em.find(Video.class, id);
            if (v != null) em.remove(v);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
    
    // Phương thức incrementViews (giữ nguyên, vì đã đúng pattern)
    @Override
    public void incrementViews(String id) {
        EntityManager em = XJPA.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("UPDATE Video v SET v.views = v.views + 1 WHERE v.id = :id")
              .setParameter("id", id)
              .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Video> findTopViews(int top) {
        EntityManager em = XJPA.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v WHERE v.active = true ORDER BY v.views DESC";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            query.setMaxResults(top);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    @Override
    public List<Share> findSharesByVideoId(String videoId) {
        EntityManager em = XJPA.getEntityManager();
        try {
            String jpql = "SELECT s FROM Share s WHERE s.video.id = :videoId";
            TypedQuery<Share> query = em.createQuery(jpql, Share.class);
            query.setParameter("videoId", videoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}