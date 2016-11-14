<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="en-US" application="include/approot.xml">
	<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="org.apache.log4j.Logger" %>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.VerificationApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.OnePassApi"%>
	<%response.setHeader("Cache-Control", "no-cache");%>

	<%

		Logger log = Logger.getLogger("startVerification.jsp");
		request.setCharacterEncoding("UTF-8");
		log.info("=====startVerification.jsp=====");

		String personId = (request.getParameter("personId") != null
				&& !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
		String host = (request.getParameter("host") != null
				&& !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "00";
		String port = (request.getParameter("port") != null
				&& !request.getParameter("port").isEmpty()) ? request.getParameter("port") : "00";
		String mode = (request.getParameter("mode") != null
				&& !request.getParameter("mode").isEmpty()) ? request.getParameter("mode") : "none";

		log.info("      jsp: host = " + host);
		log.info("      jsp: port = " + port);
		log.info("      jsp: personId = " + personId);
		log.info("      jsp: mode = " + mode);

		OnePassApi onePassApi = new OnePassApi(host, port, mode);
		VerificationApi verificationApi = onePassApi.person(personId).startStaticVerification();
		String sessionId = verificationApi.getSessionId();

		log.info("      jsp: sessionId = " + sessionId);
%>

	<form id="getPerson">
		<block>
			<assign name="sessionId" expr="'<%=sessionId%>'"/>
			<goto next="verification.xml"/>
		</block>
	</form>
</vxml>