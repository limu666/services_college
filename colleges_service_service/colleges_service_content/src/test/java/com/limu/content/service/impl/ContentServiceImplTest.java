package com.limu.content.service.impl;

import com.limu.apis.user.IUserClient;
import com.limu.content.mapper.ContentMapper;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.content.pojos.Content;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ContentServiceImplTest {

    @Mock
    private ContentMapper mockContentMapper;
    @Mock
    private IUserClient mockUserClient;

    @InjectMocks
    private ContentServiceImpl contentServiceImplUnderTest;

    @Test
    void testGetContentList() {
        // Setup
        final Content content = new Content();
        content.setContentId(0L);
        content.setContent("content");
        content.setRemark("remark");
        content.setPageNo(0);
        content.setPageSize(0);

        // Configure ContentMapper.selectContentList(...).
        final Content content1 = new Content();
        content1.setContentId(0L);
        content1.setContent("content");
        content1.setRemark("remark");
        content1.setPageNo(0);
        content1.setPageSize(0);
        final List<Content> contentList = Arrays.asList(content1);
        final Content content2 = new Content();
        content2.setContentId(0L);
        content2.setContent("content");
        content2.setRemark("remark");
        content2.setPageNo(0);
        content2.setPageSize(0);
        when(mockContentMapper.selectContentList(content2)).thenReturn(contentList);

        when(mockContentMapper.getCount()).thenReturn(0);

        // Run the test
        final ResponseResult<?> result = contentServiceImplUnderTest.getContentList(content);

        // Verify the results
    }

    @Test
    void testGetContentList_ContentMapperSelectContentListReturnsNoItems() {
        // Setup
        final Content content = new Content();
        content.setContentId(0L);
        content.setContent("content");
        content.setRemark("remark");
        content.setPageNo(0);
        content.setPageSize(0);

        // Configure ContentMapper.selectContentList(...).
        final Content content1 = new Content();
        content1.setContentId(0L);
        content1.setContent("content");
        content1.setRemark("remark");
        content1.setPageNo(0);
        content1.setPageSize(0);
        when(mockContentMapper.selectContentList(content1)).thenReturn(Collections.emptyList());

        when(mockContentMapper.getCount()).thenReturn(0);

        // Run the test
        final ResponseResult<?> result = contentServiceImplUnderTest.getContentList(content);

        // Verify the results
    }

    @Test
    void testGetContentById() {
        // Setup
        // Configure ContentMapper.selectContentByContentId(...).
        final Content content = new Content();
        content.setContentId(0L);
        content.setContent("content");
        content.setRemark("remark");
        content.setPageNo(0);
        content.setPageSize(0);
        when(mockContentMapper.selectContentByContentId(0L)).thenReturn(content);

        // Run the test
        final ResponseResult<Content> result = contentServiceImplUnderTest.getContentById(0L);

        // Verify the results
    }

    @Test
    void testGetContentById_ContentMapperReturnsNull() {
        // Setup
        when(mockContentMapper.selectContentByContentId(0L)).thenReturn(null);

        // Run the test
        final ResponseResult<Content> result = contentServiceImplUnderTest.getContentById(0L);

        // Verify the results
    }

    @Test
    void testUpdateContentById() {
        // Setup
        final Content content = new Content();
        content.setContentId(0L);
        content.setContent("content");
        content.setRemark("remark");
        content.setPageNo(0);
        content.setPageSize(0);

        // Run the test
        final ResponseResult<?> result = contentServiceImplUnderTest.updateContentById(content);

        // Verify the results
        // Confirm ContentMapper.updateContent(...).
        final Content content1 = new Content();
        content1.setContentId(0L);
        content1.setContent("content");
        content1.setRemark("remark");
        content1.setPageNo(0);
        content1.setPageSize(0);
        verify(mockContentMapper).updateContent(content1);
    }

    @Test
    void testDelContentById() {
        // Setup
        // Configure ContentMapper.selectContentByContentId(...).
        final Content content = new Content();
        content.setContentId(0L);
        content.setContent("content");
        content.setRemark("remark");
        content.setPageNo(0);
        content.setPageSize(0);
        when(mockContentMapper.selectContentByContentId(0L)).thenReturn(content);

        // Run the test
        final ResponseResult<?> result = contentServiceImplUnderTest.delContentById(0L);

        // Verify the results
    }

    @Test
    void testDelContentById_ContentMapperReturnsNull() {
        // Setup
        when(mockContentMapper.selectContentByContentId(0L)).thenReturn(null);

        // Run the test
        final ResponseResult<?> result = contentServiceImplUnderTest.delContentById(0L);

        // Verify the results
    }

    @Test
    void testExportContentList() {
        // Setup
        // Run the test
        final ResponseResult<?> result = contentServiceImplUnderTest.exportContentList();

        // Verify the results
    }
}
