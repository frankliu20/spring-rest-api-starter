/*
 * Creation by madmath03 the 2017-12-20.
 */

package com.monogramm.starter.api.user.listener;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.monogramm.starter.api.user.event.OnRegistrationCompleteEvent;
import com.monogramm.starter.config.properties.EmailProperties;
import com.monogramm.starter.persistence.user.entity.User;
import com.monogramm.starter.persistence.user.exception.VerificationTokenNotFoundException;
import com.monogramm.starter.persistence.user.service.VerificationTokenService;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * {@link RegistrationListener} Unit Test.
 * 
 * @author madmath03
 */
public class RegistrationListenerTest {

  private VerificationTokenService verificationService;

  private MessageSource messages;

  private JavaMailSender mailSender;

  private EmailProperties emailProperties;

  private RegistrationListener listener;

  /**
   * @throws java.lang.Exception if the test setup crashes.
   */
  @BeforeEach
  public void setUp() throws Exception {
    verificationService = mock(VerificationTokenService.class);
    assertNotNull(verificationService);

    messages = mock(MessageSource.class);
    assertNotNull(messages);

    mailSender = mock(JavaMailSender.class);
    assertNotNull(mailSender);

    emailProperties = new EmailProperties();
    assertNotNull(emailProperties);

    this.listener =
        new RegistrationListener(verificationService, messages, mailSender, emailProperties);
  }

  /**
   * @throws java.lang.Exception if the test cleanup crashes.
   */
  @AfterEach
  public void tearDown() throws Exception {
    Mockito.reset(verificationService);
    Mockito.reset(messages);
    Mockito.reset(mailSender);
    this.emailProperties = null;

    this.listener = null;
  }

  /**
   * Test method for
   * {@link RegistrationListener#RegistrationListener(VerificationTokenService, MessageSource, JavaMailSender, EmailProperties)}.
   */
  @Test
  public void testRegistrationListener() {
    assertNotNull(
        new RegistrationListener(verificationService, messages, mailSender, emailProperties));
  }

  /**
   * Test method for
   * {@link RegistrationListener#RegistrationListener(VerificationTokenService, MessageSource, JavaMailSender, EmailProperties)}.
   */
  @Test
  public void testRegistrationListenerNullTokenService() {
    assertThrows(IllegalArgumentException.class, () -> {
      new RegistrationListener(null, messages, mailSender, emailProperties);
    });
  }

  /**
   * Test method for
   * {@link RegistrationListener#RegistrationListener(VerificationTokenService, MessageSource, JavaMailSender, EmailProperties)}.
   */
  @Test
  public void testRegistrationListenerNullMessageSource() {
    assertThrows(IllegalArgumentException.class, () -> {
      new RegistrationListener(verificationService, null, mailSender, emailProperties);
    });
  }

  /**
   * Test method for
   * {@link RegistrationListener#RegistrationListener(VerificationTokenService, MessageSource, JavaMailSender, EmailProperties)}.
   */
  @Test
  public void testRegistrationListenerNullJavaMailSender() {
    assertThrows(IllegalArgumentException.class, () -> {
      new RegistrationListener(verificationService, messages, null, emailProperties);
    });
  }

  /**
   * Test method for
   * {@link RegistrationListener#RegistrationListener(VerificationTokenService, MessageSource, JavaMailSender, EmailProperties)}.
   */
  @Test
  public void testRegistrationListenerNullEnvironment() {
    assertThrows(IllegalArgumentException.class, () -> {
      new RegistrationListener(verificationService, messages, mailSender, null);
    });
  }

  /**
   * Test method for {@link RegistrationListener#onApplicationEvent(OnRegistrationCompleteEvent)}.
   */
  @Test
  public void testOnApplicationEvent() {
    final User user = User.builder().build();
    final Locale locale = Locale.getDefault();
    final String appUrl = "http://dummy/";
    final OnRegistrationCompleteEvent event =
        new OnRegistrationCompleteEvent(user, Locale.getDefault(), appUrl);

    // Mock
    when(verificationService.add(any())).thenReturn(true);

    final String subject = "dummy_subject";
    when(messages.getMessage("api.user.listener.confirm_email_subject", null, locale))
        .thenReturn(subject);
    final String message = "dummy_message";
    when(messages.getMessage(any(String.class), any(String[].class), any(Locale.class)))
        .thenReturn(message);

    this.emailProperties.setAppTitle("My App Unit Test");
    this.emailProperties.setNoReply("no_reply@dummy.com");

    // Operation
    this.listener.onApplicationEvent(event);

    // Check
    verify(verificationService, times(1)).add(any());
    verifyNoMoreInteractions(verificationService);

    verify(messages, times(1)).getMessage("api.user.listener.confirm_email_subject", null, locale);
    verify(messages, times(2)).getMessage(any(String.class), any(String[].class),
        any(Locale.class));
    verifyNoMoreInteractions(verificationService);

    verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    verifyNoMoreInteractions(mailSender);
  }

  /**
   * Test method for {@link RegistrationListener#onApplicationEvent(OnRegistrationCompleteEvent)}.
   */
  @Test
  public void testOnApplicationEventTokenNotAdded() {
    assertThrows(VerificationTokenNotFoundException.class, () -> {
      final User user = User.builder().build();
      final String appUrl = "http://dummy/";
      final OnRegistrationCompleteEvent event =
          new OnRegistrationCompleteEvent(user, Locale.getDefault(), appUrl);

      // Mock
      when(verificationService.add(any())).thenReturn(false);

      // Operation
      this.listener.onApplicationEvent(event);
    });
  }

}
