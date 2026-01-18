package pl.wsb.fitnesstracker.mail.api;

/**
 * Data Transfer Object for email details.
 *
 * @param toAddress recipient's email address
 * @param from      sender's email address
 * @param subject   email subject
 * @param content   email body content
 */
public record EmailDto(String toAddress, String from, String subject, String content) {

}
