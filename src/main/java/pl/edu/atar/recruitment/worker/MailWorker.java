package pl.edu.atar.recruitment.worker;

import pl.edu.atar.recruitment.service.MailService;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class MailWorker {

    private static Logger LOGGER = LoggerFactory.getLogger(MailWorker.class);

    @Value("${spring.mail.host:N/A}")
    private String host;
    @Value("${spring.mail.port:N/A}")
    private String port;
    @Value("${spring.mail.username:N/A}")
    private String username;
    @Value("${spring.mail.password:N/A}")
    private String password;
    @Value("${spring.mail.sendername:N/A}")
    private String sendername;
    @Value("${spring.mail.sendermail:N/A}")
    private String sendermail;

    @JobWorker(type = "sendMailRegistration")
    public Map<String, Object> sendMailRegistration(final JobClient client, final ActivatedJob job) {
        HashMap<String, Object> jobResultVariables = new HashMap<>();

        LOGGER.info("Job sendMailRegistration is started.");
        final Map<String, Object> jobVariables = job.getVariablesAsMap();
        for (Map.Entry<String, Object> entry : jobVariables.entrySet()) {
            LOGGER.info("Job variables (process & task input): {}", entry.getKey() + " : " + entry.getValue());
        }

        try {
            String from = sendermail;
            String fromName = sendername;
            String toAddress = (String) job.getVariablesAsMap().get("email");
            String toAddressName = job.getVariablesAsMap().get("firstName") + " " + (String) job.getVariablesAsMap().get("lastName");
            String ccAddresses = "";
            String bccAddresses = "";
            String subject = "Informacja o rejestracji aplikacji";
            String body = "Aplikacja na kierunek " + (String) job.getVariablesAsMap().get("radonFacultyName") +
                    ", studia " + (String) job.getVariablesAsMap().get("radonFacultyLevel") +
                    ", studia " + (String) job.getVariablesAsMap().get("radonFacultyForm") +
                    ", profil " + (String) job.getVariablesAsMap().get("radonFacultyProfile") +
                    " o czasie trwania " + (String) job.getVariablesAsMap().get("radonFacultyNumberOfSemesters") +
                    " semestrów" +
                    " z tytułem zawodowym " + (String) job.getVariablesAsMap().get("radonFacultyTitle") +
                    " została zarejestrowana. " +
                    "Identyfikator aplikacji: " + (Integer) job.getVariablesAsMap().get("applicationId");

            sendMail(from, fromName, toAddress, ccAddresses, bccAddresses, subject, body);
        } catch (Exception e) {
            client.newThrowErrorCommand(job.getKey())
                    .errorCode("EMAIL_NOTSENT_REGISTRATION")
                    .send()
                    .join();
            LOGGER.error("Error EMAIL_NOTSENT_REGISTRATION thrown.");
        }
        return jobResultVariables;
    }

    @JobWorker(type = "sendMailDecisionNotification")
    public Map<String, Object> sendMailEnrollment(final JobClient client, final ActivatedJob job) {
        HashMap<String, Object> jobResultVariables = new HashMap<>();

        LOGGER.info("Job sendMailDecisionNotification is started.");
        final Map<String, Object> jobVariables = job.getVariablesAsMap();
        for (Map.Entry<String, Object> entry : jobVariables.entrySet()) {
            LOGGER.info("Job variables (process & task input): {}", entry.getKey() + " : " + entry.getValue());
        }

        try {
            String from = sendermail;
            String fromName = sendername;
            String toAddress = (String) job.getVariablesAsMap().get("email");
            String toAddressName = job.getVariablesAsMap().get("firstName") + " " + (String) job.getVariablesAsMap().get("lastName");
            String ccAddresses = "";
            String bccAddresses = "";
            String subject = "Informacja o kwalifikacji do wpisu na I rok studiów";
            String body = "Uczelniana Komisja Rekrutacyjna podjęła decyzję o zakwalifikowaniu do wpisu na I rok studiów na kierunek "
                    + (String) job.getVariablesAsMap().get("radonFacultyName") +
                    ", studia " + (String) job.getVariablesAsMap().get("radonFacultyLevel") +
                    ", studia " + (String) job.getVariablesAsMap().get("radonFacultyForm") +
                    ", profil " + (String) job.getVariablesAsMap().get("radonFacultyProfile") +
                    " o czasie trwania " + (String) job.getVariablesAsMap().get("radonFacultyNumberOfSemesters") +
                    " semestrów" +
                    " z tytułem zawodowym " + (String) job.getVariablesAsMap().get("radonFacultyTitle") + ". " +
                    "Identyfikator decyzji: AT/D/" + (Integer) job.getVariablesAsMap().get("applicationId");

            sendMail(from, fromName, toAddress, ccAddresses, bccAddresses, subject, body);
        } catch (Exception e) {
            client.newThrowErrorCommand(job.getKey())
                    .errorCode("EMAIL_NOTSENT_DECISION_NOTIFICATION")
                    .send()
                    .join();
            LOGGER.error("Error EMAIL_NOTSENT_DECISION_NOTIFICATION thrown.");
        }
        return jobResultVariables;
    }

    @JobWorker(type = "sendPaymentProcessingNotofication")
    public Map<String, Object> sendPaymentProcessingNotofication(final JobClient client, final ActivatedJob job) {
        HashMap<String, Object> jobResultVariables = new HashMap<>();

        LOGGER.info("Job sendPaymentProcessingNotofication is started.");
        final Map<String, Object> jobVariables = job.getVariablesAsMap();
        for (Map.Entry<String, Object> entry : jobVariables.entrySet()) {
            LOGGER.info("Job variables (process & task input): {}", entry.getKey() + " : " + entry.getValue());
        }

        try {
            String from = sendermail;
            String fromName = sendername;
            String toAddress = (String) job.getVariablesAsMap().get("email");
            String toAddressName = job.getVariablesAsMap().get("firstName") + " " + (String) job.getVariablesAsMap().get("lastName");
            String ccAddresses = "";
            String bccAddresses = "";
            String subject = "Informacja o rozpoczęciu płatności";
            String body = "Płatność na kierunek " + (String) job.getVariablesAsMap().get("radonFacultyName") +
                    ", studia " + (String) job.getVariablesAsMap().get("radonFacultyLevel") +
                    ", studia " + (String) job.getVariablesAsMap().get("radonFacultyForm") +
                    ", profil " + (String) job.getVariablesAsMap().get("radonFacultyProfile") +
                    " o czasie trwania " + (String) job.getVariablesAsMap().get("radonFacultyNumberOfSemesters") +
                    " semestrów" +
                    " z tytułem zawodowym " + (String) job.getVariablesAsMap().get("radonFacultyTitle") +
                    " została rozpoczęta. " +
                    "Identyfikator płatności: P/" + (Integer) job.getVariablesAsMap().get("applicationId");

            sendMail(from, fromName, toAddress, ccAddresses, bccAddresses, subject, body);
        } catch (Exception e) {
            client.newThrowErrorCommand(job.getKey())
                    .errorCode("EMAIL_NOTSENT_PAYMENT_PROCESSING_NOTIFICATION")
                    .send()
                    .join();
            LOGGER.error("Error EMAIL_NOTSENT_PAYMENT_PROCESSING_NOTIFICATION thrown.");
        }
        return jobResultVariables;
    }

    private void sendMail(String from, String fromName, String toAddress, String ccAddresses, String bccAddresses, String subject, String body) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        MailService gs = new MailService(mailSender);
        gs.sendMail(from, fromName, subject, toAddress, ccAddresses, bccAddresses, body);
    }
}