<template>
  <div class="min-h-screen flex items-center justify-center">
    <LoadingSpinner />
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const route  = useRoute()
const router = useRouter()
const auth   = useAuthStore()

onMounted(() => {
  const token = route.query.token
  if (!token) { router.push('/login'); return }

  auth.setToken(token)
  router.push('/')
})
</script>
