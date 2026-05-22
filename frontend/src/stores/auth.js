import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '@/api/axios'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('access_token'))
  const isLoggedIn = computed(() => !!token.value)

  function setToken(newToken) {
    token.value = newToken
    localStorage.setItem('access_token', newToken)
  }

  async function fetchUser() {
    const { data } = await api.get('/user/me')
    user.value = data.data
  }

  function logout() {
    token.value = null
    user.value = null
    localStorage.removeItem('access_token')
  }

  return { user, token, isLoggedIn, setToken, fetchUser, logout }
})
