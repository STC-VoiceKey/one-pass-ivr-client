<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ page import="org.apache.log4j.Logger" %>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
	<%@ page import="com.speechpro.biometric.platform.onepass.api.OnePassApi"%>
	<%response.setHeader("Cache-Control", "no-cache");%>

<%

	Logger log = Logger.getLogger("getPerson");
	request.setCharacterEncoding("UTF-8");
	log.info("=====getPerson.jsp=====");
	String personId = (request.getParameter("personId") != null
			&& !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
	personId = personId.replaceAll(" ", "");
	personId = personId.replaceAll("'", "");
	personId = personId.replaceAll(";", "");

	String host = (request.getParameter("host") != null
			&& !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "00";
	String port = (request.getParameter("port") != null
			&& !request.getParameter("port").isEmpty()) ? request.getParameter("port") : "";
    String root = (request.getParameter("root") != null
    && !request.getParameter("root").isEmpty()) ? request.getParameter("root") : "none";
    String protocol = (request.getParameter("protocol") != null
    && !request.getParameter("protocol").isEmpty()) ? request.getParameter("protocol") : "none";

	log.info("      jsp: personId: " + personId);
	log.info("      jsp: host: " + host);
	log.info("      jsp: port: " + port);
	log.info("      jsp: root: " + root);
    log.info("      jsp: protocol: " + protocol);

	OnePassApi onePassApi = new OnePassApi(protocol, host, port, root);
	PersonApi personApi = onePassApi.person(personId);
	boolean createdPerson = false;
	boolean isFullEnrollment = false;
	boolean personExists = personApi.exists();
	if(personExists){
		isFullEnrollment = personApi.isFullEnrolled();
	}
	else{
		createdPerson = personApi.createPerson();
	}

	log.info("      jsp: personExists: " + personExists);
	log.info("      jsp: createdPerson: " + createdPerson);
	log.info("      jsp: isFullEnrollment: " + isFullEnrollment);

%>

    <form id="getPerson">
	    <block>
			<assign name="application.host" 	expr="'<%=host%>'"/>
	        <assign name="application.port" 	expr="<%=port%>"/>
			<assign name="application.personId" expr="<%=personId%>"/>
	        <if cond="<%=personExists%> == true">
	            <submit next="startVerification.jsp" namelist="personId host port root protocol"/>
	        <else/>
	            <goto next="enrollment_1.xml"/>
	        </if>
	    </block>
	</form>
</vxml>