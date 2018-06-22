package model;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Dolencia2 extends Dolencia {

	@FXML // fx:id="dolencia2"
	private static ChoiceBox<String> tipoFX;
	@FXML // fx:id="aspecto2"
	private static ChoiceBox<String> aspectoFX;
	@FXML // fx:id="inflamacion2"
	private static ChoiceBox<String> inflamacionFX;
	@FXML // fx:id="origen2"
	private static ChoiceBox<String> origenFX;
	@FXML // fx:id="mejora2"
	private static ChoiceBox<String> mejoraFX;
	@FXML // fx:id="despierta2"
	private static ChoiceBox<String> despiertaFX;
	@FXML // fx:id="edad2"
	private static TextField edadFX;
	@FXML // fx:id="persiste2"
	private static TextField persisteFX;
	
	
	@Override
	public ChoiceBox<String> getTipoFX() {
		return tipoFX;
	}
	@Override
	public ChoiceBox<String> getAspectoFX() {
		return aspectoFX;
	}
	@Override
	public ChoiceBox<String> getInflamacionFX() {
		return inflamacionFX;
	}
	@Override
	public ChoiceBox<String> getOrigenFX() {
		return origenFX;
	}
	@Override
	public ChoiceBox<String> getMejoraFX() {
		return mejoraFX;
	}
	@Override
	public ChoiceBox<String> getDespiertaFX() {
		return despiertaFX;
	}
	@Override
	public TextField getEdadFX() {
		return edadFX;
	}
	@Override
	public TextField getPersisteFX() {
		return persisteFX;
	}
	
}
