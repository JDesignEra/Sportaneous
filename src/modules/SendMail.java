package modules;

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
	private static final String SMTP_SERVER_HOST = "smtp.gmail.com";
	private static final String SMTP_SERVER_PORT = "587";
	private static final String SMTP_USERNAME = "joel.jdesignera.dev@gmail.com";
	private static final String FROM_USER_EMAIL = "admin@sportaneous.com";
	private static final String FROM_USER_FULL_NAME = "Sportaneous";
	private static final String REFRESH_TOKEN = "1/dHSTRJsFN4JQdXPmvxImmfNh-Mg5IEzavoDdTK9044U";
	private static final String CLIENT_ID = "270557509619-0ug5hcbibgktfqk8fo0c3icjo8akq3i3.apps.googleusercontent.com";
	private static final String CLIENT_SEC = "STHvzXAXcfVKqx0dNijYKYW6";

	public void send(String toEmail, String subject, String body) {
		try {
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", SMTP_SERVER_PORT);
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM_USER_EMAIL, FROM_USER_FULL_NAME));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject(subject);
			msg.setContent(body, "text/html");

			SMTPTransport transport = new SMTPTransport(session, null);
			transport.connect(SMTP_SERVER_HOST, SMTP_USERNAME, null);
			transport.issueCommand(
					"AUTH XOAUTH2 " + new String(BASE64EncoderStream.encode(String.format("user=%s\1auth=Bearer %s\1\1", SMTP_USERNAME, refreshAccessToken()).getBytes())), 235);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		}
		catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public String refreshAccessToken() {
		try {
			TokenResponse response = new GoogleRefreshTokenRequest(new NetHttpTransport(), new JacksonFactory(), REFRESH_TOKEN, CLIENT_ID, CLIENT_SEC).execute();
			return response.getAccessToken();
		}
		catch (IOException e) {
			e.getStackTrace();
		}

		return null;
	}
}