<script setup>
import DemoSimpleTableBasics from '@/views/pages/tables/DemoSimpleTableBasics.vue'
import DemoSimpleTableDensity from '@/views/pages/tables/DemoSimpleTableDensity.vue'
import DemoSimpleTableFixedHeader from '@/views/pages/tables/DemoSimpleTableFixedHeader.vue'
import DemoSimpleTableHeight from '@/views/pages/tables/DemoSimpleTableHeight.vue'
import DemoSimpleTableTheme from '@/views/pages/tables/DemoSimpleTableTheme.vue'
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const topicInput = ref(route.params.topicInput);
const topicList = ref([]);

const loading = ref(true); // Start with loading state true by default
onMounted(async () => {
  try {
    const response = await axios.get('https://api.tg-redback.com:8080/user/topic_list/getAll');
    if (response.data.data) {
      topicList.value = response.data.data;
      items.value = [...topicList.value];
    }
  } catch (error) {
    console.error('Error fetching all topics:', error);
  }
});

const topicTitles = ref([]);

onMounted(async () => {
  loading.value = true;
  try {
    loading.value = true;
    const response = await axios.get(`https://api.tg-redback.com:8080/user/topic_list/inputGenerate/${topicInput.value}`);
    if (response.data.data) {
      topicTitles.value = response.data.data;
    }
  } catch (error) {
    console.error('Error fetching topics:', error);
  } finally {
    loading.value = false;
  }
});
const addSelectedTopicsToSideCard = async () => {
    const topicsToAdd = [];

    selectedTopicIndexes.value.forEach(index => {
        const topic = topicTitles.value[index];
        if (!items.value.includes(topic)) {
            items.value.push(topic);
            topicsToAdd.push(topic); // push the topic to the topicsToAdd list
        }
    });
    selectedTopicIndexes.value = []; 

    // Now, make the axios POST request to save the topics to the backend:
    if (topicsToAdd.length > 0) {
        try {
            const response = await axios.post('https://api.tg-redback.com:8080/user/topic_list/add', {
                topics: topicsToAdd
            });
            if (response.data && response.data.status === "success") {
                console.log('Topics successfully added to the backend.');
            } else {
                console.error('Error adding topics to backend:', response.data.message || 'Unknown error');
            }
        } catch (error) {
            console.error('Error making request to add topics:', error);
        }
    }
};


const searchTerm = ref('');
const selectedIndex = ref(-1);
const newItem = ref('');
const items = ref([...topicList.value]);
const filteredItems = computed(() => {
  if (!searchTerm.value) {
    return items.value;
  }
  const normalizedTerm = searchTerm.value.toLowerCase();
  return items.value.filter(item =>
    item.toLowerCase().includes(normalizedTerm)
  );
});
const selectItem = index => {
  selectedIndex.value = index;
};
const addItem = async () => {
  if (newItem.value) {
    try {
      await axios.post('https://api.tg-redback.com:8080/user/topic_list/add', { topics: [newItem.value] });
      topicList.value.push(newItem.value);
      items.value = [...topicList.value];
      newItem.value = '';
    } catch (error) {
      console.error('Error adding topic:', error);
    }
  }
};
const removeSelectedItems = async () => {
  const selectedItems = selectedIndexes.value.map(index => topicList.value[index]);
  const isConfirmed = window.confirm('Are you sure you want to delete this topic?');
  if (!isConfirmed) {
    return; // If the user does not confirm, exit the function.
  }
  try {
    await axios.post('https://api.tg-redback.com:8080/user/topic_list/remove', { topics: selectedItems });
    const newItems = topicList.value.filter((item, index) => !selectedIndexes.value.includes(index));
    topicList.value = newItems;
    items.value = [...topicList.value];
    selectedIndexes.value = [];
  } catch (error) {
    console.error('Error removing topics:', error);
  }
};
const selectedIndexes = ref([]);
const toggleSelect = index => {
  const selectedIndex = selectedIndexes.value.indexOf(index);
  if (selectedIndex === -1) {
    selectedIndexes.value.push(index);
  } else {
    selectedIndexes.value.splice(selectedIndex, 1);
  }
};

const sortAlphabetically = () => {
  items.value.sort((a, b) => a.localeCompare(b));
};
const sortedItems = computed(() => {
  return items.value.slice().sort((a, b) => a.localeCompare(b));
});
const inputText = ref('');

const clearInput = () => {
  inputText.value = '';
};
const selectSentence = (sentence) => {
  inputText.value = sentence;
};
const selectedTopicIndexes = ref([]);

</script>

<template>
  <div class="d-flex">
    <!-- Main content -->
    <div class="full-width-card mb-6 me-6">
      <div class="full-width-card">
        <VCard class="full-width-card">
          <VCardText class="py-3 px-4">
            <div class="input-container">
              <span class="topic-label">Your Topic Idea:&nbsp;</span> {{ route.params.topicInput }}
            </div>
            <div>
              <h4>&nbsp;&nbsp;&nbsp;&nbsp;Generated Topics...</h4>
            </div>
            <div class="scroll-container full-width-card">
              <v-progress-circular v-if="loading" indeterminate style="position:center"></v-progress-circular>
                <div
                  v-for="(sentence, index) in topicTitles" 
                  :key="index"
                  class="d-flex align-center mb-2 sentence-checkbox"
                >
                  <VCheckbox
                    class="mr-2 px-3"
                    v-model="selectedTopicIndexes"
                    :value="index"
                  />
                  <VTextField v-model="topicTitles[index]"/>
                </div>
            </div>
          </VCardText>
          <!-- Add button at the bottom right of the main card -->
          <div class="add-button-container">
            <VBtn @click="addSelectedTopicsToSideCard">Add</VBtn>
          </div>
        </VCard>
      </div>
    </div>

    <!-- Side card -->
    <div class="flex-shrink-0 w-25 p-2" style="height: 60vh">
      <VCard>
        <div class="sidebar-header">
          <VCardTitle>Topics List</VCardTitle>
          <div class="mx-2 mb-2">
            <VTextField v-model="searchTerm" label="Search for Topics" />
          </div>
          <div class="mx-2 mt-2 d-flex align-center">
            <VTextField v-model="newItem" label="New Item" class="mr-2" />
            <div class="d-flex align-center">
              <VBtn @click="addItem">Add</VBtn>
            </div>
          </div>
          <div class="d-flex justify-end align-center mx-2 mt-2">
            <VBtn @click="removeSelectedItems" class="mx-2 mr-2 bg-error"
              >Remove</VBtn
            >
            <VIcon @click="sortAlphabetically" class="cursor-pointer mt-2 mr-2">
              bx-sort-a-z
            </VIcon>
          </div>
        </div>
        <div class="sidebar-content">
          <ul>
            <li
              v-for="(item, index) in filteredItems"
              :key="index"
              class="d-flex align-center mb-2"
            >
              <VCheckbox
                class="mr-2 px-3"
                v-model="selectedIndexes"
                :value="index"
              />
              {{ item }}
            </li>
          </ul>
        </div>
      </VCard>
    </div>
  </div>
</template>

<style>
.sentence-button {
  margin-right: 8px;
  margin-bottom: 8px;
  padding: 0.75rem;
  border: 0.2px solid rgba(0, 123, 255, 0.2);
  background-color: rgba(0, 123, 255, 0.1) !important;
  color: rgba(0, 123, 255, 1) !important;
  cursor: pointer;
}
.input-container {
  position: sticky;
  top: 0;
  z-index: 1;
  padding: 1rem;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
}
.clear-button {
  margin-left: 10px;
}
.generate-button {
  margin-left: 10px;
}
.scroll-container {
  max-height: calc(60vh - 50px); /* Adjust as needed */
  overflow-y: auto;
  padding: 1rem;
}
.selected {
  background-color: #f0f0f0;
}
.sidebar-header {
  position: sticky;
  top: 0;
  z-index: 1;
  padding: 1rem;
  border-bottom: 1px solid #ccc;
}
.sidebar-content {
  max-height: calc(60vh - 100px); /* Adjust as needed */
  overflow-y: auto;
  padding: 1rem;
}
.sidebar-footer {
  padding: 1rem;
}
.sentence-checkbox {
  width: 100%;
  cursor: pointer;
}
.add-button-container {
  display: flex;
  justify-content: flex-end;
  margin: 10px 15px 15px 10px; /* Adjust margins as desired */
}
.topic-label {
    font-weight: bold; 
    font-size: 1em;  
}
</style>