//by Milan K.
package com.senhotel.project.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ca.on.senecac.prg556.senhotel.bean.Hotel;
import ca.on.senecac.prg556.senhotel.dao.HotelDAO;

class HotelData implements HotelDAO{
	private DataSource ds;
	HotelData(DataSource ds)
	{
		setDataSource(ds);
	}
	@Override
	public List<Hotel> getAvailableHotels() throws SQLException {
		
		try (Connection conn = getDataSource().getConnection())
		{
			try(Statement cmd = conn.createStatement())
			{
				try (ResultSet rslt = cmd.executeQuery("SELECT * FROM hotels"))
				{
					List <Hotel> hotels = new ArrayList<Hotel>();
					while(rslt.next())
					{
						try(PreparedStatement count = conn.prepareStatement("SELECT COUNT(*) AS count FROM reservations WHERE hotelid = ?"))
						{				// checks if there there are rooms are available						
							count.setInt(1, rslt.getInt("id"));
							try(ResultSet rslt_c = count.executeQuery())
							{
								if (rslt_c.next())
								{
									if(rslt.getInt("rooms")!=rslt_c.getInt("count"))
										hotels.add(new Hotel(rslt.getInt("id"), rslt.getString("name"), rslt.getInt("floors"), rslt.getInt("rooms")));
								}
							}
						}
					}
					return hotels;
				}
			}
		}
}

	@Override
	public Hotel getHotel(int ID) throws SQLException {
		try(Connection conn = getDataSource().getConnection())
		{
			try (PreparedStatement cmd = conn.prepareStatement("SELECT * FROM hotel WHERE id=?"))
				{
					cmd.setInt(1, ID);
					try(ResultSet rslt = cmd.executeQuery())
					{
						if (rslt.next())
						{	
							return new Hotel(rslt.getInt("id"), rslt.getString("name"), rslt.getInt("floors"), rslt.getInt("rooms"));
						}	
						else 
							return null;
					}
				}
		}
	}
	private DataSource getDataSource()
	{
		return ds;
	}
	private void setDataSource(DataSource ds)
	{
		this.ds = ds;
	}
}