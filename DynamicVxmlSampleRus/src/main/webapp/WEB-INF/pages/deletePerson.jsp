<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>

<%response.setHeader("Cache-Control", "no-cache");%>

<%
	Logger log = Logger.getLogger("deletePerson");
	request.setCharacterEncoding("UTF-8");
	log.info("=====deletePerson.jsp=====");

	String personId = (request.getParameter("personId") != null
    && !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
	String sessionId = (request.getParameter("sessionId") != null
			&& !request.getParameter("sessionId").isEmpty()) ? request.getParameter("sessionId") : "00";

	log.info("      jsp: Trying to delete a person with id = " + personId);

    PersonApi personApi = new PersonApi(personId, sessionId);
    personApi.delete();
%>

<form id="getPerson">
	<block>
		<prompt>
			Ваш отпечаток удален! До свидания!
		</prompt>
		<disconnect/>
	</block>
	</form>
</vxml>