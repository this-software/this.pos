/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class MailUtil
{
    
    private static final Logger logger = LogManager.getLogger(MailUtil.class.getSimpleName());
    
    private static Properties props;
    private static Session session;
    private static MimeMessage mimeMessage;
    
    private InternetAddress[] addresses;
    private String subject;
    private String content;

    public InternetAddress[] getAddresses() {
        return addresses;
    }

    public void setAddresses(InternetAddress[] addresses) {
        this.addresses = addresses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void send()
    {
        send(false);
    }
    
    /**
     * Método encargado de enviar un correo a una lista de direcciones predeterminadas
     * @param shutdown Boolean.TRUE para terminar con la ejecución del ThreadPool
     */
    public void send(Boolean shutdown)
    {
        
        logger.info(Thread.currentThread().getName() + " start.");

        props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("milton.cavazos@gmail.com", "milton/cavazos91");
            }
        };

        session = Session.getDefaultInstance(props, authenticator);

        try
        {
            mimeMessage = new MimeMessage(session);
            mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mimeMessage.addHeader("format", "flowed");
            mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");
            mimeMessage.addRecipients(Message.RecipientType.TO, addresses);
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(content, "text/html");
            mimeMessage.setSentDate(new Date());
            logger.info("Mail session has been created.");

            //Transport transport = session.getTransport("smtp");
            //transport.connect("smtp.gmail.com", "milton.cavazos@gmail.com", "milton/cavazos91");
            //transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            //transport.close();
            Transport.send(mimeMessage, mimeMessage.getAllRecipients());
            logger.info("Mail has been sent to: " + Arrays.toString(mimeMessage.getAllRecipients()));
        }
        catch (MessagingException ex)
        {
            logger.error("Email error", ex);
            //Guardar correo para intentar enviar más tarde
            //Escenario: Se canceló una salida de inventario y no había internet por lo que
            //el correo de notificación no fue enviado
        }
        logger.info(Thread.currentThread().getName() + " end.");
        
        if (shutdown)
        {
            ApplicationSession.getThreadPoolExecutor().shutdown();
        }
        
    }
    
}
