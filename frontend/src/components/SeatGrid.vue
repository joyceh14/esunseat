<script setup>
import { computed } from "vue";

const props = defineProps({
  seats: { type: Array, default: () => [] },
  selectedSeq: { type: Number, default: null },  // 可選：綠色選取用
  onSelect: { type: Function, default: () => {} } // 點格子事件
});

// 依樓層分組並排序每層的 seatNo
const byFloor = computed(() => {
  const map = new Map();
  for (const s of props.seats) {
    if (!map.has(s.floorNo)) map.set(s.floorNo, []);
    map.get(s.floorNo).push(s);
  }
  for (const arr of map.values()) arr.sort((a,b)=>a.seatNo-b.seatNo);
  return [...map.entries()].sort((a,b)=>a[0]-b[0]); // [[floorNo, SeatDto[]], ...]
});

const seatClass = (s) => {
  if (props.selectedSeq === s.floorSeatSeq) return "seat chosen"; // 綠
  if (!s.empId) return "seat empty";  // 灰
  return "seat occupied";             // 紅
};

const seatLabel = (s) =>
  `${s.floorNo}樓：座位${s.seatNo}` + (s.empId ? `（員編:${s.empId}）` : "");
</script>

<template>
  <div class="legend">
    <span class="chip empty"></span> 空位
    <span class="chip occupied"></span> 已佔用
    <span class="chip chosen"></span> 請選擇
  </div>

  <div class="floors">
    <div v-for="[floor, arr] in byFloor" :key="floor" class="floor">
      <div class="floor-title">{{ floor }} 樓</div>

      <!-- 固定 4 欄的排版（和題目畫面一致） -->
      <div class="grid">
        <button
          v-for="s in arr"
          :key="s.floorSeatSeq"
          :class="seatClass(s)"
          @click="props.onSelect(s)"
          :title="seatLabel(s)"
        >
          <div class="row1">{{ s.floorNo }}樓：座位{{ s.seatNo }}</div>
          <div class="row2" v-if="s.empId">【員編:{{ s.empId }}】</div>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.legend { display:flex; gap:12px; align-items:center; margin-bottom:10px; }
.chip { width:16px; height:16px; border-radius:4px; display:inline-block; margin-right:6px; border:1px solid #cbd5e1; }
.chip.empty { background:#eeeeee; }
.chip.occupied { background:#e53935; }
.chip.chosen { background:#7ae582; }

.floors { display: grid; gap: 18px; }
.floor-title { font-weight: 700; margin-bottom: 8px; }

.grid { display: grid; grid-template-columns: repeat(4, minmax(160px, 1fr)); gap: 10px; }

.seat { border: 1px solid #d1d5db; border-radius: 10px; padding: 10px 12px;
        text-align: center; cursor: pointer; }
.seat.empty { background: #eeeeee; color:#222; }
.seat.occupied { background: #e53935; color: white; }
.seat.chosen { background: #7ae582; color: #083e16; }
.row1 { font-weight: 600; }
.row2 { margin-top: 2px; font-size: 12px; }
</style>
