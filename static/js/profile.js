// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],

    mounted() {
        // Load initial data or perform any initialization
        this.loadProfile()
    },
    
    data() {
        return {
            isEditingProfile: false,
            profile: {
                AccountId: '',
                AccountCode: '',
                FirstName: '',
                LastName: '',
                UserName: '',
                Password: '',
                Email: '',
                Status: ''
            },
            editedProfile: {
                AccountId: '',
                AccountCode: '',
                FirstName: '',
                LastName: '',
                UserName: '',
                Password: '',
                Email: '',
                Status: ''
            }
        };
    },

    methods: {
        
        loadProfile() {
            // Fetch user profile data from the API
            console.log(dataStore.state.signedInUser.UserName)
            axios.get('/api/accounts/searchByUsername', {
                params: {
                    username: dataStore.state.signedInUser.UserName
                }
            })
            .then(response => {
                // Assuming response contains user profile data
                this.profile = { ...response.data };
            })
            .catch(error => {
                console.error('Error fetching profile:', error);
                // Handle error appropriately
            });
        },
        
        toggleEdit() {
            // Toggle the editing mode
            this.isEditingProfile = !this.isEditingProfile;
            this.editedProfile = { ...this.profile };
        },
        
        saveProfile() {
            // Send edited profile data to the backend API using Axios
            axios.put('/api/accounts/update', this.editedProfile)
                .then(response => {
                    console.log('Profile updated:', response.data);
                    // Assuming response contains updated profile data
                    // Update the current profile data
                    this.profile = { ...this.editedProfile };
                    // Turn off editing mode
                    this.isEditingProfile = false;
                })
                .catch(error => {
                    console.error('Error updating profile:', error);
                    // Handle error appropriately
                });
        }
    }
});


import { BasicAccessAuthentication } from './authentication.js';
// import the navigation menu
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js';
app.use(dataStore);
// attach the controller to the <main> tag
app.mount("main");
