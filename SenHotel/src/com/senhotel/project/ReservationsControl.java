/*****************
 * @author Milan K.
 ******************/
package com.senhotel.project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.senhotel.project.data.GuestDAOFactory;
import com.senhotel.project.data.ReservationInfoDAOFactory;

import ca.on.senecac.prg556.common.Control;
import ca.on.senecac.prg556.senhotel.bean.Guest;
import ca.on.senecac.prg556.senhotel.bean.Hotel;
import ca.on.senecac.prg556.senhotel.bean.Reservation;

public class ReservationsControl implements Control{

	public ReservationsControl() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String doLogic(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try
		{	// get a list of all reservations associated with the guest 
			HttpSession session = req.getSession();
			Guest gSession = (Guest)session.getAttribute("userSession");
			List<Reservation> reservation_list;
			reservation_list = GuestDAOFactory.getGuestDAO().getReservations(gSession.getId());
			req.setAttribute("reservations", reservation_list);
			//if (reservation_list.isEmpty())
			//req.setAttribute("tester", true);
			return null;
		}
		catch (SQLException sqle)
		{	
			throw new ServletException(sqle);
		}
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
