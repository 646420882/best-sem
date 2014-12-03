package com.perfect.service;

import com.perfect.dto.creative.CreativeSourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface CreativeSourceService {

    void save(CreativeSourceDTO creativeSourceDTO);

    Page<CreativeSourceDTO> search(String title, String desc, String url, Pageable pageable);

    void save(List<CreativeSourceDTO> resultList);

    boolean exits(String id);
}
