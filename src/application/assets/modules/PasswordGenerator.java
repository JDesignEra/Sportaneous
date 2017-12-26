package application.assets.modules;

import java.security.SecureRandom;

public class PasswordGenerator {
	public String newPassword() {
		SecureRandom random = new SecureRandom();
		final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "abcdefghijklmnopqrstuvwxyz"
				+ "0123456789"
				+ "!@#$%^&*_=+-";
		String result = "";
		
		for (int i = 0; i < 16; i++) {
			int index = random.nextInt(CHARS.length());
			result += CHARS.charAt(index);
		}
		
		return result;
	}
}
