package business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

@Stateless
public class ProducerCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	public Producer getProducer (int id) throws ApplicationException {
		TypedQuery<Producer> query = em.createNamedQuery(Producer.FIND_BY_ID, Producer.class);
		query.setParameter(Producer.PID, id);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Producer not found.", e);
		}
	}
	
	public List<Producer> getProducers() throws ApplicationException {
		try {
			TypedQuery<Producer> query = em.createNamedQuery(Producer.FIND_ALL, Producer.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the producers list", e);
		}
	}
	
	public boolean validNumber(int num) throws ApplicationException {
		List<Producer> listProducers = getProducers();
		Boolean check = false;
		for(Producer p : listProducers)
			if(p.getId() == num) check = true;
		return check;
	}


}
