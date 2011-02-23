package org.molgenis.fieldtypes;

import java.util.List;

import org.molgenis.MolgenisFieldTypes;
import org.molgenis.model.MolgenisModelException;
import org.molgenis.model.elements.Entity;
import org.molgenis.model.elements.Field;

/**
 * Definition of a MOLGENIS field type. For example <field name="x"
 * type="string" would relate to type StringField
 */
public abstract class FieldType
{
	/**
	 * For xref purposes we sometimes need a handle of the field this type was
	 * defined as part of.
	 */
	protected Field f;

	/**
	 * Get the field type from a field. Equal to field.getType();
	 * 
	 * @param f
	 * @return
	 * @throws MolgenisModelException
	 */
	public FieldType getFieldType(Field f) throws MolgenisModelException
	{
		return MolgenisFieldTypes.get(f);
	}

	/**
	 * Get an entity from the field type (only works if the field type is linked
	 * to a Field instance).
	 * 
	 * @param name
	 * @return
	 */
	public Entity getEntityByName(String name)
	{
		return (Entity) f.getEntity().get(name);
	}

	/**
	 * 
	 * @return
	 * @throws MolgenisModelException
	 */
	public String getJavaSetterType() throws MolgenisModelException
	{
		return this.getJavaPropertyType();
	}

	/**
	 * Product the Java type of this field type. Default: "String".
	 * 
	 * @return type in java code
	 * @throws MolgenisModelException
	 */
	abstract public String getJavaPropertyType() throws MolgenisModelException;

	/**
	 * Produce a valid Java snippet to set the default of a field, using the
	 * 'getDefault' function of that field. Default: "\""+f.getDefault()+"\"".
	 * 
	 * @return default in java code
	 * @throws MolgenisModelException
	 */
	abstract public String getJavaPropertyDefault()
			throws MolgenisModelException;

	/**
	 * Produce a valid Java snippet to set a value for field.
	 * 
	 * @return default in java code
	 * @throws MolgenisModelException
	 */
	public abstract String getJavaAssignment(String value)
			throws MolgenisModelException;

	/**
	 * Produce a valid mysql snippet indicating the mysql type. E.g. "BOOL".
	 * 
	 * @return mysql type string
	 * @throws MolgenisModelException
	 */
	abstract public String getMysqlType() throws MolgenisModelException;

	/**
	 * Produce valid XSD type
	 */
	abstract public String getXsdType() throws MolgenisModelException;

	/**
	 * Convert a list of string to comma separated values.
	 * 
	 * @param elements
	 * @return csv
	 */
	public String toCsv(List<String> elements)
	{
		String result = "";

		for (String str : elements)
		{
			result += ((elements.get(0) == str) ? "" : ",") + "'" + str + "'";
		}

		return result;
	}

	/**
	 * Produce a valid hsql snippet indicating the mysql type. E.g. "BOOL".
	 * 
	 * @return hsql type string
	 * @throws MolgenisModelException
	 */
	public abstract String getHsqlType() throws MolgenisModelException;

	public void setField(Field f)
	{
		this.f = f;
	}

	/**
	 * Get the format string, e.g. '%s'
	 * @return
	 */
	public abstract String getFormatString();

	/**
	 * The string value of this type, e.g. 'int' or 'xref'.
	 */
	public String toString()
	{
		return this.getClass().getSimpleName().replace("Field", "")
				.toLowerCase();
	}
}