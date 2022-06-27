package yehor.epam.actions.commands.tickets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import yehor.epam.actions.BaseCommand;
import yehor.epam.exceptions.TicketException;
import yehor.epam.dao.SeatDAO;
import yehor.epam.dao.TicketDao;
import yehor.epam.entities.Ticket;
import yehor.epam.services.TicketService;
import yehor.epam.services.impl.ErrorServiceImpl;
import yehor.epam.services.impl.TicketServiceImpl;
import yehor.epam.utilities.RedirectManager;
import yehor.epam.utilities.LoggerManager;

import java.util.List;

import static yehor.epam.utilities.constants.CommandConstants.COMMAND_VIEW_SUCCESS_PAY_PAGE;

public class BuyTicketCommand implements BaseCommand {
    private static final Logger logger = LoggerManager.getLogger(BuyTicketCommand.class);
    private static final String CLASS_NAME = BuyTicketCommand.class.getName();
    private final TicketService ticketService;

    public BuyTicketCommand() {
        ticketService = new TicketServiceImpl();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Called execute() in " + CLASS_NAME);
        try {
            List<Ticket> ticketList = (List<Ticket>) request.getSession().getAttribute("ticketList");
            logger.debug("Received ticketList = " + ticketList);
            ticketService.saveTicketList(ticketList);
            response.sendRedirect(RedirectManager.getRedirectLocation(COMMAND_VIEW_SUCCESS_PAY_PAGE));
        } catch (Exception e) {
            ErrorServiceImpl.handleException(request, response, CLASS_NAME, e);
        }
    }
}
