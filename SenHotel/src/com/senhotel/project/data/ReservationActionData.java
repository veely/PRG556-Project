//Vincent Ly
package com.senhotel.project.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ca.mkk.project.DataSourceFactory;
import ca.on.senecac.prg556.senhotel.dao.*;
import ca.on.senecac.prg556.senhotel.bean.*;

import java.util.List;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;


class ReservationActionData implements ReservationActionDAO
{
	private DataSource ds;
	ReservationActionData(DataSource ds)
	{
		setDs(ds);
	}
	private DataSource getDataSource()
	{
		return ds;
	}
	private void setDs(DataSource ds)
	{
		this.ds = ds;
	}
	@Override
	public void cancelReservation(int hotelId, int roomNo, int guestId) throws SQLException
	{
		try
		{
			try (Connection conn = getDataSource().getConnection())
			{
				try (PreparedStatement psGuest = conn.prepareStatement("SELECT hotelid, roomno, guestid FROM reservations WHERE hotelid = ? AND roomno = ? AND guestid = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE))
				{
					psGuest.setInt(1, hotelId);
					psGuest.setInt(2, roomNo);
					psGuest.setInt(3, guestId);
					
					try (ResultSet rsGuest = psGuest.executeQuery())
					{
						while (rsGuest.next())
						{
							rsGuest.deleteRow();
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			//do nothing
		}
	}
	
	@Override
	public Reservation createReservation(int hotelId, int roomNo, int guestId) throws SQLException 
	{
		try (Connection conn = getDataSource().getConnection())
		{
			try (Statement cmd = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE))
			{		
				try (ResultSet rsRsrv = cmd.executeQuery("SELECT * FROM reservations"))
				{
					rsRsrv.moveToInsertRow();
					rsRsrv.updateInt("hotelid",hotelId);
					rsRsrv.updateInt("roomno", roomNo);
					rsRsrv.updateInt("guestid", guestId);
					rsRsrv.insertRow();
					new HotelDAOFactory();
					Hotel hotelobj = HotelDAOFactory.getHotelDAO().getHotel(hotelId);
					return new Reservation(hotelId, hotelobj.getName(), roomNo, guestId);
				}
			}
		}
		catch (Exception e)
		{
			return null;
		}
		/*List<Hotel> hotellist = new HotelDAOFactory().getHotelDAO().getAvailableHotels();
		String hotelname = null;
		
		if (hotellist.get(0).getId() == hotelId)
			hotelname = hotellist.get(0).getName();
		else if (hotellist.get(1).getId() == hotelId)
			hotelname = hotellist.get(1).getName();
		else if (hotellist.get(2).getId() == hotelId)
			hotelname = hotellist.get(2).getName();
		
		Reservation res = new Reservation(hotelId, hotelname, roomNo, guestId);
		return res;*/
	}

}
