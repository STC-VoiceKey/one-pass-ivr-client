<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
    <%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="org.apache.log4j.Logger" %>
    <%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi" %>
    <%@ page import="java.util.UUID" %>

    <%response.setHeader("Cache-Control", "no-cache");%>

    <%
        Logger log = Logger.getLogger("checkFullEnrollment.jsp");
        request.setCharacterEncoding("UTF-8");
        log.info("===checkFullEnrollment.jsp===");
        String personId = (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty())
                ? request.getParameter("personId") : "00";
        String sessionId = (request.getParameter("sessionId") != null
                && !request.getParameter("sessionId").isEmpty()) ? request.getParameter("sessionId") : "00";
        log.info("      jsp: personId: " + personId);
        log.info("      jsp: sessionId: " + sessionId);
        if (!personId.equals("00")) {
            log.info("      jsp: personId does not equal 00");

            PersonApi personApi = new PersonApi(personId, UUID.fromString(sessionId));
            boolean personExists = personApi.personExists();
            boolean isFullyEnrolled = personApi.getDynamicModelsNumber() == 2;
            if (!isFullyEnrolled && personExists) {
                personApi.deletePerson();
            }
        }
    %>

    <form id="getPerson">
        <block>
        </block>
    </form>
</vxml>