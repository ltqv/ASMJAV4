package dao.impl;

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
}