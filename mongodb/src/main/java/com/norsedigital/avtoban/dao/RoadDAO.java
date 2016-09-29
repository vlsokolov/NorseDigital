package com.norsedigital.avtoban.dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RoadDAO {
	
	MongoClient mongo = new MongoClient("localhost", 27017);
	MongoDatabase db = mongo.getDatabase("Autoban");
	
	MongoCollection<Document> roads = db.getCollection("roads");
	
	public ObjectId createRoad(String startPoint, String endPoint, int value){
		Document road = new Document();
		road.put("startPoint", startPoint);
		road.put("endPoint", endPoint);
		road.put("value", value);
		roads.insertOne(road);
		ObjectId id = (ObjectId) road.get("_id");
		return id;
	}
	
	public void updateRoad(String id, String startPoint, String endPoint, int value){
		roads.updateOne(new Document("_id", new ObjectId(id)), new Document("$set", new Document("startPoint", startPoint)
					.append("endPoint", endPoint).append("value", value)));
	}
	
	public void deleteRoad(String id){
		roads.deleteOne(new Document("_id", new ObjectId(id)));
	}

	public Document getRoadByPointsName(String startPoint, String endPoint){		
		Document searchQuery = new Document("startPoint", startPoint).append("endPoint", endPoint);
		Document searchQuery1 = new Document("startPoint", endPoint).append("endPoint", startPoint);
		BasicDBList or = new BasicDBList();
		or.add(searchQuery);
		or.add(searchQuery1);
		Document query = new Document("$or", or);
		FindIterable<Document> cursor = roads.find(query);
		return cursor.first(); 	
	}		
	
	public Document getById(String id){		
		Document searchQuery = new Document();
		searchQuery.put("_id", new ObjectId(id));
		FindIterable<Document> cursor = roads.find(searchQuery);			
		return cursor.first();				 
	} 
}
