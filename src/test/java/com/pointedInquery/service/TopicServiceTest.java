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
class TopicServiceTest {

    @InjectMocks
    private TopicServiceImpl topicService;

    @Mock
    private TopicService topicRepository; // Replace with your actual TopicService interface

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
        // Perform cleanup if needed
    }

    @Test
    void testCheck() {
        String state = "active";
        String topicId = "1";
        boolean expectedResult = true; // Define your expected result here

        // Mock behavior of the TopicService interface
        when(topicRepository.check(state, topicId)).thenReturn(expectedResult);

        // Call the method in your service implementation
        boolean actualResult = topicService.check(state, topicId);

        // Assert the result
        assertEquals(expectedResult, actualResult);

        // Optionally, verify that the method was called with the correct arguments
        verify(topicRepository, times(1)).check(state, topicId);
    }

    @Test
    void testAddTopic() {
        String expertId = "1";
        String title = "Topic Title";
        String text = "Topic Text";
        Integer price = 100;
        String way = "Online";
        Integer expectedTopicId = 1; // Define your expected topic ID here

        // Mock behavior of the TopicService interface
        when(topicRepository.addTopic(expertId, title, text, price, way)).thenReturn(expectedTopicId);

        // Call the method in your service implementation
        Integer actualTopicId = topicService.addTopic(expertId, title, text, price, way);

        // Assert the result
        assertEquals(expectedTopicId, actualTopicId);

        // Optionally, verify that the method was called with the correct arguments
        verify(topicRepository, times(1)).addTopic(expertId, title, text, price, way);
    }
    

    @Test
    void testGetTopicByExpertId() {
        String expertId = "1";
        List<Topic> expectedTopics = new ArrayList<>(); // Define your expected list of topics here

        // Mock behavior of the TopicService interface
        when(topicRepository.getTopicByExpertId(expertId)).thenReturn(expectedTopics);

        // Call the method in your service implementation
        List<Topic> actualTopics = topicService.getTopicByExpertId(expertId);

        // Assert the result
        assertEquals(expectedTopics, actualTopics);

        // Optionally, verify that the method was called with the correct arguments
        verify(topicRepository, times(1)).getTopicByExpertId(expertId);
    }
}