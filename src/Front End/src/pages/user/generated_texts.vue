<script setup>
import DemoSimpleTableBasics from '@/views/pages/tables/DemoSimpleTableBasics.vue'
import DemoSimpleTableDensity from '@/views/pages/tables/DemoSimpleTableDensity.vue'
import DemoSimpleTableFixedHeader from '@/views/pages/tables/DemoSimpleTableFixedHeader.vue'
import DemoSimpleTableHeight from '@/views/pages/tables/DemoSimpleTableHeight.vue'
import DemoSimpleTableTheme from '@/views/pages/tables/DemoSimpleTableTheme.vue'
import { ref, computed, onBeforeMount, watch} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { quillEditor } from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import { Editor, EditorContent } from '@tiptap/vue-3';
import Document from '@tiptap/extension-document';
import Paragraph from '@tiptap/extension-paragraph';
import Text from '@tiptap/extension-text';
import Bold from '@tiptap/extension-bold';
import Italic from '@tiptap/extension-italic';
import Heading from '@tiptap/extension-heading';
import BulletList from '@tiptap/extension-bullet-list';
import ListItem from '@tiptap/extension-list-item';
import TextAlign from '@tiptap/extension-text-align';
import FontFamily from '@tiptap/extension-font-family';
import { jsPDF } from 'jspdf';
import { Document as DocxDocument, Packer, Paragraph as DocxParagraph } from "docx";
import axios from 'axios';

const router = useRouter();
const route = useRoute();

const topicsList = [
  'Technology',
  'Science',
  'Art',
  'History',
  'Music',
  'Food',
  'Travel',
  'Sports',
  'Health',
  'Education',
  'Fashion',
  'Environment',
  'Politics',
  'Business',
  'Literature',
  'Movies',
  'Fitness',
  'Gaming',
  'Photography',
  'Animals',
]

const searchTerm = ref('');
const selectedIndex = ref(-1);
const newItem = ref('');
const items = ref([...topicsList]);

const dropdownOptions = [
  'Formal',
  'Creative',
  // Add more options as needed
];

const selectedOption = ref('');
const numberInput = ref('');
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
  value => value >= 50 && value <= 1000 || 'Word count must be between 100 and 5000'
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

const inputText = ref(route.params.textInput || ''); 


const topicInput = ref(route.params.textInput);
const wordCount = ref(route.params.wordCount);
const difficulty = ref(route.params.difficulty);
const instructions = ref(route.params.instructions);
const grade = ref(route.params.grade);
const ease = ref(route.params.ease);
const level = ref(route.params.level);
const currentText = ref('');

onBeforeMount(async () => {
  await generateTextFromServer(); 
});
const isLoading = ref(true);

const generateTextFromServer = async () => {
    isLoading.value = true;  // Start loading animation
    const apiUrl = 'https://api.tg-redback.com:8080/user/text_generation';
    try {
        const response = await axios.post(apiUrl, {
            topic: topicInput.value,
            wordNumber: wordCount.value,
            difficulty_level: difficulty.value,
            grade_level: grade.value,
            special_instructions: instructions.value,
            ease_level: ease.value,
            currentText: editor.value.getHTML()
        });
        if (response.data.msg == "suc") {
            const formattedText = response.data.data.map(para => `<p>${para}</p>`).join('');
            editor.value.commands.setContent(formattedText);
        } else {
            console.error('Failed to generate texts:', response.data.message);
        }
    } catch (error) {
        console.error('Error generating texts:', error);
    } finally {
        isLoading.value = false;  // Stop loading animation
    }
};

watch([topicInput, wordCount, difficulty], () => {
    router.push({
        name: route.name,
        params: {
            ...route.params,
            textInput: topicInput.value,
            wordCount: wordCount.value.toString(),
            difficulty: difficulty.value.toString(),
            grade: grade.value.toString(),
            instructions: instructions.value.toString(),
            ease: ease.value.toString()
        }
    });
}, { deep: true });

const generateSentences = async () => {
    if (!topicInput.value.trim()) {
      snackbarMessage.value = "Topic is required!";
      snackbar.value = true;
      return;
    }
    
    if (!wordCount.value) {
      snackbarMessage.value = "Word Count is required!";
      snackbar.value = true;
      return;
    }
    
    if (!difficulty.value) {
      snackbarMessage.value = "Difficulty Level is required!";
      snackbar.value = true;
      return;
    }

    if (!grade.value) {
      snackbarMessage.value = "Grade Level is required!";
      snackbar.value = true;
      return;
    }

    if (!instructions.value) {
      snackbarMessage.value = "Special Instructions is required!";
      snackbar.value = true;
      return;
    }

    if (!ease.value) {
      snackbarMessage.value = "Reading Ease Score is required!";
      snackbar.value = true;
      return;
    }
    await generateTextFromServer();
    router.push({
        name: 'generated_texts',
        params: {
            textInput: topicInput.value,
            wordCount: wordCount.value.toString(),
            difficulty: difficulty.value.toString(),
            instructions: instructions.value.toString(),
            ease: ease.value.toString()
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

const editor = ref(null);

editor.value = new Editor({
  extensions: [
    Document,
    Paragraph,
    Text,
    Bold,
    Italic,
    Heading.configure({ levels: [1, 2, 3] }),
    BulletList,
    ListItem,
    TextAlign,
    FontFamily
  ],
  content: 'generating ...' 
});

const totalWordCount = ref(0); 
watch(() => editor.value?.getHTML(), (newContent) => {
  // Count the words and update wordCount
  if (newContent) {
    const text = newContent.replace(/<[^>]*>/g, ''); // Remove HTML tags
    totalWordCount.value = text.split(/\s+/).filter(Boolean).length; // Count words
  } else {
    totalWordCount.value = 0;
  }
}, { deep: true });

const downloadAsPDF = () => {
    const doc = new jsPDF();
    const contentHTML = editor.value.getHTML();
    const parser = new DOMParser();
    const docParsed = parser.parseFromString(contentHTML, 'text/html');
    let yPosition = 10;
    const pageHeight = 297; // A4 page height in mm
    const bottomMargin = 10; // space at the bottom of the page

    const addContent = (content, fontSize, isBold = false) => {
        doc.setFontSize(fontSize);
        if (isBold) doc.setFont(undefined, 'bold');
        const lines = doc.splitTextToSize(content, 190); // Wrap content to fit within page width
        doc.text(lines, 10, yPosition);
        yPosition += lines.length * 7;
        if (yPosition > (pageHeight - bottomMargin)) { // Check if the new content will fit on the page
            doc.addPage();
            yPosition = 10;  // Reset yPosition for the new page
        }
    }

    docParsed.body.childNodes.forEach(node => {
        if (node.nodeType === Node.ELEMENT_NODE) {
            let fontSize = 12;
            switch (node.tagName) {
                case 'H1':
                    fontSize = 24;
                    addContent(node.textContent, fontSize, true);
                    break;
                case 'H2':
                    fontSize = 22;
                    addContent(node.textContent, fontSize, true);
                    break;
                case 'H3':
                    fontSize = 20;
                    addContent(node.textContent, fontSize, true);
                    break;
                case 'P':
                    fontSize = 12;
                    addContent(node.textContent, fontSize);
                    break;
                case 'UL': {
                    const listItems = node.querySelectorAll('LI');
                    listItems.forEach(item => {
                        addContent(`• ${item.textContent}`, 12);
                    });
                    break;
                }
            }
        }
    });

    doc.save(`${route.params.textInput}.pdf`);
};

const downloadAsWordFile = () => {
  const contentHTML = editor.value.getHTML();
  const parser = new DOMParser();
  const docParsed = parser.parseFromString(contentHTML, 'text/html');
  const children = [];
  const fileName = `${route.params.textInput}.docx`;
  docParsed.body.childNodes.forEach(node => {
    if (node.nodeType === Node.ELEMENT_NODE) {
      let isBold = !!node.querySelector('strong');
      let isItalic = !!node.querySelector('em');
      switch (node.tagName) {
        case 'H1':
          children.push(new DocxParagraph({
            text: node.textContent,
            heading: 'Heading1'
          }));
          break;
        case 'H2':
          children.push(new DocxParagraph({
            text: node.textContent,
            heading: 'Heading2'
          }));
          break;
        case 'H3':
          children.push(new DocxParagraph({
            text: node.textContent,
            heading: 'Heading3'
          }));
          break;
        case 'P':
          children.push(new DocxParagraph({
            text: node.textContent,
            bold: isBold,
            italic: isItalic
          }));
          break;
        case 'UL': {
          const listItems = node.querySelectorAll('LI');
          listItems.forEach(item => {
            children.push(new DocxParagraph({
              text: item.textContent,
              bullet: {
                level: 0
              }
            }));
          });
          break;
        }
      }
    }
  });
  const doc = new DocxDocument({
    sections: [{
      properties: {},
      children
    }]
  });
  Packer.toBlob(doc).then(blob => {
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    document.body.appendChild(a);
    a.click();
    a.remove();
  });
};

const copyToClipboard = () => {
  const content = editor.value.getHTML();
  const plainText = content
    .replace(/<\/(?:h\d+|p)>/g, '\n\n')
    .replace(/<[^>]*>/g, ''); 
  navigator.clipboard.writeText(plainText).then(() => {
    alert('Texts copied to clipboard!');
  }).catch(err => {
    console.error('Could not copy text: ', err);
  });
};

const showDialog = ref(false);
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

<template>
  <!-- Main content -->

      <div class="full-width-card">
        <VCard class="full-width-card">
          <VCardText class="py-3 px-4">
            <div class="d-flex input-container">
              <VTextField v-model="topicInput" label="Enter Topic" :rules="topicRules" maxlength="150" />
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
          <div class="inner-cards-container">

            <VCard class="left-inner-card">
                <div class="inner-card-header">
                    <VCardTitle>Generated Texts</VCardTitle>
                    <div class="editor-toolbar">
                        <button @click="editor.chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }"><i class="fa fa-bold"></i></button>
                        <button @click="editor.chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }"><i class="fa fa-italic"></i></button>
                        <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"><i class="fa fa-heading"></i>1</button>
                        <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }"><i class="fa fa-heading"></i>2</button>
                        <button @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }"><i class="fa fa-heading"></i>3</button>
                        <button @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }"><i class="fa fa-list-ul"></i></button>
                        <button @click="downloadAsPDF" class="export-button">
                            <i class="fa fa-file-pdf"></i> Export as PDF
                        </button>
                        <button @click="downloadAsWordFile" class="export-button">
                            <i class="fa fa-file-text"></i> Export as Word
                        </button>
                        <button @click="copyToClipboard" class="export-button">
                            <i class="fa fa-copy"></i> Copy to Clipboard
                        </button>
                    </div>
                </div>

                <div class="sidebar-content">
                    <EditorContent :editor="editor" style="margin-left:20px;"/>
                </div>
            </VCard>

            <VCard class="right-inner-card" >
              <VCardText class="inner-card-text">
                <div class="inner-card-header">
                  <VCardTitle>Generate Options</VCardTitle>
                  <VIcon @click="showDialog = true" class="ml-3">mdi-information-outline</VIcon>
                </div>
                <div class="input-section">
                  <VForm>
                    <VTextField v-model="wordCount" label="Word Count" :rules="wordCountRules" outlined class="mb-4" maxlength="4" type="number" min="50" max="1000" @input="validateWordCount"/>
                    <span>Flesch-Kincaid Difficulty Level</span>
                    <VSelect
                        v-model="difficulty"
                        :items="['Grade 5 and below', 'Grade 6', 'Grade 7', 'Grade 8 to 9', 'Grade 10 to 12', 'College', 'College Graduate']"
                        outlined
                        disabled
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
                <div class="total-word-count" style = "margin-top: 20px">
                  &nbsp;&nbsp;&nbsp;&nbsp;Total Word Count: {{ totalWordCount }}
                </div>
              </VCardText>
            </VCard>

            <VDialog v-model="showDialog" max-width="500px">
                <VCard>
                    <VCardTitle class="headline">Flesch-Kincaid Grade Level</VCardTitle>
                    <VCardText>
                        The Flesch Reading Ease Score is a measure of how comprehensible a document is, with a score between 0 and 100. A higher score indicates easier readability.<br><br>
                        <strong>0-29 College Graduate:</strong> The text should be very difficult to read, best understood by university graduates.<br>
                        <strong>30-49 College:</strong> The text should be difficult to read.<br>
                        <strong>50-59 Grade 10 to 12:</strong> The text should be fairly difficult to read.<br>
                        <strong>60-69 Grade 8 to 9:</strong> The text should be in plain English, easily understood by 13- to 15-year-old students.<br>
                        <strong>70-79 Grade 7:</strong> The text should be fairly easy to read.<br>
                        <strong>80-89 Grade 6:</strong> The text should be easy to read, conversational English for consumers.<br>
                        <strong>90-100 Grade 5 and below:</strong> The text should be very easy to read, easily understood by an average 11-year-old student.<br>
                    </VCardText>
                    <VCardActions>
                        <VSpacer></VSpacer>
                        <VBtn color="primary" text @click="showDialog = false">Close</VBtn>
                    </VCardActions>
                </VCard>
            </VDialog>

            <div v-if="isLoading" class="loading-container">
                <VSpinner size="40" color="primary"></VSpinner>
                <p>Generating...</p>
            </div>

            <VSnackbar v-model="snackbar" color="error" :timeout="3000" top>
              {{ snackbarMessage }}
            </VSnackbar>

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
  z-index: 1;
  padding: 1rem;
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
  align-items: center;
}

.slider-tick {
  width: calc(33.33% - 1rem); 
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
.editor-toolbar button.is-active {
  background-color: #eee;
}
.editor-toolbar button, .editor-toolbar select {
  margin-top: 10px;
  margin-left: 20px;
  margin-right: 5px; /* or any space you desire */
}
.loading-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%; 
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(255, 255, 255, 0.8);
    z-index: 10; 
}

</style>
