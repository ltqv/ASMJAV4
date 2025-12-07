package dao.impl;
import java.util.ArrayList;
import java.util.List;
import dao.FavoriteDAO;
import entity.Favorite;
import util.XJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class FavoriteDAOImpl implements FavoriteDAO {

    @Override
    public List<Favorite> findByUserId(String userId) {
        EntityManager em = XJPA.getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite f WHERE f.user.id = :userId";
            TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Favorite findByUserAndVideo(String userId, String videoId) {
        EntityManager em = XJPA.getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite f WHERE f.user.id = :uid AND f.video.id = :vid";
            TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
            query.setParameter("uid", userId);
            query.setParameter("vid", videoId);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public void create(Favorite favorite) {
        EntityManager em = XJPA.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(favorite);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = XJPA.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Favorite fav = em.find(Favorite.class, id);
            if(fav != null) em.remove(fav);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally { em.close(); }
    }
    
 // THÊM: Thống kê số lượng người thích cho từng video
    @Override
    public List<Object[]> reportVideoLikes() {
        EntityManager em = XJPA.getEntityManager();
        try {
            // JPQL group by video.id và count
            String jpql = "SELECT f.video.id, f.video.title, COUNT(f) FROM Favorite f GROUP BY f.video.id, f.video.title ORDER BY COUNT(f) DESC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Object[]> getLikeStats() {
        EntityManager em = XJPA.getEntityManager();
        try {
            // Thống kê: Tiêu đề Video - Số lượt thích - ID Video
            String jpql = "SELECT f.video.title, COUNT(f), f.video.id FROM Favorite f GROUP BY f.video.title, f.video.id";
            return em.createQuery(jpql, Object[].class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Favorite> findByVideoId(String videoId) {
        EntityManager em = XJPA.getEntityManager();
        try {
            String jpql = "SELECT f FROM Favorite f WHERE f.video.id = :vid";
            TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
            query.setParameter("vid", videoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}