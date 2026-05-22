<template>
  <AppHeader v-if="showHeader" />
  <main class="min-h-screen bg-gray-50">
    <RouterView />
  </main>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import AppHeader from '@/components/common/AppHeader.vue'

const route = useRoute()
const showHeader = computed(() => route.name !== 'login' && route.name !== 'auth-callback')
</script>
