package com.app.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {

	@PersistenceContext EntityManager entityManager;
	
	public List<Object> getBoards() {
		Query query = entityManager.createNativeQuery("select board_name from ssc_examboard_mst");
		return query.getResultList();
	}
	
}
