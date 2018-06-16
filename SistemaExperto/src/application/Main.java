package application;

import java.io.IOException;
import java.net.URL;

import CLIPSJNI.Environment;
import CLIPSJNI.FactAddressValue;
import CLIPSJNI.MultifieldValue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application 
{
	//Button btnContinuar_paciente;
	public Environment clips;
	public Derby BBDD;
	
	Scene scene;
	
	@FXML // fx:id="apellido"
	private TextField apellido;
	@FXML // fx:id="nombre"
	private TextField nombre;
	@FXML // fx:id="edad"
	private TextField edad;
	@FXML // fx:id="sexo"
	private ChoiceBox<String> sexo;
	
	@FXML
	void onAdvance(ActionEvent event) {
		System.out.println("onAdvance");
		onSubmit();
	}
	
	@FXML
	void onClear(ActionEvent event) {
		System.out.println("onClear");
	}
	
	@FXML
	void onSave(ActionEvent event) {
		System.out.println("onSave");
	}
	
	
	@Override
	public void init()
	{
		System.out.println("JavaFX init()");
		BBDD = new Derby();
		if(!BBDD.initialize())
			BBDD = null;
		
		clips = new Environment();
		clips.watch(Environment.FACTS);
		clips.watch(Environment.RULES);
		clips.watch(Environment.STATISTICS);
		clips.watch(Environment.ACTIVATIONS);
		
		clips.load("./codigoCLIPS.clp");
		System.out.println(clips.toString());
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		System.out.println("JavaFX start()");
		
		URL url = getClass().getResource("gui.fxml");
		AnchorPane pane = null;
		try {
			pane =(AnchorPane)FXMLLoader.load(url);
		} catch (IOException e) {
			System.out.println("Failure loading url fxml");
			e.printStackTrace();
			return;
		}
		scene = new Scene(pane);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		TextFlow tf1 = (TextFlow) scene.lookup("#CLIPSdebug1");
		TextFlow tf2 = (TextFlow) scene.lookup("#CLIPSdebug2");
		
		FX_CLIPS_output router = new FX_CLIPS_output("FXrouter");//WTRACE
		router.setTextFlow(tf1, tf2);
		clips.addRouter(router);
	}
	
	public void onSubmit()
	{
		String nombre   = this.nombre.getText();
    	String apellido = this.apellido.getText();
    	String edad     = this.edad.getText();
    	String sexo     = this.sexo.getValue();
    	
    	String entrada = "(paciente(id_paciente 1)(apellido \"" + apellido + "\")(nombre \"" + nombre + "\")(edad " + edad + "))";
    	//System.out.println(entrada);
    	System.out.println(clips);
    	clips.assertString(entrada);
    	
    	clips.assertString("(diagnostico(id_paciente 1)(id_diagnostico 1))");
    //	clips.reset();
		clips.run();
		String resultado=null;
		MultifieldValue pv =(MultifieldValue) clips.eval("(find-all-facts((?J diagnostico))TRUE)");
		try {
			FactAddressValue fav =(FactAddressValue)pv.get(0);
			resultado = fav.getFactSlot("resultado_primera_etapa").toString();//resultado_primera_etapa
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// TODO: mostrar esta salida via javaFX
		System.out.println(resultado);
	}
	
	@Override
	public void stop()
	{
		// This function is called when exiting the application.
		// All the exit cleanup should be in this function.
		//System.out.println(" JavaFX stop()");
		System.out.println("JavaFX stop()");
		
		if(BBDD != null)
			BBDD.shutdown();
		if(clips != null)
			clips.destroy();
	}
	
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
}
