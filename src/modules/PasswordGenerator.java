package modules;

import java.security.SecureRandom;

public class PasswordGenerator {
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "!@#$%^&*_=+-";
	private SecureRandom random = new SecureRandom();
	private StringBuilder result = new StringBuilder();

	public String newPassword() {
		for (int i = 0; i < 16; i++) {
			int index = random.nextInt(CHARS.length());
			result.append(CHARS.charAt(index));
		}

		return result.toString();
	}
}
