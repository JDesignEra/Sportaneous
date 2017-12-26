package application.assets.modules;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;

public class SendMail {
	public void send(String toEmail, String subject, String body) {
		String smtpServerHost = "smtp.gmail.com";
		String smtpServerPort = "587";
		String smtpUserName = "joel.jdesignera.dev@gmail.com";
		String smtpUserAccessToken = refreshAccessToken();
		String fromUserEmail = "joel.jdesignera.dev@gmail.com";
		String fromUserFullName = "Sportaneous";
		
        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", smtpServerPort);
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromUserEmail, fromUserFullName));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(subject);
            msg.setContent(body, "text/html");

            SMTPTransport transport = new SMTPTransport(session, null);
            transport.connect(smtpServerHost, smtpUserName, null);
            transport.issueCommand("AUTH XOAUTH2 " + new String(BASE64EncoderStream.encode(String.format("user=%s\1auth=Bearer %s\1\1", smtpUserName, smtpUserAccessToken).getBytes())), 235);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
        catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
	
	public static String refreshAccessToken() {
		try {
			TokenResponse response = new GoogleRefreshTokenRequest(new NetHttpTransport(), new JacksonFactory(), "1/dHSTRJsFN4JQdXPmvxImmfNh-Mg5IEzavoDdTK9044U", "270557509619-0ug5hcbibgktfqk8fo0c3icjo8akq3i3.apps.googleusercontent.com", "STHvzXAXcfVKqx0dNijYKYW6").execute();
			return response.getAccessToken();
		}
		catch (IOException e) {
			e.getStackTrace();
		}
		return null;
	}
}