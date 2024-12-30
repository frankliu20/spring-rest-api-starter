/*
 * Creation by madmath03 the 2017-12-03.
 */

package com.monogramm.starter.config.component;

import static org.junit.jupiter.api.Assertions.*;

import com.monogramm.starter.config.component.CustomServletRequestWrapper;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.savedrequest.Enumerator;

/**
 * {@link CustomServletRequestWrapper} Unit Test.
 * 
 * @author madmath03
 */
public class CustomServletRequestWrapperTest {

  private static final String DUMMY_PARAM = "DUMMY";
  private static final String[] DUMMY_VALUE = new String[] {"42"};

  private static final String EMPTY_PARAM = "EMPTY";
  private static final String[] EMPTY_VALUE = new String[] {};

  private static final String NULL_PARAM = "NULL";

  private HttpServletRequest request;

  private Map<String, String[]> params;

  /**
   * @throws java.lang.Exception If test initialization crashes.
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.request = new MockHttpServletRequest();
    this.params = new HashMap<>();

    this.params.put(DUMMY_PARAM, DUMMY_VALUE);
    this.params.put(EMPTY_PARAM, EMPTY_VALUE);
    this.params.put(NULL_PARAM, null);
  }

  /**
   * @throws java.lang.Exception If test clean up crashes.
   */
  @AfterEach
  public void tearDown() throws Exception {
    this.request = null;
    this.params = null;
  }

  /**
   * Test method for
   * {@link CustomServletRequestWrapper#CustomServletRequestWrapper(jakarta.servlet.http.HttpServletRequest, java.util.Map)}.
   */
  @Test
  public void testCustomServletRequestWrapper() {
    final CustomServletRequestWrapper wrapper = new CustomServletRequestWrapper(request, params);

    assertNotNull(wrapper);

    CustomServletRequestWrapper otherWrapper = new CustomServletRequestWrapper(request, null);

    assertNotNull(otherWrapper);
    assertNotEquals(wrapper, otherWrapper);
  }

  /**
   * Test method for {@link CustomServletRequestWrapper#getParameter(java.lang.String)}.
   */
  @Test
  public void testGetParameterString() {
    final CustomServletRequestWrapper wrapper = new CustomServletRequestWrapper(request, params);

    assertEquals(DUMMY_VALUE[0], wrapper.getParameter(DUMMY_PARAM));

    assertEquals("", wrapper.getParameter(EMPTY_PARAM));

    assertEquals("", wrapper.getParameter(NULL_PARAM));

    assertEquals("", wrapper.getParameter("no_exists"));
  }

  /**
   * Test method for {@link CustomServletRequestWrapper#getParameterMap()}.
   */
  @Test
  public void testGetParameterMap() {
    final CustomServletRequestWrapper wrapper = new CustomServletRequestWrapper(request, params);

    assertEquals(params, wrapper.getParameterMap());

    CustomServletRequestWrapper otherWrapper = new CustomServletRequestWrapper(request, null);

    assertEquals(request.getParameterMap(), otherWrapper.getParameterMap());
  }

  /**
   * Test method for {@link CustomServletRequestWrapper#getParameterNames()}.
   */
  @Test
  public void testGetParameterNames() {
    final CustomServletRequestWrapper wrapper = new CustomServletRequestWrapper(request, params);

    final Enumeration<String> names = new Enumerator<String>(params.keySet());
    final Enumeration<String> wrapperNames = wrapper.getParameterNames();
    assertNotEquals(names, wrapperNames);

    final List<String> expectedNames = new ArrayList<>();
    while (names.hasMoreElements()) {
      expectedNames.add(names.nextElement());
    }
    final List<String> actualNames = new ArrayList<>();
    while (wrapperNames.hasMoreElements()) {
      actualNames.add(wrapperNames.nextElement());
    }

    assertEquals(expectedNames, actualNames);
  }

  /**
   * Test method for {@link CustomServletRequestWrapper#getParameterValues(java.lang.String)}.
   */
  @Test
  public void testGetParameterValuesString() {
    final CustomServletRequestWrapper wrapper = new CustomServletRequestWrapper(request, params);

    assertArrayEquals(DUMMY_VALUE, wrapper.getParameterValues(DUMMY_PARAM));
  }

}
