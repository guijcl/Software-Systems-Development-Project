package business;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class PeriodCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Period addPeriod(Date d, Date s, Date e) {
		Period p = new Period(d, s, e);
		em.persist(p);
		return p;
	}

}
