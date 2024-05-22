// create the Vue controller
const app = Vue.createApp({
  mixins: [BasicAccessAuthentication],
  mounted() {

    this.openAttribute(null, 'priority-Setting')
    this.load()

  },
  data() {
    return {
      signedIn: this.signedInUser !== undefined
    };
  },
  methods: {
    load() {
      this.signedIn = this.signedInUser !== undefined;
    },
    redirect() {
      this.load();
      if (this.signedIn) {
        window.location = "calendar.html";
      } else {
        window.location = "sign-in.html";
      }
    },
    openAttribute(evt, attributeName) {
      // Declare all variables
      var i, tabcontent, tablinks;

      
    

      // Get all elements with class="tabcontent" and hide them
      tabcontent = document.getElementsByClassName("tabcontent");
      for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
      }
    
      // Get all elements with class="tablinks" and remove the class "active"
      tablinks = document.getElementsByClassName("tablinks");
      for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
      }
    
      // Show the current tab, and add an "active" class to the button that opened the tab
      document.getElementById(attributeName).style.display = "block";
      evt.currentTarget.className += " active";
    }
  },
  computed: Vuex.mapState({
    signedInUser: 'signedInUser'
  })
});



import { BasicAccessAuthentication } from './authentication.js';
// import the navigation menu
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js';
app.use(dataStore);

// attach the controller to the <main> tag
app.mount("main");