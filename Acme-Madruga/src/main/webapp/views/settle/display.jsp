<%--
 * 
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<b><spring:message code="settle.area" /></b>:
	<jstl:out value="${settle.area}"/><br/>
	
	<jstl:if test="${settle.pictures != null && (not empty settle.pictures)}">
	<b><spring:message code="settle.pictures" /></b>:
	<br/>
	<div class="row">
	
		<jstl:forEach items="${settle.pictures}" var="picture" >
			<jstl:if test="${picture != null}">
 <div class="column">
    <img src="${picture }" style="width:100%">
  </div>	        </jstl:if>
		</jstl:forEach>
		<br/>
		</div>
	</jstl:if>
<jstl:choose>
<jstl:when test="${empty brotherhood }">
	<b><spring:message code="settle.brotherhood" /></b>: <spring:message code="settle.empty.brotherhood" />
</jstl:when>
<jstl:otherwise>
	<b><spring:message code="settle.brotherhood" /></b>: <jstl:out value="${brotherhood.title}"/><br/>
</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('ADMIN')">
<br />
<a href="settle/administrator/edit.do?settleId=${settle.id}"> <spring:message
						code="settle.edit" /></a>
</security:authorize>

