package com.senhotel.project.data;

import ca.mkk.project.DataSourceFactory;
import ca.on.senecac.prg556.senhotel.dao.GuestDAO;

public class GuestDAOFactory
{
	public static GuestDAO getGuestDAO()
	{
		return new GuestData(DataSourceFactory.getDataSource());
	}
}