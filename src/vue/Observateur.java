package vue;

import modele.DataArduino;

public interface Observateur {
	public void afficherNotification(DataArduino data);
}
