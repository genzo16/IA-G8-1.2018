package model;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public abstract class Dolencia implements GUIFriendly{

	abstract public ChoiceBox<String> getTipoFX();
	abstract public ChoiceBox<String> getAspectoFX();
	abstract public ChoiceBox<String> getInflamacionFX();
	abstract public ChoiceBox<String> getOrigenFX();
	abstract public ChoiceBox<String> getMejoraFX();
	abstract public ChoiceBox<String> getDespiertaFX();
	abstract public TextField getEdadFX();
	abstract public TextField getPersisteFX();

	public String tipo;
	public String aspecto;
	public String inflamacion;
	public String origen;
	public String mejora;
	public String despierta;
	public int edad;
	public int persiste;
	
	public void grabFromGUI() {
		tipo = getTipoFX().getValue();
		aspecto = getAspectoFX().getValue();
		inflamacion = getInflamacionFX().getValue();
		origen = getOrigenFX().getValue();
		mejora = getMejoraFX().getValue();
		try {
			edad = Integer.parseInt( getEdadFX().getText() );
		} catch(NumberFormatException e) {
			edad = -1;
		}
		try {
			persiste = Integer.parseInt( getPersisteFX().getText() );
		} catch(NumberFormatException e) {
			persiste = -1;
		}
	}
	
	public void pushToGUI() {
		getTipoFX().setValue(tipo);
		getAspectoFX().setValue(aspecto);
		getInflamacionFX().setValue(inflamacion);
		getOrigenFX().setValue(origen);
		getMejoraFX().setValue(mejora);
		getEdadFX().setText(String.valueOf(edad));
		getPersisteFX().setText(String.valueOf(persiste));
	}
	
}
