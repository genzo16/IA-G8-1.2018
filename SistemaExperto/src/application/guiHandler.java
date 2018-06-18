package application;

import java.util.*;

import javafx.scene.Scene;

public class guiHandler {
	public static String[] data = {
			"dni",
			"apellido",
			"nombre",
			"edad",
			"sexo",
			"dolencia1",
			"dolencia2",
			"dolencia3",
			"aspecto1",
			"aspecto2",
			"aspecto3",
			"inflamacion1",
			"inflamacion2",
			"inflamacion3",
			"origen1",
			"origen2",
			"origen3",
			"mejora1",
			"mejora2",
			"mejora3",
			"despierta1",
			"despierta2",
			"despierta3",
			"edad1",
			"edad2",
			"edad3",
			"persiste1",
			"persiste2",
			"persiste3",
			"ritmo",
			"ante_colitis",
			"ante_dacti",
			"ante_ente",
			"ante_uve",
			"ante_gastro",
			"ante_uro",
			"ante_pso",
			"ante_crohn",
			"ante_colitis_ulcerosa",
			"ante_crohn2",
			"ante_colitis_ulcerosa2",
			"ante_anqui",
			"ante_reactiva",
			"ante_artritis_pso",
			"ante_indif",
			"ante_juv",
			"ante_reuma",
			"gen",
			"gen_fecha",
			"gen_resultado",
			"sangre",
			"sangre_ers",
			"sangre_pcr",
			"imagen",
			"radio_resultado",
			"reso_resultado"
	};
	
	
	public static Perfil getData(Scene s) {
		Perfil p = new Perfil();
		
		for(int i = 0; i < data.length; i++) {
			String key = data[i];
			System.out.println(key);
			Object o = s.lookup("#".concat(key));
			System.out.println(o);
			//p.setAttr(key, value);
		}
		
		return p;
	}
	
	public static void setData(Perfil p) {
		
	}
	
}
