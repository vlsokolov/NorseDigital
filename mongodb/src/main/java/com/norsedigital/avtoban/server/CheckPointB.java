package com.norsedigital.avtoban.server;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class CheckPointB {
	public static void main(String[] arg) {
        int serverPort = 3000; 
        String address = "127.0.0.1"; 
        String personId = "57ece1db93c58c13447efbc8";
        try {
            InetAddress ipAddress = InetAddress.getByName(address); 
            Socket socket = new Socket(ipAddress, serverPort); 
                       
            OutputStream sout = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(sout);

            boolean check = true;
            
            while (check) {            	     
                String message = (personId + " " + new Date().getTime() + " B");
                out.writeUTF(message); 
                out.flush(); 
                check = false;                
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
