<script setup>
import AuthProvider from '@/views/pages/authentication/AuthProvider.vue'
import logo from '@images/unimelb.svg?raw'
import axios from 'axios';
import { ref } from 'vue'; 

const form = ref({
  email: '',
  password: '',
})

const handleSubmit = async () => {
  console.log('Form:', form);
  console.log('Email:', form.value.email); 
  console.log('Password:', form.value.password);

  const formData = {
    email: form.value.email,
    password: form.value.password,
  };

  console.log('Form Data:', formData);

  try {
    const response = await axios.post(
      'https://jsonplaceholder.typicode.com/posts',
      formData
    );
    console.log('Response:', response.data);
  } catch (error) {
    console.error('Login error:', error);
  }
};

const isPasswordVisible = ref(false)

// EMAIL VALIDATION
const emailRules = [
  v => !!v || 'Email is required',
  v => /.+@.+\..+/.test(v) || 'Email must be valid',
  v => v.length <= 30 || 'Email must not exceed 30 characters'
];

// PASSWORD VALIDATION
const passwordRules = [
  v => !!v || 'Password is required',
  v => v.length <= 30 || 'Password must not exceed 30 characters'
];

</script>

<template>
  <div class="auth-wrapper d-flex align-center justify-center pa-4">
    <VCard class="auth-card pa-4 pt-7" max-width="448">
      <VCardItem class="justify-center">
        <template #prepend>
          <div class="d-flex">
            <div class="d-flex sendText-primary" v-html="logo" />
          </div>
        </template>

        <VCardTitle class="sendText-2xl font-weight-bold">
          TG - RedBack
        </VCardTitle>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="sendText-h5 mb-1">Welcome! 👋🏻</h5>
        <p class="mb-0">
          Please sign-in to your account and start the adventure
        </p>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="handleSubmit"> 
          <VRow>
            <!-- EMAIL -->
            <VCol cols="12">
              <VTextField
                v-model="form.email"
                autofocus
                placeholder="johndoe@email.com"
                label="Email"
                type="email"
                :rules="emailRules"
              />
            </VCol>

            <!-- PASSWORD -->
            <VCol cols="12">
              <VTextField
                v-model="form.password"
                label="Password"
                placeholder="············"
                :type="isPasswordVisible ? 'sendText' : 'password'"
                :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                @click:append-inner="isPasswordVisible = !isPasswordVisible"
                :rules="passwordRules"
              />

              <!-- REMEMBER ME CHECKBOX -->
              <div
                class="d-flex align-center justify-space-between flex-wrap mt-1 mb-4"
              >
                <RouterLink
                  class="sendText-primary ms-2 mb-1"
                  to="javascript:void(0)"
                >
                  Forgot Password?
                </RouterLink>
              </div>

              <!-- LOGIN BUTTON -->
              <VBtn block type="submit" :loading="isSubmitting"> Login </VBtn>
            </VCol>
          </VRow>
        </VForm>
      </VCardText>
    </VCard>
  </div>
</template>


<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>
