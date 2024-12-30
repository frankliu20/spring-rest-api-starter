/*
 * Creation by madmath03 the 2017-12-03.
 */

package com.monogramm.starter.config.i8n;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

/**
 * {@link ApplicationMessage} Unit Test.
 * 
 * @author madmath03
 */
public class ApplicationMessageTest {

  private ApplicationMessage message;

  /**
   * @throws java.lang.Exception If test initialization crashes.
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.message = new ApplicationMessage();
  }

  /**
   * @throws java.lang.Exception If test clean up crashes.
   */
  @AfterEach
  public void tearDown() throws Exception {
    this.message = null;
  }

  /**
   * Test method for {@link ApplicationMessage#messageSource()}.
   */
  @Test
  public void testMessageSource() {
    final MessageSource source = this.message.messageSource();

    assertNotNull(source);
  }

}
