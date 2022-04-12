package business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

@Stateless
public class TypeCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	public EType getType (String name) throws ApplicationException {
		TypedQuery<EType> query = em.createNamedQuery(EType.FIND_BY_NAME, EType.class);
		query.setParameter(EType.NAME, name);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Type not found.", e);
		}
	}
	
	public List<EType> getTypes() throws ApplicationException {
		try {
			TypedQuery<EType> query = em.createNamedQuery(EType.FIND_ALL, EType.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the types list", e);
		}
	}

}
