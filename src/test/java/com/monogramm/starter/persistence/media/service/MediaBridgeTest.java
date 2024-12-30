/*
 * Creation by madmath03 the 2019-01-24.
 */

package com.monogramm.starter.persistence.media.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import com.monogramm.starter.dto.media.MediaDto;
import com.monogramm.starter.persistence.AbstractGenericBridgeTest;
import com.monogramm.starter.persistence.media.entity.Media;
import com.monogramm.starter.persistence.permission.dao.PermissionRepository;
import com.monogramm.starter.persistence.user.dao.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@link MediaBridge} Unit Test.
 * 
 * @author madmath03
 */
public class MediaBridgeTest extends AbstractGenericBridgeTest<Media, MediaDto, MediaBridge> {

  @Override
  protected MediaBridge buildTestBridge() {
    return new MediaBridge();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.monogramm.starter.persistence.AbstractGenericBridgeTest#setUp()
   */
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.monogramm.starter.persistence.AbstractGenericBridgeTest#tearDown()
   */
  @AfterEach
  public void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link MediaBridge#MediaBridge()}.
   */
  @Test
  public void testMediaBridge() {
    final MediaBridge mediaBridge = new MediaBridge();

    super.testAbstractGenericBridge(mediaBridge);
  }

  /**
   * Test method for {@link MediaBridge#MediaBridge(UserRepository)}.
   */
  @Test
  public void testMediaBridgeIUserRepository() {
    final MediaBridge mediaBridge = new MediaBridge(getUserRepository());

    super.testAbstractGenericBridgeIUserRepository(mediaBridge);
  }

  /**
   * Test method for {@link MediaBridge#MediaBridge(UserRepository, PermissionRepository)}.
   */
  @Test
  public void testMediaBridgeNull() {
    final MediaBridge mediaBridge = new MediaBridge(null);

    super.testAbstractGenericBridge(mediaBridge);
  }

  /**
   * Test method for {@link MediaBridge#buildEntity()}.
   */
  @Test
  public void testBuildEntity() {
    super.testBuildEntity();

    assertNull(this.getBridge().buildEntity().getName());
  }

  /**
   * Test method for {@link MediaBridge#buildDto()}.
   */
  @Test
  public void testBuildDto() {
    super.testBuildDto();

    assertNull(this.getBridge().buildDto().getName());
  }

}
