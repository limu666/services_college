package com.limu.content.service.impl;

import com.limu.model.content.pojos.Comment;
import com.limu.content.mapper.CommentMapper;
import com.limu.content.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
