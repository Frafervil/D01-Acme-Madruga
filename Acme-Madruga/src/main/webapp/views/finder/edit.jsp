<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="finder/member/edit.do"
	modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="member" />
	<form:hidden path="results" />

	<acme:textbox code="finder.keyword" path="keyWord"/>
	<acme:textbox code="finder.area" path="area"/>
	
	<form:label path="dateMin">
		<spring:message code="finder.dateMin" />:
	</form:label>
	<form:input type="text" id="datepickerMin" path="dateMin" />
	<form:errors cssClass="error" path="dateMin" />
	<br />
	<form:label path="dateMax">
		<spring:message code="finder.dateMax" />:
	</form:label>
	<form:input type="text" id="datepickerMax" path="dateMax" />
	<form:errors cssClass="error" path="dateMax" />
	

	<spring:message code="finder.search" var="searchFinder" />
	<spring:message code="finder.clear" var="clearFinder" />

	<br />
	<input type="submit" id="search" name="search" value="${searchFinder}" />
	<input type="submit" id="clear" name="clear" value="${clearFinder}" />

</form:form>

<!-- Lista de resultados -->
<h3><spring:message code="finder.results" /></h3>

<display:table name="processions" id="row" pagesize="5" requestURI="${requestURI}" 
class="displaytag" keepStatus="true">

	<!-- Display -->
	<display:column>
		<a href="procession/display.do?processionId=${row.id}"><spring:message code="finder.procession.display"/></a>
	</display:column>
	
	<!-- Title -->
	<spring:message code="finder.procession.title" var="titleHeader" />
	<display:column  property="title" title="${titleHeader}" />
	
	<!-- Description -->
	<spring:message code="finder.procession.description" var="descriptionHeader" />
	<display:column  property="description" title="${descriptionHeader}" />
	
</display:table>
<script type="text/javascript">
$( function() {
  $( "#datepickerMin" ).datepicker();
} );
$( function() {
	  $( "#datepickerMax" ).datepicker();
} );
</script>
