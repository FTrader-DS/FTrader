import api from './axios'

export const mypageApi = {
  getMe:          () => api.get('/user/me'),
  getWrongAnswers: () => api.get('/user/wrong-answers'),
  getSavedQuizzes: () => api.get('/user/saved-quiz'),
  getWeeklyRank:  () => api.get('/rank/weekly'),
}
