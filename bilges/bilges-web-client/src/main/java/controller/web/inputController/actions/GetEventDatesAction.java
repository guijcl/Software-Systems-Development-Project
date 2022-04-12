package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.services.IClientServiceRemote;
import presentation.web.model.SingleSaleModel;

@Stateless
public class GetEventDatesAction extends Action {

	@EJB
	private IClientServiceRemote clientService;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SingleSaleModel model = new SingleSaleModel();
		model.setClientService(clientService);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/singleTicketSale/getEventDates.jsp").forward(request, response);
	}
	
}
