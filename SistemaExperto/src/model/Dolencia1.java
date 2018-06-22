package model;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Dolencia1 extends Dolencia {

	@FXML // fx:id="dolencia1"
	private static ChoiceBox<String> tipoFX;
	@FXML // fx:id="aspecto1"
	private static ChoiceBox<String> aspectoFX;
	@FXML // fx:id="inflamacion1"
	private static ChoiceBox<String> inflamacionFX;
	@FXML // fx:id="origen1"
	private static ChoiceBox<String> origenFX;
	@FXML // fx:id="mejora1"
	private static ChoiceBox<String> mejoraFX;
	@FXML // fx:id="despierta1"
	private static ChoiceBox<String> despiertaFX;
	@FXML // fx:id="edad1"
	private static TextField edadFX;
	@FXML // fx:id="persiste1"
	private static TextField persisteFX;
	
	
	public ChoiceBox<String> getTipoFX() {
		return tipoFX;
	}
	public ChoiceBox<String> getAspectoFX() {
		return aspectoFX;
	}
	public ChoiceBox<String> getInflamacionFX() {
		return inflamacionFX;
	}
	public ChoiceBox<String> getOrigenFX() {
		return origenFX;
	}
	public ChoiceBox<String> getMejoraFX() {
		return mejoraFX;
	}
	public ChoiceBox<String> getDespiertaFX() {
		return despiertaFX;
	}
	public TextField getEdadFX() {
		return edadFX;
	}
	public TextField getPersisteFX() {
		return persisteFX;
	}
	
}
