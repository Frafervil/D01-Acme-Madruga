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

<jstl:choose>
<jstl:when test="${procession.isDraft == false}">
<h3><spring:message code="procession.nopermission" /></h3>
</jstl:when>
<jstl:otherwise>

<form:form action="procession/brotherhood/edit.do" modelAttribute="procession">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="brotherhood"/>
		
		<acme:textbox code="procession.title" path="title"/>
		
		<acme:textarea code="procession.description" path="description"/>
		
		<acme:textbox code="procession.moment" path="moment" placeholder="dd/MM/yyyy HH:mm" />
		
	<!-- CONTINUAR POR AQUÍ -->	
	
		<acme:select code="floatB.procession" path="procession"
		items="${processions}" itemLabel="title" id="procession"/>
		
		<acme:submit name="save" code="floatB.save"/>
		
		<jstl:if test="${floatB.id != 0 }">
			<acme:submit name="delete" code="floatB.delete"/>
		</jstl:if>
		
		<acme:cancel url="floatB/brotherhood/list.do" code="floatB.cancel"/>
		
</form:form>

</jstl:otherwise>
</jstl:choose>