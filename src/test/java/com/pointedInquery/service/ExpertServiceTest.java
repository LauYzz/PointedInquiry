package com.pointedInquery.service;

import com.pointedInquery.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pointedInquery.PointedInqueryApplication;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PointedInqueryApplication.class)
@Transactional
@Rollback
class ExpertServiceTest {

    @InjectMocks
    private ExpertServiceImpl expertService;

    @Mock
    private ExpertService expertRepository; // Replace with your actual ExpertService interface

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
        // Perform cleanup if needed
    }

    @Test
    void testGetOneExpert() {
        String phone = "1234567890";
        ExpertDetailedDto expectedDto = new ExpertDetailedDto(); // Create your expected DTO here

        // Mock behavior of the ExpertService interface
        when(expertRepository.getOneExpert(phone)).thenReturn(expectedDto);

        // Call the method in your service implementation
        ExpertDetailedDto actualDto = expertService.getOneExpert(phone);

        // Assert the result
        assertEquals(expectedDto, actualDto);

        // Optionally, verify that the method was called with the correct arguments
        verify(expertRepository, times(1)).getOneExpert(phone);
    }

    @Test
    void testListByType() {
        int type = 1;
        int sortType = 0;
        List<ExpertServiceImpl.ExpertWithTopics> expectedList = new ArrayList<>(); // Create your expected list here

        // Mock behavior of the ExpertService interface
        when(expertRepository.listByType(type, sortType)).thenReturn(expectedList);

        // Call the method in your service implementation
        List<ExpertServiceImpl.ExpertWithTopics> actualList = expertService.listByType(type, sortType);

        // Assert the result
        assertEquals(expectedList, actualList);

        // Optionally, verify that the method was called with the correct arguments
        verify(expertRepository, times(1)).listByType(type, sortType);
    }

    @Test
    void testSaveOrUpdate() {
        AddExpertDto addExpertDto = new AddExpertDto(); // Create your DTO object here

        // Mock behavior of the ExpertService interface
        when(expertRepository.saveOrUpdate(addExpertDto)).thenReturn(1);

        // Call the method in your service implementation
        int result = expertService.saveOrUpdate(addExpertDto);

        // Assert the result
        assertEquals(1, result);

        // Optionally, verify that the method was called with the correct arguments
        verify(expertRepository, times(1)).saveOrUpdate(addExpertDto);
    }
}