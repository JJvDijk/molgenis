package org.molgenis.framework.server.async;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.molgenis.MolgenisOptions;
import org.molgenis.framework.server.MolgenisContext;

public class AsyncMolgenisContext extends MolgenisContext
{

	private LoadingScreenFactory loadingScreenUUIDFactory;
	
	
	public AsyncMolgenisContext(ServletContext sc, DataSource ds, MolgenisOptions usedOptions, String variant)
	{
		super(sc, ds, usedOptions, variant);
		this.loadingScreenUUIDFactory = new LoadingScreenFactory();
	}


	public LoadingScreenFactory getLoadingScreenUUIDFactory()
	{
		return loadingScreenUUIDFactory;
	}
	
	
	
}
