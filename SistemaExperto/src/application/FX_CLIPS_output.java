package application;

import CLIPSJNI.Environment;
import CLIPSJNI.Router;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FX_CLIPS_output extends Router {
	private TextFlow tf;
	
	public FX_CLIPS_output(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public void setTextFlow(TextFlow _tf)
	{
		tf = _tf;
		tf.getChildren().add(new Text("CLIPS version "+Environment.getCLIPSVersion()+"\n"+
				"CLIPSJNI version "+Environment.getCLIPSJNIVersion()+"\n"));
	}
	
	@Override
	public void print(String arg0, String arg1) {
	//	if(arg0.equals("wtrace"))
			System.out.print(arg1);
			tf.getChildren().add(new Text(arg1));
	//	super.print(arg0, arg1);
	}

}
