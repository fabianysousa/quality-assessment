package br.com.yaw.sjpac.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.SwingUtilities;

import br.com.yaw.sjpac.action.AbstractAction;
import br.com.yaw.sjpac.dao.MercadoriaDAO;
import br.com.yaw.sjpac.dao.MercadoriaDAOJPA;
import br.com.yaw.sjpac.event.AbstractEventListener;
import br.com.yaw.sjpac.event.AtualizarListarMercadoriaEvent;
import br.com.yaw.sjpac.event.BuscarMercadoriaEvent;
import br.com.yaw.sjpac.event.DeletarMercadoriaEvent;
import br.com.yaw.sjpac.event.IncluirMercadoriaEvent;
import br.com.yaw.sjpac.model.Mercadoria;
import br.com.yaw.sjpac.ui.ListaMercadoriasFrame;
import br.com.yaw.sjpac.ui.SobreFrame;
import br.com.yaw.sjpac.util.JPAUtil;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.sjpac.controller.PersistenceController
 * 
 * @author YaW Tecnologia
 */
public class ListaMercadoriaController extends PersistenceController {

	private ListaMercadoriasFrame frame;
	private SobreFrame sobreFrame;
	
	private IncluirMercadoriaController incluirController;
	private BuscarMercadoriaController buscarController;
	
	public ListaMercadoriaController() {
		loadPersistenceContext();
		frame = new ListaMercadoriasFrame();
		frame.addWindowListener(this);
		incluirController = new IncluirMercadoriaController(this);
		buscarController = new BuscarMercadoriaController(this);
		this.sobreFrame = new SobreFrame();
		
		registerAction(frame.getNewButton(), new AbstractAction() {
			public void action() {
				incluirController.show();
			}
		});
		
		registerAction(frame.getRefreshButton(), new AbstractAction() {
			public void action() {
				fireEvent(new AtualizarListarMercadoriaEvent());
			}
		});
		
		registerAction(frame.getFindButton(), new AbstractAction() {
			public void action() {
				buscarController.show();
			}
		});
		
		AbstractAction sobreAction =  new AbstractAction() {
			@Override
			protected void action() {
				sobreFrame.setVisible(true);
			}
		};
		registerAction(frame.getMenuSobre(), sobreAction);
		frame.getMenuAjuda().addListener(sobreAction);
		
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
		
		registerEventListener(IncluirMercadoriaEvent.class, new AbstractEventListener<IncluirMercadoriaEvent>() {
			public void handleEvent(IncluirMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(DeletarMercadoriaEvent.class, new AbstractEventListener<DeletarMercadoriaEvent>() {
			public void handleEvent(DeletarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(AtualizarListarMercadoriaEvent.class, new AbstractEventListener<AtualizarListarMercadoriaEvent>() {
			public void handleEvent(AtualizarListarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(BuscarMercadoriaEvent.class, new AbstractEventListener<BuscarMercadoriaEvent>() {
			public void handleEvent(final BuscarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						List<Mercadoria> list = event.getTarget();
						if (list != null) {
							frame.refreshTable(event.getTarget());
						}
					}
				});
			}
		});
		
		frame.setVisible(true);
		refreshTable();
	}
	
	private void refreshTable() {
		MercadoriaDAO dao = new MercadoriaDAOJPA(getPersistenceContext());
		frame.refreshTable(dao.getAll());
	}
	
	@Override
	protected void cleanUp() {
		super.cleanUp();
		
		JPAUtil.closeEntityManagerFactory();
	}
}
