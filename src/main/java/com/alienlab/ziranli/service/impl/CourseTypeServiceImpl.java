package com.alienlab.ziranli.service.impl;

import com.alienlab.ziranli.service.CourseTypeService;
import com.alienlab.ziranli.domain.CourseType;
import com.alienlab.ziranli.repository.CourseTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing CourseType.
 */
@Service
@Transactional
public class CourseTypeServiceImpl implements CourseTypeService{

    private final Logger log = LoggerFactory.getLogger(CourseTypeServiceImpl.class);
    
    private final CourseTypeRepository courseTypeRepository;

    public CourseTypeServiceImpl(CourseTypeRepository courseTypeRepository) {
        this.courseTypeRepository = courseTypeRepository;
    }

    /**
     * Save a courseType.
     *
     * @param courseType the entity to save
     * @return the persisted entity
     */
    @Override
    public CourseType save(CourseType courseType) {
        log.debug("Request to save CourseType : {}", courseType);
        CourseType result = courseTypeRepository.save(courseType);
        return result;
    }

    /**
     *  Get all the courseTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourseType> findAll(Pageable pageable) {
        log.debug("Request to get all CourseTypes");
        Page<CourseType> result = courseTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one courseType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CourseType findOne(Long id) {
        log.debug("Request to get CourseType : {}", id);
        CourseType courseType = courseTypeRepository.findOne(id);
        return courseType;
    }

    /**
     *  Delete the  courseType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseType : {}", id);
        courseTypeRepository.delete(id);
    }
}
