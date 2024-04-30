
<!-- EventModal.vue -->
<template>
  <div class="modal" v-if="event">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <h2>{{ event.title }}</h2>
      <p><strong>Start Date:</strong> {{ event.start.toLocaleString() }}</p>
      <p><strong>End Date:</strong> {{ event.end.toLocaleString() }}</p>
      <p><strong>Description:</strong> {{ event.extendedProps.description }}</p>
      <p><strong>Location:</strong> {{ event.extendedProps.location }}</p>
      <p><strong>Completed:</strong> {{ event.extendedProps.completed ? 'Yes' : 'No' }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    event: Object
  },
  methods: {
    closeModal() {
      this.$emit('close');
    }
  }
}
</script>

<style scoped>
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content/Box */
.modal-content {
  background-color: #fefefe;
  margin: 15% auto; /* 15% from the top and centered */
  padding: 20px;
  border: 1px solid #888;
  width: 80%; /* Could be more or less, depending on screen size */
}

/* Close Button */
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
