package br.com.bryan.webservice.handlers;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Set;

import javax.ejb.EJB;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import br.com.bryan.facade.UserFacade;
import br.com.bryan.model.User;

public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext> {
	
	@EJB
	private UserFacade userFacade;

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
	    if (!isRequest) {
	        try {
	            SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
	            SOAPHeader header = envelope.getHeader();
	            if (header == null) {
	                throw new SOAPFaultException(createFault("No SOAP header found.", context));
	            }
	
	            SOAPElement usernameElement = (SOAPElement) header.getElementsByTagName("username").item(0);
	            SOAPElement passwordElement = (SOAPElement) header.getElementsByTagName("password").item(0);
	            
	            if (usernameElement == null || passwordElement == null) {
	                throw new SOAPFaultException(createFault("Authentication credentials are missing.", context));
	            }
	
	            String username = usernameElement.getValue();
	            String password = passwordElement.getValue();
	
	            if (username == null || password == null) {
	                throw new SOAPFaultException(createFault("Username or password cannot be null.", context));
	            }
	
	            User user = userFacade.authenticate(username, password);

	            if (user == null) {
	                throw new SOAPFaultException(createFault("Authentication failed.", context));
	            }
	        } catch (SOAPException e) {
	            throw new RuntimeException("SOAP error: " + e.getMessage());
	        } catch (NoSuchAlgorithmException e) {
	        	throw new RuntimeException("Security error: " + e.getMessage());
			}
	    }
	    return true;
	}
	
	private SOAPFault createFault(String reason, SOAPMessageContext context) throws SOAPException {
		SOAPFault fault = context.getMessage().getSOAPPart().getEnvelope().getBody().addFault();
        fault.setFaultString(reason);
        return fault;
    }

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
		
	}

	@Override
	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}

}
