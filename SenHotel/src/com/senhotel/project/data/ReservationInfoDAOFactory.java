// By Milan K.
package com.senhotel.project.data;

import ca.mkk.project.DataSourceFactory;
import ca.on.senecac.prg556.senhotel.dao.ReservationInfoDAO;

public class ReservationInfoDAOFactory {

	public static ReservationInfoDAO getReservationInfoDAO()
	{
		return new ReservationInfoData(DataSourceFactory.getDataSource());
		
	}

}
