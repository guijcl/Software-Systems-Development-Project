package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import facade.dto.EventDTO;
import facade.exceptions.ApplicationException;

@Stateless
public class AddEventHandler {
	
	@EJB
	private TypeCatalog typeCatalog;
	
	@EJB
	private EventCatalog eventCatalog;
	
	@EJB
	private ProducerCatalog producerCatalog;
	
	@EJB
	private PeriodCatalog periodCatalog;
	
	@EJB
	private InstallationCatalog installationCatalog;
	
	@EJB
	private TicketCatalog ticketCatalog;
	
	static final String INDIVIDUAL = "individual";
	
	public List<EType> createEvent() throws ApplicationException {
		try {
			List<EType> typeList = typeCatalog.getTypes();
			return typeList;
		} catch (Exception e) {
			throw new ApplicationException("Error fetching Types ", e);
		}
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public EventDTO addEvent(String type, String name, List<String> dates, 
			List<String> hours, int producer) throws ApplicationException, ParseException {
		
		try {		
			EType t = typeCatalog.getType(type);
			
			if(!eventCatalog.uniqueName(name)) 
				throw new ApplicationException("Invalid Name");
			
			List<Date> startHours = new ArrayList<>();
			List<Date> endingHours = new ArrayList<>();
			if(eventCatalog.validHours(hours, startHours, endingHours)) 
				throw new ApplicationException("Invalid Hours");
			
			SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
			if(eventCatalog.checkConsecutiveDays(dates, day)) {
				System.out.println("3");
				throw new ApplicationException("Invalid Date");
			}
			
			if(!producerCatalog.validNumber(producer)) 
				throw new ApplicationException("Invalid Producer");
			
			Producer p = producerCatalog.getProducer(producer);
			boolean check = false;
			for(EType ty : p.getEventTypeLicence()) {
				if(ty.getTypeName().equals(type)) check = true;
			}
			if(!check)
				throw new ApplicationException("Invalid Producer License");
			
			List<Period> eD = new ArrayList<>();
			for(int i = 0; i < dates.size(); i++) {
				eD.add(periodCatalog.addPeriod(day.parse(dates.get(i)), startHours.get(i), endingHours.get(i)));
			}
			
			Event e = eventCatalog.addEvent(null, name, t, producerCatalog.getProducer(producer), eD, new ArrayList<>());
			System.out.println("AddingEvent done");
			return new EventDTO(e.getId(), e.getName(), e.getType().getTypeName(), null);
		} catch (Exception e) {
			throw new ApplicationException("Error adding Event ", e);
		}
	}
	
	public List<Installation> startInstallation() throws ApplicationException {
		try {
			List<Installation> installationList = installationCatalog.getInstallations();
			return installationList;
		} catch (Exception e) {
			throw new ApplicationException("Error fetching Installations ", e);
		}
	}
	
	
	//@Transactional(Transactional.TxType.REQUIRES_NEW)
	public EventDTO assignInstallation(String eventName, String installationName, String dateStartSale, double singlePrice, double passPrice) throws ApplicationException, ParseException {
		try {			
			if(eventCatalog.uniqueName(eventName)) 
				throw new ApplicationException("Invalid Event name");
			
			Event e = eventCatalog.getEvent(eventName);
			
			if(e.checkForInstallation()) 
				throw new ApplicationException("Already contains Installations");
			
			EType t = e.getType();
			Installation i = installationCatalog.getInstallation(installationName);
			if(!(i.checkSeatingType(INDIVIDUAL) && t.getSeatingType().equals("seated")) && 
					!(i.checkSeatingType("open") && t.getSeatingType().equals("standing")))
				throw new ApplicationException("Incompatible Event/Installation pair");
			
			List<Period> listPeriods = e.getEventDuration();
			if(!i.checkIfAvailable(listPeriods))
				throw new ApplicationException("Date already occupied");
			
			if(i.checkSeatingType(INDIVIDUAL) && i.getCapacity() > t.getmaxCap())
				throw new ApplicationException("Installation can't handle Event capacity");
			
			SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
			Date d = day.parse(dateStartSale);
			if(d.after(listPeriods.get(0).getDate()) && d.before(day.parse(day.format(new Date()))))
				throw new ApplicationException("Invalid start sale date");
			
			if(singlePrice < 0.0)
				throw new ApplicationException("Invalid price");
		
			e.setInstallation(i);
			i.addEventToCalendar(e);
			
			List<Ticket> tickets = new ArrayList<>();
			if(i.checkSeatingType(INDIVIDUAL)) {
				for(Seat s : i.getSeats()) {
					List<Single> listSingles = new ArrayList<>();
					for(Period p : e.getEventDuration()) {
						Single singleTicket = ticketCatalog.addSingleTicket(e, singlePrice, false, false, d, s, p);
						tickets.add(singleTicket);
						listSingles.add(singleTicket);
					}
					if(passPrice >= 0.0)
						tickets.add(ticketCatalog.addPassTicket(e, passPrice, false, false, d, listSingles));
				}
			} else if(i.checkSeatingType("open")) {
				int cap = 0;
				if (i.getCapacity() < e.getType().getmaxCap())
					cap = i.getCapacity();
				else
					cap = e.getType().getmaxCap();
				for(int k = 0; k < cap; k++) {
					List<Single> listSingles = new ArrayList<>();
					for(Period p : e.getEventDuration()) {
						Single singleTicket = ticketCatalog.addSingleTicket(e, singlePrice, false, false, d, null, p);
						tickets.add(singleTicket);
						listSingles.add(singleTicket);
					}
					if(passPrice >= 0.0)
						tickets.add(ticketCatalog.addPassTicket(e, passPrice, false, false, d, listSingles));
				}
			}
			e.setEventTickets(tickets);
			System.out.println("AssignInstallation done");
			return new EventDTO(e.getId(), e.getName(), e.getType().getTypeName(), e.getInstallation().getName());
		} catch (Exception exception) {
			throw new ApplicationException("Error assigning Installation ", exception);
		} 
	}
	
	public EventDTO getEventDetails(int id) throws ApplicationException {
		Event event = eventCatalog.getEventByID(id);
		return new EventDTO(id, event.getName(), event.getType().getTypeName(), event.getInstallation().getName());
	}

}
