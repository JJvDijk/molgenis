package org.molgenis.framework.db.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public abstract class AbstractDataSourceWrapper implements DataSourceWrapper
{
	private static transient final Logger logger = Logger.getLogger(AbstractDataSourceWrapper.class.getSimpleName());
	
	public Connection getConnection() throws NamingException, SQLException
	{
		return this.getDataSource().getConnection();
	}
	
	@Override
	public String getDriverClassName()
	{
		DataSource ds;
		try
		{
			ds = getDataSource();
			if(ds instanceof BasicDataSource)
			{
				return ((BasicDataSource)ds).getDriverClassName();
			}
		}
		catch (NamingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.error("UNKNOWN DRIVER");
		return "UNKNOWN";
	}
	
	
	protected abstract DataSource getDataSource() throws NamingException;
}
