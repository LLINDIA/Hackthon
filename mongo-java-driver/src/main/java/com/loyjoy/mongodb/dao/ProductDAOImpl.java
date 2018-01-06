package com.loyjoy.mongodb.dao;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class ProductDAOImpl implements Products{
	private MongoOperations mongoOps;
	private static final String COLLECTION_NAME = "Images.files";
	public ProductDAOImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
		
	}
	public ProductDAO readById(String id) {
		Query query = new Query(Criteria.where("keyword").is("camera"));
		return this.mongoOps.findOne(query, ProductDAO.class, COLLECTION_NAME);
		
	}
	

}
