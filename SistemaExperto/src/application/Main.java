package application;

import CLIPSJNI.Environment;
import CLIPSJNI.FactAddressValue;
import CLIPSJNI.MultifieldValue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.fxml.FXMLLoader;


public class Main extends Application 
{
	Button btnContinuar_paciente;
	Environment clips;
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);//,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			TextFlow tf = (TextFlow) scene.lookup("#CLIPSdebug");
			TextFlow tf2 = (TextFlow) scene.lookup("#CLIPSdebug1");
			btnContinuar_paciente = (Button) scene.lookup("#continuar_paciente");
			btnContinuar_paciente.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	TextField tfNom = (TextField) scene.lookup("#apellido");
			    	TextField tfApe = (TextField) scene.lookup("#nombre");
			    	TextField tfEdad = (TextField) scene.lookup("#edad");
			    	System.out.println("(paciente(id_paciente 1)(apellido \""+tfNom.getText()+"\")(nombre \""+
			    			tfApe.getText()+"\")(edad "+tfEdad.getText()+"))");
			    	clips.assertString("(paciente(id_paciente 1)(apellido \""+tfNom.getText()+"\")(nombre \""+
			    			tfApe.getText()+"\")(edad "+tfEdad.getText()+"))");
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
					System.out.println(resultado);
					
			    }});
			clips = new Environment();
			
			FX_CLIPS_output router = new FX_CLIPS_output("FXrouter");//WTRACE	
			router.setTextFlow(tf, tf2);
			clips.addRouter(router);
			clips.watch(Environment.FACTS);
			clips.watch(Environment.RULES);
			clips.watch(Environment.STATISTICS);
			clips.watch(Environment.ACTIVATIONS);
			
			clips.load("./codigoCLIPS.clp");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
