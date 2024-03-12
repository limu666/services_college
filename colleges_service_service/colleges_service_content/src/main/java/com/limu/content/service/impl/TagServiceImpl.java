package com.limu.content.service.impl;

import com.limu.model.content.pojos.Tag;
import com.limu.content.mapper.TagMapper;
import com.limu.content.service.ITagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
