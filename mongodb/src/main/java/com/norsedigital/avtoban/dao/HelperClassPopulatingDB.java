package com.norsedigital.avtoban.dao;

import java.util.Date;

import org.bson.types.ObjectId;

public class HelperClassPopulatingDB {
	
	public static void main(String[] args) {
		PersonDAO person = new PersonDAO();
		RoadDAO road = new RoadDAO();
		InvoiceDAO invoice = new InvoiceDAO();
		
		ObjectId personId1 = person.createPerson("John", "Doe", "johndoe@gmail.com");
		ObjectId personId2 = person.createPerson("Vladimir", "Sokolov", "vladimir.sokolov.od@gmail.com");// my real credentials
		
		road.createRoad("A", "B", 10);
		road.createRoad("C", "D", 3);
		road.createRoad("E", "F", 5);
		road.createRoad("G", "H", 4);
		road.createRoad("J", "K", 7);
		road.createRoad("E", "H", 12);
		road.createRoad("G", "K", 8);
		
		String stringId1 = personId1.toString();
		String stringId2 = personId2.toString();
		invoice.createInvoice(stringId1, "C", new Date());
		invoice.createInvoice(stringId2, "A", new Date());				
	}
}
