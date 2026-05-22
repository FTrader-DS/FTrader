<template>
  <span>
    <template v-for="(seg, i) in segments" :key="i">
      <!-- 하이라이트된 용어 -->
      <button
        v-if="seg.isTerm"
        type="button"
        @click.stop="save(seg)"
        :disabled="saving === seg.vocabId"
        :class="termClass(seg.vocabId)"
        class="inline underline underline-offset-2 font-medium transition-colors leading-[inherit]"
      >{{ seg.text }}</button>

      <!-- 일반 텍스트 -->
      <span v-else>{{ seg.text }}</span>
    </template>

    <!-- 저장 피드백 토스트 -->
    <Teleport to="body">
      <Transition
        enter-active-class="transition ease-out duration-200"
        enter-from-class="opacity-0 translate-y-2"
        leave-active-class="transition ease-in duration-150"
        leave-to-class="opacity-0 translate-y-2"
      >
        <div
          v-if="toast"
          class="fixed bottom-24 left-1/2 -translate-x-1/2 bg-gray-800 text-white text-xs px-4 py-2 rounded-full z-50 pointer-events-none whitespace-nowrap"
        >{{ toast }}</div>
      </Transition>
    </Teleport>
  </span>
</template>

<script setup>
import { ref, computed, onUnmounted } from 'vue'
import { vocabularyApi } from '@/api/vocabulary'

const props = defineProps({
  text:  { type: String, default: '' },
  // [{ vocabId: number, term: string }]
  terms: { type: Array,  default: () => [] },
})

const saving        = ref(null)
const savedVocabIds = ref(new Set())
const toast         = ref('')
let toastTimer      = null

// 텍스트를 일반 세그먼트 + 용어 세그먼트로 분리
// 긴 용어부터 처리해 짧은 용어가 긴 용어 안에서 중복 매칭되는 것을 방지
const segments = computed(() => {
  if (!props.terms?.length || !props.text) return [{ text: props.text, isTerm: false }]

  const sorted = [...props.terms].sort((a, b) => b.term.length - a.term.length)
  let result   = [{ text: props.text, isTerm: false, vocabId: null }]

  for (const termRef of sorted) {
    const next = []
    for (const seg of result) {
      if (seg.isTerm) { next.push(seg); continue }

      const parts = seg.text.split(termRef.term)
      if (parts.length === 1) { next.push(seg); continue }

      for (let i = 0; i < parts.length; i++) {
        if (parts[i]) next.push({ text: parts[i], isTerm: false, vocabId: null })
        if (i < parts.length - 1)
          next.push({ text: termRef.term, isTerm: true, vocabId: termRef.vocabId })
      }
    }
    result = next
  }

  return result
})

async function save(seg) {
  if (saving.value === seg.vocabId) return

  if (savedVocabIds.value.has(seg.vocabId)) {
    showToast('이미 저장된 단어입니다')
    return
  }

  saving.value = seg.vocabId
  try {
    await vocabularyApi.save(seg.vocabId)
    savedVocabIds.value = new Set([...savedVocabIds.value, seg.vocabId])
    showToast(`📖 '${seg.text}' 단어장에 저장됐어요`)
  } catch {
    showToast('저장에 실패했습니다')
  } finally {
    saving.value = null
  }
}

function termClass(vocabId) {
  if (savedVocabIds.value.has(vocabId)) return 'text-green-600 decoration-green-400 cursor-default'
  if (saving.value === vocabId)         return 'text-gray-400 decoration-gray-300 cursor-wait'
  return 'text-blue-600 decoration-blue-400 hover:text-blue-800 cursor-pointer'
}

function showToast(msg) {
  toast.value = msg
  clearTimeout(toastTimer)
  toastTimer = setTimeout(() => { toast.value = '' }, 2000)
}

onUnmounted(() => clearTimeout(toastTimer))
</script>
