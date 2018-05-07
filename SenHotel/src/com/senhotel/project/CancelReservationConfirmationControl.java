/*****************
 * @author Milan K.
 ******************/
package com.senhotel.project;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.senhotel.project.data.GuestDAOFactory;
import com.senhotel.project.data.ReservationActionDAOFactory;
import com.senhotel.project.data.ReservationInfoDAOFactory;

import ca.on.senecac.prg556.common.Control;
import ca.on.senecac.prg556.common.StringHelper;
import ca.on.senecac.prg556.senhotel.bean.Guest;
import ca.on.senecac.prg556.senhotel.bean.Reservation;



public class CancelReservationConfirmationControl implements Control{
	
public CancelReservationConfirmationControl() 
{}

	@Override
	public String doLogic(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		HttpSession session = req.getSession();
		Guest gSession = (Guest)session.getAttribute("userSession");
		Reservation Res_canc;
		int hotelId = Integer.parseInt(req.getParameter("hotel_id"));
		int roomNo = Integer.parseInt(req.getParameter("room_no"));
		try {
			if (hotelId==0 && roomNo==0)
			throw new RequestInvalidException();
			try
			{
				Res_canc = ReservationInfoDAOFactory.getReservationInfoDAO().getReservation(hotelId, roomNo);
			}
			catch (SQLException  npesql)
			{
					throw new RequestInvalidException("invalid reservation, ' npesql_occured'",npesql);
			}
			} 
		catch (NumberFormatException | NullPointerException nfnpe) 
		{
			throw new RequestInvalidException(String.format("parameters are null for hotelid=%1d room_no=%2d",hotelId,roomNo),nfnpe);
		}

		if("POST".equals(req.getMethod()))
		{
			if (req.getParameter("cancel") != null)	// from reservations.jspx
			{
			try 
			{
				Res_canc = ReservationInfoDAOFactory.getReservationInfoDAO().getReservation(hotelId, roomNo);
				
				if (null == Res_canc)
					throw new RequestInvalidException("Not a valid reservation");
			} 
			catch (SQLException sql) 
			{
				throw new RequestInvalidException("SQL exception occured", sql);
			}
		 req.setAttribute("confirm_reserv", Res_canc);
		 return null;
		}
			
			if(null!=req.getParameter("confirm"))
			{
				try		//Canceling reservation
				{
					ReservationActionDAOFactory.getReservationActionDAO().cancelReservation(Res_canc.getHotelId(), Res_canc.getRoomNo(), Res_canc.getGuestId());
					if ( GuestDAOFactory.getGuestDAO().getReservations(gSession.getId())!=null)
						return "R:"+req.getContextPath()+"/reservations.jspx"; 
					// else return 	null
				}
				catch ( SQLException sql)
				{
				throw new ServletException(sql);
				}
			}
			if(null!=(req.getParameter("abort")))
				return "R:"+req.getContextPath()+"/reservations.jspx"; 
		}
		else 
			return "R:"+req.getContextPath()+"/reservations.jspx"; 
		return null; 
		
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
