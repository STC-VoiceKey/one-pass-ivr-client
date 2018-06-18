<?xml version="1.0" encoding="UTF-8"?>
<vxml version="2.0" xmlns="http://www.w3.org/2001/vxml" xml:lang="ru-RU" application="include/approot.xml">
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.speechpro.biometric.platform.onepass.api.VerificationApi" %>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.net.URLEncoder"%>
	<%@ page import="java.util.UUID" %>

	<%response.setHeader("Cache-Control", "no-cache");%>

<%

	Logger log = Logger.getLogger("getPerson");
	request.setCharacterEncoding("UTF-8");
	log.info("=====startVerification.jsp=====");
	String personId = (request.getParameter("personId") != null
	&& !request.getParameter("personId").isEmpty()) ? request.getParameter("personId") : "00";
	String sessionId = (request.getParameter("sessionId") != null
			&& !request.getParameter("sessionId").isEmpty()) ? request.getParameter("sessionId") : "none";

    log.info("      jsp: personId = " + personId);
	log.info("      jsp: sessionId = " + sessionId);

	VerificationApi verificationApi = new VerificationApi(personId, UUID.fromString(sessionId));
	String password  = verificationApi.getVerificationPassword();
	UUID transactionId = verificationApi.getTransactionId();

	log.info("      jsp: password = " + password);
	String encodedPassword = URLEncoder.encode(password, "UTF-8");

    log.info("      jsp: password = " + password);

%>

    <form id="getPerson">
	    <block>
		    <var name="application.password" expr="'<%=password%>'"/>
		    <var name="application.sessionId" expr="'<%=sessionId%>'"/>
			<var name="application.transactionId" expr="'<%=transactionId.toString()%>'"/>
			<var name="application.personId" expr="'<%=personId%>'"/>
			<var name="application.encodedPassword" expr="'<%=encodedPassword%>'"/>
			<prompt bargein="false">
				После гудка произнесите цифры
				<value expr="'<%=password%>'"/>
			</prompt>
		</block>
		<record name="verification" maxtime="20s" beep="true" dtmfterm="false" finalsilence="1s">
			<filled>
				<submit expr="'verification.jsp?sessionId=' + application.sessionId +
                '&amp;password=' + encodeURI(application.password) +
                '&amp;encodedPassword=' + encodeURI(encodedPassword) +
                '&amp;personId=' + application.personId +
                '&amp;transactionId=' + transactionId"
						enctype="multipart/form-data" method="post"
						namelist="verification"/>
			</filled>
		</record>
	</form>
</vxml>