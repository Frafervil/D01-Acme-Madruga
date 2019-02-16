<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3> <jstl:out value="${brotherhood.title}"> </jstl:out> </h3>
<br />
<strong> <spring:message code="brotherhood.establishmentDate" /> : </strong> 
<jstl:out value="${brotherhood.establishmentDate}"> </jstl:out>
<br />

<strong> <spring:message code="brotherhood.pictures" /> : </strong>

<ul>
<jstl:forEach items="${brotherhood.pictures}" var="pictures"><img src='<jstl:out value="${pictures}"></jstl:out>'>
<br />
</jstl:forEach>
</ul>
<!-- Members -->
<h3> <spring:message code="brotherhood.members" /> </h3>
<jstl:choose>
<jstl:when test="${not empty brotherhood.enrolments}">
<display:table pagesize="5" class="displaytag" name="members" requestURI="brotherhood/display.do" id="members">
		<spring:message code="brotherhood.members.name" var="name" />
		<display:column property="name" title="${name}" sortable="true"/>
	
		<spring:message code="brotherhood.members.surname" var="surname" />
		<display:column property="surname" title="${surname}" sortable="true"/>
			
</display:table>
</jstl:when>
<jstl:otherwise>
<spring:message code="brotherhood.members.empty" /> 
</jstl:otherwise>
</jstl:choose>

<!-- Processions -->
<h3> <spring:message code="brotherhood.processions" /> </h3>
<jstl:choose>
<jstl:when test="${not empty brotherhood.processions}">
<display:table pagesize="5" class="displaytag" name="processions" requestURI="brotherhood/display.do" id="processions">
		<spring:message code="brotherhood.processions.title" var="title" />
		<display:column property="title" title="${title}" sortable="true"/>
	
		<spring:message code="brotherhood.processions.moment" var="moment" />
		<display:column property="moment" title="${moment}" sortable="true"/>
			
</display:table>
</jstl:when>
<jstl:otherwise>
<spring:message code="brotherhood.processions.empty" /> 
</jstl:otherwise>
</jstl:choose>
<!-- Floats -->
<h3> <spring:message code="brotherhood.floats" /> </h3>
<jstl:choose>
<jstl:when test="${not empty brotherhood.floatBs}">
<display:table pagesize="5" class="displaytag" name="floats" requestURI="brotherhood/display.do" id="floats">
		<spring:message code="brotherhood.floats.title" var="title" />
		<display:column property="title" title="${title}" sortable="true"/>
	
		<spring:message code="brotherhood.floats.description" var="description" />
		<display:column property="description" title="${description}" sortable="true"/>
			
</display:table>
</jstl:when>
<jstl:otherwise>
<spring:message code="brotherhood.floats.empty" /> 
</jstl:otherwise>
</jstl:choose>