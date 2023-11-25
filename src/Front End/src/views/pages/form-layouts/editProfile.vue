<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref, onMounted, watch } from 'vue';
import CryptoJS from 'crypto-js';

const router = useRouter();
const username = ref('')
const email = ref('')
const description = ref('')
const password = ref()

const showPassword = ref(false);

const userDescription = localStorage.getItem('userDescription')
const isAdmin = computed(() => userDescription === 'admin')

const form = ref('');
const errorMessage = ref('');

const fetchedPassword = ref(''); 

const fetchUserData = async (email) => {
  try {
    const response = await axios.get(`https://api.tg-redback.com:8080/admin_user/detail/${email}`);
    if (response.data) {
      username.value = response.data.data.name;
      password.value = response.data.data.password;
      fetchedPassword.value = response.data.data.password;  // Store the fetched password
      description.value = response.data.data.description;
    }
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
};

const props = defineProps({
  userEmail: ref('')
});

onMounted(async () => {
  const userEmail = router.currentRoute.value.params.userEmail;
  if (userEmail) {
    await fetchUserData(userEmail);
  }
});

watch(
  () => props.userEmail, 
  async (newEmail, oldEmail) => {
    if (newEmail && newEmail !== oldEmail) {
      await fetchUserData(newEmail);
    }
  }
);

function hashPassword(email, password) {
    const saltedPassword = email + password;
    const hashedPassword = CryptoJS.SHA256(saltedPassword).toString();
    return hashedPassword;
}

const editUser = async () => {
    let hashedPassword = password.value;  // Assume it's the same as the one fetched

    // Only hash if the password has been changed
    if (password.value !== fetchedPassword.value) {  // Assuming fetchedPassword is the password you fetched from the database
        hashedPassword = hashPassword(router.currentRoute.value.params.userEmail, password.value);
    }
    const userData = {
        name: username.value,
        email: router.currentRoute.value.params.userEmail,
        password: hashedPassword,
        description: description.value
    };
    try {
        if (userData.email) {
            const response = await axios.put(`https://api.tg-redback.com:8080/admin_user/edit/${userData.email}`, userData);
            if (response.data && response.data.msg == 'suc') {
                router.push({ name: 'topics' });
            } else {
                console.error('Failed to update user details:', response.data);
            }
        } 
    } catch (error) {
        console.error('Error handling user data:', error);
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

// Password validation rules
const passwordRules = [
  v => !!v || 'Password is required',
  v => v.length <= 100 || 'Password must not exceed 100 characters'
];

// Description validation rules
const descriptionRules = [
  v => !!v || 'Role is required',
  v => v.length <= 50 || 'Role must not exceed 50 characters'
];
</script>

<template>
  <VForm :ref="form" @submit.prevent="editUser">
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
              persistent-placeholder
              :rules="usernameRules"
              maxlength="15"
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
            <VTextField id="password" v-model="password" :type="showPassword ? 'password' : 'password'" persistent-placeholder :rules="passwordRules" maxlength="100"/>
        </VRow>
      </VCol>

      <VCol cols="12" v-if="isAdmin">
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
              persistent-placeholder
              :rules="descriptionRules"
              maxlength="50"
            />
          </VCol>
        </VRow>
      </VCol>

      <!-- 👉 submit button -->
      <VCol
        offset-md="3"
        cols="12"
        md="9"
        class="d-flex gap-4"
      >
        <VBtn type="submit">
          Submit
        </VBtn>
      </VCol>

      <!-- LOGIN ERROR -->
      <p class="error-sendText">{{ errorMessage }}</p>
    </VRow>
  </VForm>
</template>

<style scoped>
.error-sendText {
    color: red;
    margin-top: 10px;
}
</style>
