<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<table class="ui celled table">
	<thead>
		<tr>
			<img src="${actor.photo}" class="ui mini rounded image">
			<div class="content">
				<spring:message code="administrator.profile.title" />
				${actor.name}
			</div>

		</tr>
	</thead>
	<tbody>
		<tr>
			<td><spring:message code="administrator.name" />
			<td data-label="name">${actor.name}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.middleName" />
			<td data-label="MiddleName">${actor.middleName}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.surname" />
			<td data-label="surname">${actor.surname}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.email" />
			<td data-label="email">${actor.email}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.phone" />
			<td data-label="phone">${actor.phone}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.address" />
			<td data-label="address">${actor.address}</td>
		</tr>
	</tbody>
</table>

<input type="button" name="save" class="ui button"
	value="<spring:message code="administrator.edit" />"
	onclick="javascript: relativeRedir('administrator/edit.do');" />

</body>
</html>