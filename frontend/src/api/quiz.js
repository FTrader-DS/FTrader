import api from './axios'

export const quizApi = {
  getToday:   ()                     => api.get('/quiz/today'),
  getQuiz:    (quizId)               => api.get(`/quiz/${quizId}`),
  getSet:     (setId)                => api.get(`/quiz/set/${setId}`),
  submit:     (quizId, choiceId)     => api.post(`/quiz/${quizId}/submit`, { choiceId }),
  bookmark:   (quizId)               => api.post(`/quiz/${quizId}/bookmark`),
}
