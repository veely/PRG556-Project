//by Milan K.
package com.senhotel.project.data;
import javax.sql.DataSource;

import ca.mkk.project.DataSourceFactory;
import ca.on.senecac.prg556.senhotel.dao.HotelDAO;
public class HotelDAOFactory {

	public static HotelDAO getHotelDAO() {
		return new HotelData(DataSourceFactory.getDataSource());	
		// TODO Auto-generated constructor stub
	}

}
