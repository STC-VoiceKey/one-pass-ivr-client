<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
    <%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="org.apache.log4j.Logger" %>
    <%@ page import="org.apache.commons.codec.binary.Base64" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.api.VerificationApi" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.util.SoundSender" %>
    <%@ page import="java.net.URLDecoder" %>
    <%@ page import="com.speechpro.biometric.platform.exception.platform.PlatformException" %>

    <%response.setHeader("Cache-Control", "no-cache");%>

    <%
        request.setCharacterEncoding("UTF-8");
        Logger log = Logger.getLogger("===verification.jsp===");
        byte[] audioBytes = SoundSender.readAudioFromRequest(request);
        log.info("=====verification.jsp=====");

        String sessionId = (request.getParameter("sessionId") != null && !request.getParameter("sessionId").isEmpty())
                ? request.getParameter("sessionId") : "02";
        String password = (request.getParameter("password") != null && !request.getParameter("password").isEmpty())
                ? request.getParameter("password") : "02";
        String encodedPassword = (request.getParameter("encodedPassword") != null && !request.getParameter("encodedPassword").isEmpty())
                ? request.getParameter("encodedPassword") : "02";
        String personId = (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty())
                ? request.getParameter("personId") : "02";
        String transactionId = (request.getParameter("transactionId") != null && !request.getParameter("transactionId").isEmpty())
                ? request.getParameter("transactionId") : "none";

        log.info("      jsp: verificationSessionId = " + sessionId);
        log.info("      jsp: password = " + password);
        log.info("      jsp: encoded password = " + URLDecoder.decode(encodedPassword, "UTF-8"));
        log.info("      jsp: personId = " + personId);
        log.info("      jsp: transactionId = " + transactionId);

        byte[] encoded = Base64.encodeBase64(audioBytes);
        String encodedString = new String(encoded);

        VerificationApi verificationApi = new VerificationApi(personId, sessionId, transactionId);
        boolean serverStatus = false;
        try {
            serverStatus = verificationApi.sendVerificationVoice(URLDecoder.decode(encodedPassword, "UTF-8"), encodedString);
        } catch (PlatformException ex) {
            log.error(ex.getMessage());
        }
        log.info("      jsp: serverStatusVerification = " + serverStatus);

    %>

    <form id="getPerson">
        <block>
            <if cond="<%=serverStatus%> == true">
                <goto next="verificationResult.xml"/>
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