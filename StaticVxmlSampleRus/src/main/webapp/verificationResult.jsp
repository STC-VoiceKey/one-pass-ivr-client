<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
	<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="org.apache.log4j.Logger" %>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.OnePassApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.VerificationApi"%>
	<%response.setHeader("Cache-Control", "no-cache");%>

	<%
		Logger log = Logger.getLogger("getPerson");
		log.info("=====verificationResult.jsp=====");
		request.setCharacterEncoding("UTF-8");

		String sessionId = (request.getParameter("sessionId") != null && !request.getParameter("sessionId").isEmpty()) ? request.getParameter("sessionId") : "02";
		String personId = (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "02";
		String host = (request.getParameter("host") != null && !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "02";
		String port = (request.getParameter("port") != null && !request.getParameter("port").isEmpty()) ? request.getParameter("port") : "02";
		String mode = (request.getParameter("mode") != null
				&& !request.getParameter("mode").isEmpty()) ? request.getParameter("mode") : "none";

		log.info("      jsp: verificationSessionId = " + sessionId);
		log.info("      jsp: personId = " + personId);
		log.info("      jsp: host = " + host);
		log.info("      jsp: port = " + port);
		log.info("      jsp: mode = " + mode);

		String verificationSessionId = (request.getParameter("sessionId")
				!= null && !request.getParameter("sessionId").isEmpty()) ? request.getParameter("sessionId") : "02";
		log.info("      jsp: verificationSessionId = " + verificationSessionId);

		OnePassApi onePassApi = new OnePassApi(host, port, mode);
		PersonApi personApi = onePassApi.person(personId);
		VerificationApi verificationApi = personApi.verification(sessionId);

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