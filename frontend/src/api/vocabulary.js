import api from './axios'

export const vocabularyApi = {
  getByTerm:  (term)        => api.get(`/vocabulary/${term}`),
  getMyList:  ()            => api.get('/user/vocabulary'),
  save:       (vocabId)     => api.post('/user/vocabulary', { vocabId }),
  delete:     (userVocabId) => api.delete(`/user/vocabulary/${userVocabId}`),
}
