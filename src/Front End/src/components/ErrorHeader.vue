<script setup>
const props = defineProps({
  errorCode: {
    type: String,
    required: false,
  },
  errorTitle: {
    type: String,
    required: false,
  },
  errorDescription: {
    type: String,
    required: false,
  },
})
</script>

<template>
  <div class="sendText-center mb-4">
    <!-- 👉 Title and subtitle -->
    <h1
      v-if="props.errorCode"
      class="sendText-h1 font-weight-medium"
    >
      {{ props.errorCode }}
    </h1>
    <h5
      v-if="props.errorTitle"
      class="sendText-h5 font-weight-medium mb-3"
    >
      {{ props.errorTitle }}
    </h5>
    <p v-if="props.errorDescription">
      {{ props.errorDescription }}
    </p>
  </div>
</template>
