//Vincent Ly
package com.senhotel.project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.senhotel.project.data.GuestDAOFactory;

import ca.on.senecac.prg556.senhotel.bean.Guest;
import ca.on.senecac.prg556.senhotel.bean.Reservation;

public class SenHotelMenuFilter implements Filter
{
	
	@Override
	public void destroy()
	{
		
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		try
		{
			HttpServletRequest req = (HttpServletRequest)request;
			Guest user = ((Guest)req.getSession().getAttribute("userSession"));
			int guestId = ((Guest)req.getSession().getAttribute("userSession")).getId();
			
			List<Reservation> temp = GuestDAOFactory.getGuestDAO().getReservations(guestId);
			int resSize= temp.size();
			{
				request.setAttribute("reservationcount", resSize);
			}
			request.setAttribute("guest", user);
			chain.doFilter(request, response);
		}
		catch (SQLException e)
		{
			throw new ServletException(e);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		
		
	}

}
