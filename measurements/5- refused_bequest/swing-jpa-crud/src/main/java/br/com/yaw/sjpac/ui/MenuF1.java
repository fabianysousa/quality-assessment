package br.com.yaw.sjpac.ui;

import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JMenu;


import br.com.yaw.sjpac.action.AbstractAction;

/**
 * <code>JMenu</code> com atalho pré-definido para F1 (hot key).
 * 
 * @author YaW Tecnologia
 */
public class MenuF1 extends JMenu {

	public MenuF1(String title) {
		super(title);
	}
	
	/**
	 * @param action vinculada com a tecla F1.
	 */
	public void addListener(final AbstractAction action) {
		this.getActionMap().put("click", new javax.swing.AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed();
			}
		});
	}

}
