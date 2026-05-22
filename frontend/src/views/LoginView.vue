<template>
  <div class="min-h-screen bg-gradient-to-b from-blue-50 to-white flex flex-col items-center justify-center px-4">
    <div class="mb-8 text-center">
      <h1 class="text-4xl font-bold text-blue-600 mb-2">FTrader</h1>
      <p class="text-gray-500 text-sm">실제 뉴스로 배우는 주식 논리 트레이닝</p>
    </div>

    <div class="bg-white rounded-2xl shadow-lg p-8 w-full max-w-sm text-center">
      <!-- 인증 실패 에러 메시지 -->
      <div v-if="authError"
           class="bg-red-50 border border-red-200 text-red-600 text-xs rounded-xl px-4 py-3 mb-5">
        로그인에 실패했습니다. 다시 시도해주세요.
      </div>

      <p class="text-gray-600 mb-6 text-sm">카카오 계정으로 간편하게 시작하세요</p>
      <a href="/api/auth/kakao"
         class="flex items-center justify-center gap-2 w-full py-3 rounded-xl bg-yellow-300 hover:bg-yellow-400 transition font-semibold text-gray-800">
        <img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
             alt="카카오" class="w-5 h-5" />
        카카오로 로그인
      </a>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route     = useRoute()
const authError = computed(() => route.query.error === 'auth_failed')
</script>
