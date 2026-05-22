package com.ftrader.rank;

import com.ftrader.rank.dto.RankItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankMapper {
    List<RankItem> findWeeklyRank();
}
