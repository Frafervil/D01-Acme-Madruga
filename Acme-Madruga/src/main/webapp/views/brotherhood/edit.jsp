

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${permission }">

<form:form action="${actionURI}" modelAttribute="brotherhood" id="form" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="processions"/>
	<form:hidden path="enrolments"/>
	<form:hidden path="dropOuts"/>
	<form:hidden path="establishmentDate"/>
	
	<form:label path="name">
		<spring:message code="brotherhood.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />
	
	<form:label path="middleName">
		<spring:message code="brotherhood.middlename" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	<br />
	
	<form:label path="surname">
		<spring:message code="brotherhood.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	<br />
	
	
	<form:label path="email">
		<spring:message code="brotherhood.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<br />
	
	<form:label path="phone">
		<spring:message code="brotherhood.phone" />:
	</form:label>
	<spring:message code="brotherhood.phonePlaceholder" var="placeholder" />
	<form:input path="phone" id="phone" placeholder="${placeholder }" size="30"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	<br />
	
	<form:label path="address">
		<spring:message code="brotherhood.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<br />
	
	<h3><spring:message code="brotherhood.brotherhood" /></h3>
	<form:label path="title">
		<spring:message code="brotherhood.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />
	
	<form:label path="pictures">
		<spring:message code="brotherhood.pictures" />:
	</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" />
	<br />
	<br />
	
	<jstl:choose>
	<jstl:when test="${brotherhood.id == 0}">
	
	<form:hidden path="userAccount.authorities[0].authority" value="BROTHERHOOD" />
	
	
	<form:label path="userAccount.username">
		<spring:message code="brotherhood.username" />:
	</form:label>
	<spring:message code="brotherhood.username.placeholder" var="usernamePlaceholder"/> 
	<form:input path="userAccount.username" placeholder="${usernamePlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	<br />
	
	
	
	<form:label path="userAccount.password">
	<spring:message code="brotherhood.password" />:
	</form:label>
	<spring:message code="brotherhood.password.placeholder" var="passwordPlaceholder"/> 
	<form:password path="userAccount.password" placeholder="${passwordPlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	<br />
	

		
	
	</jstl:when>
	<jstl:otherwise>
	
	
	<form:hidden path="userAccount"/>
	

	</jstl:otherwise>
	</jstl:choose>
	

	<input type="button" name="save" id="save"
		value="<spring:message code="brotherhood.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="brotherhood.cancel" />"
		onclick="javascript: relativeRedir('${redirectURI}');" />
	<br />

</form:form>


<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='brotherhood.confirmationPhone'/>","${countryCode}");});


	 
</script>



</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="member.nopermission" /></h3>
</jstl:if>


