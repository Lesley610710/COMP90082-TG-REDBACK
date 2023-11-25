<script setup>
import axios from 'axios';
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const users = ref([]); 
const searchQuery = ref('');
const router = useRouter();
const loggedInUserEmail = ref('');

const fetchUsers = async () => {
  try {
    const response = await axios.get('https://api.tg-redback.com:8080/admin_user/userList');
    const rawData = response.data.data;
    users.value = Object.entries(rawData).map(([email, details]) => ({
      email: details.email,
      username: details.name, 
      description: details.description
    }));
  } catch (error) {
    console.error('Error fetching users:', error);
  }
};

onMounted(async () => {
  await fetchUsers();
  loggedInUserEmail.value = localStorage.getItem('userEmail'); 
});

const filteredUsers = computed(() => {
  if (!searchQuery.value) {
    return users.value;
  }
  const normalizedQuery = searchQuery.value.toLowerCase();
  return users.value.filter(user => 
    user.username.toLowerCase().includes(normalizedQuery)
  );
});

const deleteUser = async (userEmail) => {
    if (!userEmail) {
       console.error('User email is not valid:', userEmail);
       return;
    }
    const isConfirmed = window.confirm('Are you sure you want to delete this user?');
    if (!isConfirmed) {
      return; // If the user does not confirm, exit the function.
    }
    try {
        const response = await axios.delete(`https://api.tg-redback.com:8080/admin_user/delete/${userEmail}`);

        if (response.data && response.data.msg == 'suc') {
            fetchUsers();
        } else {
            console.error('Failed to delete user:', response.data);
        }
    } catch (error) {
        console.error('Error deleting user:', error);
    }
};

const editUser = (userEmail) => {
  router.push({
    name: 'editUser',
    params: { userEmail: userEmail }
  });
};
</script>

<template>
    <VCol
    cols="12"
    md="3"
    >
    <VTextField
        id="firstNameHorizontalIcons"
        v-model="searchQuery"
        prepend-inner-icon="bx-search"
        placeholder="Search Username"
        persistent-placeholder
    />
    </VCol>

  <VTable height="500">
    <thead>
      <tr>
        <th>
          Username
        </th>
        <th>
          Email
        </th>
        <th>
          Role
        </th>
        <th>
          Action
        </th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in filteredUsers" :key="item.username">
        <td>
          {{ item.username }}
        </td>
        <td>
          {{ item.email }}
        </td>
        <td>
          {{ item.description }}
        </td>
        <td>
          <button 
            type="button" 
            class="v-btn v-btn--elevated v-theme--light bg-warning v-btn--density-default v-btn--size-small v-btn--variant-elevated action-btn" 
            @click="editUser(item.email)">
            <span class="v-btn__overlay"></span>
            <span class="v-btn__underlay"></span>
            <span class="v-btn__content" data-no-activator=""> Edit </span>
          </button>
          <button 
            type="button" 
            class="v-btn v-btn--elevated v-theme--light bg-error v-btn--density-default v-btn--size-small v-btn--variant-elevated action-btn" 
            @click="deleteUser(item.email)" :disabled="item.email === loggedInUserEmail">
            <span class="v-btn__overlay"></span>
            <span class="v-btn__underlay"></span>
            <span class="v-btn__content" data-no-activator=""> Remove </span>
          </button>
        </td>

      </tr>
    </tbody>
  </VTable>
</template>

<style scoped>
.action-btn {
  margin-right: 10px;
}
button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}
</style>
