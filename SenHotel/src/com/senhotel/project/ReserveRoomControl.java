//Vincent Ly
package com.senhotel.project;

import ca.on.senecac.prg556.common.Control;
import ca.on.senecac.prg556.common.StringHelper;
import ca.on.senecac.prg556.senhotel.dao.*;
import ca.on.senecac.prg556.senhotel.bean.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.senhotel.project.data.HotelDAOFactory;
import com.senhotel.project.data.ReservationActionDAOFactory;
import com.senhotel.project.data.ReservationInfoDAOFactory;

import ca.on.senecac.prg556.senhotel.bean.Hotel;


public class ReserveRoomControl implements Control
{
	public String doLogic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Reservation reserve = null;
		String hotelid = request.getParameter("hotel_id");
		Hotel hotelobj = null;
		
		if (StringHelper.isNullOrEmpty(hotelid))
		{
			throw new RequestInvalidException();
		}
		else
		{
			
			List<Hotel> hotelList;
			try 
			{
				hotelList = HotelDAOFactory.getHotelDAO().getAvailableHotels();
				
				for (Hotel i : hotelList)
				{
					if (i.getId() == Integer.parseInt(hotelid))
					{
						hotelobj = (i);
					}
				}
				
				if ("POST".equals(request.getMethod()) & StringHelper.isNotNullOrEmpty((String)request.getParameter("Submit")))
				{
					int floor, unit;
					try
					{
						floor = Integer.parseInt(request.getParameter("floor"));
						unit = Integer.parseInt(request.getParameter("unit"));
					}
					catch (NumberFormatException nfe)
					{
						throw new RequestInvalidException(nfe);
					}
					
					if ((floor < 1 | floor > hotelobj.getFloors()) | (unit < 1 | unit > hotelobj.getRoomsPerFloor()))
						throw new RequestInvalidException();
					else
					{
						boolean isReserved;
						int roomNO = floor*100 + unit;
						
						isReserved = ReservationInfoDAOFactory.getReservationInfoDAO().isReserved(Integer.parseInt(hotelid), roomNO);
						if (isReserved == false)
						{
							HttpServletRequest req = (HttpServletRequest)request;
							int guestId = ((Guest)req.getSession().getAttribute("userSession")).getId();
							reserve = ReservationActionDAOFactory.getReservationActionDAO().createReservation(hotelobj.getId(), roomNO, guestId);
							return "R:reservations.jspx";
						}
						else
						{
							
							List<Reservation> reslist = ReservationInfoDAOFactory.getReservationInfoDAO().getReservations(hotelobj.getId());
							Reservation[][] resarray = new Reservation[hotelobj.getFloors()][hotelobj.getRoomsPerFloor()];
							for (Reservation i : reslist)
							{
								int currentfloor = Integer.parseInt( Integer.toString(i.getRoomNo()).substring(0, 1) );
								int currentunit = Integer.parseInt( Integer.toString(i.getRoomNo()).substring(1) );
								resarray[currentfloor-1][currentunit-1] = i;	//assigns current Reservation object in the list to object array
							}
							request.setAttribute("hotel", hotelobj);
							request.setAttribute("resarray", resarray);
						}
					}
				}
				else
				{
					List<Reservation> reslist = ReservationInfoDAOFactory.getReservationInfoDAO().getReservations(hotelobj.getId());
					Reservation[][] resarray = new Reservation[hotelobj.getFloors()][hotelobj.getRoomsPerFloor()];
					for (Reservation i : reslist)
					{
						int currentfloor = i.getRoomNo()/100;
						int currentunit = i.getRoomNo()%100;
						resarray[currentfloor-1][currentunit-1] = i;	//assigns current Reservation object in the list to object array
					}
					request.setAttribute("hotel", hotelobj);
					request.setAttribute("resarray", resarray);
				}
			} 
			catch (SQLException e)
			{
				throw new ServletException(e);
			}
		}
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
