<script setup>
import DemoSimpleTableBasics from '@/views/pages/tables/DemoSimpleTableBasics.vue'
import DemoSimpleTableDensity from '@/views/pages/tables/DemoSimpleTableDensity.vue'
import DemoSimpleTableFixedHeader from '@/views/pages/tables/DemoSimpleTableFixedHeader.vue'
import DemoSimpleTableHeight from '@/views/pages/tables/DemoSimpleTableHeight.vue'
import DemoSimpleTableTheme from '@/views/pages/tables/DemoSimpleTableTheme.vue'
import { ref, computed, onMounted, onBeforeMount, nextTick  } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const topicList = ref([]);
const items = ref([...topicList.value]);
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
const showDialog = ref(false);
const searchTerm = ref('');
const selectedIndex = ref(-1);
const newItem = ref('');

const dropdownOptions = [
  'Formal',
  'Creative',
  // Add more options as needed
];

const selectedOption = ref('');
const numberInput = ref('');
const difficulty = ref('');
const grade = ref('');
const ease = ref('');
const instructions = ref('None');
const level = ref('Medium');
const currentText = ref('');
const sliderValue = ref(50);
const sliderTicks = ref([
  { value: 0, label: 'Easy' },
  { value: 5, label: 'Medium' },
  { value: 10, label: 'Difficult' }
]);

const paragraphCountRules = [
  value => !!value || 'Paragraph count is required',
  value => /^\d+$/.test(value) || 'Paragraph count must be a number',
  value => value >= 1 && value <= 20 || 'Paragraph count must be between 1 and 20'
];

const wordCountRules = [
  value => !!value || 'Word count is required',
  value => /^\d+$/.test(value) || 'Word count must be a number',
  value => value >= 50 && value <= 1000 || 'Word count must be between 50 and 1000'
];

const validateParagraphCount = () => {
  if (numberInput1.value < 1) {
    numberInput1.value = 1;
  } else if (numberInput1.value > 20) {
    numberInput1.value = 20;
  }
};

const validateWordCount = () => {
  if (numberInput.value < 50) {
    numberInput.value = 50;
  } else if (numberInput.value > 1000) {
    numberInput.value = 1000;
  }
};

const filteredItems = computed(() => {
  if (!searchTerm.value) {
    return items.value;
  }
  const normalizedTerm = searchTerm.value.toLowerCase();
  return items.value.filter(item =>
    item.toLowerCase().includes(normalizedTerm)
  );
});

const selectItem = (item, index) => {
  inputText.value = item;
  selectedIndex.value = index;
};

const addItem = () => {
  if (newItem.value) {
    items.value.push(newItem.value);
    newItem.value = '';
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

const removeSelectedItems = () => {
  const newItems = items.value.filter((item, index) => !selectedIndexes.value.includes(index));
  items.value = newItems;
  selectedIndexes.value = [];
};

const sortAlphabetically = () => {
  items.value.sort((a, b) => a.localeCompare(b));
};

const sortedItems = computed(() => {
  return items.value.slice().sort((a, b) => a.localeCompare(b));
});

const inputText = ref('');

const generateSentences = async () => {
  if (!inputText.value.trim()) {
    snackbarMessage.value = "Topic is required!";
    snackbar.value = true;
    return;
  }
  
  if (!numberInput.value) {
    snackbarMessage.value = "Word count is required!";
    snackbar.value = true;
    return;
  }
  
  if (!difficulty.value) {
    snackbarMessage.value = "Difficulty is required!";
    snackbar.value = true;
    return;
  }
  router.push({
    name: 'generated_texts',
    params: { 
      textInput: inputText.value,
      wordCount: numberInput.value, 
      difficulty: difficulty.value 
      difficulty: difficulty.value,
      grade: grade.value,
      ease: ease.value,
      level: level.value,
      instructions: instructions.value
    }
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


const snackbar = ref(false);
const snackbarMessage = ref('');

const difficultyLevels = [
  'Grade 5 and below',
  'Grade 6',
  'Grade 7',
  'Grade 8 to 9',
  'Grade 10 to 12',
  'College',
  'College Graduate'
];

watch(() => [difficulty.value, level.value], ([newDifficulty, newLevel]) => {
  ease.value = getEaseBasedOnDifficulty(newDifficulty, newLevel);
  grade.value = getGradeBasedOnDifficulty(newDifficulty, newLevel);
});


const getEaseBasedOnDifficulty = (difficulty, level) => {
  if (difficulty === 'College Graduate' && level === 'Low') return 29.9;
  if (difficulty === 'College' && level === 'Low') return 49.9;
  if (difficulty === 'Grade 10 to 12' && level === 'Low') return 59.9;
  if (difficulty === 'Grade 8 to 9' && level === 'Low') return 69.9;
  if (difficulty === 'Grade 7' && level === 'Low') return 79.9;
  if (difficulty === 'Grade 6' && level === 'Low') return 89.9;
  if (difficulty === 'Grade 5 and below' && level === 'Low') return 100.0;

  if (difficulty === 'College Graduate' && level === 'Medium') return 15.0;
  if (difficulty === 'College' && level === 'Medium') return 40.0;
  if (difficulty === 'Grade 10 to 12' && level === 'Medium') return 55.0;
  if (difficulty === 'Grade 8 to 9' && level === 'Medium') return 65.0;
  if (difficulty === 'Grade 7' && level === 'Medium') return 75.0;
  if (difficulty === 'Grade 6' && level === 'Medium') return 85.0;
  if (difficulty === 'Grade 5 and below' && level === 'Medium') return 95.0;

  if (difficulty === 'College Graduate' && level === 'High') return 0.0;
  if (difficulty === 'College' && level === 'High') return 30.0;
  if (difficulty === 'Grade 10 to 12' && level === 'High') return 50.0;
  if (difficulty === 'Grade 8 to 9' && level === 'High') return 60.0;
  if (difficulty === 'Grade 7' && level === 'High') return 70.0;
  if (difficulty === 'Grade 6' && level === 'High') return 80.0;
  if (difficulty === 'Grade 5 and below' && level === 'High') return 90.0;
  return '';
};

const getGradeBasedOnDifficulty = (difficulty, level) => {
  if (difficulty === 'College Graduate' && level === 'Low') return 16.0;
  if (difficulty === 'College' && level === 'Low') return 13.0;
  if (difficulty === 'Grade 10 to 12' && level === 'Low') return 10.0;
  if (difficulty === 'Grade 8 to 9' && level === 'Low') return 8.0;
  if (difficulty === 'Grade 7' && level === 'Low') return 7.0;
  if (difficulty === 'Grade 6' && level === 'Low') return 6.0;
  if (difficulty === 'Grade 5 and below' && level === 'Low') return 5.0;

  if (difficulty === 'College Graduate' && level === 'Medium') return 17.0;
  if (difficulty === 'College' && level === 'Medium') return 14.5;
  if (difficulty === 'Grade 10 to 12' && level === 'Medium') return 11.5;
  if (difficulty === 'Grade 8 to 9' && level === 'Medium') return 9.0;
  if (difficulty === 'Grade 7' && level === 'Medium') return 7.5;
  if (difficulty === 'Grade 6' && level === 'Medium') return 6.5;
  if (difficulty === 'Grade 5 and below' && level === 'Medium') return 5.5;

  if (difficulty === 'College Graduate' && level === 'High') return 18.0;
  if (difficulty === 'College' && level === 'High') return 15.9;
  if (difficulty === 'Grade 10 to 12' && level === 'High') return 12.9;
  if (difficulty === 'Grade 8 to 9' && level === 'High') return 9.9;
  if (difficulty === 'Grade 7' && level === 'High') return 7.9;
  if (difficulty === 'Grade 6' && level === 'High') return 6.9;
  if (difficulty === 'Grade 5 and below' && level === 'High') return 5.9;
  return '';
};
</script>

<template >

  <!-- Main content -->
      <div class="full-width-card">
        <VCard class="full-width-card">
          <VCardText class="py-3 px-4">
            <div class="d-flex input-container">
              <VTextField v-model="inputText" label="Enter Topic" :rules="topicRules" maxlength="150" />
              <VBtn @click="clearInput" class="clear-button bg-secondary"
                >Clear</VBtn
              >
              <VBtn
                @click="generateSentences"
                class="generate-button bg-primary"
                >Generate Texts</VBtn
              >
            </div>
          </VCardText>
          <div class="d-flex inner-cards-container">

            <VCard class="left-inner-card">
              <div class="inner-card-header">
                  <VCardTitle>Topics List</VCardTitle>
                  <div class="mx-2 mb-2">
                      <VTextField v-model="searchTerm" label="Search for Topics" maxlength="150" />
                  </div>
                  <div class="d-flex justify-end align-center mx-2 mt-2">
                      <VIcon
                          @click="sortAlphabetically"
                          class="cursor-pointer mt-2 mr-2"
                      >
                          bx-sort-a-z
                      </VIcon>
                  </div>
              </div>

              <div class="sidebar-content">
                  <ul>
                      <li
                          v-for="(item, index) in filteredItems"
                          :key="index"
                          class="d-flex align-center mb-2 topic-list-item"
                          :class="{ 'selected-item': selectedIndex === index }"
                          @click="selectItem(item, index)"
                      >
                          {{ item }}
                      </li>
                  </ul>
              </div>
            </VCard>

            <VCard class="right-inner-card" >
              <VCardText class="inner-card-text">
                <div class="inner-card-header">
                  <VCardTitle>Generate Options</VCardTitle>
                </div>
                <div class="input-section">
                  <VForm>
                    <VTextField v-model="numberInput" label="Word Count" :rules="wordCountRules" outlined class="mb-4" maxlength="4" type="number" min="50" max="1000" @input="validateWordCount"/>
                    <span>Flesch-Kincaid Difficulty Level</span>
                    <VSelect
                        v-model="difficulty"
                        :items="['Grade 5 and below', 'Grade 6', 'Grade 7', 'Grade 8 to 9', 'Grade 10 to 12', 'College', 'College Graduate']"
                        outlined
                    ></VSelect>
                    <br>
                    <VRadioGroup v-model="level">
                      <div style="display: flex; justify-content: space-between;">
                        <VRadio label="Low" value="Low"></VRadio>
                        <VRadio label="Medium" value="Medium"></VRadio>
                        <VRadio label="High" value="High"></VRadio>
                      </div>
                    </VRadioGroup>
                    <br>
                    <VTextarea v-model="instructions" label="Special Instructions" outlined class="mb-4"></VTextarea>
                    <VTextField v-model="grade" label="Flesch-Kincaid Grade Level" outlined class="mb-4" maxlength="4" type="number" min="0" max="18" disabled/>
                    <VTextField v-model="ease" label="Flesch Reading Ease Score" outlined class="mb-4" maxlength="4" type="number" min="0" max="100" disabled/>
                  </VForm>
                </div>
              </VCardText>
            </VCard>

            <VSnackbar v-model="snackbar" color="warning" :timeout="3000" top>
              {{ snackbarMessage }}
            </VSnackbar>

            <VDialog v-model="showDialog" max-width="500px">
                <VCard>
                    <VCardTitle class="headline">Flesch-Kincaid Grade Level</VCardTitle>
                    <VCardText>
                        The Flesch-Kincaid Grade Level is a readability metric that estimates the U.S. school grade level at which a reader can easily understand a particular piece of text.<br><br>
                        <strong>Kindergarten:</strong> Typically ages 5-6. Texts are fundamental, often focusing on basic vocabulary and simple sentences.<br>
                        <strong>Elementary school students:</strong> Ages 6-10. At this stage, texts become slightly more detailed but remain straightforward, introducing readers to foundational concepts and ideas.<br>
                        <strong>Middle school students:</strong> Ages 11-13. Texts delve into more complex topics, expanding vocabulary and introducing varied sentence structures.<br>
                        <strong>High school students:</strong> Ages 14-18. Texts become more diverse, ranging from general subjects to specialized topics, equipping students with knowledge for higher education and the real world.<br>
                        <strong>College undergraduates:</strong> Texts at this level delve into specialized areas, discussing advanced topics, concepts, and theories pertinent to specific fields of study.<br>
                        <strong>Graduate school level:</strong> Catered for advanced academic or professional purposes, texts here are dense and highly specialized, often necessitating deep expertise in the subject matter.<br>
                    </VCardText>
                    <VCardActions>
                        <VSpacer></VSpacer>
                        <VBtn color="primary" text @click="showDialog = false">Close</VBtn>
                    </VCardActions>
                </VCard>
            </VDialog>

          </div>
        </VCard>
      </div>

</template>

<style>
.page-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.content-container {
  width: 100%;
}

.main-card {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center; 
  margin-bottom: 1rem;
}

.input-container {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 1;
  padding: 1rem;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.inner-cards-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start; 
  margin-top: 1rem;
  margin-bottom: 1rem;
}

.left-inner-card {

  width: 70% !important; 
  padding: 1rem;
  margin-right: 1rem;
  margin-left:1rem;
}

.right-inner-card {

  width: 30% !important; 
  padding: 1rem;
  margin-right: 1rem;
  margin-left:1rem;
}

.inner-card-header {
  background-color: #fff;
  padding: 1rem;
  border-bottom: 1px solid #ccc;
}

.inner-card-text {
  padding: 0;
}

.custom-slider .v-slider__thumb {
  visibility: hidden;
}

.slider-ticks {
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
  align-items: center; /* Add this to vertically align the labels with the slider thumb */
}

.slider-tick {
  width: calc(33.33% - 1rem); /* Divide by the number of ticks */
  text-align: center;
  font-size: 12px;
  color: #999;
}

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
  display: flex;
  align-items: center;
}

.input-section {
  padding: 1rem;
  border-bottom: 1px solid #ccc;
}

.clear-button {
  margin-left: 10px;
}

.generate-button {
  margin-left: 10px;
}

.scroll-container {
  max-height: calc(60vh - 50px); 
  overflow-y: auto;
  padding: 1rem;
}

.selected {
  background-color: #f0f0f0;
}

.sidebar-header {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 1;
  padding: 1rem;
  border-bottom: 1px solid #ccc;
}

.sidebar-content {
  max-height: 60vh; 
  overflow-y: auto;
  padding: 1rem;
}

.sidebar-footer {
  padding: 1rem;
}

.topic-list-item {
    cursor: pointer;
    transition: background-color 0.3s;
    padding: 0.5rem 0.5rem;
}

.topic-list-item:hover {
    background-color: #e0e0e0;
}

.selected-item {
    background-color: rgba(105, 108, 255, 0.2);
    color: #696CFF;
}
</style>
