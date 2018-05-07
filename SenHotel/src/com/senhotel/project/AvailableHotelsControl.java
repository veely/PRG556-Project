/*****************
 * @author Milan K.
 ******************/
package com.senhotel.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.internal.util.privilegedactions.GetConstructor;

import com.senhotel.project.data.HotelDAOFactory;

import ca.on.senecac.prg556.common.Control;
import ca.on.senecac.prg556.senhotel.bean.Guest;
import ca.on.senecac.prg556.senhotel.bean.Hotel;

import java.sql.SQLException;
import java.util.List;

public class AvailableHotelsControl implements Control{

	/**
	 * 
	 */
	public AvailableHotelsControl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doLogic(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// gets the list of available hotels 
			try
			{
				HttpSession session = req.getSession();
				Guest gSession = (Guest)session.getAttribute("userSession");
				int GuestID = gSession.getId(); // no method to use with
				List <Hotel> hotel_list;
				hotel_list = HotelDAOFactory.getHotelDAO().getAvailableHotels();
				req.setAttribute("hotels", hotel_list);
				return null;
			}
			catch ( SQLException sql )
			{
				throw new ServletException(sql);
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
