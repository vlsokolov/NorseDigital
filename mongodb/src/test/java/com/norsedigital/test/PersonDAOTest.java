package com.norsedigital.test;

import static org.junit.Assert.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.norsedigital.avtoban.dao.PersonDAO;

public class PersonDAOTest {

	ObjectId id = null;
	String stringId = null;
	PersonDAO person = new PersonDAO();
		
	@Test
	public void createPersonTest() {
		String testFirstName = "testFirstName";
		String testLastName = "testLastName";
		String testEmail = "testEmail";		
		
		id = person.createPerson(testFirstName, testLastName, testEmail);
		stringId = id.toString();
		assertNotNull(stringId);	
	}
	
	@Test
	public void updatePersonTest(){
		
		String testFirstName = "testFirstName";
		String testLastName = "testLastName";
		String testEmail = "testEmail";		
		
		id = person.createPerson(testFirstName, testLastName, testEmail);
		stringId = id.toString();
		assertNotNull(stringId);
		
		String updateFirstName = "updateFirstName";
		String updateLastName = "updateLastName";
		String updateEmail = "updateEmail";
		
		person.updatePerson(stringId, updateFirstName, updateLastName, updateEmail);
		
		assertEquals(updateFirstName, person.getById(stringId).getString("firstName"));
		assertEquals(updateLastName, person.getById(stringId).getString("lastName"));
		assertEquals(updateEmail, person.getById(stringId).getString("email"));
	}

	@Test
	public void getPersonByIdTest(){
		
		String testFirstName = "testFirstName";
		String testLastName = "testLastName";
		String testEmail = "testEmail";		
		
		id = person.createPerson(testFirstName, testLastName, testEmail);
		stringId = id.toString();
		assertNotNull(stringId);
		
		Document personFromDB = person.getById(stringId);
		assertEquals(testFirstName,personFromDB.getString("firstName"));
		assertEquals(testLastName,personFromDB.getString("lastName"));
		assertEquals(testEmail,personFromDB.getString("email"));
	}
	
	@Test
	public void deletePersonTest(){
		String testFirstName = "testFirstName";
		String testLastName = "testLastName";
		String testEmail = "testEmail";		
		
		id = person.createPerson(testFirstName, testLastName, testEmail);
		stringId = id.toString();
		assertNotNull(stringId);
		
		person.deletePerson(stringId);
		assertNull(person.getById(stringId));		
	}
}
