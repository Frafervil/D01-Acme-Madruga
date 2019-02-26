<%--
 * register.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
		
<jstl:if test="${administratorForm != null }">

	<form:form action="${formURI}" modelAttribute="administratorForm">
		
		<form:hidden path="idAdministrator" />
		<form:hidden path="authority"/>
		
		<fieldset>
    	<legend><spring:message code="administrator.fieldset.personalInformation"/></legend>
		<acme:textbox code="administrator.name" path="name" placeholder="Homer"/>
		<acme:textbox code="administrator.middleName" path="middleName" placeholder="Jay"/>
		<acme:textbox code="administrator.surname" path="surname" placeholder="Simpson"/>
		<acme:textbox code="administrator.photo" path="photo" placeholder="https://www.jazzguitar.be/images/bio/homer-simpson.jpg"/>
		<acme:textbox code="administrator.email" path="email" placeholder="homerjsimpson@email.com"/>
		<acme:textbox code="administrator.phone" path="phone" placeholder="+34 600 1234"/>
		<acme:textbox code="administrator.address" path="address" placeholder="123 Main St Anytown, Australia"/>
		</fieldset>
		<br/>
		
		<fieldset>
    	<legend><spring:message code="administrator.fieldset.userAccount"/></legend>
		<acme:textbox code="administrator.username" path="username" placeholder="HomerS"/>
		
		<acme:password code="administrator.password" path="password"/>
		<acme:password code="administrator.passwordCheker" path="passwordChecker"/>
		
		</fieldset>
		<br/>
		<security:authorize access="isAnonymous()">
			<acme:checkbox code="administrator.terms" path="legalConsentment"/>
		</security:authorize>
		
		<acme:submit name="save" code="administrator.save"/>

		<acme:cancel url="administrator/viewProfile.do" code="administrator.cancel"/>
	</form:form>
</jstl:if>