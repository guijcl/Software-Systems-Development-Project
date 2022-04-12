package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.services.IClientServiceRemote;
import presentation.web.model.PassSaleModel;

@Stateless
public class BuyPassTicketsAction extends Action {

	@EJB
	private IClientServiceRemote clientService;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PassSaleModel model = new PassSaleModel();
		model.setClientService(clientService);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/passTicketSale/buyPassTickets.jsp").forward(request, response);
	}

}
