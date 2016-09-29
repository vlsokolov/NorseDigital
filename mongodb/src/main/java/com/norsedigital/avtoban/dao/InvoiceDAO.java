package com.norsedigital.avtoban.dao;

import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InvoiceDAO {
	
	MongoClient mongo = new MongoClient("localhost", 27017);
	MongoDatabase db = mongo.getDatabase("Autoban");
	
	MongoCollection<Document> invoices = db.getCollection("invoices");
	
	public String createInvoice(String personId, String startPoint, Date date){		
		Document invoice = new Document();
		invoice.put("_id", personId);
		invoice.put("startPoint", startPoint);
		invoice.put("Date", date);
		invoices.insertOne(invoice);
		return personId;
	}
		
	public void deleteInvoice(String personId){
		invoices.deleteOne(new Document("_id", personId));
	}
	
	public Document getInvoiceById(String personId){
		Document searchQuery = new Document("_id", personId);
		FindIterable<Document> cursor = invoices.find(searchQuery);
		return cursor.first();
	}	
}
