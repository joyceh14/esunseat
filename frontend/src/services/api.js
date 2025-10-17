import axios from 'axios'
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '/api',
  timeout: 10000
})

export const fetchSeats      = () => api.get("/seats").then(r => r.data);
export const fetchEmployees = () => api.get("/employees").then(r => r.data);
export const assignSeat = (payload) => api.post("/seats/assign", payload).then(r => r.data);
export const releaseSeat  = (floorSeatSeq) => api.delete(`/seats/${floorSeatSeq}/release`);

export default api
