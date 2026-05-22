<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <LoadingSpinner v-if="loading" />

    <template v-else-if="quizSet">
      <!-- 뉴스 카드 (고정) -->
      <div class="bg-blue-50 border border-blue-200 rounded-xl p-4 mb-6">
        <p class="text-xs text-blue-500 font-semibold mb-1">📰 뉴스 / 공시</p>
        <p class="text-sm text-gray-700 leading-relaxed">
          <TermSave :text="quizSet.newsContent" :terms="quizSet.vocabTerms" />
        </p>
      </div>

      <!-- 진행 표시 -->
      <div class="flex items-center gap-2 mb-5">
        <span v-for="(_, i) in quizSet.quizzes" :key="i"
              :class="i === currentIndex ? 'bg-blue-500' : i < currentIndex ? 'bg-green-400' : 'bg-gray-200'"
              class="h-2 flex-1 rounded-full transition-all" />
      </div>

      <!-- 현재 문제 -->
      <template v-if="currentIndex < quizSet.quizzes.length">
        <p class="text-xs text-gray-400 mb-2">Q{{ currentIndex + 1 }} / {{ quizSet.quizzes.length }}</p>
        <p class="text-base font-semibold text-gray-800 mb-5 leading-snug">{{ currentQuiz.question }}</p>

        <div class="space-y-3">
          <button v-for="c in currentQuiz.choices" :key="c.choiceId"
                  @click="select(c.choiceId)"
                  :disabled="stepSubmitted"
                  :class="stepChoiceClass(c.choiceId)"
                  class="w-full text-left px-4 py-3 rounded-xl border text-sm font-medium transition">
            <span class="mr-2 text-gray-400">{{ c.choiceNo }}.</span>{{ c.content }}
          </button>
        </div>

        <!-- 해설 + 다음 버튼 -->
        <div v-if="stepResult" class="mt-5">
          <div :class="stepResult.correct ? 'bg-green-50 border-green-300' : 'bg-red-50 border-red-300'"
               class="border rounded-xl p-4 mb-4">
            <p class="font-semibold mb-1" :class="stepResult.correct ? 'text-green-700' : 'text-red-600'">
              {{ stepResult.correct ? '✅ 정답!' : '❌ 오답' }}
            </p>
            <p class="text-sm text-gray-700">{{ currentQuiz.explanation }}</p>
          </div>
          <button @click="next"
                  class="w-full py-3 rounded-xl bg-blue-600 text-white font-semibold hover:bg-blue-700 transition">
            {{ currentIndex + 1 < quizSet.quizzes.length ? '다음 문제 →' : '세트 완료 🎉' }}
          </button>
        </div>
      </template>

      <!-- 세트 완료 화면 -->
      <div v-else class="text-center py-16">
        <p class="text-3xl mb-3">🎉</p>
        <p class="text-lg font-bold text-gray-800 mb-1">연쇄 문제 완료!</p>
        <p class="text-sm text-gray-500 mb-6">총 {{ results.filter(r => r.correct).length }} / {{ quizSet.quizzes.length }} 정답</p>
        <button @click="$router.push('/')" class="px-6 py-2 rounded-xl bg-blue-600 text-white font-semibold hover:bg-blue-700 transition">
          홈으로
        </button>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { quizApi } from '@/api/quiz'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import TermSave from '@/components/quiz/TermSave.vue'

const route  = useRoute()
const router = useRouter()

const quizSet      = ref(null)
const loading      = ref(true)
const currentIndex = ref(0)
const stepSubmitted = ref(false)
const stepResult    = ref(null)
const selectedId    = ref(null)
const results       = ref([])

const currentQuiz = computed(() => quizSet.value?.quizzes[currentIndex.value])

onMounted(async () => {
  try {
    const { data } = await quizApi.getSet(route.params.setId)
    quizSet.value = data.data
  } finally {
    loading.value = false
  }
})

async function select(choiceId) {
  if (stepSubmitted.value) return
  selectedId.value    = choiceId
  stepSubmitted.value = true

  try {
    const { data } = await quizApi.submit(currentQuiz.value.quizId, choiceId)
    stepResult.value = data.data
    results.value.push(data.data)
  } catch {
    stepSubmitted.value = false
    selectedId.value    = null
  }
}

function next() {
  currentIndex.value++
  stepSubmitted.value = false
  stepResult.value    = null
  selectedId.value    = null
}

function stepChoiceClass(choiceId) {
  if (!stepSubmitted.value)
    return choiceId === selectedId.value
      ? 'bg-blue-50 border-blue-400 text-blue-700'
      : 'bg-white border-gray-200 hover:bg-blue-50 hover:border-blue-300'
  if (!stepResult.value) return 'bg-gray-50 border-gray-200 text-gray-400'
  if (choiceId === stepResult.value.correctChoiceId) return 'bg-green-50 border-green-400 text-green-700'
  if (choiceId === selectedId.value)                 return 'bg-red-50 border-red-400 text-red-600'
  return 'bg-gray-50 border-gray-200 text-gray-400'
}
</script>
