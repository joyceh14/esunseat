<script setup>
import { ref, onMounted } from "vue";
import { fetchSeats, fetchEmployees, assignSeat, releaseSeat } from "./services/api";
import SeatGrid from "./components/SeatGrid.vue";
import EmployeeSelect from "./components/EmployeeSelect.vue";

const seats = ref([]);
const selected = ref(null);
const employees = ref([]);
const selectedEmpId = ref("");

const loading = ref(false);
const error = ref("");
const info = ref("");

// Load Seats
const loadSeats = async () => {
  loading.value = true; error.value = "";
  try { seats.value = await fetchSeats(); }
  catch (e) { error.value = e?.response?.data?.message || e.message || "載入失敗"; }
  finally { loading.value = false; }
};

// Load Employees
const loadEmployees = async () => {
  try { employees.value = await fetchEmployees(); }
  catch (e) { error.value = e?.response?.data?.message || e.message || "載入員工失敗"; }
};

onMounted(async () => {
  await Promise.all([loadSeats(), loadEmployees()]);
});

const onSelectSeat = (s) => { selected.value = s; info.value = ""; };

// Assign Seat
const doAssign = async () => {
  if (!selected.value) { info.value = "請先在座位圖選一個座位"; return; }
  if (!selectedEmpId.value) { info.value = "請先從下拉選單選員工"; return; }

  loading.value = true; error.value = ""; info.value = "";
  try {
    await assignSeat({
      empId: selectedEmpId.value,
      floorNo: selected.value.floorNo,
      seatNo: selected.value.seatNo
    });
    await loadSeats();
    info.value = `已指派：${selectedEmpId.value} → ${selected.value.floorNo}樓/座位${selected.value.seatNo}`;
  } catch (e) {
    error.value = e?.response?.data?.message || e.message || "指派失敗";
  } finally {
    loading.value = false;
  }
};

// Release Seat (Clean occupied to be empty seat) 
const doRelease = async () => {
  if (!selected.value) { info.value = "請先選一個座位"; return; }
  if (!selected.value.empId) { info.value = "此座位本來就是空位"; return; }

  if (!confirm(`確定釋放 ${selected.value.floorNo}樓/座位${selected.value.seatNo}（員編：${selected.value.empId}）嗎？`)) return;

  loading.value = true; error.value = ""; info.value = "";
  try {
    await releaseSeat(selected.value.floorSeatSeq);  // 呼叫後端 DELETE /api/seats/{id}/release
    await loadSeats();                                // 重新載入，畫面變灰
    // 更新目前選取（避免殘留舊 empId）
    const seq = selected.value.floorSeatSeq;
    selected.value = seats.value.find(s => s.floorSeatSeq === seq) || null;
    info.value = "已釋放座位（清為空位）";
  } catch (e) {
    error.value = e?.response?.data?.message || e.message || "釋放失敗";
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <main>
    <h1>座位總覽</h1>

    <div class="toolbar">
      <button @click="loadSeats" :disabled="loading">
        {{ loading ? '載入中...' : '重新載入座位' }}
      </button>

      <EmployeeSelect v-model="selectedEmpId" :employees="employees" />
      <button class="primary" :disabled="loading || !selected || !selectedEmpId" @click="doAssign">
        送出
      </button>

      <!-- 釋放為空位（只有選到且為佔用才可按） -->
      <button class="danger" :disabled="loading || !selected || !selected.empId" @click="doRelease">
        釋放為空位
      </button>
    </div>

    <p v-if="error" class="err">{{ error }}</p>
    <p v-if="info" class="ok">{{ info }}</p>

    <SeatGrid
      :seats="seats"
      :selectedSeq="selected?.floorSeatSeq || null"
      :onSelect="onSelectSeat"
    />

    <div v-if="selected" class="hint">
      目前選擇：{{ selected.floorNo }}樓 / 座位{{ selected.seatNo }}
      <span v-if="selected.empId">（已佔用：{{ selected.empId }}）</span>
      <span v-else>（空位）</span>
    </div>
  </main>
</template>

<style scoped>
main { max-width: 1000px; margin: 32px auto; padding: 0 16px; }
.toolbar { display:flex; gap:10px; align-items:center; flex-wrap:wrap; margin-bottom:10px; }
button { padding: 8px 12px; border: 1px solid #ddd; border-radius: 8px; background: #fff; cursor: pointer; }
button.primary { background:#1e40af; color:#fff; border-color:#1e40af; }
button.danger  { background:#b91c1c; color:#fff; border-color:#b91c1c; }
.err { color:#dc2626; margin:6px 0; }
.ok  { color:#16a34a; margin:6px 0; }
.hint { margin-top:10px; opacity:.85; }
</style>
