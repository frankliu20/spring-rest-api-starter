/*
 * Creation by madmath03 the 2019-01-13.
 */

package com.monogramm.starter.api.discoverability.listener;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.google.common.net.HttpHeaders;
import com.monogramm.starter.api.discoverability.event.PaginatedResultsRetrievedEvent;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * {@link PaginatedResultsRetrievedDiscoverabilityListener} Unit Test.
 * 
 * @author madmath03
 */
public class PaginatedResultsRetrievedDiscoverabilityListenerTest {

  private String requestUrl;

  private UriComponentsBuilder uriBuilder;

  private int page;

  private int totalPages;

  private int pageSize;

  private HttpServletResponse response;

  private Environment env;

  private PaginatedResultsRetrievedDiscoverabilityListener listener;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.requestUrl = "https://dummy.company.com/api/resources/";
    this.uriBuilder = new UriComponentsBuilder() {};
    this.page = 1;
    this.totalPages = 42;
    this.pageSize = 20;
    this.response = mock(HttpServletResponse.class);

    this.env = mock(Environment.class);
    assertNotNull(env);

    this.listener = new PaginatedResultsRetrievedDiscoverabilityListener(this.env);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  public void tearDown() throws Exception {
    this.uriBuilder = null;
    this.page = -1;
    this.totalPages = 0;
    this.pageSize = -1;

    Mockito.reset(this.response);
    this.response = null;

    Mockito.reset(this.env);
    this.env = null;

    this.listener = null;
  }

  /**
   * Test method for
   * {@link PaginatedResultsRetrievedDiscoverabilityListener#PaginatedResultsRetrievedDiscoverabilityListener()}.
   */
  @Test
  public void testPaginatedResultsRetrievedDiscoverabilityListener() {
    assertNotNull(this.listener);
  }

  /**
   * Test method for
   * {@link PaginatedResultsRetrievedDiscoverabilityListener#onApplicationEvent(com.monogramm.starter.api.discoverability.event.PaginatedResultsRetrievedEvent)}.
   */
  @Test
  public void testOnApplicationEvent() {
    assertThrows(IllegalStateException.class, () -> {
      // Mock
      final PaginatedResultsRetrievedEvent event =
          new PaginatedResultsRetrievedEvent(response, uriBuilder, page, totalPages, pageSize);

      // Operation
      this.listener.onApplicationEvent(event);
    });
  }

  /**
   * Test method for
   * {@link PaginatedResultsRetrievedDiscoverabilityListener#addLinkHeaderOnPagedResourceRetrieval(String, UriComponentsBuilder, HttpServletResponse, int, int, int)}.
   */
  @Test
  public void testAddLinkHeaderOnPagedResourceRetrieval() {
    // Mock

    // Operation
    PaginatedResultsRetrievedDiscoverabilityListener.addLinkHeaderOnPagedResourceRetrieval(
        requestUrl, uriBuilder, response, page, totalPages, pageSize);

    // Check
    verify(this.response, times(1)).addHeader(eq(HttpHeaders.LINK), any(String.class));
    verifyNoMoreInteractions(this.response);
  }

}
