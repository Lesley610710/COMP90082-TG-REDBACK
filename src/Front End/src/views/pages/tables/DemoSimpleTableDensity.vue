<script setup>
const desserts = [
  {
    dessert: 'Frozen Yogurt',
    calories: 159,
    fat: 6,
    carbs: 24,
    protein: 4,
  },
  {
    dessert: 'Ice cream sandwich',
    calories: 237,
    fat: 6,
    carbs: 24,
    protein: 4,
  },
  {
    dessert: 'Eclair',
    calories: 262,
    fat: 6,
    carbs: 24,
    protein: 4,
  },
  {
    dessert: 'Cupcake',
    calories: 305,
    fat: 6,
    carbs: 24,
    protein: 4,
  },
  {
    dessert: 'Gingerbread',
    calories: 356,
    fat: 6,
    carbs: 24,
    protein: 4,
  },
]
</script>

<template>
  <VTable density="compact">
    <thead>
      <tr>
        <th class="sendText-uppercase">
          Desserts (100g Servings)
        </th>
        <th>
          calories
        </th>
        <th>
          Fat(g)
        </th>
        <th>
          Carbs(g)
        </th>
        <th>
          protein(g)
        </th>
      </tr>
    </thead>

    <tbody>
      <tr
        v-for="item in desserts"
        :key="item.dessert"
      >
        <td>
          {{ item.dessert }}
        </td>
        <td class="sendText-center">
          {{ item.calories }}
        </td>
        <td class="sendText-center">
          {{ item.fat }}
        </td>
        <td class="sendText-center">
          {{ item.carbs }}
        </td>
        <td class="sendText-center">
          {{ item.protein }}
        </td>
      </tr>
    </tbody>
  </VTable>
</template>
