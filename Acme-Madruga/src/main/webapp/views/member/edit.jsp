

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

<form:form action="${actionURI}" modelAttribute="member" id="form" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="requests"/>
	<form:hidden path="enrolments"/>
	<form:hidden path="dropOuts"/>
	
	<form:label path="name">
		<spring:message code="member.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />
	
	<form:label path="middleName">
		<spring:message code="member.middleName" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	<br />
	
	<form:label path="surname">
		<spring:message code="member.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	<br />
	
	
	<form:label path="email">
		<spring:message code="member.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<br />
	
	<form:label path="phone">
		<spring:message code="member.phone" />:
	</form:label>
	<spring:message code="member.phonePlaceholder" var="placeholder" />
	<form:input path="phone" id="phone" placeholder="${placeholder }" size="30"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	<br />
	
	<form:label path="address">
		<spring:message code="member.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<br />
	
	<jstl:choose>
	<jstl:when test="${member.id == 0}">
	
	<form:hidden path="userAccount.authorities[0].authority" value="MEMBER" />
	
	
	<form:label path="userAccount.username">
		<spring:message code="member.username" />:
	</form:label>
	<spring:message code="member.username.placeholder" var="usernamePlaceholder"/> 
	<form:input path="userAccount.username" placeholder="${usernamePlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	<br />
	
	
	
	<form:label path="userAccount.password">
	<spring:message code="member.password" />:
	</form:label>
	<spring:message code="member.password.placeholder" var="passwordPlaceholder"/> 
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
		value="<spring:message code="member.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="member.cancel" />"
		onclick="javascript: relativeRedir('${redirectURI}');" />
	<br />

</form:form>


<script type="text/javascript">
$("#save").on("click",function(){validatePhone("<spring:message code='member.confirmationPhone'/>","${countryCode}");});


	 
</script>



</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="member.nopermission" /></h3>
</jstl:if>


