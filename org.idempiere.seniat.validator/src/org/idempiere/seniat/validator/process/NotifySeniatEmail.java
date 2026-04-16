package org.idempiere.seniat.validator.process;

import java.util.logging.Level;

import org.adempiere.base.annotation.Process;
import org.compiere.model.MMailText;
import org.compiere.model.MSysConfig;
import org.compiere.model.MClient;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Msg;

import org.idempiere.seniat.validator.base.CustomProcess;

@Process
public class NotifySeniatEmail extends CustomProcess {

    
    private int p_R_MailText_ID = 0;
    private MMailText m_MailText = null;

    EMail email = null;
    
    int EmailCount = 0;
	boolean isSend;
    
    @Override
    protected void prepare() {
        for (var parameter : getParameter()) {
            String name = parameter.getParameterName();
            if (parameter.getParameter() == null) continue;

            switch (name) {
                case "R_MailText_ID":
                	p_R_MailText_ID = parameter.getParameterAsInt();
                    break;
            }
        }
    }
    
    @Override
    protected String doIt() throws Exception {
    	
    	m_MailText = new MMailText(getCtx(), p_R_MailText_ID, get_TrxName());
        
        int count = DB.getSQLValue(get_TrxName(), "SELECT COUNT(*) FROM ftu_rv_dni ");
        
        if (count > 0) {
            
        	String emailSeniat = MSysConfig.getValue("EMAIL_SENIAT", null, getAD_Client_ID());
        	
            if (PrepareEMail(emailSeniat, count)) {
                
                String errorMessage = email.send();
                
                if (errorMessage.equalsIgnoreCase("OK")) {
                    return "Email Enviado Exitosamente!";
                } else {
                    log.severe("Failed to send email: " + errorMessage);
                    return "No se Pudo Enviar el Email: " + errorMessage;
                }
            } else {
                return "Error al Prepara el Email";
            }
        } else {
            return "No se Encontraron Entregas Sin Factura Para este Periodo Fiscal";
        }
    }
    
    private boolean PrepareEMail(String p_Email, int count) {
        try {
            MClient m_client = MClient.get(getCtx());
            MUser m_user = new MUser(getCtx(), getAD_User_ID(), get_TrxName());
            
            String message = m_MailText.getMailText(true);
            message = Msg.parseTranslation(getCtx(), message);
            String subject = "Existen " + count + " Entregas Sin Factura";
            
            String fromAddress = m_client.getRequestEMail(); 
            
            if (fromAddress == null || fromAddress.isEmpty()) {
                throw new IllegalArgumentException("Client 'From' email address is not configured.");
            }

            EMail email = m_client.createEMail(null, p_Email, subject, message);
            
            if (m_MailText.isHtml()) {
				email.setMessageHTML(subject, message);
			} else {
				email.setSubject (subject);
				email.setMessageText (message);
			}
            
            if (email == null) {
                log.severe("Failed to create email object. Check MClient or MUser configuration.");
                return false;
            }

            this.email = email;
            
            return true;
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error preparing email for: " + p_Email, e);
            addLog(0, null, null, "Error preparing the Email: " + e.getMessage());
            return false;
        }
    }

}
