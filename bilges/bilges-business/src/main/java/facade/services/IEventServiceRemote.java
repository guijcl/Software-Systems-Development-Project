package facade.services;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;

import facade.dto.EventDTO;
import facade.dto.InstallationDTO;
import facade.dto.TypeDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IEventServiceRemote {

	public Collection<TypeDTO> createEvent() throws ApplicationException;

	public EventDTO addEvent(String type, String name, List<String> dates, List<String> hours, int producer)
			throws ApplicationException, ParseException;

	public Collection<InstallationDTO> startInstallation() throws ApplicationException;

	public EventDTO assignInstallation(String eventName, String installationName, String dateStartSale,
			double singlePrice, double passPrice) throws ApplicationException, ParseException;
}
