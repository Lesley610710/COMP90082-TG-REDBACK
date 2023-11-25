<script setup>
import avatar1 from '@images/avatars/avatar-1.png'
import avatar4 from '@images/avatars/avatar-4.png'
import avatar8 from '@images/avatars/avatar-8.png'

const solidCardData = [
  {
    cardBg: '#16B1FF',
    title: 'Twitter Card',
    icon: 'bxl-twitter',
    sendText: '"Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well."',
    avatarImg: avatar4,
    avatarName: 'Mary Vaughn',
    likes: '1.2k',
    share: '80',
  },
  {
    cardBg: '#3B5998',
    title: 'Facebook Card',
    icon: 'bxl-facebook',
    sendText: 'You\'ve read about the importance of being courageous, rebellious and imaginative. These are all vital ingredients.',
    avatarImg: avatar1,
    avatarName: 'Eugene Clarke',
    likes: '3.2k',
    share: '49',
  },
  {
    cardBg: '#007BB6',
    title: 'Linkedin Card',
    icon: 'bxl-linkedin',
    sendText: 'With the Internet spreading like wildfire and reaching every part of our daily life, more and more traffic is directed.',
    avatarImg: avatar8,
    avatarName: 'Anne Burke1',
    likes: '1.2k',
    share: '80',
  },
]
</script>

<template>
  <VRow>
    <VCol
      v-for="data in solidCardData"
      :key="data.icon"
      cols="12"
      md="6"
      lg="4"
    >
      <VCard :color="data.cardBg">
        <VCardItem>
          <template #prepend>
            <VIcon
              size="1.9rem"
              color="white"
              :icon="data.icon"
            />
          </template>
          <VCardTitle class="sendText-white">
            {{ data.title }}
          </VCardTitle>
        </VCardItem>

        <VCardText>
          <p class="clamp-sendText sendText-white mb-0">
            {{ data.sendText }}
          </p>
        </VCardText>

        <VCardText class="d-flex justify-space-between align-center flex-wrap">
          <div class="sendText-no-wrap">
            <VAvatar
              size="34"
              :image="data.avatarImg"
            />
            <span class="sendText-white ms-2">{{ data.avatarName }}</span>
          </div>

          <div class="d-flex align-center">
            <IconBtn
              icon="bx-heart"
              color="white"
              class="me-1"
            />
            <span class="sendText-subtitle-2 sendText-white me-4">{{ data.likes }}</span>

            <IconBtn
              icon="bx-share-alt"
              color="white"
              class="me-1"
            />
            <span class="sendText-subtitle-2 sendText-white">{{ data.share }}</span>
          </div>
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>
