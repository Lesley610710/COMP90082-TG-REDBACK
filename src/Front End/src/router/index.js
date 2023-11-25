import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/user/login' },
    {
      path: '/',
      component: () => import('../layouts/default.vue'),
      children: [
        {
          path: 'admin_user/userList',
          name: 'userList',
          component: () => import('../pages/admin_user/userList.vue'),
          meta: { requiresAdmin: true }
        },
        {
          path: 'admin_user/add',
          component: () => import('../pages/admin_user/add.vue'),
          meta: { requiresAdmin: true }
        },
        {
          path: 'admin_user/edit/:userEmail',
          name: 'editUser',
          component: () => import('../pages/admin_user/edit.vue'),
          props: true,
          meta: { requiresAdmin: true }
        },
        {
          path: 'admin_user/editProfile/:userEmail',
          name: 'editProfile',
          component: () => import('../pages/admin_user/editProfile.vue'),
          props: true
        },
        {
          path: 'user/texts',
          component: () => import('../pages/user/texts.vue'),
          props: true
        },
        {
          path: 'user/generated_texts/:textInput/:wordCount/:ease/:grade/:difficulty/:instructions/:level',
          name: 'generated_texts',
          component: () => import('../pages/user/generated_texts.vue'),
          props: true
        },
        {
          path: 'user/topics',
          name: 'topics',
          component: () => import('../pages/user/topics.vue'),
        },
        {
          path: 'user/generated_topics/:topicInput',
          name: 'generated_topics',
          component: () => import('../pages/user/generated_topics.vue'),
        },
      ],
    },
    {
      path: '/',
      component: () => import('../layouts/blank.vue'),
      children: [
        {
          path: '/user/login',
          component: () => import('../pages/user/login.vue'),
        },
        {
          path: 'register',
          component: () => import('../pages/register.vue'),
        },
        {
          path: '/:pathMatch(.*)*',
          component: () => import('../pages/[...all].vue'),
        },
      ],
    },
  ],
})

// Check authentication before each route
router.beforeEach((to, from, next) => {
  const publicRoutes = ['/user/login'];
  const isAuthenticated = localStorage.getItem('authToken');
  const userDescription = localStorage.getItem('userDescription');

  if (!isAuthenticated && !publicRoutes.includes(to.path)) {
    // If the user is not authenticated and the route is not public, redirect to login
    next({
      path: '/user/login',
      query: { showLoginPrompt: true }
    });
  } else if (to.matched.some(record => record.meta.requiresAdmin) && userDescription !== 'admin') {
    next({ path: '/user/topics' }); 
  } else {
    next();
  }
});

export default router