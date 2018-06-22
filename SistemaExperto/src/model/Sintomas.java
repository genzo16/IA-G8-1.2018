package model;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class Sintomas implements GUIFriendly {
	
	@FXML // fx:id="ritmo"
	private static ChoiceBox<String> ritmoFX;
	
	public String ritmo;
	
	public Dolencia d1;
	public Dolencia d2;
	public Dolencia d3;
	
	public Sintomas() {
		d1 = new Dolencia1();
		d2 = new Dolencia2();
		d3 = new Dolencia3();
	}
	
	public void grabFromGUI() {
		d1.grabFromGUI();
		d2.grabFromGUI();
		d3.grabFromGUI();
		
		ritmo = ritmoFX.getValue();
	}
	
	public void pushToGUI() {
		d1.pushToGUI();
		d2.pushToGUI();
		d3.pushToGUI();
		
		ritmoFX.setValue(ritmo);
	}
}
