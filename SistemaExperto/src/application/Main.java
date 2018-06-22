package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import CLIPSJNI.FactAddressValue;
import CLIPSJNI.MultifieldValue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Paciente;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application 
{
	//Button btnContinuar_paciente;
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
		
		String nombre   = this.nombre.getText();
    	String apellido = this.apellido.getText();
    	String edad     = this.edad.getText();
    	//String sexo     = this.sexo.getValue();
    	
    	String entrada = "(paciente(id_paciente 1)(apellido \"" + apellido + "\")(nombre \"" + nombre + "\")(edad " + edad + "))";
    	System.out.println(entrada);
    	ClipsHandler.getInstance().assertString(entrada);
    	
    	ClipsHandler.getInstance().assertString("(diagnostico(id_paciente 1)(id_diagnostico 1))");
    //	ClipsHandler.getInstance().reset();
    	ClipsHandler.getInstance().run();
		String resultado=null;
		MultifieldValue pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J diagnostico))TRUE)");
		
		try {
			FactAddressValue fav =(FactAddressValue)pv.get(0);
			resultado = fav.getFactSlot("resultado").toString();//resultado_primera_etapa
		} catch (Exception e1) {
			System.out.println("Failure with CLIPS output.");
			//e1.printStackTrace();
		}
		
		// TODO: mostrar esta salida via javaFX
		System.out.println(resultado);
	}
	
	@FXML
	void onClear(ActionEvent event) {
		System.out.println("onClear");
		Paciente p = null;
		try {
			p= DerbyUtils.LoadPaciente(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.DNI+"\n"+p.getNombre()+" "+p.getApellido()+"\n"+p.getSexo()+"\n"+p.Edad+"\n"+p.Fecha);
	}
	
	@FXML
	void onSave(ActionEvent event) {
		System.out.println("onSave");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
		Paciente p = new Paciente(0,"12.123.123","Rafael","Aylas",32,date,"Masculino");
//		p.grabFromGUI();
		try {
			DerbyUtils.SavePaciente(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void init()
	{
		//System.out.println("JavaFX init()");
		BBDD = Derby.getInstance();
		if(!BBDD.initialize())
			BBDD = null;
		try {
			Paciente p = DerbyUtils.LoadPaciente(1);
			System.out.println(p.Nombre+" "+p.getFecha());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClipsHandler.init();
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		//System.out.println("JavaFX start()");
		
		URL url = getClass().getResource("gui_tabbed.fxml");
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
		ClipsHandler.getInstance().addRouter(router);
	}
	
	@Override
	public void stop()
	{
		//System.out.println("JavaFX stop()");
		
		if(BBDD != null)
			BBDD.shutdown();
		ClipsHandler.destroy();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
