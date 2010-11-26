<#include "GeneratorHelper.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="1.0" 
             xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd">
	<persistence-unit name="molgenis" transaction-type="RESOURCE_LOCAL">
	    <exclude-unlisted-classes>false</exclude-unlisted-classes>
	    <properties>
	      <property name="javax.persistence.jdbc.url" value="${options.dbUri}"/>
	      <property name="javax.persistence.jdbc.password" value="${options.dbPassword}"/>
	      <property name="javax.persistence.jdbc.driver" value="${options.dbDriver}"/>
	      <property name="javax.persistence.jdbc.user" value="${options.dbUser}"/>
	      <property name="eclipselink.logging.level" value="SEVERE"/>
	      <property name="eclipselink.ddl-generation" value="create-tables"/>
	    </properties>
	</persistence-unit>
</persistence>