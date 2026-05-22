<template>
  <div class="max-w-3xl mx-auto px-4 py-6">
    <h2 class="text-xl font-bold text-gray-800 mb-4">📖 나만의 단어장</h2>

    <LoadingSpinner v-if="loading" />

    <div v-else-if="list.length === 0" class="text-center py-20 text-gray-400">
      저장된 단어가 없습니다.<br/>퀴즈 중 모르는 용어를 클릭해 저장하세요.
    </div>

    <!-- 카드 그리드 -->
    <div v-else class="grid grid-cols-1 sm:grid-cols-2 gap-3">
      <div v-for="item in list" :key="item.userVocabId"
           @click="toggle(item)"
           class="bg-white rounded-xl border border-gray-100 shadow-sm p-4 cursor-pointer hover:shadow-md transition select-none">

        <!-- 앞면 -->
        <template v-if="flipped !== item.userVocabId">
          <div class="flex items-start justify-between">
            <div>
              <p class="font-bold text-gray-800">{{ item.term }}</p>
              <p class="text-xs text-gray-400 mt-0.5">{{ item.category }}</p>
            </div>
            <button @click.stop="deleteItem(item.userVocabId)"
                    class="text-gray-300 hover:text-red-400 transition text-sm ml-2">✕</button>
          </div>
          <p class="text-xs text-blue-400 mt-3">탭하여 뜻 보기 →</p>
        </template>

        <!-- 뒷면 (카드 플립) -->
        <template v-else>
          <p class="text-xs text-gray-400 mb-1">{{ item.term }}</p>
          <p class="text-sm text-gray-700 leading-relaxed">{{ item.definition }}</p>
          <p v-if="item.example" class="text-xs text-gray-400 mt-2 italic">예: {{ item.example }}</p>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { vocabularyApi } from '@/api/vocabulary'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const list    = ref([])
const loading = ref(true)
const flipped = ref(null)

onMounted(async () => {
  try {
    const { data } = await vocabularyApi.getMyList()
    list.value = data.data
  } finally {
    loading.value = false
  }
})

function toggle(item) {
  flipped.value = flipped.value === item.userVocabId ? null : item.userVocabId
}

async function deleteItem(userVocabId) {
  const prev = list.value
  list.value = list.value.filter(v => v.userVocabId !== userVocabId)
  try {
    await vocabularyApi.delete(userVocabId)
  } catch {
    list.value = prev
  }
}
</script>
