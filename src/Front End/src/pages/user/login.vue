<script setup>
import AuthProvider from '@/views/pages/authentication/AuthProvider.vue'
import logo from '@images/unimelb.svg?raw'
import axios from 'axios';
import { ref } from 'vue'; 
import { useRouter } from 'vue-router';
import { onMounted } from 'vue';
import LoginPromptDialog from '@/layouts/components/LoginPromptDialog.vue';
import bcrypt from 'bcryptjs';
import CryptoJS from 'crypto-js';

const router = useRouter();

const loginError = ref('');

const form = ref({
  email: '',
  password: '',
})

function hashPassword(email, password) {
    const saltedPassword = email + password;
    const hashedPassword = CryptoJS.SHA256(saltedPassword).toString();
    return hashedPassword;
}

const handleSubmit = async () => {
  
  const hashedPassword = hashPassword(form.value.email, form.value.password);
  const formData = {
      email: form.value.email,
      password: hashedPassword,
  };

  try {
    const response = await axios.post(
      'https://api.tg-redback.com:8080/user/login',
      formData
    );

    if (response.data.msg === 'suc') {
      localStorage.setItem('authToken', response.data.token);
      localStorage.setItem('userName', response.data.data.name);
      localStorage.setItem('userDescription', response.data.data.description);
      localStorage.setItem('userEmail', formData.email);
      const currentTime = new Date().getTime();
      localStorage.setItem('loginTimestamp', currentTime);
      router.push('/user/topics');
    } else {
      loginError.value = 'Login failed. Please check your credentials.';
    }
  } catch (error) {
    console.error('Login error:', error);
    loginError.value = 'Login failed. Please check your credentials.';
  }
};

// Check on page load if the user is already authenticated
onMounted(() => {
  const token = localStorage.getItem('authToken');
  const loginTimestamp = localStorage.getItem('loginTimestamp');

  if (token && loginTimestamp) {
    const currentTime = new Date().getTime();
    const timeDifference = currentTime - loginTimestamp;

    // Check if more than 30 minutes (1800000 milliseconds) have passed
    if (timeDifference > 1800000) {
      // If more than 30 minutes have passed, remove the token to log the user out
      localStorage.removeItem('authToken');
      router.push('/login');
    } else {
      router.push('/user/topics'); // Redirect to topics if token exists
    }
  }
});

const isPasswordVisible = ref(false)

// EMAIL VALIDATION
const emailRules = [
  v => !!v || 'Email is required',
  v => /.+@.+\..+/.test(v) || 'Email must be valid',
  v => v.length <= 50 || 'Email must not exceed 50 characters'
];
 
// PASSWORD VALIDATION
const passwordRules = [
  v => !!v || 'Password is required',
  v => v.length <= 100 || 'Password must not exceed 50 characters'
];

</script>
<script>
export default {
  components: {
    LoginPromptDialog
  },
  data() {
    return {
      showDialog: false
    }
  },
  mounted() {
    // Show dialog for 3 seconds if not authenticated
    if (!localStorage.getItem('authToken')) {
      this.showDialog = true;
      setTimeout(() => {
        this.showDialog = false;
      }, 3000);
    }
  }
}
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
                maxlength="50"
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
                maxlength="100"
              />

              <!-- REMEMBER ME CHECKBOX -->
              <div
                class="d-flex align-center justify-space-between flex-wrap mt-1 mb-4"
              >
              </div>

              <!-- LOGIN BUTTON -->
              <VBtn block type="submit"> Login </VBtn>

              <!-- LOGIN ERROR -->
              <p v-if="loginError" class="sendText-danger">{{ loginError }}</p>
            </VCol>
          </VRow>
        </VForm>
      </VCardText>
    </VCard>
  </div>

  <LoginPromptDialog :isVisible="showDialog" />
</template>


<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>
