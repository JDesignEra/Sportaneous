package modules;

public class RatingStars {
	public String getShapes(double rating) {
		StringBuilder ratingStars = new StringBuilder();
		
		if (rating > 0) {
			for (int i = 0; i < 5; i++) {
				if (i < (int) rating) {
					ratingStars.append("\uf005 ");
				}
				else if ((rating - i) >= 0.5) {
					ratingStars.append("\uf123 ");
				}
				else {
					ratingStars.append((i < 4 ? "\uf006 " : "\uf006"));
				}
			}
		}
		else {
			ratingStars.append("\uf006 \uf006 \uf006 \uf006 \uf006");
		}
		
		return ratingStars.toString();
	}
}
