package br.com.yaw.sjpac.controller;

import javax.swing.JOptionPane;

import br.com.yaw.sjpac.action.AbstractAction;
import br.com.yaw.sjpac.action.BooleanExpression;
import br.com.yaw.sjpac.action.ConditionalAction;
import br.com.yaw.sjpac.action.TransactionalAction;
import br.com.yaw.sjpac.dao.MercadoriaDAO;
import br.com.yaw.sjpac.dao.MercadoriaDAOJPA;
import br.com.yaw.sjpac.event.DeletarMercadoriaEvent;
import br.com.yaw.sjpac.event.IncluirMercadoriaEvent;
import br.com.yaw.sjpac.model.Mercadoria;
import br.com.yaw.sjpac.ui.IncluirMercadoriaFrame;
import br.com.yaw.sjpac.validation.MercadoriaValidator;
import br.com.yaw.sjpac.validation.Validator;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de inclusão/edição de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.sjpac.controller.PersistenceController
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaController extends PersistenceController {

	private IncluirMercadoriaFrame frame;
	private Validator<Mercadoria> validador = new MercadoriaValidator();
	
	public IncluirMercadoriaController(AbstractController parent) {
		super(parent);
		this.frame = new IncluirMercadoriaFrame();
		
		frame.addWindowListener(this);
		registerAction(frame.getCancelarButton(), new AbstractAction() {
			public void action() {
				cleanUp();
			}
		});
		
		registerAction(frame.getSalvarButton(), 
			ConditionalAction.build()
				.addConditional(new BooleanExpression() {
					@Override
					public boolean conditional() {
						Mercadoria m = frame.getMercadoria();
						String msg = validador.validate(m);
						if (!"".equals(msg == null ? "" : msg)) {
							JOptionPane.showMessageDialog(frame, msg, "Validação", JOptionPane.INFORMATION_MESSAGE);
							return false;
						}
						return true;
					}
				})
				.addAction(
					TransactionalAction.build(this)
						.addAction(
							new AbstractAction() {
								private Mercadoria m;
								
								@Override
								protected void action() {
									m = frame.getMercadoria();
									MercadoriaDAO dao = new MercadoriaDAOJPA(getPersistenceContext());
									dao.save(m);
								}
								
								public void posAction() {
									cleanUp();
									fireEvent(new IncluirMercadoriaEvent(m));
								}
							})));
		
		registerAction(frame.getExcluirButton(), 
				TransactionalAction.build(this)
				.addAction(new AbstractAction() {
					private Mercadoria m;
					
					@Override
					protected void action() {
						Integer id = frame.getMercadoriaId();
						if (id != null) {
							MercadoriaDAO dao = new MercadoriaDAOJPA(getPersistenceContext());
		                    m = dao.findById(id);
		                    if (m != null) {
		                        dao.remove(m);
		                    }
						}
					}
					
					public void posAction() {
						cleanUp();
						fireEvent(new DeletarMercadoriaEvent(m));
					}
				})
		);
	}
	
	public void show() {
		loadPersistenceContext(((PersistenceController)getParentController()).getPersistenceContext());
		frame.setVisible(true);
	}
	
	public void show(Mercadoria m) {
		frame.configFrame("EditarMercadoria", true, false, m);
		show();
	}
	
	
	@Override
	protected void cleanUp() {
		frame.configFrame("Incluir Mercadoria", false, true, null);
		
		super.cleanUp();
	}
	
}
