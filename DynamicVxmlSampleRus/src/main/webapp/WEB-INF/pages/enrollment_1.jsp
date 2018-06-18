<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
    <%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.api.RegistrationApi" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.util.SoundSender" %>
    <%@ page import="org.apache.commons.codec.binary.Base64" %>
    <%@ page import="org.apache.log4j.Logger" %>
    <%@ page import="java.net.URLDecoder" %>
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

        log.info("      jsp: personId = " + personId);
        log.info("      jsp: password: " + URLDecoder.decode(password, "UTF-8"));
        log.info("      jsp: sessionId = " + sessionId);

        PersonApi personApi = new PersonApi(personId, UUID.fromString(sessionId));
        try {
            personApi.deletePerson();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        RegistrationApi registrationApi = new RegistrationApi(personId, UUID.fromString(sessionId));
        registrationApi.createPerson();
        UUID transactionId = registrationApi.getTransactionId();

        byte[] encoded = Base64.encodeBase64(audioBytes);
        String encodedString = new String(encoded);
        boolean sendingResult = false;
        String passwordEncoded = URLEncoder.encode("ноль один два три четыре пять шесть семь восемь девять", "UTF-8");
        try {
            sendingResult = registrationApi.sendDynamicRegistrationVoice("ноль один два три четыре пять шесть семь восемь девять", encodedString);
        } catch (PlatformException ex) {
            log.error(ex.getMessage());
        }

        log.info("      jsp: Trying to create first model for the person = " + personId);

    %>

    <form id="getPerson">
        <block>
            <assign name="transactionId" expr="'<%=transactionId.toString()%>'"/>
            <if cond="<%=sendingResult%> == true">
                <goto next="enrollment_2.xml"/>
                <else/>
                <goto next="enrollment_1.xml"/>
            </if>
        </block>
    </form>
</vxml>