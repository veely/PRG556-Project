package com.senhotel.project.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ca.on.senecac.prg556.senhotel.bean.Hotel;
import ca.on.senecac.prg556.senhotel.bean.Reservation;
import ca.on.senecac.prg556.senhotel.dao.ReservationInfoDAO;

class ReservationInfoData implements ReservationInfoDAO{
	private DataSource ds;
	ReservationInfoData(DataSource ds)
	{
		setDataSource(ds);
	}
	private void setDataSource(DataSource ds2) {
		// TODO Auto-generated method stub
		this.ds = ds2;
	}
	private DataSource getDataSource()
	{
		return ds;
	}
	public ReservationInfoData() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Reservation getReservation(int hotelID, int roomNO) throws SQLException {
		for (Reservation reservation : getReservations(hotelID))
		{
		    if (reservation.getRoomNo() == roomNO)
			  return reservation;
		}
		return null;
		
	}

	@Override
	public List<Reservation> getReservations(int hotelID) throws SQLException {
		
		try(Connection conn = getDataSource().getConnection())
		{
			try (PreparedStatement cmd = conn.prepareStatement("SELECT hotelId,name,roomno,guestid FROM reservations inner join hotels on reservations.hotelid = hotels.id where hotelid =?"))
				{
					cmd.setInt(1, hotelID);
					try(ResultSet rslt = cmd.executeQuery())
					{
						List<Reservation> reservations = new ArrayList<Reservation>();
						while (rslt.next())
						{	
							
							reservations.add(new Reservation(rslt.getInt("hotelid"), rslt.getString("name"),rslt.getInt("roomno"), rslt.getInt("guestid")));
						}
						return reservations;
					}
				}
		}
	}

	@Override
	public boolean isReserved(int hotelID, int roomNO) throws SQLException {
		
			if(getReservation(hotelID, roomNO) != null)
				return true;
			else
				return false;
	}

}
