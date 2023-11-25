<script setup>
import DemoSimpleTableBasics from '@/views/pages/tables/DemoSimpleTableBasics.vue'
import DemoSimpleTableDensity from '@/views/pages/tables/DemoSimpleTableDensity.vue'
import DemoSimpleTableFixedHeader from '@/views/pages/tables/DemoSimpleTableFixedHeader.vue'
import DemoSimpleTableHeight from '@/views/pages/tables/DemoSimpleTableHeight.vue'
import DemoSimpleTableTheme from '@/views/pages/tables/DemoSimpleTableTheme.vue'
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const topicList = ref([]);


const suggestedTopics = ref([]);

onMounted(async () => {
  loading.value = true;
  try {
    const response = await axios.get('https://api.tg-redback.com:8080/user/topic_list/randomGenerate');
    if (response.data && response.data.data) {
      suggestedTopics.value = response.data.data;
    }
  } catch (error) {
    console.error('Error fetching topics:', error);
  } finally {
    loading.value = false;
  }
});


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
const selectedIndexes = ref([]);

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
  const isConfirmed = window.confirm('Are you sure you want to delete this topic?');
  if (!isConfirmed) {
    return; // If the user does not confirm, exit the function.
  }
  const selectedItems = selectedIndexes.value.map(index => topicList.value[index]);
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

const sortAlphabetically = () => {
  items.value.sort((a, b) => a.localeCompare(b));
};
const sortedItems = computed(() => {
  return items.value.slice().sort((a, b) => a.localeCompare(b));
});
const inputText = ref('');
const generateSentences = () => {
  // Logic to generate sentences based on inputText
  // For example, you can split inputText into words and create sentences
  // suggestedTopics = [sentence1, sentence2, ...];
  if (!inputText.value.trim()) {
    return; // Exit the function if inputText is empty
  }
  router.push({
    name: 'generated_topics',
    params: { topicInput: inputText.value }
});

};
const clearInput = () => {
  inputText.value = '';
};
const selectSentence = (sentence) => {
  inputText.value = sentence;
};

// TOPIC INPUT VALIDATION
const topicRules = [
  v => v.length <= 150 || 'Topic ideas must not exceed 150 characters'
];

const loading = ref(true); // Start with loading state true by default

const isButtonClicked = ref(false);
const isScrollContainerVisible = ref(false);
const toggleScrollContainerVisibility = () => {
  isScrollContainerVisible.value = !isScrollContainerVisible.value;
  isButtonClicked.value = !isButtonClicked.value;
};
</script>

<template>
  <div class="d-flex">
    <!-- Main content -->
    <div class="flex-grow-1">
      <div class="d-flex align-center flex-wrap">
        <VCard class="full-width-card mb-6 me-6">
          <VCardText class="py-3 px-4">
            <div class="input-container">
              <VTextField v-model="inputText" label="Enter Topic Ideas" :rules="topicRules" maxlength="150" />
              <VBtn @click="clearInput" class="clear-button bg-secondary"
                >Clear</VBtn
              >
              <VBtn
                @click="generateSentences"
                class="generate-button bg-primary"
                >Generate Topics</VBtn
              >
            </div>
            <div>
              <br>
            </div>
            <VBtn @click="toggleScrollContainerVisibility">
              {{ isButtonClicked ? 'Hide Inspirations' : 'Seeking Inspiration? Click Here!' }}
            </VBtn>
            <div class="scroll-container" v-if="isScrollContainerVisible">
              <v-progress-circular v-if="loading" indeterminate style="position:center"></v-progress-circular>

              <div class="d-flex flex-wrap">
                <VBtn
                  v-for="(sentence, index) in suggestedTopics"
                  :key="index"
                  @click="selectSentence(sentence)"
                  class="sentence-button"
                >
                  {{ sentence }}
                </VBtn>
              </div>
            </div>
          </VCardText>
        </VCard>
      </div>
    </div>

    <!-- Side card -->
    <div class="flex-shrink-0 w-25 p-2" style="height: 60vh">
      <VCard>
        <div class="sidebar-header">
          <VCardTitle>Topics List</VCardTitle>
          <div class="mx-2 mb-2">
            <VTextField v-model="searchTerm" label="Search for Topics" maxlength="150"/>
          </div>
          <div class="mx-2 mt-2 d-flex align-center">
            <VTextField v-model="newItem" label="New Item" class="mr-2" maxlength="150"/>
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
.full-width-card {
  flex-grow: 1;
}

</style>