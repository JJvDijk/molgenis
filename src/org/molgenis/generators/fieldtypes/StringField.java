package org.molgenis.generators.fieldtypes;

import org.molgenis.model.MolgenisModelException;
import org.molgenis.model.elements.Field;

public class StringField extends AbstractField
{
	@Override
	public String getJavaAssignment(String value) throws MolgenisModelException
	{
		if(value == null || value.equals("") ) return "null";
		return "\""+value+"\"";
	}
	
	@Override
	public String getJavaPropertyDefault() throws MolgenisModelException
	{
		return getJavaAssignment(f.getDefaultValue());
	}

	@Override
	public String getJavaPropertyType() throws MolgenisModelException
	{
		return "String";
	}

	@Override
	public String getMysqlType() throws MolgenisModelException
	{
		return "VARCHAR("+f.getVarCharLength()+")";
	}

	@Override
	public String getHsqlType() throws MolgenisModelException
	{
		return "VARCHAR("+f.getVarCharLength()+")";
	}
	@Override
	public String getXsdType() throws MolgenisModelException
	{
		// TODO Auto-generated method stub
		return "string";
	}

}
