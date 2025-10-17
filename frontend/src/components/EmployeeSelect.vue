<script setup>
const props = defineProps({
  employees: { type: Array, default: () => [] },
  modelValue: { type: String, default: "" }
});
const emit = defineEmits(["update:modelValue"]);
const onChange = (e) => emit("update:modelValue", e.target.value);
</script>

<template>
  <select :value="modelValue" @change="onChange">
    <option disabled value="">請選擇員工</option>
    <option v-for="e in employees" :key="e.empId" :value="e.empId">
      {{ e.empId }} - {{ e.name }}
    </option>
  </select>
</template>

<style scoped>
select { padding: 8px 10px; border: 1px solid #ddd; border-radius: 8px; }
</style>
