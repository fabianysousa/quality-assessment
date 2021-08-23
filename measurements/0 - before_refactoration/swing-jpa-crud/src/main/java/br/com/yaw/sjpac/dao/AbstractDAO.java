package br.com.yaw.sjpac.dao;

import java.lang.reflect.ParameterizedType;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.yaw.sjpac.model.AbstractEntity;

/**
 * Componente <code>DAO</code> (design pattern: <code>Data Access Object</code>) base da aplicação.
 * 
 * <p>
 *  Define as operações básicas de cadastro para cada tipo de entidade. 
 *  (<strong>C</strong>reate / <strong>R</strong>ead / <strong>U</strong>pdate / <strong>D</strong>elete)
 * </p>
 * 
 * <p>Dessa forma os componentes <code>DAO</code> das entidades, a priori, não devem se preocupar com essas funcionalidades.</p>
 * 
 * <p>
 *  Efetivamente, na <code>JPA</code>, todas as operações de persistência são realizadas pelo componente <code>EntityManager</code>, 
 *  por isso dependemos desse componente.
 * </p>
 * 
 * @author YaW Tecnologia
 *
 * @param <Entity> determina o tipo da entidade, deve herdar <code>AbstractEntity</code>.
 * @param <PK> determina o tipo da chave primária (Primary Key) da entidade, deve herdar <code>Number</code>.
 */
public abstract class AbstractDAO<Entity extends AbstractEntity, PK extends Number> {
	
	/**
	 * "Tipo" da entidade gerenciada pelo <code>DAO</code>.
	 */
	private Class<Entity> entityClass;
	
	/**
	 * Referência do <i>contexto de persistência</i>.
	 */
	private EntityManager em;
	
	/**
	 * A dependência para <code>EntityManager</code> deve ser resolvida durante a construção do <code>AbstractDAO</code>.
	 * @param em referência para o <code>EntityManager</code>.
	 */
	@SuppressWarnings("unchecked")
	public AbstractDAO(EntityManager em) {
		this.em = em;
		
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<Entity>) genericSuperclass.getActualTypeArguments()[0];
	}
	
	/**
	 * Faz o <code>insert</code> caso a entidade ainda não tenha sido persistida (<code> id == null </code>), ou <code>update</code> caso contrário. 
	 * @param e indica a entidade que deverá ser persistida.
	 * @return a referência atual da entidade.
	 */
	public Entity save(Entity e) {
		if (e.getId() != null)
			return em.merge(e);
		else {
			em.persist(e);
			return e;
		}
	}

	/**
	 * Faz o <code>delete</code> da entidade no banco de dados.
	 * @param e
	 */
	public void remove(Entity e) {
		em.remove(e);
	}
	
	/**
	 * Busca o registro por <code>id</code> (primary key).
	 * @param id filtro da consulta.
	 * @return a entidade encontrada de acordo com o <code>id</id>.
	 * @throws <code>RuntimeException</code> caso o não exista registro para esse id.
	 */
	public Entity findById(PK id) { 
		return em.find(entityClass, id); 
	}
	
	/**
	 * @return uma coleção (<code>List</code>) com todos os registro da entidade armazenados no banco de dados.
	 * Caso não existam registros, retorna uma coleção vazia.
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> getAll() {
		Query query = getPersistenceContext().createQuery("SELECT o FROM "+entityClass.getName() + " o");
		return (List<Entity>) query.getResultList(); 
	}
	
	/**
	 * @return referência do componente <code>EntityManager</code>.
	 */
	protected EntityManager getPersistenceContext() {
		return this.em;
	}
}
