package com.kapango.infra.mapper;

import java.util.Collection;
import org.hibernate.Hibernate;

public interface LazyLoadingAwareMapper {
  default boolean isNotLazyLoaded(Collection<?> sourceCollection){
    // Case: Source field in domain object is lazy: Skip mapping
    if (Hibernate.isInitialized(sourceCollection)) {
      // Continue Mapping
      return true;
    }
    
    // Skip mapping
    return false;
  }
}