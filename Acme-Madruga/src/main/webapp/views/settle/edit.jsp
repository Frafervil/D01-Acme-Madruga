<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="settle/administrator/edit.do"
	modelAttribute="settle">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<br />
	<br />
	<acme:textbox code="settle.area" path="area"/>
	<acme:textarea code="settle.pictures" path="pictures"/>

	<spring:message code="settle.save" var="saveSettle" />
	<spring:message code="settle.delete" var="deleteSettle" />
	<spring:message code="settle.cancel" var="cancelSettle" />
<br />
	<input type="submit" id="save" name="save" value="${saveSettle}" />
	<input type="submit" id="delete" name="delete" value="${deleteSettle}" />

	<input type="button" name="cancel" value="${cancelSettle}"
		onclick="javascript: relativeRedir('settle/administrator/list.do');" />
	<br />


</form:form>
