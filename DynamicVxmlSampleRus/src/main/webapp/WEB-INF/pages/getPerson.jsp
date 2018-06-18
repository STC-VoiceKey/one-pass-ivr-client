<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.speechpro.biometric.platform.onepass.api.OnePassApi" %>
<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
<%@ page import="com.speechpro.biometric.platform.onepass.api.SessionApi"%>
	<%@ page import="org.apache.log4j.Logger" %>
	<%@ page import="java.util.UUID" %>

	<%response.setHeader("Cache-Control", "no-cache");%>

<%

	Logger log = Logger.getLogger("getPerson");
	request.setCharacterEncoding("UTF-8");
	log.info("=====getPerson.jsp=====");
	String personId = (request.getParameter("personId") != null
	&& !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
	String host = (request.getParameter("host") != null
	&& !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "00";
	String port = (request.getParameter("port"));
	String protocol = (request.getParameter("protocol") != null
    && !request.getParameter("protocol").isEmpty()) ? request.getParameter("protocol") : "00";
	String root = (request.getParameter("root") != null
	&& !request.getParameter("root").isEmpty()) ? request.getParameter("root") : "none";

	log.info("      jsp: protocol: " + protocol);
	log.info("      jsp: root: " + root);
	log.info("      jsp: host: " + host);
	log.info("      jsp: port: " + port);

	OnePassApi onePassApi = new OnePassApi(protocol, host, port, root);
	SessionApi sessionApi = new SessionApi("admin", "QL0AFWMIX8NRZTKeof9cXsvbvu8=", 201);
	String sessionId = sessionApi.startSession().toString();

	PersonApi personApi = onePassApi.person(personId, UUID.fromString(sessionId));
	boolean isFullEnrollment = false;
	boolean personExists = personApi.personExists();
	if (personExists) {
		isFullEnrollment = personApi.getDynamicModelsNumber() == 2;
	}
	log.info("      jsp: personExists: " + personExists);
	log.info("      jsp: isFullEnrollment: " + isFullEnrollment);

%>

    <form id="getPerson">
	    <block>
			<assign name="application.host" expr="'<%=host%>'"/>
			<assign name="application.protocol" expr="'<%=protocol%>'"/>
	        <assign name="application.port" expr="<%=port%>"/>
	        <assign name="application.root" expr="'<%=root%>'"/>
			<assign name="application.personId" expr="'<%=personId%>'"/>
			<assign name="application.sessionId" expr="'<%=sessionId%>'"/>
	        <if cond="<%=isFullEnrollment%> == true">
	            <submit next="startVerification.jsp" namelist="sessionId personId host port protocol root"/>
	        <else/>
	            <goto next="enrollment_1.xml"/>
	        </if>
	    </block>
	</form>
</vxml>