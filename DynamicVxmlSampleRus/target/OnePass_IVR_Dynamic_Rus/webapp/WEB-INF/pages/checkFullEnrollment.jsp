<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.speechpro.biometric.platform.onepass.api.PersonApi"%>
<%@ page import="com.speechpro.biometric.platform.onepass.api.OnePassApi"%>

<%response.setHeader("Cache-Control", "no-cache");%>

<%

	Logger log = Logger.getLogger("checkFullEnrollment.jsp");
	request.setCharacterEncoding("UTF-8");
	log.info("===checkFullEnrollment.jsp===");
	String personId = (request.getParameter("personId") != null && !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
    log.info("      jsp: personId: " + personId);
	if (!personId.equals("00")){
		log.info("      jsp: personId does not equal 00");
	    String host = (request.getParameter("host") != null
	    && !request.getParameter("host").isEmpty()) ? request.getParameter("host") : "00";
	    String port = (request.getParameter("port") != null
	    && !request.getParameter("port").isEmpty()) ? request.getParameter("port") : "00";
		String protocol = (request.getParameter("protocol") != null
		&& !request.getParameter("protocol").isEmpty()) ? request.getParameter("protocol") : "none";
       	String root = (request.getParameter("root") != null
        && !request.getParameter("root").isEmpty()) ? request.getParameter("root") : "none";

        log.info("      jsp: host: " + host);
        log.info("      jsp: port: " + port);
		log.info("      jsp: mode: " + mode);
		log.info("      jsp: root: " + root);

		OnePassApi onePassApi 	= new OnePassApi(protocol, host, port, root);
		PersonApi personApi		= onePassApi.person(personId);
		boolean isFullyEnrolled = personApi.isFullEnrolled();
		if(!isFullyEnrolled){
			personApi.delete();
		}
	}

%>

    <form id="getPerson">
	    <block>
	    </block>
	</form>
</vxml>