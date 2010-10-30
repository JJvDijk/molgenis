<#include "GeneratorHelper.ftl">
<#function csv items>
	<#local result = "">
	<#list items as item>
		<#if item_index != 0>
			<#local result =  result + ",">
		</#if>
		<#if item?is_hash>
			<#local result = result + "\""+item.name+"\"">
		<#else>
			<#local result = result + "\""+item+"\"">
		</#if>
	</#list>
	<#return result>
</#function>
<#--#####################################################################-->
<#--                                                                   ##-->
<#--         START OF THE OUTPUT                                       ##-->
<#--                                                                   ##-->
<#--#####################################################################-->
/* File:        ${model.getName()}/model/JDBCDatabase
 * Copyright:   Inventory 2000-${year?c}, GBIC 2002-${year?c}, all rights reserved
 * Date:        ${date}
 * 
 * generator:   ${generator} ${version}
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */
package ${package};

import java.util.Arrays;

import org.molgenis.framework.db.DatabaseException;
import org.molgenis.model.elements.Entity;
import org.molgenis.model.elements.Model;
import org.molgenis.model.elements.Field;
import org.molgenis.model.MolgenisModelException;
import org.molgenis.model.MolgenisModelValidator;
import org.molgenis.MolgenisOptions;

/**
 * This class is an in memory representation of the contents of your *_db.xml file
 * Utility of this class is to allow for dynamic querying and/or user interfacing
 * for example within a query tool or a security module.
 */
public class JDBCMetaDatabase extends Model
{
	public JDBCMetaDatabase() throws DatabaseException
	{
		super("${model.name}");
		try
		{
			<#list entities as entity><#if entity.abstract>
			Entity ${name(entity)}_entity = new Entity("${entity.name}",this.getDatabase());
			${name(entity)}_entity.setAbstract(true);<#if entity.hasImplements()>
			${name(entity)}_entity.setImplements(new String[]{${csv(entity.implements)}});</#if><#if entity.hasAncestor()>
			${name(entity)}_entity.setParents(new String[]{"${entity.getAncestor().name}"});</#if>
			<#list entity.getFields() as field><#if field.name != typefield()>
			Field ${name(entity)}_${name(field)}_field = new Field(${name(entity)}_entity, "${field.name}", Field.Type.getType("${field.type}"));
			<#if field.auto>
			${name(entity)}_${name(field)}_field.setAuto(true);
			</#if>
			<#if field.type == "xref" || field.type == "mref">${name(entity)}_${name(field)}_field.setXRefVariables("${field.xrefEntityName}", "${field.xrefFieldName}",Arrays.asList(new String[]{${csv(field.xrefLabelNames)}}));</#if>
			${name(entity)}_entity.addField(${name(entity)}_${name(field)}_field);
			</#if></#list>
			<#list entity.keys as key>
			${name(entity)}_entity.addKey(Arrays.asList(new String[]{${csv(key.fields)}}),<#if key.isSubclass()>true<#else>false</#if>,"");
			</#list></#if></#list>
			
			<#list entities as entity><#if !entity.abstract && !entity.association>
			Entity ${name(entity)}_entity = new Entity("${entity.name}",this.getDatabase());<#if entity.hasImplements()>
			${name(entity)}_entity.setImplements(new String[]{${csv(entity.implements)}});</#if><#if entity.hasAncestor()>
			${name(entity)}_entity.setParents(new String[]{"${entity.getAncestor().name}"});</#if>
			<#list entity.getFields() as field><#if field.name != typefield()>
			Field ${name(entity)}_${name(field)}_field = new Field(${name(entity)}_entity, "${field.name}", Field.Type.getType("${field.type}"));
			<#if field.auto>
			${name(entity)}_${name(field)}_field.setAuto(true);
			</#if>
			<#if field.type == "xref" || field.type == "mref">${name(entity)}_${name(field)}_field.setXRefVariables("${field.xrefEntityName}", "${field.xrefFieldName}",Arrays.asList(new String[]{${csv(field.xrefLabelNames)}}));</#if>
			${name(entity)}_entity.addField(${name(entity)}_${name(field)}_field);
			</#if></#list>
			<#list entity.keys as key>
			${name(entity)}_entity.addKey(Arrays.asList(new String[]{${csv(key.fields)}}),<#if key.isSubclass()>true<#else>false</#if>,"");
			</#list></#if></#list>
			
			
			new MolgenisModelValidator();
			MolgenisModelValidator.validate(this, new MolgenisOptions());

		} catch (MolgenisModelException e)
		{
			throw new DatabaseException(e);
		}
	}
}