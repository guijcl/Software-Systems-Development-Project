package application;

import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.AddEventHandler;
import business.EType;
import business.Installation;
import facade.dto.EventDTO;
import facade.dto.InstallationDTO;
import facade.dto.TypeDTO;
import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;

@Stateless
//@Transactional(Transactional.TxType.REQUIRES_NEW)
public class EventService implements IEventServiceRemote {
	
	@EJB
	private AddEventHandler eventHandler;
	
	@Override
	public Collection<TypeDTO> createEvent() throws ApplicationException {
		List<EType> types = eventHandler.createEvent();
		List<TypeDTO> result = new LinkedList<>();
		for(EType t : types) {
			result.add(new TypeDTO(t.getID(), t.getTypeName(), t.getSeatingType(), t.getmaxCap()));
		}
		return result;
	}
	
	@Override
	public EventDTO addEvent(String type, String name, List<String> dates, List<String> hours, int producer) throws ApplicationException, ParseException {
		return eventHandler.addEvent(type, name, dates, hours, producer);
	}
	
	@Override
	public Collection<InstallationDTO> startInstallation() throws ApplicationException {
		List<Installation> installations = eventHandler.startInstallation();
		List<InstallationDTO> result = new LinkedList<>();
		for(Installation i : installations) {
			result.add(new InstallationDTO(i.getId(), i.getName(), i.getCapacity()));
		}
		return result;
	}
	
	@Override
	public EventDTO assignInstallation(String eventName, String installationName, String dateStartSale, double singlePrice, double passPrice) throws ApplicationException, ParseException {
		return eventHandler.assignInstallation(eventName, installationName, dateStartSale, singlePrice, passPrice);
	}

}
