<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';
import bcrypt from 'bcryptjs';
import CryptoJS from 'crypto-js';

const router = useRouter();
const username = ref('')
const email = ref('')
const description = ref('')
const password = ref()

const showPassword = ref(false);

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const generatePassword = () => {
  const randomPassword = generateRandomPassword();
  password.value = randomPassword;
};

const generateRandomPassword = () => {
  const allCharacters =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?';
  let password = '';
  for (let i = 0; i < 12; i++) {
    const randomIndex = Math.floor(Math.random() * allCharacters.length);
    password += allCharacters[randomIndex];
  }
  return password;
};

function hashPassword(email, password) {
    const saltedPassword = email + password;
    const hashedPassword = CryptoJS.SHA256(saltedPassword).toString();
    return hashedPassword;
}
const errorMessage = ref('')

const registerUser = async () => {
    // Check if email already exists
    const response = await axios.get(`https://api.tg-redback.com:8080/admin_user/detail/${email.value}`);
    if (response.data && response.data.data) {
        errorMessage.value = 'Email already exists.';  // Display the error message
        return;
    }

    const hashedPassword = hashPassword(email.value, password.value);
    const userData = {
        name: username.value,
        email: email.value,
        password: hashedPassword,
        description: description.value
    };
    try {
        const response = await axios.post('https://api.tg-redback.com:8080/admin_user/add', userData);
        if (response.data && response.data.msg == 'suc') {
            router.push({ name: 'userList' });
        } else {
            console.error('Failed to add user:', response.data);
        }
    } catch (error) {
        console.error('Error registering user:', error);
    }
};


// Username validation rules
const usernameRules = [
  v => !!v || 'Username is required',
  v => /^[a-zA-Z0-9._-]*$/.test(v) || 'Invalid username. Use alphanumeric characters, dot, underscore, or hyphen.',
  v => !/^[._-]/.test(v) || 'Username cannot start with a dot, underscore or hyphen',
  v => !/[._-]$/.test(v) || 'Username cannot end with a dot, underscore or hyphen',
  v => v.length <= 15 || 'Username must not exceed 15 characters'
];

// Email validation rules
const emailRules = [
  v => !!v || 'Email is required',
  v => /.+@.+\..+/.test(v) || 'Email must be valid',
  v => v.length <= 50 || 'Email must not exceed 50 characters'
];

// Password validation rules
const passwordRules = [
  v => !!v || 'Password is required',
  v => v.length <= 50 || 'Password must not exceed 50 characters'
];

// Description validation rules
const descriptionRules = [
  v => !!v || 'Role is required',
  v => v.length <= 50 || 'Role must not exceed 50 characters'
];

</script>

<template>
  <VForm @submit.prevent="registerUser">
    <VRow>
      <VCol cols="12">
        <VRow no-gutters>

          <!-- 👉 Username -->
          <VCol
            cols="12"
            md="3"
          >
            <label for="username">Username</label>
          </VCol>
          <VCol
            cols="12"
            md="9"
          >
            <VTextField
              id="username"
              v-model="username"
              placeholder="John"
              persistent-placeholder
              :rules="usernameRules"
              maxlength="15"
            />
          </VCol>
        </VRow>
      </VCol>

      <VCol cols="12">
        <VRow no-gutters>
          <!-- 👉 Email -->
          <VCol
            cols="12"
            md="3"
          >
            <label for="email">Email</label>
          </VCol>
          <VCol
            cols="12"
            md="9"
          >
            <VTextField
              id="email"
              v-model="email"
              placeholder="johndoe@email.com"
              persistent-placeholder
              :rules="emailRules"
              maxlength="50"
            />
          </VCol>
        </VRow>
      </VCol>

      <VCol cols="12">
        <VRow no-gutters>
            <!-- 👉 Password -->
            <VCol cols="12" md="3">
            <label for="password">Password</label>
            </VCol>
            <VTextField id="password" v-model="password" :type="showPassword ? 'text' : 'password'" placeholder="············" persistent-placeholder :rules="passwordRules" maxlength="50"/>
            <v-icon @click="togglePasswordVisibility" class="mx-2 mt-4">
                {{ showPassword ? 'mdi-eye-off' : 'mdi-eye' }}
            </v-icon>
            <VBtn @click="generatePassword" color="primary" class="mx-2 mt-2">
                Generate Password
            </VBtn>
        </VRow>
      </VCol>

      <VCol cols="12">
        <VRow no-gutters>
          <!-- 👉 Description -->
          <VCol
            cols="12"
            md="3"
          >
            <label for="description">Role</label>
          </VCol>
          <VCol
            cols="12"
            md="9"
          >
            <VTextField
              id="description"
              v-model="description"
              placeholder="Please enter role"
              persistent-placeholder
              :rules="descriptionRules"
              maxlength="50"
            />
          </VCol>
        </VRow>
      </VCol>

      <!-- 👉 submit and reset button -->
      <VCol
        offset-md="3"
        cols="12"
        md="9"
        class="d-flex gap-4"
      >
        <VBtn type="submit">
          Submit
        </VBtn>
        <VBtn
          color="secondary"
          variant="tonal"
          type="reset"
        >
          Reset
        </VBtn>
      </VCol>
      <p class="error-text">{{ errorMessage }}</p>
    </VRow>
  </VForm>
</template>
