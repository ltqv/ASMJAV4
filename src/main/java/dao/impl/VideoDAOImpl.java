package dao.impl;

import java.util.List;
import entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.XJPA;
import dao.VideoDAO;
import dao.VideoDAO;
public class VideoDAOImpl implements VideoDAO {

    private EntityManager em = XJPA.getEntityManager();

    @Override
    public List<Video> findAll() {
        TypedQuery<Video> query = em.createQuery("SELECT v FROM Video v WHERE v.active = true", Video.class);
        return query.getResultList();
    }

    @Override
    public Video findById(String id) {
        return em.find(Video.class, id);
    }

    @Override
    public void create(Video video) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(video);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Video video) {
    	EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(video);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            Video v = em.find(Video.class, id);
            if (v != null) em.remove(v);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
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
}
