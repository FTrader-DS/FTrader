import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login',       name: 'login', component: () => import('@/views/LoginView.vue') },
  { path: '/auth/callback', name: 'auth-callback', component: () => import('@/views/OAuthCallbackView.vue') },

  { path: '/',                    name: 'home',         component: () => import('@/views/HomeView.vue'),                       meta: { auth: true } },
  { path: '/quiz/:quizId',        name: 'quiz',         component: () => import('@/views/QuizView.vue'),                       meta: { auth: true } },
  { path: '/quiz/set/:setId',     name: 'quiz-set',     component: () => import('@/views/QuizSetView.vue'),                    meta: { auth: true } },
  { path: '/result/:quizId',      name: 'result',       component: () => import('@/views/ResultView.vue'),                     meta: { auth: true } },
  { path: '/rank',                name: 'rank',         component: () => import('@/views/RankView.vue'),                       meta: { auth: true } },
  { path: '/mypage',              name: 'mypage',       component: () => import('@/views/mypage/MyPageView.vue'),              meta: { auth: true } },
  { path: '/mypage/vocabulary',   name: 'vocabulary',   component: () => import('@/views/mypage/VocabularyView.vue'),          meta: { auth: true } },
  { path: '/mypage/saved-quiz',   name: 'saved-quiz',   component: () => import('@/views/mypage/SavedQuizView.vue'),           meta: { auth: true } },
  { path: '/mypage/wrong-answers',name: 'wrong-answers',component: () => import('@/views/mypage/WrongAnswerView.vue'),         meta: { auth: true } },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ top: 0 }),
})

router.beforeEach((to) => {
  if (to.meta.auth && !localStorage.getItem('access_token')) {
    return '/login'
  }
})

export default router
