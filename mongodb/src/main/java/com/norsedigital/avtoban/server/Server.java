package com.norsedigital.avtoban.server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bson.Document;

import com.norsedigital.avtoban.dao.InvoiceDAO;
import com.norsedigital.avtoban.dao.PersonDAO;
import com.norsedigital.avtoban.dao.RoadDAO;

public class Server {
	
	   private static String personId;
	   private static String checkPoint;
	   private static String firstCheckPoint;
	   private static double millisecondsTime, millisecondsHour, payTimeInHours, totalCost, costPerHour;
	   private static Date firstDate;
	   private static Date newDate;
	   
	   public static void main(String[] arg) {
	     int port = 3000; 
	       try {
	         ServerSocket ss = new ServerSocket(port); 
	         Socket socket = ss.accept(); 
	         	 
	         InputStream sin = socket.getInputStream();
	         DataInputStream in = new DataInputStream(sin);
	        
	         String line = null;
	         while(true) {
	           line = in.readUTF(); 
	           personId = line.substring(0, line.indexOf(" "));
	           checkPoint = line.substring(line.lastIndexOf(" ") + 1);
	           String date = (line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" ")));
	           Long longDate = Long.valueOf(date);
	           newDate = new Date(longDate);
	           
	           checkInvoice(personId);
	         }
	      } catch(Exception x) {
	    	  x.printStackTrace(); 
	      } 
	   }
	   
	   public static void checkInvoice(String personId){
		   		      		   
		   InvoiceDAO invoice = new InvoiceDAO();
		   RoadDAO road = new RoadDAO();
		   		      
		   Document check = invoice.getInvoiceById(personId);
		   if (check == null){
			   invoice.createInvoice(personId, checkPoint, newDate);
		   } else {
			   firstCheckPoint = check.getString("startPoint");
			   firstDate = check.getDate("Date");
			   millisecondsTime = (double)(newDate.getTime() - firstDate.getTime());
			   millisecondsHour = 1000 * 60 * 60;
			   payTimeInHours = new BigDecimal(millisecondsTime/millisecondsHour).setScale(2, RoundingMode.HALF_UP).doubleValue();
			   Document currentRoad = road.getRoadByPointsName(firstCheckPoint, checkPoint);
			   costPerHour = (double) currentRoad.getInteger("value");
			   totalCost = new BigDecimal(payTimeInHours * costPerHour).setScale(2, RoundingMode.HALF_UP).doubleValue();
			   
			   sendEmail();			   
			   invoice.deleteInvoice(personId);
		   }
	   }
	   
	   public static void sendEmail(){
		   
		    PersonDAO person = new PersonDAO();
		    Document customer = person.getById(personId);
		    String firstName = customer.getString("firstName");
		    String lastName = customer.getString("lastName");
		    String email = customer.getString("email");
		    String timeOnARoad = String.format("%02d:%02d:%02d", 
		    		TimeUnit.MILLISECONDS.toHours((long) millisecondsTime),
		    		TimeUnit.MILLISECONDS.toMinutes((long) millisecondsTime) -  
		    		TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours((long) millisecondsTime)),
		    		TimeUnit.MILLISECONDS.toSeconds((long) millisecondsTime) - 
		    		TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) millisecondsTime))); 
		    
		    final String username = "highwaymailbot@gmail.com";
	        final String password = "1a2s3d4f5g";

	        Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(email));
	            message.setSubject("Payment notification");
	            message.setText("Dear mr(ms)" + firstName + " " + lastName + ","
	                + "\n\n You was cross the " + firstCheckPoint + " at " + firstDate + " and leave the paid road at "
	                + checkPoint + " at " + newDate + "."
	                + "\n\n Total time: " + timeOnARoad
	                + "\n\n Total cost: " + totalCost + " $"
	                + "\n\n You may pay it in any convinient for you manner."
	                + "\n\n Best regards,"
	                + "\n\n Highway Inc.");

	            Transport.send(message);            

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	  }
}
	
