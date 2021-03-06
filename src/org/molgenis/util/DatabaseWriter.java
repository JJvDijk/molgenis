package org.molgenis.util;

import java.util.ArrayList;
import java.util.List;

import org.molgenis.framework.db.AbstractMapper;
import org.molgenis.framework.db.Database;

/** Tuple writer that translates tuples into rows in a Database */
public class DatabaseWriter implements TupleWriter
{
	public final int BATCH_SIZE = AbstractMapper.BATCH_SIZE;
	private final Database db;
	private final Class<? extends Entity> entityClass;
	private final List<Entity> batch = new ArrayList<Entity>();

	public DatabaseWriter(Database db, Class<? extends Entity> entityClass)
	{
		this.db = db;
		this.entityClass = entityClass;
	}

	@Override
	public void writeHeader() throws Exception
	{
		// NA
	}

	@Override
	public void writeRow(Entity e) throws Exception
	{
		if (e.getClass().equals(entityClass)) batch.add(e);

		if (batch.size() >= BATCH_SIZE)
		{
			db.add(batch);
			batch.clear();
			;
		}
	}

	@Override
	public void writeRow(Tuple t)
	{
		try
		{
			Entity e;
			e = this.entityClass.newInstance();
			e.set(t);
			this.writeRow(e);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void writeValue(Object object)
	{
		throw new UnsupportedOperationException("can only write tuples or entities");
	}

	@Override
	public void setHeaders(List<String> fields)
	{
		//TODO we could use this to only set particular fields
		throw new UnsupportedOperationException("can only write fields in class");
	}

	@Override
	public void writeEndOfLine()
	{
		//NA
	}

	@Override
	public void close() throws Exception
	{
		if(batch.size() > 0)
		{
			db.add(batch);
			batch.clear();
		}

	}

	@Override
	public void writeMatrix(List<String> rowNames, List<String> colNames,
			Object[][] elements)
	{
		throw new UnsupportedOperationException();
	}

}
