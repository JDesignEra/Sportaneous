package modules;

import java.security.SecureRandom;

public class PasswordGenerator {
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "!@#$%^&*_=+-";

	public String newPassword() {
		SecureRandom random = new SecureRandom();
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < 16; i++) {
			int index = random.nextInt(CHARS.length());
			result.append(CHARS.charAt(index));
		}

		return result.toString();
	}
}
