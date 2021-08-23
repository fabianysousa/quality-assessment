package br.com.yaw.sjpac.ui;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BotoesPanel{
	
	private JButton bNewMercadoria;
	private JButton bFindMercadoria;
	private JButton bRefreshLista;
	
	public BotoesPanel() {		
		bNewMercadoria = inicializaBotao("Nova", "novaMercadoriaAction", KeyEvent.VK_N);
		bFindMercadoria = inicializaBotao("Buscar", "buscarMercadoriasAction", KeyEvent.VK_B);
		bRefreshLista = inicializaBotao("Atualizar", "atualizarMercadoriasAction", KeyEvent.VK_A);
	}
	
	public JButton inicializaBotao(String nome, String comando, int keyevent) {
		JButton jbutton = new JButton(nome);
		jbutton.setActionCommand(comando);
		jbutton.setMnemonic(keyevent);
		return jbutton;
	}

	
	public JPanel inicializarJPanel(){
		JPanel panel = new JPanel();
		panel.add(bNewMercadoria);
		panel.add(bFindMercadoria);
		panel.add(bRefreshLista);
		return panel;
	}
	
}
