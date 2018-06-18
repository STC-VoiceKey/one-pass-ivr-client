<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
    <%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="org.apache.log4j.Logger" %>
    <%@ page import="org.apache.commons.codec.binary.Base64" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.util.SoundSender" %>
    <%@ page import="java.util.UUID" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.api.RegistrationApi" %>
    <%response.setHeader("Cache-Control", "no-cache");%>

    <%
        Logger log = Logger.getLogger("===enrollment_1.jsp===");
        log.info("===enrollment_1.jsp===");
        byte[] audioBytes = SoundSender.readAudioFromRequest(request);
        log.info("      jsp: audio size is: " + audioBytes.length);
        String sessionId = (request.getParameter("sessionId") != null
                && !request.getParameter("sessionId").isEmpty()) ? request.getParameter("sessionId") : "00";
        String transactionId = (request.getParameter("transactionId") != null
                && !request.getParameter("transactionId").isEmpty()) ? request.getParameter("transactionId") : "00";
        String personId = (request.getParameter("personId") != null
                && !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";

        log.info("      jsp: personId = " + personId);
        log.info("      jsp: sessionId = " + sessionId);

        byte[] encoded = Base64.encodeBase64(audioBytes);
        String encodedString = new String(encoded);

        RegistrationApi registrationApi = new RegistrationApi(personId, UUID.fromString(sessionId), UUID.fromString(transactionId));
        boolean sendingResult = registrationApi.sendStaticRegistrationVoice(encodedString);

        log.info("      jsp: Trying to create first model for the person = " + personId);
    %>

    <form id="getPerson">
        <block>
            <if cond="<%=sendingResult%> == true">
                <goto next="enrollment_2.xml"/>
                <else/>
                <goto next="enrollment_1.xml"/>
            </if>
        </block>
    </form>
</vxml>