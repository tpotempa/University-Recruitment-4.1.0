package pl.edu.atar.recruitment.worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.edu.atar.recruitment.model.Mail;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailSecondaryWorker {

    private static Logger LOGGER = LoggerFactory.getLogger(MailSecondaryWorker.class);

    @Value("${mail.atar.edu.pl.secondary-host:N/A}")
    private String host;

    @Value("${mail.atar.edu.pl.secondary-port:N/A}")
    private String port;

    @Value("${mail.atar.edu.pl.secondary-username:N/A}")
    private String username;

    @Value("${mail.atar.edu.pl.secondary-password:N/A}")
    private String password;

    @Value("${mail.atar.edu.pl.secondary-sendername:N/A}")
    private String sendername;

    @Value("${mail.atar.edu.pl.secondary-sendermail:N/A}")
    private String secondary_sendermail;

    @JobWorker(type = "sendEmail")
    public Map<String, Object> sendEmail(final JobClient client, final ActivatedJob job) {
        HashMap<String, Object> jobResultVariables = new HashMap<>();

        LOGGER.info("Job sendEmail is started.");
        final Map<String, Object> jobVariables = job.getVariablesAsMap();
        for (Map.Entry<String, Object> entry : jobVariables.entrySet()) {
            LOGGER.info("Job variables (process & task input): {}", entry.getKey() + " : " + entry.getValue());
        }

        Mail mt = new Mail(0, (String) job.getVariablesAsMap().get("firstName") + " " + (String) job.getVariablesAsMap().get("lastName"), (String) job.getVariablesAsMap().get("email"), null, (Integer) job.getVariablesAsMap().get("applicationId"));
        try {
            mt.sendMail(host, username, password, sendername, secondary_sendermail);
            LOGGER.info("Sending mail succeeded.");
            jobResultVariables.put("mailSendingResult", true);
        } catch (Exception e) {
            LOGGER.error("Sending mail failed.");
            jobResultVariables.put("mailSendingResult", false);
        }

        return jobResultVariables;
    }
}