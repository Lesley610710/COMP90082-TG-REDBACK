<template>
  <div v-if="isVisible" class="login-prompt">
    Please log in first!
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
.login-prompt {
  position: fixed;
  top: 20px;  
  left: 50%; 
  transform: translateX(-50%); 
  padding: 10px 20px;
  background-color: #ff9999;
  border-radius: 5px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}
</style>
