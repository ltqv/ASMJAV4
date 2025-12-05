package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class XJPA {
	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("asmJAV4");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public static void main(String[] args) {
		try {
			EntityManager em = XJPA.getEntityManager();
			System.out.println("connect finish");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("connect fail");
		}
	}
}
