package com.ftrader.rank;

import com.ftrader.rank.dto.RankItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {
    private final RankMapper rankMapper;

    public List<RankItem> getWeelkyRank(){
        return rankMapper.findWeeklyRank();
    }
}
