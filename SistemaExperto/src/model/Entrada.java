package model;

public class Entrada implements GUIFriendly{
	
	public Paciente paciente;
	public Sintomas sintomas;
	public Antecedentes antecedentes;
	public Estudios estudios;
	// maybe? public Resultados resultados;
	
	public void grabFromGUI() {
		paciente.grabFromGUI();
		sintomas.grabFromGUI();
		antecedentes.grabFromGUI();
		estudios.grabFromGUI();
	}
	
	public void pushToGUI() {
		paciente.pushToGUI();
		sintomas.pushToGUI();
		antecedentes.pushToGUI();
		estudios.pushToGUI();
	}
}
