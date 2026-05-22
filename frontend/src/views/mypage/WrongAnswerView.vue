<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <h2 class="text-xl font-bold text-gray-800 mb-4">❌ 오답노트</h2>

    <LoadingSpinner v-if="loading" />

    <template v-else>
      <!-- 유형 분석 -->
      <div v-if="analysis.length" class="bg-white rounded-xl border border-gray-100 shadow-sm p-4 mb-5">
        <p class="text-xs text-gray-400 font-semibold mb-3">📊 자주 틀리는 유형</p>
        <div class="space-y-2">
          <div v-for="a in analysis" :key="a.quizType" class="flex items-center gap-3">
            <span class="text-xs text-gray-600 w-32 shrink-0">{{ a.quizTypeName }}</span>
            <div class="flex-1 bg-gray-100 rounded-full h-2">
              <div class="bg-red-400 h-2 rounded-full transition-all"
                   :style="{ width: barWidth(a.wrongCount) + '%' }" />
            </div>
            <span class="text-xs text-gray-500 w-8 text-right">{{ a.wrongCount }}개</span>
          </div>
        </div>
      </div>

      <!-- 오답 목록 -->
      <div v-if="items.length === 0" class="text-center py-20 text-gray-400">
        오답이 없습니다. 훌륭해요! 🎉
      </div>

      <div v-else class="space-y-3">
        <div v-for="item in items" :key="item.resultId"
             class="bg-white rounded-xl border border-gray-100 shadow-sm p-4">
          <div class="flex items-center gap-2 mb-2">
            <span class="text-xs bg-gray-100 text-gray-500 px-2 py-0.5 rounded-full">{{ item.sectorName }}</span>
            <span class="text-xs text-gray-400">{{ formatDate(item.solvedAt) }}</span>
          </div>
          <p class="text-sm font-medium text-gray-800 mb-3">{{ item.question }}</p>
          <div class="space-y-1 text-xs">
            <p class="text-red-500">내 답: {{ item.selectedContent }}</p>
            <p class="text-green-600">정답: {{ item.correctContent }}</p>
          </div>
          <div class="mt-3 bg-gray-50 rounded-lg p-3">
            <p class="text-xs text-gray-500 leading-relaxed">{{ item.explanation }}</p>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { mypageApi } from '@/api/mypage'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const items    = ref([])
const analysis = ref([])
const loading  = ref(true)

const maxWrong = computed(() => Math.max(...analysis.value.map(a => Number(a.wrongCount)), 1))
const barWidth = count => Math.round((Number(count) / maxWrong.value) * 100)

onMounted(async () => {
  try {
    const { data } = await mypageApi.getWrongAnswers()
    items.value    = data.data.items
    analysis.value = data.data.analysis
  } finally {
    loading.value = false
  }
})

function formatDate(dt) {
  return new Date(dt).toLocaleDateString('ko-KR', { month: 'short', day: 'numeric' })
}
</script>
