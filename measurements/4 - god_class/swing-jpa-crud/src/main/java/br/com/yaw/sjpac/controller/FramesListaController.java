package br.com.yaw.sjpac.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import br.com.yaw.sjpac.action.AbstractAction;
import br.com.yaw.sjpac.model.Mercadoria;
import br.com.yaw.sjpac.ui.ListaMercadoriasFrame;
import br.com.yaw.sjpac.ui.SobreFrame;

public class FramesListaController extends PersistenceController  {


	private SobreFrame sobreFrame;
	private IncluirMercadoriaController incluirController;
	
	public FramesListaController() {
	}
		
	public void incializarFrameSobre(ListaMercadoriasFrame frame) {
		this.sobreFrame = new SobreFrame();
		this.sobreFrame.setTitle("Sobre a aplicação");
		this.sobreFrame.setLocationRelativeTo(null);
		AbstractAction sobreAction =  new AbstractAction() {
			@Override
			protected void action() {
				sobreFrame.setVisible(true);
			}
		};
		registerAction(frame.getMenuSobre(), sobreAction);
		frame.getMenuAjuda().addListener(sobreAction);	
	}
	
	public void incializarFrameControler(final ListaMercadoriasFrame frame) {
		incluirController = new IncluirMercadoriaController(this);
		registerAction(frame.getNewButton(), new AbstractAction() {
			public void action() {
				incluirController.show();
			}
		});
		
		frame.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					Mercadoria m = frame.getTable().getMercadoriaSelected();
					if (m != null) {
						incluirController.show(m);
					}
				}
			}
		});
		
	}
	
}
