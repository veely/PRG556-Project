package com.senhotel.project.data;

import ca.on.senecac.prg556.senhotel.dao.*;
import ca.on.senecac.prg556.senhotel.bean.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

class GuestData implements GuestDAO
{
	private DataSource ds;
	GuestData(DataSource ds)
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
	public Guest authenticateGuest(String username, String password) throws SQLException
	{
		try
		{
			try (Connection conn = getDataSource().getConnection())
			{
				try (PreparedStatement psGuest = conn.prepareStatement("SELECT id, firstname, lastname FROM guests WHERE username = ? AND password = ?"))
				{
					psGuest.setString(1, username);
					psGuest.setString(2, password);
					try (ResultSet rsGuest = psGuest.executeQuery())
					{
						if (rsGuest.next())
						{
							return new Guest(rsGuest.getInt("id"), rsGuest.getString("firstname"), rsGuest.getString("lastname"));
						}
						else
							return null;
					}
				}
			}
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	public Guest getGuest(int guestId) throws SQLException
	{
		try
		{
			try (Connection conn = getDataSource().getConnection())
			{
				try (PreparedStatement psGuest = conn.prepareStatement("SELECT id, firstname, lastname FROM guests WHERE id = ?"))
				{
					psGuest.setInt(1, guestId);
					try (ResultSet rsGuest = psGuest.executeQuery())
					{
						if (rsGuest.next())
						{
							return new Guest(rsGuest.getInt("id"), rsGuest.getString("firstname"), rsGuest.getString("lastname"));
						}
						else
							return null;
					}
				}
			}
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	public List<Reservation> getReservations(int guestId) throws SQLException
	{
		try
		{
			try (Connection conn = getDataSource().getConnection())
			{
				try (PreparedStatement psRes = conn.prepareStatement("SELECT hotelid, name, roomno, guestid FROM reservations INNER JOIN hotels ON reservations.hotelid=hotels.id WHERE guestid = ?"))
				{
					psRes.setInt(1, guestId);
					try (ResultSet rsRes = psRes.executeQuery())
					{
						List<Reservation> reslist = new ArrayList<Reservation>();
						while (rsRes.next())
						{
							reslist.add(new Reservation(rsRes.getInt("hotelid"), rsRes.getString("name"), rsRes.getInt("roomno"), rsRes.getInt("guestid")));
						}
						return reslist;
					}
				}
			}
		}
		catch (Exception e)
		{
			return null;
		}
	}
}