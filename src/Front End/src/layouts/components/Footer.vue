<template>
  <div class="h-100 d-flex flex-wrap align-center justify-space-between">
    <!-- 👉 Footer: left content -->
    <span class="d-flex align-center">
      &copy;
      {{ new Date().getFullYear() }}
      Made With
      <VIcon
        icon="bx-heart"
        color="error"
        size="1.25rem"
        class="mx-1"
      />
      By <a
        href="https://demos.themeselection.com/sneat-vuetify-vuejs-admin-template/demo-1/dashboards/crm"
        target="_blank"
        rel="noopener noreferrer"
        class="sendText-primary ms-1"
      >TG - RedBack</a>
    </span>
  </div>
</template>
