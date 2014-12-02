package com.perfect.db.mongodb.impl;

import com.perfect.dao.bidding.BiddingLogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.bidding.BiddingLogDTO;
import com.perfect.entity.bidding.BiddingLogEntity;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 14-9-4.
 */
@Component
public class BiddingLogDAOImpl extends AbstractUserBaseDAOImpl<BiddingLogDTO, Long> implements BiddingLogDAO {
    @Override
    public Class<BiddingLogEntity> getEntityClass() {
        return BiddingLogEntity.class;
    }

    @Override
    public Class<BiddingLogDTO> getDTOClass() {
        return BiddingLogDTO.class;
    }
}
