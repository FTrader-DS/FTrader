<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <h2 class="text-xl font-bold text-gray-800 mb-4">📊 주간 랭킹</h2>

    <LoadingSpinner v-if="loading" />

    <div v-else-if="ranks.length === 0" class="text-center py-20 text-gray-400">
      아직 이번 주 풀이 기록이 없습니다.
    </div>

    <div v-else class="space-y-2">
      <div v-for="r in ranks" :key="r.userId"
           class="bg-white rounded-xl border border-gray-100 shadow-sm px-4 py-3 flex items-center gap-4">
        <!-- 순위 -->
        <span :class="rankClass(r.ranking)" class="w-8 text-center font-bold text-lg">
          {{ r.ranking <= 3 ? ['🥇','🥈','🥉'][r.ranking - 1] : r.ranking }}
        </span>

        <!-- 프로필 -->
        <img v-if="r.profileImg" :src="r.profileImg" class="w-9 h-9 rounded-full object-cover" />
        <div v-else class="w-9 h-9 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-bold text-sm">
          {{ r.nickname?.[0] }}
        </div>

        <div class="flex-1">
          <p class="font-semibold text-gray-800 text-sm">{{ r.nickname }}</p>
          <p class="text-xs text-gray-400">{{ r.levelName }}</p>
        </div>

        <p class="font-bold text-blue-600 text-sm">{{ r.weeklyPoints.toLocaleString() }}점</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mypageApi } from '@/api/mypage'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const ranks   = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const { data } = await mypageApi.getWeeklyRank()
    ranks.value = data.data
  } finally {
    loading.value = false
  }
})

const rankClass = r => r === 1 ? 'text-yellow-500' : r === 2 ? 'text-gray-400' : r === 3 ? 'text-orange-400' : 'text-gray-500'
</script>
