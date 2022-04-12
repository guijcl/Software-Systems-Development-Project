package controller.web.inputController.actions;

import java.io.IOException;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dto.TicketDTO;
import facade.exceptions.ApplicationException;
import facade.services.IClientServiceRemote;
import presentation.web.model.SingleSaleModel;

@Stateless
public class SingleSeatSaleInfoAction extends Action {

	@EJB
	private IClientServiceRemote clientService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SingleSaleModel model = createHelper(request);
		request.setAttribute("model", model);
		try {
			Collection<TicketDTO> ticketsDTO = clientService.chooseSingleSeats(model.getSeatsStr(), model.getEmail());
			ticketsDTO = clientService.buySingleTickets();
			updateModel(model, ticketsDTO);
			request.getRequestDispatcher("/singleTicketSale/singleSeatSaleInfo.jsp").forward(request, response);
		} catch (ApplicationException e) {
			model.addMessage("Request unable to be completed: " + e.getMessage());
			request.getRequestDispatcher("/singleTicketSale/singleSeatSaleError.jsp").forward(request, response);
		}

	}

	private SingleSaleModel createHelper(HttpServletRequest request) {
        // Create the object model
		SingleSaleModel model = new SingleSaleModel();

        model.setClientService(clientService);

        // fill it with data from the request
        model.setSeatsStr(request.getParameter("seats"));
		model.setEmail(request.getParameter("email"));
        
        return model;
    }
	
	protected void updateModel(SingleSaleModel model, Collection<TicketDTO> ticketsDTO) {
		double sum = 0;
		for(TicketDTO t : ticketsDTO) {
			sum += t.getPrice();
			model.setDates(t.getDate());
		}
		model.setPrice(String.valueOf(sum));
	}
	
}
