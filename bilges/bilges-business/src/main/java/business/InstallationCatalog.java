package business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

@Stateless
public class InstallationCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	public Installation getInstallation (String name) throws ApplicationException {
		TypedQuery<Installation> query = em.createNamedQuery(Installation.FIND_BY_NAME, Installation.class);
		query.setParameter(Installation.INAME, name);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Installation not found.", e);
		}
	}
	
	public List<Installation> getInstallations() throws ApplicationException {
		try {
			TypedQuery<Installation> query = em.createNamedQuery(Installation.FIND_ALL, Installation.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the installations list", e);
		}
	}

}
