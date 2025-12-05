package dao.impl;

import java.util.List;

import dao.UserDAO;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.XJPA;

public class UserDAOImpl implements UserDAO {

	// Tim theo id
	@Override
	public User findById(String id) {
		EntityManager em = XJPA.getEntityManager();
		try {
			return em.find(User.class, id);
		} finally {
			em.close();
		}
	}

	// Tim theo email
	@Override
	public User findByEmail(String email) {
		EntityManager em = XJPA.getEntityManager();
		try {
			String jpql = "select u from User u where u.email = :email";
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			query.setParameter("email", email);
			// Su dung stream de tranh loi~ neu khong tim thay kq
			return query.getResultStream().findFirst().orElse(null);
		} finally {
			// TODO: handle finally clause
			em.close();
		}
	}

	// Tim tat ca User
	@Override
	public List<User> findAll() {
		EntityManager em = XJPA.getEntityManager();
		try {
			String jpql = "select u from User u";
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			return query.getResultList();
		} finally {
			// TODO: handle finally clause
			em.close();
		}
	}

	// Tao moi user
	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		EntityManager em = XJPA.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(user);
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	// Cap nhat user
	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		EntityManager em = XJPA.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.merge(user);
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			throw e;
			// TODO: handle exception
		} finally {
			em.close();
		}
	}

	// Xoa theo id
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		EntityManager em = XJPA.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			User user = em.find(User.class, id);
			if (user != null) {
				em.remove(user);
			}
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

}
