/*
 * Creation by madmath03 the 2017-11-10.
 */

package com.monogramm.starter.utils.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import jakarta.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * {@link EmailValidator} Unit Test.
 * 
 * @author madmath03
 */
public class EmailValidatorTest {

  private ValidEmail constraintAnnotation;

  private ConstraintValidatorContext context;

  private EmailValidator validator;

  /**
   * @throws java.lang.Exception if the test setup crashes.
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.context = mock(ConstraintValidatorContext.class);

    this.validator = new EmailValidator();
  }

  /**
   * @throws java.lang.Exception if the test cleanup crashes.
   */
  @AfterEach
  public void tearDown() throws Exception {
    Mockito.reset(context);

    this.validator = null;
  }

  /**
   * Test method for
   * {@link EmailValidator#initialize(com.monogramm.starter.utils.validation.ValidEmail)}.
   */
  @Test
  public void testInitialize() {
    this.validator.initialize(this.constraintAnnotation);
  }

  /**
   * Test method for
   * {@link EmailValidator#isValid(java.lang.String, jakarta.validation.ConstraintValidatorContext)}.
   */
  @Test
  public void testIsValid() {
    assertFalse(this.validator.isValid(null, this.context));

    assertFalse(this.validator.isValid("", this.context));

    assertFalse(this.validator.isValid("foo", this.context));

    assertFalse(this.validator.isValid("foo@bar", this.context));

    assertFalse(this.validator.isValid("foo42@bar", this.context));

    assertFalse(this.validator.isValid("foo&@bar", this.context));

    assertTrue(this.validator.isValid("foo@bar.com", this.context));

    assertTrue(this.validator.isValid("foo42@bar.com", this.context));

    assertFalse(this.validator.isValid("foo&@bar.com", this.context));
  }

}
