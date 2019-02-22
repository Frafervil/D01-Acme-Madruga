<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
 <%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
<jstl:when test="${procession.isDraft == false}">
<h3><spring:message code="procession.nopermission" /></h3>
</jstl:when>
<jstl:otherwise>

<form:form action="procession/brotherhood/edit.do" modelAttribute="procession">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="brotherhood"/>
		
		<acme:textbox code="procession.title" path="title"/>
		
		<acme:textarea code="procession.description" path="description"/>
		
		<acme:textbox code="procession.moment" path="moment" placeholder="dd/MM/yyyy HH:mm" />
		
		<form:label path="isDraft">
			<spring:message code="procession.isDraft" />:
		</form:label>
		<form:radiobutton path="isDraft" value="true" checked="checked" /><spring:message code="procession.isDraft.true" />
		<form:radiobutton path="isDraft" value="false"/><spring:message code="procession.isDraft.false" />
		<br>
		<br>
		
		<acme:textbox code="procession.maxRow" path="maxRow"/>
		
		<acme:textbox code="procession.maxColumn" path="maxColumn"/>	
	
	<!-- Floatbs -->
	
		<acme:select code="procession.floatbs" path="floatbs"
		items="${floatbs}" itemLabel="title" id="floatbs"/>
		
		<acme:submit name="save" code="procession.save"/>
		
		<jstl:if test="${procession.id != 0 }">
			<acme:submit name="delete" code="procession.delete"/>
		</jstl:if>
		
		<acme:cancel url="procession/brotherhood/list.do" code="procession.cancel"/>
		
</form:form>

</jstl:otherwise>
</jstl:choose>