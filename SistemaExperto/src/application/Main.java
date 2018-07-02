package application;

import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import CLIPSJNI.FactAddressValue;
import CLIPSJNI.MultifieldValue;
import derbySQL.Derby;
import hibernate.HibernateUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Analisis;
import model.Antecedentes;
import model.Diagnostico;
import model.Dolor;
import model.Paciente;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
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
	
	private int pestaña;
	private TextFlow tf1, tf2;
	//-------------------------------------------------------------------------------
	@FXML // fx:id="tabContainer"
	private TabPane tabContainer;
	@FXML
	private TextField nro_afiliado;
	@FXML
	private TextField dni;
	@FXML // fx:id="apellido"
	private TextField apellido;
	@FXML // fx:id="nombre"
	private TextField nombre;
	@FXML // fx:id="edad"
	private TextField edad;
	@FXML // fx:id="sexo"
	private ChoiceBox<String> sexo;
	@FXML
	private TextArea diag_paciente;
	
	@FXML
	private ChoiceBox<Paciente> lPacientes;
	//-------------------------------------------------------------------------------
	@FXML // fx:id="dolencia1"
	private ChoiceBox<String> dolencia1;
	@FXML // fx:id="origen1"
	private ChoiceBox<String> origen1;
	@FXML // fx:id="mejora1"
	private ChoiceBox<String> mejora1;
	@FXML // fx:id="persiste1"
	private TextField persiste1;
	//-------------------------------------------------------------------------------
	@FXML 
	private CheckBox p_uveitis;
	@FXML 
	private CheckBox p_dactilitis;
	@FXML
	private CheckBox p_rotuliano;
	@FXML 
	private CheckBox p_diarrea;
	@FXML 
	private CheckBox p_psoriasis;
	@FXML
	private CheckBox p_entecitis;
	@FXML
	private CheckBox p_infeccion;
	//-------------------------------------------------------------------------------
	@FXML 
	private CheckBox f_uveitis;
	@FXML 
	private CheckBox f_dactilitis;
	@FXML
	private CheckBox f_rotuliano;
	@FXML 
	private CheckBox f_diarrea;
	@FXML 
	private CheckBox f_psoriasis;
	@FXML
	private CheckBox f_entecitis;
	@FXML
	private CheckBox f_infeccion;
	//-------------------------------------------------------------------------------
	@FXML 
	private CheckBox gen;
	@FXML 
	private CheckBox sangre;
	@FXML
	private CheckBox imagen;
	@FXML 
	private ChoiceBox<String> gen_resultado;
	@FXML 
	private ChoiceBox<String> radio_resultado;
	@FXML
	private ChoiceBox<String> reso_resultado;
	@FXML 
	private TextField sangre_pcr;
	@FXML
	private TextField sangre_ers;
	//-------------------------------------------------------------------------------
	@FXML
	private Label diagnostico;
	@FXML
	private Label sospecha;
	@FXML
	private Label derivacion;
	@FXML
	private Label estudio_solicitado;
	@FXML
	private Label dolor_si_no;
	@FXML
	private Label antecedente_si_no;
	
	// Este es el paciente cargado actualmente
	private Paciente actual = null;
	private Antecedentes m_antecedentes = null;
	private Diagnostico m_diagnostico = null;
	private Dolor m_dolor = null;
	private Analisis m_analisis = null;
	
	
	@FXML
	void onAdvance(ActionEvent event) 
	{
		System.out.println("onAdvance");
		String entrada=null;
		
		switch(pestaña)
		{
		case 0:// Entrada de datos de paciente
			String nombre   = this.nombre.getText();
	    	String apellido = this.apellido.getText();
	    	String edad     = this.edad.getText();
	    	String sexo     = (this.sexo.getValue()).toLowerCase();
	    	
	    	entrada = "(paciente(id_paciente 1)(apellido \"" + apellido + "\")(nombre \"" + nombre + "\")(edad " + edad + ")(sexo "+sexo+"))";
	    	System.out.println(entrada);
	    	ClipsHandler.getInstance().assertString(entrada);
	    	
	    	ClipsHandler.getInstance().assertString("(diagnostico(id_paciente 1)(id_diagnostico 1))");
	    	ClipsHandler.getInstance().run();
			onAvanzar(0);
			break;
		case 1:
	    	
	    	entrada = "(dolor(id_diagnostico 1)(zona " + dolencia1.getValue()+ ")(inicio_dolor " +  origen1.getValue()+ ")(condicion_alivio " 
	    			+  mejora1.getValue() + ")(recurrencia "+ persiste1.getText()+"))";
	    	System.out.println(entrada);
	    	ClipsHandler.getInstance().assertString(entrada);
	    	
	    	ClipsHandler.getInstance().run();
			
			onAvanzar(1);
			break;
		case 2:
		    String enfermedades = "";
		    
			if(p_uveitis.isSelected())
				enfermedades += "uveitis ";
			if(p_dactilitis.isSelected())
				enfermedades += "dactilitis ";
			if(p_rotuliano.isSelected())
				enfermedades += "rotuliano ";
			if(p_diarrea.isSelected())
				enfermedades += "diarrea ";
			if(p_psoriasis.isSelected())
				enfermedades += "psoriasis ";
			if(p_entecitis.isSelected())
				enfermedades += "entesitis ";
			if(p_infeccion.isSelected())
				enfermedades += "infeccion ";
				
	    	entrada = "(enfermedades_preexistentes(enfermedad "+enfermedades+"))";
	    	System.out.println(entrada);
	    	ClipsHandler.getInstance().assertString(entrada);
	    	
	    	enfermedades = "";
		    
			if(f_rotuliano.isSelected())// artritis reumatoide
				enfermedades += "rotuliano uveitis ";
			if(f_diarrea.isSelected())// crohn
				enfermedades += "diarrea ";
			if(f_psoriasis.isSelected())// psoriasica
				enfermedades += "psoriasis ";
			if(f_infeccion.isSelected())// reactiva
				enfermedades += "infeccion ";
			
			// TODO: estas tres no estan definidas para antecedente familiar
		/*	if(f_uveitis.isSelected())
				enfermedades += "uveitis ";
			if(f_dactilitis.isSelected())
				enfermedades += "dactilitis ";
			if(f_entecitis.isSelected())
				enfermedades += "entecitis ";*/
				
	    	entrada = "(antecedente_familiar(id_paciente 1)(enfermedad "+enfermedades+"))";
	    	System.out.println(entrada);
	    	ClipsHandler.getInstance().assertString(entrada);
	    	
	    	ClipsHandler.getInstance().run();
			onAvanzar(2);
			break;
		case 3:
			if(gen.isSelected())
			{
				String fact = "(estudio (id_estudio 1)(id_paciente 1)(resultado "+gen_resultado.getValue() +")(tipo_estudio HLAB27))";
				System.out.println(fact);
		//		ClipsHandler.getInstance().assertString(fact);
			}
			if(sangre.isSelected())
			{
				String fact = "(laboratorio (id_laboratorio 1)(ERS "+sangre_ers.getText()+")(PCR "+sangre_pcr.getText()+"))";
				System.out.println(fact);
		//		ClipsHandler.getInstance().assertString(fact);
			}
			if(imagen.isSelected())
			{
				if(!radio_resultado.getValue().equals(""))
					ClipsHandler.getInstance().assertString("(estudio (id_estudio 1)(id_paciente 1)(resultado "+radio_resultado.getValue() +")(tipo_estudio radiografia))");
				if(!reso_resultado.getValue().equals(""))
					ClipsHandler.getInstance().assertString("(estudio (id_estudio 1)(id_paciente 1)(resultado "+reso_resultado.getValue() +")(tipo_estudio resonancia))");	
			}
	    	ClipsHandler.getInstance().run();
			onAvanzar(3);
			break;
		case 4:
			String resultado=null;
			MultifieldValue pv=null;
			// Obtener resultados de CLIPS
			// DOLOR
			pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J dolor))TRUE)");	
			try {
				FactAddressValue fav =(FactAddressValue)pv.get(0);
				resultado = fav.getFactSlot("resultado").toString();
				m_dolor = new Dolor(null, fav.getFactSlot("tipo").toString()
						, fav.getFactSlot("zona").toString(), fav.getFactSlot("inicio_dolor").toString()
						, fav.getFactSlot("condicion_alivio").toString()
						, fav.getFactSlot("recurrencia").intValue());
			} catch (Exception e1) {
				System.out.println("Failure with CLIPS output.");
			}
		//	ClipsHandler.getInstance().eval("(facts)");
			
			// DIAGNOSTICO
			pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J diagnostico))TRUE)");	
			try {
				FactAddressValue fav =(FactAddressValue)pv.get(0);
				resultado = fav.getFactSlot("resultado").toString();
				m_diagnostico = new Diagnostico(null, actual.getId_paciente(), fav.getFactSlot("resultado_espondilitis").toString()
						, fav.getFactSlot("resultado").toString()
						, fav.getFactSlot("estudio_solicitado").toString());
			} catch (Exception e1) {
				System.out.println("Failure with CLIPS output.");
			}
			// ANTECEDENTES
/*			pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J dolor))TRUE)");	
			try {
				FactAddressValue fav =(FactAddressValue)pv.get(0);
				resultado = fav.getFactSlot("resultado").toString();
				m_antecedentes = new Antecedentes(null, fav.getFactSlot("tipo").toString()
						, fav.getFactSlot("zona").toString(), fav.getFactSlot("inicio_dolor").toString()
						, fav.getFactSlot("condicion_alivio").toString()
						, fav.getFactSlot("recurrencia").intValue());
			} catch (Exception e1) {
				System.out.println("Failure with CLIPS output.");
			}
			//ANALISIS
			pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J dolor))TRUE)");	
			try {
				FactAddressValue fav =(FactAddressValue)pv.get(0);
				resultado = fav.getFactSlot("resultado").toString();
				m_analisis = new Analisis(null, fav.getFactSlot("tipo").toString()
						, fav.getFactSlot("zona").toString(), fav.getFactSlot("inicio_dolor").toString()
						, fav.getFactSlot("condicion_alivio").toString()
						, fav.getFactSlot("recurrencia").intValue());
			} catch (Exception e1) {
				System.out.println("Failure with CLIPS output.");
			}*/
			actual.getDiagnosticos().add(m_diagnostico);
			Paciente.saveOrUpdate(actual);
			ActualizarListaPacientes(null);
			break;
			
		default:
		}
		
	}
	
	private void onAvanzar(int pestaña_actual)
	{
		String resultado=null;
		MultifieldValue pv=null;
		// Obtener resultados de CLIPS
		pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J diagnostico))TRUE)");	
		try {
			FactAddressValue fav =(FactAddressValue)pv.get(0);
			resultado = fav.getFactSlot("resultado").toString();
		} catch (Exception e1) {
			System.out.println("Failure with CLIPS output.");
		}
		ClipsHandler.getInstance().eval("(facts)");
		System.out.println(resultado);
		
		if(resultado.equals("\"derivacion\"") | resultado.equals("\"reprogramar consulta\"") |  resultado.equals("\"SPAX\""))
		{
			onMostrarResultados();
			tabContainer.getSelectionModel().select(4);
			pestaña = 4;// ->pestaña Resultados
		}else {
			if(resultado.equals("\"realizar estudios\""))
			{
				onMostrarResultados();
				tabContainer.getSelectionModel().select(4);
				pestaña = 3;// ->pestaña estudios
			}else {
				pestaña = pestaña_actual+1;
				tabContainer.getSelectionModel().select(pestaña);
				if(pestaña==4)
					onMostrarResultados();
			}
		}
	}
	
	private void onMostrarResultados()
	{
		MultifieldValue pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J diagnostico))TRUE)");	
		try {
			FactAddressValue fav =(FactAddressValue)pv.get(0);
			String spax = fav.getFactSlot("resultado").toString().replaceAll("\"", "");
			if(spax.equals("null"))
				diagnostico.setText("indeterminado");
			else
				diagnostico.setText(spax);
			sospecha.setText(fav.getFactSlot("grado_de_confianza").toString());
			derivacion.setText(fav.getFactSlot("derivacion").toString().replaceAll("\"", ""));
			estudio_solicitado.setText(fav.getFactSlot("estudio_solicitado").toString().replaceAll("\"", ""));
			dolor_si_no.setText(fav.getFactSlot("dolor_lumbar_inflamatorio").toString().replaceAll("\"", ""));
			antecedente_si_no.setText("ninguno");
		} catch (Exception e1) {
			System.out.println("Failure with CLIPS output.");
		}
		
		pv =(MultifieldValue) ClipsHandler.getInstance().eval("(find-all-facts((?J antecedente_familiar))TRUE)");	
		try {
			FactAddressValue fav =(FactAddressValue)pv.get(0);
			String antec="";
			antec = (fav.getFactSlot("enfermedad")).multifieldValue().toString();
			if(antec.equals("[]"))
				antecedente_si_no.setText("ninguno");
			else
				antecedente_si_no.setText(antec);
			
		} catch (Exception e1) {
			System.out.println("Failure with CLIPS output.");
		}
	}
	
	@FXML
	void onClear(ActionEvent event) {
		System.out.println("onClear");
		ClipsHandler.getInstance().reset();
		tabContainer.getSelectionModel().select(0);
		pestaña = 0;// ->pestaña Estudios
		tf1.getChildren().clear();
		tf2.getChildren().clear();
		
		List<Diagnostico> diags = lPacientes.getSelectionModel().getSelectedItem().getDiagnosticos();
		if(!diags.isEmpty())
  		  diag_paciente.setText(diags.get(diags.size()-1).toString());
	}
	
	@FXML
	void onCargar(ActionEvent event) 
	{
		actual = lPacientes.getSelectionModel().getSelectedItem();
		apellido.setText(actual.getApellido());
		nombre.setText(actual.getNombre());
		edad.setText(actual.getEdad().toString());
		dni.setText(actual.getDNI());
		nro_afiliado.setText(actual.getId_paciente().toString());
		if(actual.getSexo().equals("masculino"))
			sexo.getSelectionModel().select(1);
		else
			sexo.getSelectionModel().select(2);
	}
	
	@FXML
	void ActualizarListaPacientes(ActionEvent event) 
	{		
	/*	lPacientes.getItems().clear();
		for(Paciente p :HibernateUtils.listPacientes()) // DerbyUtils.LoadPacientes()
			lPacientes.getItems().add(p);
		lPacientes.getSelectionModel().selectFirst();*/
	}
	
	@FXML
	void onSave(ActionEvent event) {
		System.out.println("onSave");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
		if(Integer.parseInt(nro_afiliado.getText())==0)
		{
			actual = new Paciente(null, dni.getText(), nombre.getText(), apellido.getText(),
					Integer.parseInt(edad.getText()), date, sexo.getSelectionModel().getSelectedItem());
			Paciente.saveOrUpdate(actual);
			lPacientes.getItems().add(actual);
		}else{
			actual.setApellido(apellido.getText());
			actual.setNombre(nombre.getText());
			actual.setDNI(dni.getText());
			actual.setEdad(Integer.parseInt(edad.getText()));
			actual.setSexo(sexo.getSelectionModel().getSelectedItem());
			Paciente.saveOrUpdate(actual);
		}
	//	ActualizarListaPacientes(null);
	}
	
	
	@FXML
	public void initialize()
	{
		pestaña = 0;
		Derby.getInstance().initialize();
		ClipsHandler.init();
		HibernateUtils.getSessionFactory();
		
		lPacientes.getSelectionModel().selectedIndexProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) 
		      {
		    	  diag_paciente.setText("No hay consultas previas.");
		    	  if(lPacientes == null)
		    		  return;
		    	  List<Diagnostico> diags = lPacientes.getItems().get(number2.intValue()).getDiagnosticos();
		    	  if(diags == null)
		    		  return;
		    	  if(!diags.isEmpty())
		    		  diag_paciente.setText(diags.get(diags.size()-1).toString());
		      }
		    });
		
		for(Paciente p :HibernateUtils.listPacientes()) // DerbyUtils.LoadPacientes()
			lPacientes.getItems().add(p);
		lPacientes.getSelectionModel().selectFirst();
		
		
	}
	
	@Override
	public void start(Stage primaryStage)
	{
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
		
		tf1 = (TextFlow) scene.lookup("#CLIPSdebug1");
		tf2 = (TextFlow) scene.lookup("#CLIPSdebug2");
		
		FX_CLIPS_output router = new FX_CLIPS_output("FXrouter");//WTRACE
		router.setTextFlow(tf1, tf2);
		ClipsHandler.getInstance().addRouter(router);
	//	ClipsHandler.getInstance().
	}
	
	@Override
	public void stop()
	{
		HibernateUtils.shutdown();
		ClipsHandler.destroy();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
