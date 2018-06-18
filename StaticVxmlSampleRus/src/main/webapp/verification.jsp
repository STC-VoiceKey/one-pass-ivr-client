<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
	<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="org.apache.log4j.Logger" %>
	<%@ page import="org.apache.commons.codec.binary.Base64" %>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.VerificationApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.util.SoundSender"%>
	<%@ page import="java.util.UUID" %>
	<%response.setHeader("Cache-Control", "no-cache");%>

	<%
		Logger log = Logger.getLogger("===verification.jsp===");
		byte[] audioBytes = SoundSender.readAudioFromRequest(request);
		log.info("=====verification.jsp=====");
		String sessionId = (request.getParameter("sessionId") != null && !request.getParameter("sessionId").isEmpty())
		? request.getParameter("sessionId") : "02";
		String personId = (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty())
		? request.getParameter("personId") : "02";

		log.info("      jsp: sessionId = " + sessionId);
		log.info("      jsp: personId = " + personId);

		byte[] encoded = Base64.encodeBase64(audioBytes);
		String encodedString = new String(encoded);

		VerificationApi verificationApi = new VerificationApi(personId, UUID.fromString(sessionId));
		UUID transactionId = verificationApi.getTransactionId();

		boolean serverStatus = verificationApi.sendStaticVerificationVoice(encodedString);
		log.info("      jsp: serverStatusVerification = " + serverStatus);

%>

	<form id="getPerson">
		<block>
			<assign name="transactionId" expr="'<%=transactionId%>'"/>
			<if cond="<%=serverStatus%> == true">
				<goto next = "verificationResult.xml"/>
			<else/>
				<if cond="verificationCounter &lt; application.attempts">
					<assign name="verificationCounter" expr="verificationCounter + 1"/>
					<goto next = "verification.xml"/>
				<else/>
					<prompt>
						Вы не прошли верификацию. До свидания!
					</prompt>
					<disconnect/>
				</if>
			</if>
		</block>
	</form>
</vxml>