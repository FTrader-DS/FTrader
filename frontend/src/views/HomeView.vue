<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <h2 class="text-xl font-bold text-gray-800 mb-4">오늘의 퀴즈</h2>

    <LoadingSpinner v-if="loading" />

    <div v-else-if="items.length === 0" class="text-center py-20 text-gray-400">
      오늘 풀 문제를 모두 완료했습니다! 🎉
    </div>

    <div v-else class="space-y-3">
      <div v-for="item in items" :key="item.quizId ?? item.setId"
           @click="navigate(item)"
           class="bg-white rounded-xl shadow-sm border border-gray-100 p-4 flex items-center justify-between cursor-pointer hover:shadow-md hover:border-blue-200 transition">
        <div class="flex items-center gap-3">
          <!-- 유형 뱃지 -->
          <span :class="typeBadge(item.quizType)" class="text-xs font-semibold px-2 py-0.5 rounded-full">
            {{ typeName(item.quizType) }}
          </span>
          <div>
            <p class="text-sm font-medium text-gray-700">{{ item.sectorName }}</p>
            <p v-if="item.itemType === 'SET'" class="text-xs text-gray-400 mt-0.5">
              {{ item.questionCount }}문제 연쇄
            </p>
          </div>
        </div>
        <!-- 난이도 뱃지 (SET은 세트 내 문제마다 난이도가 다르므로 표시 안 함) -->
        <span v-if="item.itemType === 'SINGLE'"
              :class="diffBadge(item.difficulty)"
              class="text-xs font-semibold px-2 py-0.5 rounded-full">
          {{ diffName(item.difficulty) }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { quizApi } from '@/api/quiz'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const router  = useRouter()
const items   = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const { data } = await quizApi.getToday()
    items.value = data.data
  } finally {
    loading.value = false
  }
})

function navigate(item) {
  if (item.itemType === 'SET') router.push(`/quiz/set/${item.setId}`)
  else                         router.push(`/quiz/${item.quizId}`)
}

const typeMap = { 1: '호재/악재', 2: '섹터/밸류체인', 3: '용어', 4: '연쇄' }
const typeBadgeMap = {
  1: 'bg-orange-100 text-orange-700',
  2: 'bg-blue-100 text-blue-700',
  3: 'bg-purple-100 text-purple-700',
  4: 'bg-green-100 text-green-700',
}
const diffMap      = { 1: '초급', 2: '중급', 3: '고급' }
const diffBadgeMap = {
  1: 'bg-green-100 text-green-700',
  2: 'bg-yellow-100 text-yellow-700',
  3: 'bg-red-100 text-red-700',
}

const typeName  = t => typeMap[t]  ?? '-'
const typeBadge = t => typeBadgeMap[t] ?? ''
const diffName  = d => diffMap[d]  ?? '-'
const diffBadge = d => diffBadgeMap[d] ?? ''
</script>
