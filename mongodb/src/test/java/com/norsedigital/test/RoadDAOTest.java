package com.norsedigital.test;

import static org.junit.Assert.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.norsedigital.avtoban.dao.RoadDAO;

public class RoadDAOTest {

	ObjectId id = null;
	String stringId = null;
	RoadDAO road = new RoadDAO();
		
	@Test
	public void createRoadTest(){
		String startPoint = "A";
		String endPoint = "B";
		int value = 5;
		
		id = road.createRoad(startPoint, endPoint, value);
		stringId = id.toString();
		assertNotNull(stringId);
	}
	
	@Test
	public void updateRoadTest(){
		String startPoint = "A";
		String endPoint = "B";
		int value = 5;
		
		id = road.createRoad(startPoint, endPoint, value);
		stringId = id.toString();
		assertNotNull(stringId);
		
		String updatedStartPoint = "C";
		String updatedEndPoint = "D";
		int updatedValue = 10;
		
		road.updateRoad(stringId, updatedStartPoint, updatedEndPoint, updatedValue);
		
		assertEquals(updatedStartPoint, road.getById(stringId).getString("startPoint"));
		assertEquals(updatedEndPoint, road.getById(stringId).getString("endPoint"));
		assertEquals(updatedValue, road.getById(stringId).get("value"));
	}
	
	@Test
	public void getRoadByPointsNameTest(){
		String startPoint = "A";
		String endPoint = "B";
		int value = 10;
		
		id = road.createRoad(startPoint, endPoint, value);
		stringId = id.toString();
		assertNotNull(stringId);
		
		Document roadDB = road.getRoadByPointsName(startPoint, endPoint);
		
		assertEquals(startPoint, roadDB.getString("startPoint"));
		assertEquals(endPoint, roadDB.getString("endPoint"));
		assertEquals(value, roadDB.get("value"));
		
		Document roadDBReverse = road.getRoadByPointsName(endPoint, startPoint);
		
		assertEquals(startPoint, roadDBReverse.getString("startPoint"));
		assertEquals(endPoint, roadDBReverse.getString("endPoint"));
		assertEquals(value, roadDBReverse.get("value"));
	}
	
	@Test
	public void deleteRoadTest(){
		String startPoint = "A";
		String endPoint = "B";
		int value = 10;
		
		id = road.createRoad(startPoint, endPoint, value);
		stringId = id.toString();
		assertNotNull(stringId);
		
		road.deleteRoad(stringId);
		
		assertNull(road.getById(stringId));
	}

}
