package com.perfect.service;

import com.perfect.dto.UrlDTO;

import java.util.List;

/**
 * Created by baizz on 2014-9-26.
 * 2014-11-26 refactor
 */
public interface BiddingMaintenanceService {

    List<UrlDTO> findAll();

    void saveUrlEntity(UrlDTO urlDTO);
}
