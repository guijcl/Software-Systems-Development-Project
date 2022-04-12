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
import presentation.web.model.PassSaleModel;

@Stateless
public class PassTicketInfoAction extends Action {

	@EJB
	private IClientServiceRemote clientService;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PassSaleModel model = createHelper(request);
		request.setAttribute("model", model);
		try {
            Collection<TicketDTO> ticketsDTO = clientService.orderPassTicket(Integer.parseInt(model.getQuantity()), model.getEmail());
            updateModel(model, ticketsDTO);
            request.getRequestDispatcher("/passTicketSale/passTicketInfo.jsp").forward(request, response);
        } catch (ApplicationException e) {
            model.addMessage("Request unable to be completed: " + e.getMessage());
            request.getRequestDispatcher("/passTicketSale/passTicketError.jsp").forward(request, response);
        }		
	}
	
	private PassSaleModel createHelper(HttpServletRequest request) {
        // Create the object model
		PassSaleModel model = new PassSaleModel();
        model.setClientService(clientService);

        // fill it with data from the request
        model.setQuantity(request.getParameter("quantity"));
        model.setEmail(request.getParameter("email"));

        return model;
    }
	
	protected void updateModel(PassSaleModel model, Collection<TicketDTO> ticketsDTO) {
		double sum = 0;
		String name = "";
		for(TicketDTO t : ticketsDTO) {
			sum += t.getPrice();
			name = t.getEventName();
		}
		model.setPrice(String.valueOf(sum));
		model.setEventName(name);
	}
	

}
