package com.th.mail;

import java.util.Date;
import java.util.Properties;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class OneMail {
	
	private String host;
	private String port="25";
	private String sender;
	private String receiver;
	private String username;
	private String password;
	private String title;
	private String text;
	

	 
	 
 
	    public void sethost(String host) {   
	      this.host = host;   
	    }  

	    public void setport(String port) {   
	      this.port = port;   
	    }  
	    
	    

	    public void setsender(String sender) {   
	      this.sender = sender;   
	    }  
 
	    public void setpassword(String password) {   
	      this.password = password;   
	    }  

	    public void setreceiver(String receiver) {   
	      this.receiver = receiver;   
	    }   

	    public void setusername(String username) {   
	      this.username = username;   
	    }  

	    public void settitle(String title) {   
	      this.title = title;   
	    }  
 
	    public void settext(String text) {   
	      this.text = text;   
	    }   
	    
	    
	    public void sendmail() {   
		      
		       
		      Properties prop = new Properties();   
		      prop.put("mail.smtp.host", this.host);   
		      prop.put("mail.smtp.port", this.port); 
		      prop.put("mail.smtp.auth", "true");    
		         
		      try {   

		      Message message = new MimeMessage(Session.getInstance(prop,new Authenticator() {
		    	  @Override
		    	protected PasswordAuthentication getPasswordAuthentication() {
		    		// TODO Auto-generated method stub
		    		return new PasswordAuthentication(username, password);
		    	}
			}));   
		      
		      message.setFrom(new InternetAddress(sender));   
		      
		      message.setRecipient(Message.RecipientType.TO,new InternetAddress(receiver));   

		      message.setSubject(title);   

		      message.setSentDate(new Date());   
   
		       
		      message.setText(text);   

		      Transport.send(message);  
		      
		      } catch (MessagingException ex) {   
		          ex.printStackTrace();   
		      }   
		      
		    }   
	

}
