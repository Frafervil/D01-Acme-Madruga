<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="position/administrator/edit.do"
	modelAttribute="position">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<br />
	<br />
	<form:label path="languagePositions">
		<spring:message code="position.position" />:
	</form:label>
	<spring:message code="position.name" />: <form:input path="languagePositions.name" />
	<spring:message code="position.language" />: <form:input path="languagePositions.language" />
	<form:errors cssClass="error" path="languagePositions" />
	<br />
	<br />


	<br />
	<br />





	<spring:message code="position.save" var="savePosition" />
	<spring:message code="position.delete" var="deletePosition" />
	<spring:message code="position.cancel" var="cancelPosition" />

	<input type="submit" id="save" name="save" value="${savePosition}" />

	<input type="button" name="cancel" value="${cancelPosition}"
		onclick="javascript: relativeRedir('position/administrator/list.do');" />
	<br />


</form:form>
