<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <h2 class="text-xl font-bold text-gray-800 mb-4">🔖 나만의 문제집</h2>

    <LoadingSpinner v-if="loading" />

    <div v-else-if="items.length === 0" class="text-center py-20 text-gray-400">
      저장된 문제가 없습니다.<br/>결과 화면에서 문제를 저장해보세요.
    </div>

    <div v-else class="space-y-3">
      <div v-for="item in items" :key="item.savedQuizId"
           @click="$router.push(`/quiz/${item.quizId}?review=true`)"
           class="bg-white rounded-xl border border-gray-100 shadow-sm p-4 cursor-pointer hover:shadow-md hover:border-blue-200 transition">
        <div class="flex items-center gap-2 mb-2">
          <span :class="diffBadge(item.difficulty)" class="text-xs font-semibold px-2 py-0.5 rounded-full">
            {{ diffName(item.difficulty) }}
          </span>
          <span class="text-xs bg-gray-100 text-gray-500 px-2 py-0.5 rounded-full">{{ item.sectorName }}</span>
        </div>
        <p class="text-sm font-medium text-gray-800 leading-snug">{{ item.question }}</p>
        <p v-if="item.memo" class="text-xs text-blue-400 mt-2">📝 {{ item.memo }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mypageApi } from '@/api/mypage'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const items   = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const { data } = await mypageApi.getSavedQuizzes()
    items.value = data.data
  } finally {
    loading.value = false
  }
})

const diffMap      = { 1: '초급', 2: '중급', 3: '고급' }
const diffBadgeMap = { 1: 'bg-green-100 text-green-700', 2: 'bg-yellow-100 text-yellow-700', 3: 'bg-red-100 text-red-700' }
const diffName  = d => diffMap[d]  ?? '-'
const diffBadge = d => diffBadgeMap[d] ?? ''
</script>
