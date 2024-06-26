var registerApi = "/api/accounts/createAccount";
const app = Vue.createApp({
    mounted() {
        dataStore.commit("error", [])
    },
    data() {
        return {
            account: new Object()
        };
    },
    methods: {
        register(account) {
            this.account = account;
            if (this.errorCheck()) {
                return;
            }
            if (this.account !== null) {
                axios.post(registerApi, account, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                        .then(() => {
                            window.location = "sign-in.html";
                        })
                        .catch(error => {
                            alert("Please enter a different username");
                        });
            }
        },
        
        errorCheck() {
            var errors = []
            if (this.account.UserName.length < 6) {
                errors.push("Username must have 6 or more characters")
            } else {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "api/accounts/usernames", false); // false indicates synchronous request
                xhr.send();

                if (xhr.status === 200) {
                    var usernames = JSON.parse(xhr.responseText);
                    if (usernames.includes(this.account.UserName)) {
                        errors.push("Username is taken");
                    }
                } else {
                    alert("Connection Error");
                }
            }
            
            if (this.account.Password.length < 6) {
                errors.push("Password must have 6 or more characters")
            }
            var validStatus = ["Student", "Teacher"]
            if (!validStatus.includes(this.account.Status)) {
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
            
            // Check email based on status
            if (this.account.Status === "Teacher" && !this.account.Email.endsWith("@otago.ac.nz")) {
                errors.push("Invalid email for Status 'Teacher'");
            } else if (this.account.Status === "Student" && !(this.account.Email.endsWith("@otago.ac.nz") || this.account.Email.endsWith("@student.otago.ac.nz"))) {
                errors.push("Invalid email for Status 'Student'");
            }
            
            dataStore.commit("error", errors)
            return errors.length != 0
        }
    }
});
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js'
        app.use(dataStore);
app.mount("main");