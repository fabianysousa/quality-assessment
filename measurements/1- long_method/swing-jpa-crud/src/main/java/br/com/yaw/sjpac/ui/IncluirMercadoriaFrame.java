package br.com.yaw.sjpac.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.yaw.sjpac.model.Mercadoria;


/**
 * Tela para incluir/editar o registro da <code>Mercadoria</code>.
 * 
 * <p>
 *  Essa tela trabalha em modo inclusão ou edição de <code>Mercadoria</code>.
 *  Em edição é possível acionar a funcionalidade para remover <code>Mercadoria</code>.
 * </p>
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaFrame extends JFrame {

	private JTextField tfNome;
	private JFormattedTextField tfQuantidade;
	private JTextField tfDescricao;
	private JTextField tfPreco;
	private JFormattedTextField tfId;
	private JFormattedTextField tfVersion;
	
	private JButton bSalvar;
	private JButton bCancelar;
	private JButton bExcluir;
	
	public IncluirMercadoriaFrame() {
		setSize(300,250);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
		resetForm();
	}
	
	public JPanel inicializarJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaPanelEditarMercadoria(), BorderLayout.CENTER);
		panel.add(montaPanelBotoesEditar(), BorderLayout.SOUTH);
		return panel;
	}
	
	private void inicializaComponentes() {
		JPanel panel = inicializarJPanel();
		add(panel);
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
	}
	
	public JButton inicializaBotao(String nome, String comando, int keyevent) {
		JButton jbutton = new JButton(nome);
		jbutton.setActionCommand(comando);
		jbutton.setMnemonic(keyevent);
		return jbutton;
	}
	
	private JPanel montaPanelBotoesEditar() {
		JPanel panel = new JPanel();

		bSalvar = inicializaBotao("Salvar", "salvarIncluirMercadoriaAction", KeyEvent.VK_S);
		panel.add(bSalvar);

		bCancelar = inicializaBotao("Cancelar", "cancelarIncluirMercadoriaAction", KeyEvent.VK_C);
		panel.add(bCancelar);
		
		bExcluir = inicializaBotao("Excluir", "excluirMercadoriaAction", KeyEvent.VK_E);
		panel.add(bExcluir);

		return panel;
	}

	public JFormattedTextField inicializarJFormattedTextField(int number, boolean enable) {
		JFormattedTextField jf = new JFormattedTextField();
		if(number != -1) {
			jf.setValue(new Integer(number));
		}else {
			jf.setVisible(false);
		}
		jf.setEnabled(enable);
		
		return jf;
	}
	
	private JPanel montaPanelEditarMercadoria() {
		JPanel painel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		painel.setLayout(layout);
		
		tfNome = new JTextField();
		tfDescricao = new JTextField();
		tfPreco = new JTextField();
		
		tfQuantidade = inicializarJFormattedTextField(1,true);
		tfId = inicializarJFormattedTextField(0,false);
		tfVersion = inicializarJFormattedTextField(-1,true);

		painel.add(new JLabel("Nome:"));
		painel.add(tfNome);
		painel.add(new JLabel("Descrição:"));
		painel.add(tfDescricao);
		painel.add(new JLabel("Preço:"));
		painel.add(tfPreco);
		painel.add(new JLabel("Quantidade:"));
		painel.add(tfQuantidade);
		painel.add(new JLabel("Id: "));
		painel.add(tfId);

		return painel;
	}
	
	public String getValueText(JTextField fieldText) {
		String text = null;
		if (!fieldText.getText().trim().isEmpty()) {
			text = fieldText.getText().trim();
		}
		return text;
	}
	
	public Integer getNumberText(JFormattedTextField jfield) {
		Integer number = null;
		try {
			if (!jfield.getText().trim().isEmpty())
			return number = Integer.valueOf(tfQuantidade.getText());
		} catch (NumberFormatException nex) {
			throw new RuntimeException("Erro durante a conversão do campo quantidade (Integer).\nConteudo inválido!");
		}
		return number;
	}
	
	public Integer parseIntFromText(JFormattedTextField jfield) {
		Integer number = null;
		try {
			return number = Integer.parseInt(tfId.getText());
		} catch (Exception nex) {}
		return number;
	}
	
	public Double getPricing(String preco) {
		try {
			return Mercadoria.formatStringToPreco(preco);
		} catch (ParseException nex) {
			throw new RuntimeException("Erro durante a conversão do campo preço (Double).\nConteudo inválido!");
		}
	}
	
	private Mercadoria loadMercadoriaFromPanel() {
		String nome = getValueText(tfNome);
		String descricao = getValueText(tfDescricao);
		
		Integer quantidade = getNumberText(tfQuantidade);
		
		Integer id = parseIntFromText(tfId);
		
		Integer version = parseIntFromText(tfVersion);
		
		Double preco = getPricing(getValueText(tfPreco)) ;
		
		return new Mercadoria(id, nome, descricao, quantidade, preco, version);
	}
	
	public void resetForm() {
		tfId.setValue(null);
		tfNome.setText("");
		tfDescricao.setText("");
		tfPreco.setText("");
		tfQuantidade.setValue(Integer.valueOf(1));
		tfVersion.setValue(null);
		bExcluir.setVisible(false);
	}
	
	private void populaTextFields(Mercadoria m){
		tfId.setValue(m.getId());
		tfNome.setText(m.getNome());
		tfDescricao.setText(m.getDescricao());
		tfQuantidade.setValue(m.getQuantidade());
		tfPreco.setText(m.getPrecoFormatado());
		tfVersion.setValue(m.getVersion());
	}
	
	/**
	 * Limpa e carrega os campos da tela de acordo com objeto <code>Mercadoria</code>.
	 * @param m referência da <code>Mercadoria</code> que deve ser apresentada na tela.
	 */
	public void setMercadoria(Mercadoria m){
		resetForm();
		if (m != null) {
			populaTextFields(m);
			bExcluir.setVisible(true);
		}
	}
	
	/**
	 * @return uma nova instância de <code>Mercadoria</code> com os dados preenchidos do campos na tela.
	 */
	public Mercadoria getMercadoria() {
		return loadMercadoriaFromPanel();
	}
	
	/**
	 * @return o identificador da <code>Mercadoria</code> em edição. Retorna <code>null</code> em modo de inclusão.
	 */
	public Integer getMercadoriaId() {
		try {
			return Integer.parseInt(tfId.getText());
		} catch (Exception nex) {
			return null;
		}
	}
	
	public JButton getSalvarButton() {
		return bSalvar;
	}
	
	public JButton getCancelarButton() {
		return bCancelar;
	}
	
	public JButton getExcluirButton() {
		return bExcluir;
	}

}
