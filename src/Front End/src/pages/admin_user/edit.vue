<script setup>
import DemoFormLayoutHorizontalForm from '@/views/pages/form-layouts/DemoFormLayoutHorizontalForm.vue'
import DemoFormLayoutHorizontalFormWithIcons from '@/views/pages/form-layouts/DemoFormLayoutHorizontalFormWithIcons.vue'
import DemoFormLayoutMultipleColumn from '@/views/pages/form-layouts/DemoFormLayoutMultipleColumn.vue'
import DemoFormLayoutVerticalForm from '@/views/pages/form-layouts/DemoFormLayoutVerticalForm.vue'
import DemoFormLayoutVerticalFormWithIcons from '@/views/pages/form-layouts/DemoFormLayoutVerticalFormWithIcons.vue'
import edit from '@/views/pages/form-layouts/edit.vue'
</script>

<template>
  <div>
    <VRow>
      <VCol
        cols="12"
        md="6"
      >

        <!-- 👉 Horizontal Form with Icons -->
        <VCard title="Edit User">
          <VCardText>
            <edit />
          </VCardText>
        </VCard>
      </VCol>

    </VRow>
  </div>
</template>
