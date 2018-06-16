package application;

import CLIPSJNI.Environment;

public class ClipsHandler {
	private static Environment c;
	
	public static void init() {
		getInstance();
	}
	
	public static void destroy() {
		if(c!=null) {
			c.destroy();
			c = null;
		}
	}
	
	public static Environment getInstance() {
		if(c==null) {
			c = new Environment();
			c.watch(Environment.FACTS);
			c.watch(Environment.RULES);
			c.watch(Environment.STATISTICS);
			c.watch(Environment.ACTIVATIONS);
			
			c.load("./codigoCLIPS.clp");
		}
		return c;
	}
}
