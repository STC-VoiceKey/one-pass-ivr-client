<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="en-US" application="include/approot.xml">
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page import="com.speechpro.biometric.platform.onepass.api.VerificationApi"%>
<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
<%@ page import="com.speechpro.biometric.platform.onepass.api.OnePassApi"%>
<%@ page import="com.speechpro.biometric.platform.onepass.util.SoundSender"%>

<%response.setHeader("Cache-Control", "no-cache");%>

<%
    Logger log = Logger.getLogger("===verification.jsp===");

	log.info("=====verification.jsp=====");
	byte[] audioBytes = SoundSender.readAudioFromRequest(request);
	String sessionId 	= (request.getParameter("sessionId") != null && !request.getParameter("sessionId").isEmpty())
	? request.getParameter("sessionId") : "02";
	String password 	= (request.getParameter("password") != null && !request.getParameter("password").isEmpty())
	? request.getParameter("password") : "02";
	String personId 	= (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty())
	? request.getParameter("personId") : "02";
	String root = (request.getParameter("root") != null
    && !request.getParameter("root").isEmpty()) ? request.getParameter("root") : "none";
    String host = (request.getParameter("host") != null
    && !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "none";
    String port = (request.getParameter("port") != null
    && !request.getParameter("port").isEmpty()) ? request.getParameter("port") : "none";
    String protocol = (request.getParameter("protocol") != null
    && !request.getParameter("protocol").isEmpty()) ? request.getParameter("protocol") : "none";


	log.info("      jsp: verificationSessionId = " + sessionId);
	log.info("      jsp: password = " + password);
	log.info("      jsp: personId = " + personId);
	log.info("		jsp: root = " + root);
	log.info("		jsp: protocol = " + protocol);
	log.info("		jsp: port = " + port);
	log.info("		jsp: host = " + host);

	byte[] encoded = Base64.encodeBase64(audioBytes);
    String encodedString = new String(encoded);

    OnePassApi onePassApi 	= new OnePassApi(protocol, host, port, root);
    PersonApi personApi 	= onePassApi.person(personId);
    VerificationApi verificationApi = personApi.verification(password, sessionId);

    boolean serverStatus = verificationApi.sendVerificationVoice(encodedString);
    log.info("      jsp: serverStatusVerification = " + serverStatus);
%>

<form id="getPerson">
	<block>
		<if cond="<%=serverStatus%> == true">
		    <goto next = "verificationResult.xml"/>
		<else/>
			<if cond="verificationCounter &lt; application.attempts">
			    <assign name="verificationCounter" expr="verificationCounter + 1"/>
				<prompt bargein="false">
					You are not verified. Try one more time.
				</prompt>
				<submit next="startVerification.jsp" namelist="personId protocol host port root"/>
            <else/>
				<prompt bargein="false">
					You are not verified. Good bye!
				</prompt>
				<disconnect/>
			</if>
		</if>
	</block>
	</form>
</vxml>