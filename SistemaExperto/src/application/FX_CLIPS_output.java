package application;

import CLIPSJNI.Environment;
import CLIPSJNI.Router;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FX_CLIPS_output extends Router {
	private TextFlow tf, tf2;
	
	public FX_CLIPS_output(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public void setTextFlow(TextFlow _tf, TextFlow _tf2)
	{
		tf = _tf;
		tf2 = _tf2;
		tf.getChildren().add(new Text("CLIPS version "+Environment.getCLIPSVersion()+"\n"+
				"CLIPSJNI version "+Environment.getCLIPSJNIVersion()+"\n"));
	}
	
	@Override
	public void print(String arg0, String arg1) 
	{
		if(arg0.equals("wtrace"))
		{
			tf.getChildren().add(new Text(arg1));
		}else{ 
			tf2.getChildren().add(new Text(arg1));
		}	
		super.print(arg0, arg1);
	}

}
