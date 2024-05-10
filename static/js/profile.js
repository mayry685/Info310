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
            if (this.errorCheck()) {
                return
            }
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
        },
        
        errorCheck() {
            var errors = []
            if (this.editedProfile.UserName.length < 6) {
                errors.push("Username must have 6 or more characters")
            } else {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "api/accounts/usernames", false); // false indicates synchronous request
                xhr.send();

                if (xhr.status === 200) {
                    var usernames = JSON.parse(xhr.responseText);
                    if (usernames.includes(this.editedProfile.UserName)) {
                        xhr = new XMLHttpRequest();
                        // Construct the URL with the ID parameter
                        var url = "api/accounts/searchByAccountID?id=" + encodeURIComponent(dataStore.state.signedInUser.AccountId);
                        // Open the request, with synchronous flag set to false
                        xhr.open("GET", url, false);
                        xhr.setRequestHeader("Authorization", dataStore.state.authToken);
                        xhr.send();
                        
                        if (this.editedProfile.UserName != JSON.parse(xhr.responseText).UserName) {
                            errors.push("Username is taken");
                        }
                    }
                } else {
                    alert("Connection Error");
                }
            }
            
            if (this.editedProfile.Password.length < 6) {
                errors.push("Password must have 6 or more characters")
            }
            var validStatus = ["Student", "Teacher"]
            if (!validStatus.includes(this.editedProfile.Status)) {
                var message = ""
                validStatus.forEach(function(element, index) {
                    if (index === 0) {
                        message += element;
                    } else if (index === validStatus.length - 1) {
                        message += " or " + element;
                    } else {
                        message += ", " + element;
                    }
                });
                
                errors.push("Status must be either: " + message)
            }
            
            dataStore.commit("error", errors)
            return errors.length != 0
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
