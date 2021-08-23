package br.com.yaw.sjpac.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.yaw.sjpac.model.Mercadoria;

/**
 * Implementa o contrato de persistência da entidade <code>Mercadoria</code>. 
 * Utiliza a herança para <code>AbstractDAO</code> para resolver as operações básicas de cadastro com <code>JPA</code>.
 * 
 * @see br.com.yaw.sjpac.dao.MercadoriaDAO
 * @see br.com.yaw.sjpac.dao.AbstractDAO
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaDAOJPA extends AbstractDAO<Mercadoria, Integer> implements MercadoriaDAO {

	/**
	 * @param em Recebe a referência para o <code>EntityManager</code>.
	 */
	public MercadoriaDAOJPA(EntityManager em) {
		super(em);
	}

	/**
	 * Reliza a pesquisa mercadorias com filtro no nome (via operador <code>like</code>).
	 * @see br.com.yaw.sjpac.dao.MercadoriaDAO#getMercadoriasByNome(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Mercadoria> getMercadoriasByNome(String nome) {
		if (nome == null || nome.isEmpty())
			return null;
		Query query = runQuery(nome);
		return (List<Mercadoria>) query.getResultList();
	}
	
	private Query runQuery(String nameLike) {
		Query query = getPersistenceContext().createQuery("SELECT o FROM Mercadoria o WHERE o.nome like :nome");
		query.setParameter("nome", nameLike.concat("%"));
		return query;
	}
	

}
