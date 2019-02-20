<%--
 * 
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<spring:message code="request.delete" var="requestDelete"/>

	<b><spring:message code="request.procession.title" /></b>:
	<jstl:out value="${request.procession.title}"/><br/>
	<br>	
	<b><spring:message code="request.procession.member" /></b>:
	<jstl:out value="${member.name}"/><br/>
	<br>
	<b><spring:message code="request.procession.status" /></b>:
	<jstl:out value="${request.status}"/><br/>
	
	<jstl:if test="${request.status == 'REJECTED'}">
		<b><spring:message code="request.procession.rejection" /></b>:
		<jstl:out value="${request.rejectionReason}"/><br/>
	</jstl:if>
	
	<jstl:if test="${request.status == 'APROVED'}">
		<b><spring:message code="request.procession.place" /></b>:
		<br/>
		<jstl:out value="Row: ${request.place.rowP}"/><br/>
		<jstl:out value="Column: ${request.place.columnP}"/><br/>
		
	</jstl:if>
	
	<jstl:if test="${request.status == 'PENDING'}">
		<input type="submit" name="delete" value="${requestDelete}" onclick="return confirm('${requestDelete}')" />&nbsp;					
		
	</jstl:if>
	
	
	