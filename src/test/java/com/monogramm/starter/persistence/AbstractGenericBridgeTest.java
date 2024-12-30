/*
 * Creation by madmath03 the 2017-11-18.
 */

package com.monogramm.starter.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.monogramm.starter.dto.AbstractGenericDto;
import com.monogramm.starter.persistence.user.dao.UserRepository;
import com.monogramm.starter.persistence.user.entity.User;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * {@link AbstractGenericBridge} Unit Test.
 * 
 * @author madmath03
 */
public abstract class AbstractGenericBridgeTest<E extends AbstractGenericEntity,
    D extends AbstractGenericDto, B extends AbstractGenericBridge<E, D>> {

  /**
   * The tested bridge.
   */
  private B bridge;

  private UserRepository userRepository;

  /**
   * Build an empty bridge.
   * 
   * @return an DTO for tests.
   */
  abstract protected B buildTestBridge();

  /**
   * Get the {@link #userRepository}.
   * 
   * @return the {@link #userRepository}.
   */
  protected final UserRepository getUserRepository() {
    return userRepository;
  }

  /**
   * Get the {@link #bridge}.
   * 
   * @return the {@link #bridge}.
   */
  protected final B getBridge() {
    return bridge;
  }

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.bridge = this.buildTestBridge();

    assertNotNull(bridge, "The tested bridge cannot be null!");

    userRepository = mock(UserRepository.class);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  public void tearDown() throws Exception {
    this.bridge = null;

    Mockito.reset(userRepository);
  }

  /**
   * Test method for {@link AbstractGenericBridge#AbstractGenericBridge()}.
   */
  public void testAbstractGenericBridge(B bridge) {
    assertNotNull(bridge);
    assertNull(bridge.getUserRepository());
  }

  /**
   * Test method for
   * {@link AbstractGenericBridge#AbstractGenericBridge(com.monogramm.starter.persistence.user.dao.UserRepository)}.
   */
  public void testAbstractGenericBridgeIUserRepository(B bridge) {
    assertNotNull(bridge);
    assertNotNull(bridge.getUserRepository());
  }

  /**
   * Test method for {@link AbstractGenericBridge#getUserRepository()}.
   */
  @Test
  public void testGetUserRepository() {
    assertNull(this.bridge.getUserRepository());
  }

  /**
   * Test method for
   * {@link AbstractGenericBridge#setUserRepository(com.monogramm.starter.persistence.user.dao.UserRepository)}.
   */
  @Test
  public void testSetUserRepository() {
    this.getBridge().setUserRepository(userRepository);
    assertEquals(userRepository, this.bridge.getUserRepository());
  }

  /**
   * Test method for {@link AbstractGenericBridge#buildEntity()}.
   */
  @Test
  public void testBuildEntity() {
    final E entity = this.bridge.buildEntity();

    assertNotNull(entity);
    assertNull(entity.getId());
    assertNull(entity.getCreatedAt());
    assertNull(entity.getCreatedBy());
    assertNull(entity.getModifiedAt());
    assertNull(entity.getModifiedBy());
    assertNull(entity.getOwner());
  }

  /**
   * Test method for {@link AbstractGenericBridge#buildDto()}.
   */
  @Test
  public void testBuildDto() {
    final D dto = this.bridge.buildDto();

    assertNotNull(dto);
    assertNull(dto.getId());
    assertNull(dto.getCreatedAt());
    assertNull(dto.getCreatedBy());
    assertNull(dto.getModifiedAt());
    assertNull(dto.getModifiedBy());
    assertNull(dto.getOwner());
  }

  /**
   * Test method for {@link AbstractGenericBridge#toEntity(AbstractGenericDto)}.
   */
  @Test
  public void testToEntity() {
    final D dto = this.bridge.buildDto();
    E entity = this.bridge.toEntity(dto);

    // Check data
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
    assertNull(dto.getCreatedBy());
    assertNull(entity.getCreatedBy());
    assertEquals(dto.getModifiedAt(), entity.getModifiedAt());
    assertNull(dto.getModifiedBy());
    assertNull(entity.getModifiedBy());
    assertNull(dto.getOwner());
    assertNull(entity.getOwner());

    // Set up the DTO
    dto.setId(UUID.randomUUID());
    dto.setCreatedAt(new Date());
    dto.setCreatedBy(UUID.randomUUID());
    dto.setModifiedAt(new Date());
    dto.setModifiedBy(UUID.randomUUID());
    dto.setOwner(UUID.randomUUID());

    // Test that update of DTO is not reported to previous entity
    assertNotEquals(dto.getId(), entity.getId());
    assertNotEquals(dto.getCreatedAt(), entity.getCreatedAt());
    assertNotNull(dto.getCreatedBy());
    assertNull(entity.getCreatedBy());
    assertNotEquals(dto.getModifiedAt(), entity.getModifiedAt());
    assertNotNull(dto.getModifiedBy());
    assertNull(entity.getModifiedBy());
    assertNotNull(dto.getOwner());
    assertNull(entity.getOwner());
  }

  /**
   * Test method for {@link AbstractGenericBridge#toEntity(AbstractGenericDto)}.
   */
  @Test
  public void testToEntityNoUserRepository() {
    final D dto = this.bridge.buildDto();
    this.bridge.setUserRepository(null);

    // Set up the DTO
    dto.setId(UUID.randomUUID());
    dto.setCreatedAt(new Date());
    dto.setCreatedBy(UUID.randomUUID());
    dto.setModifiedAt(new Date());
    dto.setModifiedBy(UUID.randomUUID());
    dto.setOwner(UUID.randomUUID());

    // Test that update of DTO is reported to new entity
    E entity = this.bridge.toEntity(dto);

    // Check data
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
    assertEquals(dto.getCreatedBy(), entity.getCreatedBy().getId());
    assertEquals(dto.getModifiedAt(), entity.getModifiedAt());
    assertEquals(dto.getModifiedBy(), entity.getModifiedBy().getId());
    assertEquals(dto.getOwner(), entity.getOwner().getId());
  }

  /**
   * Test method for {@link AbstractGenericBridge#toEntity(AbstractGenericDto)}.
   */
  @Test
  public void testToEntityWithUserRepository() {
    final D dto = this.bridge.buildDto();
    this.bridge.setUserRepository(userRepository);

    // Set up the DTO
    dto.setId(UUID.randomUUID());
    dto.setCreatedAt(new Date());
    dto.setCreatedBy(UUID.randomUUID());
    dto.setModifiedAt(new Date());
    dto.setModifiedBy(UUID.randomUUID());
    dto.setOwner(UUID.randomUUID());

    // Set up stubs
    final User createdBy = User.builder().id(dto.getCreatedBy()).build();
    when(userRepository.findById(dto.getCreatedBy())).thenReturn(createdBy);

    final User modifiedBy = User.builder().id(dto.getModifiedBy()).build();
    when(userRepository.findById(dto.getModifiedBy())).thenReturn(modifiedBy);

    final User owner = User.builder().id(dto.getOwner()).build();
    when(userRepository.findById(dto.getOwner())).thenReturn(owner);

    // Test that update of DTO is reported to new entity
    E entity = this.bridge.toEntity(dto);

    // Check data
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
    assertEquals(dto.getCreatedBy(), entity.getCreatedBy().getId());
    assertEquals(dto.getModifiedAt(), entity.getModifiedAt());
    assertEquals(dto.getModifiedBy(), entity.getModifiedBy().getId());
    assertEquals(dto.getOwner(), entity.getOwner().getId());

    // Check stubs call
    verify(userRepository, times(1)).findById(dto.getCreatedBy());
    verify(userRepository, times(1)).findById(dto.getModifiedBy());
    verify(userRepository, times(1)).findById(dto.getOwner());
    verifyNoMoreInteractions(userRepository);
  }

  /**
   * Test method for {@link AbstractGenericBridge#toDto(AbstractGenericEntity)}.
   */
  @Test
  public void testToDto() {
    final E entity = this.bridge.buildEntity();
    D dto = this.bridge.toDto(entity);

    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
    assertNull(entity.getCreatedBy());
    assertNull(dto.getCreatedBy());
    assertEquals(entity.getModifiedAt(), dto.getModifiedAt());
    assertNull(entity.getModifiedBy());
    assertNull(dto.getModifiedBy());
    assertNull(entity.getOwner());
    assertNull(dto.getOwner());


    // Set up the entity
    entity.setId(UUID.randomUUID());
    entity.setCreatedAt(new Date());
    entity.setCreatedBy(User.builder().id(UUID.randomUUID()).build());
    entity.setModifiedAt(new Date());
    entity.setModifiedBy(User.builder().id(UUID.randomUUID()).build());
    entity.setOwner(User.builder().id(UUID.randomUUID()).build());

    // Test that update of DTO is not reported to previous entity
    assertNotEquals(entity.getId(), dto.getId());
    assertNotEquals(entity.getCreatedAt(), dto.getCreatedAt());
    assertNotNull(entity.getCreatedBy());
    assertNull(dto.getCreatedBy());
    assertNotEquals(entity.getModifiedAt(), dto.getModifiedAt());
    assertNotNull(entity.getModifiedBy());
    assertNull(dto.getModifiedBy());
    assertNotNull(entity.getOwner());
    assertNull(dto.getOwner());
  }

}
