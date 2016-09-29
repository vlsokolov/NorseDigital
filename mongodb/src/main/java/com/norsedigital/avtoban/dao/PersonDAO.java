package com.norsedigital.avtoban.dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PersonDAO{
		
	MongoClient mongo = new MongoClient("localhost", 27017);
	MongoDatabase db = mongo.getDatabase("Autoban");
	
	MongoCollection<Document> persons = db.getCollection("persons");
	
	public ObjectId createPerson(String firstName, String lastName, String email){		
		Document personMongoDB = new Document();
		personMongoDB.put("firstName", firstName);
		personMongoDB.put("lastName", lastName);
		personMongoDB.put("email", email);
		persons.insertOne(personMongoDB);	
		ObjectId id = (ObjectId) personMongoDB.get("_id");
		return id;		
	}
	
	public void deletePerson(String id){		
		persons.deleteOne(new Document("_id", new ObjectId(id)));		
	}
	
	public void updatePerson(String id, String firstName, String lastName, String email){
		persons.updateOne(new Document("_id", new ObjectId(id)), new Document("$set", new Document("firstName", firstName)
				.append("lastName", lastName).append("email", email)));
	}
	
	public Document getById(String id){		
			Document searchQuery = new Document();
			searchQuery.put("_id", new ObjectId(id));
			FindIterable<Document> cursor = persons.find(searchQuery);			
			return cursor.first();				 
	} 	
}
