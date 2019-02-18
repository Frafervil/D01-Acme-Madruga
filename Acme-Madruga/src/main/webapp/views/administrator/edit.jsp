<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="administrator/edit.do" modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />

	<div class="ui equal width form">
		<div class="fields">
			<!-- Name -->
			<div class="field">
				<form:label path="name">
					<spring:message code="administrator.name" />
				</form:label>
				<form:input placeholder="${actor.name}" path="name" />
				<form:errors class="error" path="name" />
			</div>
			<!-- MiddleName -->
			<div class="field">
				<form:label path="middleName">
					<spring:message code="administrator.middleName" />
				</form:label>
				<form:input placeholder="${actor.middleName}" path="middleName" />
				<form:errors class="error" path="middleName" />
			</div>
			<!-- Surname -->
			<div class="field">
				<form:label path="surname">
					<spring:message code="administrator.surname" />
				</form:label>
				<form:input placeholder="${actor.surname}" path="surname" />
				<form:errors class="error" path="surname" />
			</div>
		</div>
		<div class="fields">
			<!-- Password -->
			<div class="field">
				<form:label path="userAccount.password">
					<spring:message code="userAccount.password" />
				</form:label>
				<form:input  path="userAccount.password" />
				<form:errors class="error" path="userAccount.password" />
			</div>
		</div>
		<div class="fields">
			<!-- Email -->
			<div class="field">
				<form:label path="email">
					<spring:message code="administrator.email" />
				</form:label>
				<form:input placeholder="${actor.email}" path="email" />
				<form:errors class="error" path="email" />
			</div>
			<!-- Phone Number -->
			<div class="field">
				<form:label path="phone">
					<spring:message code="administrator.phone" />
				</form:label>
				<form:input placeholder="${actor.phone}" path="phone" />
				<form:errors class="error" path="phone" />
			</div>
		</div>
		<div class="fields">
			<!-- Address -->
			<div class="field">
				<form:label path="address">
					<spring:message code="administrator.address" />
				</form:label>
				<form:input placeholder="${actor.address}" path="address" />
				<form:errors class="error" path="address" />
			</div>
			<!-- Photo -->
			<div class="field">
				<form:label path="photo">
					<spring:message code="administrator.photo" />
				</form:label>
				<form:input placeholder="${actor.photo}" path="photo" />
				<form:errors class="error" path="photo" />
			</div>
		</div>
	</div>

		<input type="submit" class="ui primary button" name="save"
			value="<spring:message code="administrator.save" />">

	<input type="button" class="ui button" name="cancel"
		value="<spring:message code="administrator.cancel" />"
		onclick="javascript: relativeRedir('administrator/viewProfile.do');">

</form:form>