package application;
	
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;

import CLIPSJNI.Environment;
import CLIPSJNI.PrimitiveValue;
import CLIPSJNI.Router;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);//,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			TextFlow tf = (TextFlow) scene.lookup("#CLIPSdebug");
			Environment clips = new Environment();
			
			FX_CLIPS_output router = new FX_CLIPS_output("FXrouter");//WTRACE	
			router.setTextFlow(tf);
			clips.addRouter(router);		
			clips.watch(Environment.FACTS);
			clips.watch(Environment.RULES);
			clips.watch(Environment.STATISTICS);
			clips.watch(Environment.ACTIVATIONS);
			
			clips.load("./codigoCLIPS.clp");
			clips.reset();
			clips.run();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
