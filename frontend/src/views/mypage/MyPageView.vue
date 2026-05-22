<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <LoadingSpinner v-if="loading" />

    <template v-else-if="user">
      <!-- 프로필 카드 -->
      <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 mb-5 flex items-center gap-4">
        <img v-if="user.profileImg" :src="user.profileImg" class="w-14 h-14 rounded-full object-cover" />
        <div v-else class="w-14 h-14 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-bold text-xl">
          {{ user.nickname?.[0] }}
        </div>
        <div class="flex-1">
          <p class="font-bold text-gray-800 text-lg">{{ user.nickname }}</p>
          <p class="text-sm text-gray-400">{{ user.levelName }} · {{ user.points.toLocaleString() }}점</p>
        </div>
        <div class="text-right">
          <p class="text-2xl font-bold text-blue-600">{{ user.accuracy }}%</p>
          <p class="text-xs text-gray-400">정답률</p>
        </div>
      </div>

      <!-- 스트릭 / 풀이수 -->
      <div class="grid grid-cols-2 gap-3 mb-5">
        <div class="bg-orange-50 rounded-xl p-4 text-center">
          <p class="text-2xl font-bold text-orange-500">🔥 {{ user.streak }}</p>
          <p class="text-xs text-gray-500 mt-1">연속 출석일</p>
        </div>
        <div class="bg-blue-50 rounded-xl p-4 text-center">
          <p class="text-2xl font-bold text-blue-600">{{ user.totalSolved }}</p>
          <p class="text-xs text-gray-500 mt-1">총 풀이 수</p>
        </div>
      </div>

      <!-- 메뉴 -->
      <div class="space-y-2">
        <RouterLink v-for="menu in menus" :key="menu.to" :to="menu.to"
                    class="flex items-center justify-between bg-white rounded-xl border border-gray-100 shadow-sm px-4 py-3 hover:border-blue-200 transition">
          <span class="text-sm font-medium text-gray-700">{{ menu.label }}</span>
          <span class="text-gray-400 text-lg">›</span>
        </RouterLink>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mypageApi } from '@/api/mypage'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const user    = ref(null)
const loading = ref(true)

const menus = [
  { to: '/mypage/vocabulary',    label: '📖 나만의 단어장' },
  { to: '/mypage/saved-quiz',    label: '🔖 나만의 문제집' },
  { to: '/mypage/wrong-answers', label: '❌ 오답노트' },
  { to: '/rank',                 label: '📊 주간 랭킹' },
]

onMounted(async () => {
  try {
    const { data } = await mypageApi.getMe()
    user.value = data.data
  } finally {
    loading.value = false
  }
})
</script>
