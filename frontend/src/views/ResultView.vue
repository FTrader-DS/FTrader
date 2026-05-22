<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <LoadingSpinner v-if="!result" />

    <template v-else>
      <!-- 스트릭 뱃지 (오늘 첫 제출 시에만 표시) -->
      <div v-if="result.newAttend"
           class="bg-orange-50 border border-orange-200 rounded-xl px-4 py-3 mb-4 flex items-center gap-3">
        <span class="text-2xl">🔥</span>
        <div>
          <p class="text-sm font-bold text-orange-700">{{ result.streak }}일 연속 출석!</p>
          <p class="text-xs text-orange-500">오늘도 학습을 완료했어요</p>
        </div>
      </div>

      <!-- 정오답 헤더 -->
      <div :class="result.correct ? 'bg-green-50 border-green-300' : 'bg-red-50 border-red-300'"
           class="border rounded-2xl p-6 mb-5 text-center">
        <p class="text-4xl mb-2">{{ result.correct ? '✅' : '❌' }}</p>
        <p class="text-xl font-bold" :class="result.correct ? 'text-green-700' : 'text-red-600'">
          {{ result.correct ? '정답입니다!' : '오답입니다' }}
        </p>
        <p class="text-sm text-gray-500 mt-1">+{{ result.earnedPoints }}점 획득 · 누적 {{ result.totalPoints }}점</p>
      </div>

      <!-- 선택지 결과 -->
      <div v-if="quiz" class="space-y-2 mb-5">
        <div v-for="c in quiz.choices" :key="c.choiceId"
             :class="choiceResultClass(c.choiceId)"
             class="px-4 py-3 rounded-xl border text-sm font-medium">
          <span class="mr-2 text-gray-400">{{ c.choiceNo }}.</span>{{ c.content }}
        </div>
      </div>

      <!-- 해설 -->
      <div class="bg-gray-50 border border-gray-200 rounded-xl p-4 mb-6">
        <p class="text-xs text-gray-400 font-semibold mb-1">💡 해설</p>
        <p class="text-sm text-gray-700 leading-relaxed">{{ result.explanation }}</p>
      </div>

      <!-- 액션 버튼 -->
      <div class="flex gap-3">
        <button @click="toggleBookmark"
                :class="bookmarked ? 'bg-yellow-400 text-white' : 'bg-white text-gray-600 border border-gray-200'"
                class="flex-1 py-3 rounded-xl font-semibold text-sm hover:opacity-90 transition">
          {{ bookmarked ? '★ 저장됨' : '☆ 문제집에 저장' }}
        </button>
        <button @click="$router.push('/')"
                class="flex-1 py-3 rounded-xl bg-blue-600 text-white font-semibold text-sm hover:bg-blue-700 transition">
          홈으로
        </button>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useQuizStore } from '@/stores/quiz'
import { quizApi } from '@/api/quiz'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const route     = useRoute()
const router    = useRouter()
const quizStore = useQuizStore()

const result     = ref(quizStore.lastResult)
const quiz       = ref(quizStore.lastQuiz)
const bookmarked = ref(false)

onMounted(() => {
  if (!result.value) { router.replace('/'); return }
  if (quiz.value) bookmarked.value = quiz.value.bookmarked
  quizStore.clear()
})

function choiceResultClass(choiceId) {
  if (!result.value) return 'bg-white border-gray-200'
  if (choiceId === result.value.correctChoiceId) return 'bg-green-50 border-green-400 text-green-700'
  return 'bg-gray-50 border-gray-200 text-gray-400'
}

async function toggleBookmark() {
  const { data } = await quizApi.bookmark(route.params.quizId)
  bookmarked.value = data.data.bookmarked
}
</script>
