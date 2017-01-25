<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
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
		String root = (request.getParameter("root") != null
   				&& !request.getParameter("root").isEmpty()) ? request.getParameter("root") : "none";
   		String protocol = (request.getParameter("protocol") != null
        && !request.getParameter("protocol").isEmpty()) ? request.getParameter("protocol") : "none";
        String host = (request.getParameter("host") != null
        && !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "none";
        String port = (request.getParameter("port") != null
        && !request.getParameter("port").isEmpty()) ? request.getParameter("port") : "";

		log.info("      jsp: host = " + host);
		log.info("      jsp: port = " + port);
	    log.info("      jsp: root = " + root);
        log.info("      jsp: personId = " + personId);
        log.info("      jsp: protocol = " + protocol);

		OnePassApi onePassApi = new OnePassApi(protocol, host, port, root);
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