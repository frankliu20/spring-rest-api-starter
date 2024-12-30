/*
 * Creation by madmath03 the 2019-01-28.
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
import com.monogramm.starter.api.discoverability.event.SingleResourceRetrievedEvent;
import com.monogramm.starter.persistence.AbstractGenericEntity;

import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * {@link SingleResourceRetrievedDiscoverabilityListener} Unit Test.
 * 
 * @author madmath03
 */
public class SingleResourceRetrievedDiscoverabilityListenerTest {

  private String requestUrl;

  private HttpServletResponse response;

  private SingleResourceRetrievedDiscoverabilityListener listener;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.requestUrl = "https://dummy.company.com/api/resources/";
    this.response = mock(HttpServletResponse.class);

    this.listener = new SingleResourceRetrievedDiscoverabilityListener();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  public void tearDown() throws Exception {
    Mockito.reset(this.response);
    this.response = null;

    this.listener = null;
  }

  /**
   * Test method for
   * {@link SingleResourceRetrievedDiscoverabilityListener#SingleResourceRetrievedDiscoverabilityListener()}.
   */
  @Test
  public void testPaginatedResultsRetrievedDiscoverabilityListener() {
    assertNotNull(this.listener);
  }

  /**
   * Test method for
   * {@link SingleResourceRetrievedDiscoverabilityListener#onApplicationEvent(SingleResourceRetrievedEvent)}.
   */
  @Test
  public void testOnApplicationEvent() {
    assertThrows(IllegalStateException.class, () -> {
      // Mock
      final AbstractGenericEntity source = new AbstractGenericEntity() {
        private static final long serialVersionUID = 1L;
      };
      final SingleResourceRetrievedEvent event =
          new SingleResourceRetrievedEvent(source, this.response);

      // Operation
      this.listener.onApplicationEvent(event);
    });
  }

  /**
   * Test method for
   * {@link SingleResourceRetrievedDiscoverabilityListener#addLinkHeaderOnSingleResourceRetrieval(String, HttpServletResponse, UUID)}.
   */
  @Test
  public void testAddLinkHeaderOnSingleResourceRetrieval() {
    // Mock
    final UUID sourceId = UUID.randomUUID();

    // Operation
    SingleResourceRetrievedDiscoverabilityListener
        .addLinkHeaderOnSingleResourceRetrieval(requestUrl, response, sourceId);

    // Check
    verify(this.response, times(1)).addHeader(eq(HttpHeaders.LINK), any(String.class));
    verifyNoMoreInteractions(this.response);
  }

}
