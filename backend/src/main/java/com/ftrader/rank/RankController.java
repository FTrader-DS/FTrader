package com.ftrader.rank;

import com.ftrader.common.ApiResponse;
import com.ftrader.rank.dto.RankItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankMapper rankMapper;

    @GetMapping("/weekly")
    public ResponseEntity<ApiResponse<List<RankItem>>> getWeeklyRank() {
        return ResponseEntity.ok(ApiResponse.success(rankMapper.findWeeklyRank()));
    }
}
