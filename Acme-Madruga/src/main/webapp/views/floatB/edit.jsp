<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<form:form action="floatB/brotherhood/edit.do" modelAttribute="floatB">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:textbox code="floatB.title" path="title"/>
		
		<acme:textarea code="floatB.description" path="description"/>
		
		<spring:message code = "floatB.pictures.placeholder" var="picturePlaceholder"/>
		<acme:textarea code="floatB.pictures" path="pictures" placeholder="${picturePlaceholder }"/>
		
		<acme:select code="floatB.procession" path="procession"
		items="${processions}" itemLabel="name" id="procession"/>
		
		<acme:submit name="save" code="floatB.save"/>
		
		<jstl:if test="${floatB.id != 0 }">
			<acme:submit name="delete" code="floatB.delete"/>
		</jstl:if>
		
		<acme:cancel url="floatB/list.do" code="floatB.cancel"/>
		
</form:form>