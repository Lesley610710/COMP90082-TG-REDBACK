<script setup>
import { useTheme } from 'vuetify'
import VerticalNavSectionTitle from '@/@layouts/components/VerticalNavSectionTitle.vue'
import upgradeBannerDark from '@images/pro/linguistic.jpg'
import upgradeBannerLight from '@images/pro/linguistic.jpg'
import VerticalNavLayout from '@layouts/components/VerticalNavLayout.vue'
import VerticalNavLink from '@layouts/components/VerticalNavLink.vue'
import { ref, onMounted } from 'vue';

// Components
import Footer from '@/layouts/components/Footer.vue'
import NavbarThemeSwitcher from '@/layouts/components/NavbarThemeSwitcher.vue'
import UserProfile from '@/layouts/components/UserProfile.vue'

const vuetifyTheme = useTheme()

const upgradeBanner = computed(() => {
  return vuetifyTheme.global.name.value === 'light' ? upgradeBannerLight : upgradeBannerDark
})

const userDescription = localStorage.getItem('userDescription')

const isAdmin = computed(() => userDescription === 'admin');

</script>

<template>
  <VerticalNavLayout>
    <!-- 👉 navbar -->
    <template #navbar="{ toggleVerticalOverlayNavActive }">
      <div class="d-flex h-100 align-center">
        <!-- 👉 Vertical nav toggle in overlay mode -->
        <IconBtn
          class="ms-n3 d-lg-none"
          @click="toggleVerticalOverlayNavActive(true)"
        >
          <VIcon icon="bx-menu" />
        </IconBtn>

        <VSpacer />

        <IconBtn
          class="me-2"
          href="https://github.com/COMP90082-2023-SM2/TG-Redback"
          target="_blank"
          rel="noopener noreferrer"
        >
          <VIcon icon="bxl-github" />
        </IconBtn>

        <UserProfile />
      </div>
    </template>

    <template #vertical-nav-content>
      <VerticalNavLink
        :item="{
          title: 'Topics',
          icon: 'bx-library',
          to: '/user/topics',
        }"
      />
      <VerticalNavLink
        :item="{
          title: 'Texts',
          icon: 'bx-text',
          to: '/user/texts',
        }"
      />

      <!-- 👉 Admin -->
      <VerticalNavSectionTitle
        v-if="isAdmin"
        :item="{
          heading: 'Admin',
        }"
      />
      <VerticalNavLink
        v-if="isAdmin"
        :item="{
          title: 'Users',
          icon: 'bxs-user-detail',
          to: '/admin_user/userList',
        }"
      />
      <VerticalNavLink
        v-if="isAdmin"
        :item="{
          title: 'Register',
          icon: 'bxs-user-plus',
          to: '/admin_user/add',
        }"
      />
    </template>

    <template #after-vertical-nav-items>
      <!-- 👉 illustration -->
      <a
        href="https://arts.unimelb.edu.au/language-testing-research-centre"
        target="_blank"
        rel="noopener noreferrer"
        style="margin-left: 7px"
      >
        <img
          :src="upgradeBanner"
          alt="upgrade-banner"
          transition="scale-transition"
          class="upgrade-banner mx-auto"
          style="max-width: 230px"
        />
      </a>
    </template>

    <!-- 👉 Pages -->
    <slot />

    <!-- 👉 Footer -->
    <template #footer>
      <Footer />
    </template>
  </VerticalNavLayout>
</template>

<style lang="scss" scoped>
.meta-key {
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  border-radius: 6px;
  block-size: 1.5625rem;
  line-height: 1.3125rem;
  padding-block: 0.125rem;
  padding-inline: 0.25rem;
}
</style>
