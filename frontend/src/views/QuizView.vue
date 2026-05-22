<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <LoadingSpinner v-if="loading" />

    <template v-else-if="quiz">
      <!-- 섹터 / 난이도 -->
      <div class="flex items-center gap-2 mb-4">
        <span class="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full">{{ quiz.sectorName }}</span>
        <span :class="diffBadge(quiz.difficulty)" class="text-xs font-semibold px-2 py-0.5 rounded-full">
          {{ diffName(quiz.difficulty) }}
        </span>
      </div>

      <!-- 뉴스 카드 -->
      <div v-if="quiz.newsContent" class="bg-blue-50 border border-blue-200 rounded-xl p-4 mb-5">
        <p class="text-xs text-blue-500 font-semibold mb-1">📰 뉴스 / 공시</p>
        <p class="text-sm text-gray-700 leading-relaxed">
          <TermSave :text="quiz.newsContent" :terms="quiz.vocabTerms" />
        </p>
      </div>

      <!-- 질문 -->
      <p class="text-base font-semibold text-gray-800 mb-5 leading-snug">
        <TermSave :text="quiz.question" :terms="quiz.vocabTerms" />
      </p>

      <!-- 선택지 -->
      <div class="space-y-3">
        <button v-for="c in quiz.choices" :key="c.choiceId"
                @click="select(c.choiceId)"
                :disabled="submitted"
                :class="choiceClass(c.choiceId)"
                class="w-full text-left px-4 py-3 rounded-xl border text-sm font-medium transition">
          <span class="mr-2 text-gray-400">{{ c.choiceNo }}.</span>{{ c.content }}
        </button>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { quizApi } from '@/api/quiz'
import { useQuizStore } from '@/stores/quiz'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import TermSave from '@/components/quiz/TermSave.vue'

const route     = useRoute()
const router    = useRouter()
const quizStore = useQuizStore()

const quiz      = ref(null)
const loading   = ref(true)
const submitted = ref(false)
const selected  = ref(null)

onMounted(async () => {
  try {
    const { data } = await quizApi.getQuiz(route.params.quizId)
    quiz.value = data.data
    if (quiz.value.solved && !route.query.review) router.replace('/')
  } finally {
    loading.value = false
  }
})

async function select(choiceId) {
  if (submitted.value) return
  selected.value  = choiceId
  submitted.value = true

  try {
    const { data } = await quizApi.submit(route.params.quizId, choiceId)
    quizStore.setResult(quiz.value, data.data)
    router.push(`/result/${route.params.quizId}`)
  } catch {
    submitted.value = false
    selected.value  = null
  }
}

function choiceClass(choiceId) {
  if (!submitted.value)
    return choiceId === selected.value
      ? 'bg-blue-50 border-blue-400 text-blue-700'
      : 'bg-white border-gray-200 hover:bg-blue-50 hover:border-blue-300'
  return 'bg-gray-50 border-gray-200 text-gray-400 cursor-not-allowed'
}

const diffMap      = { 1: '초급', 2: '중급', 3: '고급' }
const diffBadgeMap = { 1: 'bg-green-100 text-green-700', 2: 'bg-yellow-100 text-yellow-700', 3: 'bg-red-100 text-red-700' }
const diffName  = d => diffMap[d] ?? '-'
const diffBadge = d => diffBadgeMap[d] ?? ''
</script>
