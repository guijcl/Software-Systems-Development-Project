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
public class OrderTicketsPassAction extends Action {
	
	@EJB
	private IClientServiceRemote clientService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PassSaleModel model = createHelper(request);
		model.getAvailablePasses();
		request.setAttribute("model", model);
		request.getRequestDispatcher("/passTicketSale/orderTicketsPass.jsp").forward(request, response);
	}
	
	private PassSaleModel createHelper(HttpServletRequest request) {
        // Create the object model
		PassSaleModel model = new PassSaleModel();
        model.setClientService(clientService);

        // fill it with data from the request
        model.setEventName(request.getParameter("event name"));

        return model;
    }

}
