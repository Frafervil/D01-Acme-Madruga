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

	<b><spring:message code="procession.title" /></b>:
	<jstl:out value="${procession.title}"/><br/>
	
	<b><spring:message code="procession.description" /></b>:
	<jstl:out value="${procession.description }"/><br/>	
	
	<b><spring:message code="procession.moment" /></b>:
	<jstl:out value="${procession.moment }"/><br/>	
	
	<b><spring:message code="procession.ticker" /></b>:
	<jstl:out value="${procession.ticker }"/><br/>	
	
	<!-- FloatBs -->
	<b><spring:message code="procession.floatBs" /></b>:
	<a href="floatB/brotherhood/list.do">
	</a><br/>
	
	<!-- Links de editar, listar y borrar -->
	<jstl:if test="${procession.brotherhood.userAccount.username == pageContext.request.userPrincipal.name}">
	<security:authorize access="hasRole('BROTHERHOOD')">
			<a href="procession/brotherhood/edit.do?processionId=${procession.id}"><spring:message code="procession.edit"/></a><br/>
	</security:authorize>
	</jstl:if>
	
	<a href="procession/brotherhood/list.do"><spring:message code="procession.list"/></a>
	<br/>