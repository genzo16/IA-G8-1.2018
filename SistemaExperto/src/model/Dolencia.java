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

	public void grabFromGUI() {
		
	}
	
	public void pushToGUI() {
		
	}
	
}
