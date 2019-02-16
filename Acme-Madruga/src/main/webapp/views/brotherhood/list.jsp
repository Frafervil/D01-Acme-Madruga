<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- Listing grid -->


<display:table name="brotherhoods" id="row" requestURI="brotherhood/list.do"
	pagesize="5" class="displaytag">

	<!-- Attributes -->

	<spring:message code="brotherhood.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
		<spring:message code="brotherhood.establishmentDate" var="momentHeader" />
	<display:column property="establishmentDate" title="${establishmentDateHeader}"
		sortable="true" />
	<!-- Action links -->

	<display:column>
	<a href="brotherhood/display.do?brotherhoodId=${row.id }"> <spring:message code="brotherhood.display" /></a>
	</display:column>

</display:table>