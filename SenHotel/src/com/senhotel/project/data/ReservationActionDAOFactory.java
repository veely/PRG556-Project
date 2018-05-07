package com.senhotel.project.data;

import java.sql.SQLException;
import javax.sql.DataSource;
import ca.mkk.project.DataSourceFactory;
import ca.on.senecac.prg556.senhotel.dao.*;
import ca.on.senecac.prg556.senhotel.bean.*;

import java.util.List;
import java.util.ArrayList;

public class ReservationActionDAOFactory {
	public static ReservationActionDAO getReservationActionDAO()
	{
		return new ReservationActionData(DataSourceFactory.getDataSource());
	}
}
