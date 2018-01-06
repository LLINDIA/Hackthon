package com.loyjoy.mongodb.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.loyjoy.mongodb.dao.Products;
import com.loyjoy.mongodb.dao.ProductDAO;

public class SpringMongoDBXMLMain {

	public static void main(String[] args) {
		SpringMongoDBXMLMain obj = new SpringMongoDBXMLMain();
		
		obj.getProduct();
	}

	private void getProduct() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		Products product = ctx.getBean("ProductDAO", Products.class);
		ProductDAO dao = product.readById("");
		/*List<String> keywords = new ArrayList<String>();
		keywords.add("camera");
		String output = productDAO.getKeyword();*/
//		for(Product p : output) {
//			System.out.println(p.toString());
//		}
		System.out.println(dao);
		System.out.println(dao.getDescription());
		ctx.close();
	}
}
	
	/*private void testUser() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		User usr = ctx.getBean("userDAO", User.class);
		UserDAO usrDAO = new UserDAO(null, "Tejas", "P", "tejas.p@gmail.com");
		// heelo
		// Create Record
		usr.create(usrDAO);
		System.out.println("Record Created successfully");

		// Retrive Record From DB
		System.out.println("Retriving given data");
		UserDAO usrDAO1 = usr.readById(usrDAO.getId());
		System.out.println("User data retrived :" + usrDAO1);

		// Update Record
		System.out.println("Updating the given record");
		usrDAO1.setFirst_name("David");
		usrDAO1.setLast_name("Costa");
		usr.update(usrDAO1);
		System.out.println("Record Update Successfully");

		UserDAO user = usr.readById(usrDAO1.getId());

		System.out.println("Retrived the User data after Update :" + user);*/

	

