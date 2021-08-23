package br.com.yaw.sjpac.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import br.com.yaw.sjpac.model.Mercadoria;

/**
 * Tela principal da aplicação. Apresenta uma lista com as mercadorias cadastradas. 
 * 
 * <p>A partir dessa tela eh possivel criar/editar ou pesquisar mercadoria.</p>
 * 
 * @author YaW Tecnologia
 */
public class ListaMercadoriasFrame extends JFrame {
	
	private MercadoriaTable tabela;
	private JScrollPane scrollPane;
	private JButton bNewMercadoria;
	private JButton bFindMercadoria;
	private JButton bRefreshLista;
	private JMenuBar menubar;
	private MenuF1 menuAjuda;
	private JMenuItem menuSobre;
	
	public ListaMercadoriasFrame() {
		setTitle("Lista de Mercadoria");
		
		inicializarMarcadoriaTable();
		
		bNewMercadoria = inicializaBotao("Nova", "novaMercadoriaAction", KeyEvent.VK_N);
		bFindMercadoria = inicializaBotao("Buscar", "buscarMercadoriasAction", KeyEvent.VK_B);
		bRefreshLista = inicializaBotao("Atualizar", "atualizarMercadoriasAction", KeyEvent.VK_A);
		menubar = inicializarMenuBar(inicializarMenuF1("Ajuda", KeyEvent.VK_J));
		adicionaComponentes();
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public JButton inicializaBotao(String nome, String comando, int keyevent) {
		JButton jbutton = new JButton(nome);
		jbutton.setActionCommand(comando);
		jbutton.setMnemonic(keyevent);
		return jbutton;
	}

	public void inicializarMarcadoriaTable() {
		tabela = new MercadoriaTable();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tabela);
	}
	
	public MenuF1 inicializarMenuF1(String nome, int keyEvent) {
		MenuF1 menu = new MenuF1(nome);
		menu.setMnemonic(keyEvent);
        menuSobre = new JMenuItem("Sobre    - F1");
        menu.add(menuSobre);
		return menu;
	}
	
	public JMenuBar inicializarMenuBar(MenuF1 menuAjuda) {
		menubar = new JMenuBar();
		setJMenuBar(menubar);
		menubar.add(menuAjuda);
		menubar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"),"click");
		return menubar;
	}
	
	public JPanel inicializarJPanel(){
		JPanel panel = new JPanel();
		panel.add(bNewMercadoria);
		panel.add(bFindMercadoria);
		panel.add(bRefreshLista);
		return panel;
	}
	
	private void adicionaComponentes(){
		add(scrollPane);
		JPanel panel = inicializarJPanel();
		add(panel, BorderLayout.SOUTH);
	}
	
	public JButton getNewButton() {
		return bNewMercadoria;
	}

	public JButton getRefreshButton() {
		return bRefreshLista;
	}
	
	public JButton getFindButton() {
		return bFindMercadoria;
	}
	
	public void refreshTable(List<Mercadoria> mercadorias) {
		tabela.reload(mercadorias);
	}
	
	public Mercadoria getSelectedMercadoria() {
		return tabela.getMercadoriaSelected();
	}
	
	public MercadoriaTable getTable() {
		return tabela;
	}
	
	public JMenuItem getMenuSobre() {
		return menuSobre;
	}
	
	public MenuF1 getMenuAjuda() {
		return menuAjuda;
	}
}
