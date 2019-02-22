

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="request/member/edit.do" modelAttribute="request">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="status" />
	<form:hidden path="rejectionReason" />
	<form:hidden path="procession" />
	<form:hidden path="member" />
	
	
	<spring:message code="request.procession.title" />: <jstl:out value="${request.procession.title} "></jstl:out>
	<br />
	<br />	
	<form:label path="place">
		<spring:message code="request.procession.place" />:<br />
	</form:label>
	<spring:message code="request.procession.place.row" />: <form:input path="place.rowP" placeholder="${request.place.rowP }"/>
	<spring:message code="request.procession.place.column" />: <form:input path="place.columnP" placeholder="${request.place.columnP }"/>
	<form:errors cssClass="error" path="place" />
	<br />
	<br />
	<br />	
	<input type="submit" name="save"
		value="<spring:message code="request.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="request.cancel" />"
		onclick="javascript: relativeRedir('/request/member/list.do');" />
	<br />

	

</form:form>
