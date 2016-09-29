package com.norsedigital.avtoban.server;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class CheckPointH {
	public static void main(String[] arg) {
        int serverPort = 3000; 
        String address = "127.0.0.1"; 
        String personId = "57eb719abdae020ab4397da9";
        try {
            InetAddress ipAddress = InetAddress.getByName(address); 
            Socket socket = new Socket(ipAddress, serverPort); 
                       
            OutputStream sout = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(sout);

            // For our testing purpose we create this boolean variable and start only one 
            // iteration of the loop, but in real project it will be eternal loop
            boolean check = true;
            
            while (check) {            	            	
                String message = (personId + " " + new Date().getTime() + " H");
                out.writeUTF(message); 
                out.flush(); 
                check = false;                
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
