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

	<b><spring:message code="floatB.title" /></b>:
	<jstl:out value="${floatB.title}"/><br/>
	
	<b><spring:message code="floatB.description" /></b>:
	<jstl:out value="${floatB.description }"/><br/>	
	
	<jstl:if test="${floatB.pictures != null && (not empty floatB.pictures)}">
	<b><spring:message code="floatB.pictures" /></b>:
	<br/>
		<jstl:forEach items="${floatB.pictures}" var="picture" >
			<jstl:if test="${picture != null}">
	        	<acme:image src="${picture}" cssClass="external-image-landscape"/>
	        </jstl:if>
		</jstl:forEach>
		<br/>
	</jstl:if>
	
	<!-- Procession -->
	<b><spring:message code="floatB.procession" /></b>:
	<a href="procession/display.do?processionId=${floatB.procession.id}">
		<jstl:out value="${floatB.procession.title}"/>
	</a><br/>
	
	<!-- Links de editar y borrar -->
	<jstl:if test="${floatB.brotherhood.userAccount.username == pageContext.request.userPrincipal.name}">
	<security:authorize access="hasRole('BROTHERHOOD')">
			<a href="floatB/brotherhood/edit.do?floatBId=${floatB.id}"><spring:message code="floatB.edit"/></a><br/>
			<a href="floatB/brotherhood/delete.do?floatBId=${floatB.id }"><spring:message code="floatB.delete"/></a><br/>
	</security:authorize>
	</jstl:if>
	
	<a href="floatB/list.do"><spring:message code="floatB.list"/></a>
	<br/>