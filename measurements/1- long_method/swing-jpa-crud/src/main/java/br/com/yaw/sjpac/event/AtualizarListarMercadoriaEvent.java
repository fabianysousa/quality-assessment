package br.com.yaw.sjpac.event;

/**
 * Evento deve ser gerado quando for necessário atualizar a tabela de mercadorias.
 * 
 * @author YaW Tecnologia
 */
public class AtualizarListarMercadoriaEvent extends AbstractEvent<Object> {
	
	public AtualizarListarMercadoriaEvent() {
		super(null);
	}

}
