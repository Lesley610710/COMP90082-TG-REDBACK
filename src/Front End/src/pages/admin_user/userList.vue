<script setup>
import DemoSimpleTableBasics from '@/views/pages/tables/DemoSimpleTableBasics.vue'
import DemoSimpleTableDensity from '@/views/pages/tables/DemoSimpleTableDensity.vue'
import DemoSimpleTableFixedHeader from '@/views/pages/tables/DemoSimpleTableFixedHeader.vue'
import DemoSimpleTableHeight from '@/views/pages/tables/DemoSimpleTableHeight.vue'
import DemoSimpleTableTheme from '@/views/pages/tables/DemoSimpleTableTheme.vue'
import users from '@/views/pages/tables/users.vue'
</script>

<template>
  <VRow>
    <VCol cols="12">
      <VCard title="Users">
        <users />
      </VCard>
    </VCol>
  </VRow>

</template>
