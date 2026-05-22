<template>
  <header class="sticky top-0 z-50 bg-white border-b border-gray-200 shadow-sm">
    <div class="max-w-3xl mx-auto px-4 h-14 flex items-center justify-between">
      <RouterLink to="/" class="text-blue-600 font-bold text-xl tracking-tight">FTrader</RouterLink>

      <nav class="flex items-center gap-4 text-sm font-medium text-gray-600">
        <RouterLink to="/" exact-active-class="text-blue-600" class="hover:text-blue-600 transition">홈</RouterLink>
        <RouterLink to="/rank" active-class="text-blue-600" class="hover:text-blue-600 transition">랭킹</RouterLink>
        <RouterLink to="/mypage" active-class="text-blue-600" class="hover:text-blue-600 transition">마이페이지</RouterLink>

        <button @click="handleLogout" class="ml-2 text-gray-400 hover:text-red-500 transition text-xs">
          로그아웃
        </button>
      </nav>
    </div>
  </header>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const auth   = useAuthStore()
const router = useRouter()

function handleLogout() {
  auth.logout()
  router.push('/login')
}
</script>
