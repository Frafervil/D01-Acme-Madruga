<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
 <%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="float/brotherhood/create.do" modelAttribute="float">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:textbox code="float.title" path="title" placeholder="El Cristo"/>
		
		<acme:textarea code="float.description" path="description"/>
		
		<spring:message code = "float.pictures.placeholder" var="picturePlaceholder"/>
		<acme:textarea code="float.pictures" path="pictures"/>
		
		<%-- <acme:select code="float.procession" path="procession"
		items="${processions}" itemLabel="title" id="procession"/> --%>
		
		<div>
		<form:label path="procession">
		<spring:message code="float.procession" />
	</form:label>	
	<form:select id="procession" path="procession">
	<form:option value="0" label="----" />		
		<form:options items="${processions}" itemLabel="title" />
	</form:select>
	<form:errors path="procession" cssClass="error" />
		</div>
		
		<acme:submit name="save" code="float.save"/>
		
		<acme:cancel url="welcome/index.do" code="float.cancel"/>
		
</form:form>