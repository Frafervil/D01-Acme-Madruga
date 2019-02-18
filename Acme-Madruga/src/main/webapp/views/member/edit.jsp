<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="member/edit.do" modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />

	<div class="ui equal width form">
		<div class="fields">
			<!-- Name -->
			<div class="field">
				<form:label path="name">
					<spring:message code="member.name" />
				</form:label>
				<form:input placeholder="${member.name}" path="name" />
				<form:errors class="error" path="name" />
			</div>
			<!-- MiddleName -->
			<div class="field">
				<form:label path="middleName">
					<spring:message code="member.middleName" />
				</form:label>
				<form:input placeholder="${member.middleName}" path="middleName" />
				<form:errors class="error" path="middleName" />
			</div>
			<!-- Surname -->
			<div class="field">
				<form:label path="surname">
					<spring:message code="member.surname" />
				</form:label>
				<form:input placeholder="${member.surname}" path="surname" />
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
					<spring:message code="member.email" />
				</form:label>
				<form:input placeholder="${member.email}" path="email" />
				<form:errors class="error" path="email" />
			</div>
			<!-- Phone Number -->
			<div class="field">
				<form:label path="phone">
					<spring:message code="member.phone" />
				</form:label>
				<form:input placeholder="${member.phone}" path="phone" />
				<form:errors class="error" path="phone" />
			</div>
		</div>
		<div class="fields">
			<!-- Address -->
			<div class="field">
				<form:label path="address">
					<spring:message code="member.address" />
				</form:label>
				<form:input placeholder="${member.address}" path="address" />
				<form:errors class="error" path="address" />
			</div>
			<!-- Photo -->
			<div class="field">
				<form:label path="photo">
					<spring:message code="member.photo" />
				</form:label>
				<form:input placeholder="${member.photo}" path="photo" />
				<form:errors class="error" path="photo" />
			</div>
		</div>
	</div>

		<input type="submit" class="ui primary button" name="save"
			value="<spring:message code="member.save" />">

	<input type="button" class="ui button" name="cancel"
		value="<spring:message code="member.cancel" />"
		onclick="javascript: relativeRedir('member/display.do');">

</form:form>