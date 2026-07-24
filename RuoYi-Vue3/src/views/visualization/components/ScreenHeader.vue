<template>
  <div class="screen-header">
    <div class="header-left">
      <span class="header-sub">{{ sub }}</span>
    </div>
    <div class="header-center">
      <div class="title-decoration left"></div>
      <h1 class="screen-title">{{ title }}</h1>
      <div class="title-decoration right"></div>
    </div>
    <div class="header-right">
      <span class="header-time">{{ currentTime }}</span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  sub: { type: String, required: true },
  title: { type: String, required: true },
  currentTime: { type: String, default: '' }
})
</script>
