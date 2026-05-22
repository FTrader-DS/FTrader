import { defineStore } from 'pinia'
import { ref } from 'vue'

// 퀴즈 제출 결과를 QuizView → ResultView로 전달하는 임시 저장소
export const useQuizStore = defineStore('quiz', () => {
  const lastResult = ref(null)   // SubmitResponse
  const lastQuiz   = ref(null)   // QuizDetailResponse

  function setResult(quiz, result) {
    lastQuiz.value   = quiz
    lastResult.value = result
  }

  function clear() {
    lastQuiz.value   = null
    lastResult.value = null
  }

  return { lastResult, lastQuiz, setResult, clear }
})
