package br.com.yaw.sjpac.app;


import java.util.Locale;

import br.com.yaw.sjpac.controller.ListaMercadoriaController;

/**
 * Ponto de entrada da aplicação.
 * 
 * @author YaW Tecnologia
 */
public class Main {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt","BR"));
		new ListaMercadoriaController();
	}

}
