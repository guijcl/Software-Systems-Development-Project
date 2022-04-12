package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

@Stateless
public class UserCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	public EUser getUser (String name) throws ApplicationException {
		TypedQuery<EUser> query = em.createNamedQuery(EUser.FIND_BY_NAME, EUser.class);
		query.setParameter(EUser.UNAME, name);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("User not found.", e);
		}
	}
	
	public void addUser (String name) {
		EUser c = new EUser(name);
		em.persist(c);
	}

}
