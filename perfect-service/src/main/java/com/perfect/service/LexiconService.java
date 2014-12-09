package com.perfect.service;

import com.perfect.dto.keyword.LexiconDTO;

import java.util.List;

/**
 * Created by baizz on 2014-12-2.
 */
public interface LexiconService {

    void deleteLexiconByTrade(String trade, String category);

    List<LexiconDTO> save(List<LexiconDTO> dtoList);
}
