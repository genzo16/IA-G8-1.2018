package application;

import java.util.*;

public class Perfil {
	private static int idCounter = 0;
	private int id;
	
	public Map<String, String> atributos;

	
	public void setAttr(String key, String value) {
		atributos.put(key, value); 
	}
	public String getAttr(String key) {
		return atributos.get(key);
	}
	
	public Perfil() {
		id = idCounter++;
		atributos = new HashMap<String,String>();
	}
	
	public int getId() {
		return id;
	}
	
}
