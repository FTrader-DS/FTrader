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

    private final RankService rankService;

    /** 주간 랭킹 Top 20 */
    @GetMapping("/weekly")
    public ResponseEntity<ApiResponse<List<RankItem>>> getWeeklyRank() {
        return ResponseEntity.ok(ApiResponse.success(rankService.getWeelkyRank()));
    }
}
