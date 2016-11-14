<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
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
    byte[] audioBytes = SoundSender.readAudioFromRequest(request);
	log.info("=====verification.jsp=====");

	String sessionId 	= (request.getParameter("sessionId") != null && !request.getParameter("sessionId").isEmpty())
			? request.getParameter("sessionId") : "02";
	String password 	= (request.getParameter("password") != null && !request.getParameter("password").isEmpty())
			? request.getParameter("password") : "02";
	String personId 	= (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty())
			? request.getParameter("personId") : "02";
	String host			= (request.getParameter("host") != null && !request.getParameter("host").isEmpty())
			? request.getParameter("host") : "02";
	String port 		= (request.getParameter("port") != null && !request.getParameter("port").isEmpty())
			? request.getParameter("port") : "02";
	String mode 		= (request.getParameter("mode") != null && !request.getParameter("mode").isEmpty())
			? request.getParameter("mode") : "none";

	log.info("      jsp: verificationSessionId = " + sessionId);
	log.info("      jsp: password = " + password);
	log.info("      jsp: personId = " + personId);
	log.info("      jsp: host = " + host);
	log.info("      jsp: port = " + port);
	log.info("      jsp: mode = " + mode);

	byte[] encoded = Base64.encodeBase64(audioBytes);
    String encodedString = new String(encoded);

    OnePassApi onePassApi 	= new OnePassApi(host, port, mode);
    PersonApi personApi 	= onePassApi.person(personId);
    VerificationApi verificationApi = personApi.verification(password, sessionId);

    int serverStatus = verificationApi.sendVerificationVoice(encodedString);
    log.info("      jsp: serverStatusVerification = " + serverStatus);

%>

<form id="getPerson">
	<block>
		<if cond="<%=serverStatus%> == 204">
		    <goto next = "verificationResult.xml"/>
		<else/>
			<if cond="verificationCounter &lt; application.attempts">
			    <assign name="verificationCounter" expr="verificationCounter + 1"/>
					<prompt bargein="false">
						Вы не прошли верификацию. Попробуйте еще раз!
					</prompt>
				<submit next="startVerification.jsp" namelist="personId host port"/>
            <else/>
				<prompt bargein="false">
						Вы не прошли верификацию. До свидания!
				</prompt>
				<disconnect/>
			</if>
		</if>
	</block>
	</form>
</vxml>