

<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
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

<security:authorize access="hasRole('MEMBER')">

<form:form action="request/member/edit.do?processionId=${request.procession.id }" modelAttribute="request">


	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	
	<spring:message code="request.procession.title" />: <jstl:out value="${request.procession.title} "></jstl:out>
	<br />
	<spring:message code = "request.procession.place.maxRow"/>: <jstl:out value="${request.procession.maxRow} " />
	<br/>
	<spring:message code = "request.procession.place.maxColumn"/>: <jstl:out value="${request.procession.maxColumn} " />
	<br/>
	
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

</security:authorize>
<jstl:choose>
<jstl:when test="${(approve==false)}">
<security:authorize access="hasRole('BROTHERHOOD')">
<form:form action="request/brotherhood/reject.do" modelAttribute="request">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<%-- <form:label path="rejectionReason">
	<spring:message code="request.procession.rejection" />:
	</form:label>
	<br />
	<form:textarea path="rejectionReason" />
	<form:errors cssClass="error" path="rejectionReason" /> --%>
	<acme:textarea code="request.procession.rejection" path="rejectionReason"/>
	<br />
	<br />
	
	<spring:message code="request.save" var="saveRequest"  />
	<spring:message code="request.cancel" var="cancelRequest"  />
	
	<input type="submit" name="save"
		value="${saveRequest}" />&nbsp; 


	<input type="button" name="cancel"
		value="${cancelRequest}"
		onclick="javascript: relativeRedir('request/brotherhood/list.do');" />
	<br />
</form:form>
</security:authorize>
</jstl:when>
<jstl:otherwise>
<security:authorize access="hasRole('BROTHERHOOD')">
<form:form action="request/brotherhood/approve.do" modelAttribute="request">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:label path="place">
		<spring:message code="request.procession.place" />:<br />
	</form:label>
	
	<spring:message code = "request.procession.place.maxRow"/>: <jstl:out value="${request.procession.maxRow} " />
	<br/>
	<spring:message code = "request.procession.place.maxColumn"/>: <jstl:out value="${request.procession.maxColumn} " />
	<br/>
	
	<spring:message code="request.procession.place.row" />: <form:input path="place.rowP" placeholder="${request.place.rowP }"/>
	<spring:message code="request.procession.place.column" />: <form:input path="place.columnP" placeholder="${request.place.columnP }"/>
	<form:errors cssClass="error" path="place" />
	<br />
	
	<spring:message code="request.save" var="saveRequest"  />
	<spring:message code="request.cancel" var="cancelRequest"  />
	
	<input type="submit" name="save"
		value="${saveRequest}" />&nbsp; 


	<input type="button" name="cancel"
		value="${cancelRequest}"
		onclick="javascript: relativeRedir('request/brotherhood/list.do');" />
	<br />
</form:form>
</security:authorize>
</jstl:otherwise>
</jstl:choose>