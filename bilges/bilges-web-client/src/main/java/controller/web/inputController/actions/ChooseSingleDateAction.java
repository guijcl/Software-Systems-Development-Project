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
public class ChooseSingleDateAction extends Action {

	@EJB
	private IClientServiceRemote clientService;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SingleSaleModel model = createHelper(request);
		model.getPeriods();
		request.setAttribute("model", model);
		request.getRequestDispatcher("/singleTicketSale/chooseSingleDate.jsp").forward(request, response);
	}
	
	private SingleSaleModel createHelper(HttpServletRequest request) {
        // Create the object model
		SingleSaleModel model = new SingleSaleModel();
        model.setClientService(clientService);

        // fill it with data from the request
        model.setEventName(request.getParameter("event name"));

        return model;
    }

}
