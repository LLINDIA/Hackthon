package com.loyjoy.mongodb.dao;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class UserDAOImpl implements User {

	private MongoOperations mongoOps;
	private static final String COLLECTION_NAME = "usr_user";

	public UserDAOImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}

	public void create(UserDAO usr) {
		this.mongoOps.insert(usr, COLLECTION_NAME);

	}

	public UserDAO readById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return this.mongoOps.findOne(query, UserDAO.class, COLLECTION_NAME);
	}

	public void update(UserDAO usr) {
		this.mongoOps.save(usr, COLLECTION_NAME);

	}

}
