<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="administrator/edit.do" modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />

	<div class="ui equal width form">
		<div class="fields">
			<!-- Name -->
			<acme:textbox code="administrator.name" path="name"/>
			<!-- MiddleName -->
			<acme:textbox code="administrator.middleName" path="middleName"/>
			<!-- Surname -->
			<acme:textbox code="administrator.surname" path="surname"/>
		</div>
		<div class="fields">
			<!-- Email -->
			<acme:textbox code="administrator.email" path="email"/>
			<!-- Phone Number -->
			<acme:textbox code="administrator.phone" path="phone"/>
		</div>
		<div class="fields">
			<!-- Address -->
			<acme:textbox code="administrator.address" path="address"/>
			<!-- Photo -->
			<acme:textbox code="administrator.photo" path="photo"/>
		</div>
	</div>

	<acme:submit name="save" code="administrator.save"/>
	
	<acme:cancel url="administrator/viewProfile.do" code="administrator.cancel"/>


</form:form>