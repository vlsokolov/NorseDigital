package com.norsedigital.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.norsedigital.avtoban.dao.InvoiceDAO;

public class InvoiceDAOTest {
	
	ObjectId id = null;
	String stringId = null;
	InvoiceDAO invoice = new InvoiceDAO();
	String personId = "57eb719abdae020ab4397d56";

	@Test
	public void createInvoiceTest(){
		
		String startPoint = "A";
		
		stringId = invoice.createInvoice(personId, startPoint, new Date());
			
		assertNotNull(stringId);
	}
	
	@Test
	public void getInvoiceById(){
		Document invoiceDB = invoice.getInvoiceById(personId);
		
		assertNotNull(invoiceDB);
	}

	@Test
	public void deleteInvoiceTest(){
						
		invoice.deleteInvoice(personId);
		
		assertNull(invoice.getInvoiceById(personId));
	}
	
	
	
}
