<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.speechpro.biometric.platform.onepass.api.RegistrationApi" %>
<%@ page import="com.speechpro.biometric.platform.onepass.util.SoundSender"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.net.URLDecoder"%>
	<%@ page import="java.net.URLEncoder" %>
	<%@ page import="java.util.UUID" %>
	<%@ page import="com.speechpro.biometric.platform.exception.platform.PlatformException" %>
	<%response.setHeader("Cache-Control", "no-cache");%>

	<%
		Logger log = Logger.getLogger("===enrollment_1.jsp===");
		log.info("===enrollment_1.jsp===");
		byte[] audioBytes = SoundSender.readAudioFromRequest(request);
		log.info("      jsp: audio size is: " + audioBytes.length);
		String personId = (request.getParameter("personId") != null
				&& !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
		String password = (request.getParameter("password") != null
				&& !request.getParameter("password").isEmpty()) ? request.getParameter("password") : "00";
		String sessionId = (request.getParameter("sessionId") != null && !request.getParameter("sessionId").isEmpty())
				? request.getParameter("sessionId") : "none";
		String transactionId = (request.getParameter("transactionId") != null && !request.getParameter("transactionId").isEmpty())
				? request.getParameter("transactionId") : "none";

		log.info("      jsp: personId = " + personId);
		log.info("      jsp: password: " + URLDecoder.decode(password, "UTF-8"));
		log.info("      jsp: sessionId = " + sessionId);
		log.info("      jsp: transactionId = " + transactionId);

		RegistrationApi api = new RegistrationApi(personId, UUID.fromString(sessionId), UUID.fromString(transactionId));
		byte[] encoded = Base64.encodeBase64(audioBytes);
		String encodedString = new String(encoded);
		boolean sendingResult = false;
		String passwordEncoded = URLEncoder.encode("девять восемь семь шесть пять четрые три два один ноль", "UTF-8");
		try {
			sendingResult = api.sendDynamicRegistrationVoice("девять восемь семь шесть пять четыре три два один ноль", encodedString);
		}catch (PlatformException ex){
		    log.error(ex.getMessage());
		}
		log.info("      jsp: Trying to create second model for the person = " + personId);

%>

<form id="getPerson">
	<block>
		<if cond="<%=sendingResult%>==true">
        	<goto next="endOfEnrollment.xml"/>
        <else/>
        	<goto next="enrollment_2.xml"/>
        </if>
	</block>
	</form>
</vxml>