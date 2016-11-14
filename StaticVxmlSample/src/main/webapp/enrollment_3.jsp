<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="en-US" application="include/approot.xml">
	<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="org.apache.log4j.Logger" %>
	<%@ page import="org.apache.commons.codec.binary.Base64" %>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.OnePassApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.util.SoundSender"%>
	<%response.setHeader("Cache-Control", "no-cache");%>

	<%
		Logger log = Logger.getLogger("===enrollment_3.jsp===");
		log.info("===enrollment_3.jsp===");
		byte[] audioBytes = SoundSender.readAudioFromRequest(request);
		log.info("      jsp: audio size is: " + audioBytes.length);
		String personId = (request.getParameter("personId") != null
				&& !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
		String host = (request.getParameter("host") != null
				&& !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "000.000.000.000";
		String port = (request.getParameter("port") != null
				&& !request.getParameter("port").isEmpty()) ? request.getParameter("port") : "0000";
		String mode = (request.getParameter("mode") != null
				&& !request.getParameter("mode").isEmpty()) ? request.getParameter("mode") : "none";

		log.info("      jsp: personId = " + personId);
		log.info("      jsp: host = " + host);
		log.info("      jsp: port = " + port);
		log.info("      jsp: mode = " + mode);

		OnePassApi onePassApi = new OnePassApi(host, port, mode);
		PersonApi personApi = onePassApi.person(personId);
		byte[] encoded = Base64.encodeBase64(audioBytes);
		String encodedString = new String(encoded);
		int sendingResult = personApi.sendRegistrationVoice(encodedString);

		log.info("      jsp: Trying to create first model for the person = " + personId);

%>

<form id="getPerson">
	<block>
		<if cond="<%=sendingResult%> == 204">
		    <goto next="endOfEnrollment.xml"/>
		<else/>
		    <goto next="enrollment_3.xml"/>
		</if>
	</block>
	</form>
</vxml>