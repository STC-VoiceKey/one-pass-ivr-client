<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
	<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="org.apache.log4j.Logger" %>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.VerificationApi"%>
	<%@ page import="java.util.UUID" %>
	<%response.setHeader("Cache-Control", "no-cache");%>

	<%
		Logger log = Logger.getLogger("getPerson");
		log.info("=====verificationResult.jsp=====");
		request.setCharacterEncoding("UTF-8");

		String sessionId = (request.getParameter("sessionId") != null && !request.getParameter("sessionId").isEmpty())
				? request.getParameter("sessionId") : "02";
		String personId = (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty())
				? request.getParameter("personId") : "02";
		String transactionId = (request.getParameter("transactionId") != null && !request.getParameter("transactionId").isEmpty())
				? request.getParameter("transactionId") : "02";

		log.info("      jsp: verificationSessionId = " + sessionId);
		log.info("      jsp: personId = " + personId);
		log.info("      jsp: transactionId = " + transactionId);

		VerificationApi verificationApi = new VerificationApi(personId, UUID.fromString(sessionId), UUID.fromString(transactionId));
		int score = Double.valueOf(verificationApi.getStaticVerificationScore()).intValue();
		boolean closed = verificationApi.closeVerificationSession();
		log.info("      jsp: score = " + score);

%>

<form id="getPerson">
	<block>
		<var name="score" expr="'<%=score%>'"/>
		<return namelist="score"/>
	</block>
	</form>
</vxml>