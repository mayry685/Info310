/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

import { BasicAccessAuthentication } from './authentication.js';
import { navigationMenu } from './navigation-menu.js';
import { dataStore } from './data-store.js';

var accountApi = '/api/accounts/';

// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    data() {
        return {
            account: {
                username: '',
                password: ''
            }
        };
    },

    mounted() {
        dataStore.commit("error", []);
    },

    methods: {
        signIn() {
            if (this.account !== null) {
                this.createToken(this.account.username, this.account.password);
                console.log(accountApi + "validate?" + "username=" + this.account.username + "&password=" + this.account.password);

                axios.get(accountApi + "validate?" + "username=" + this.account.username + "&password=" + this.account.password)
                    .then(response => {
                        if (response.data !== null) {
                            axios.get(accountApi + "searchByUsername?" + "username=" + this.account.username)
                                .then(accountResponse => {
                                    dataStore.commit("user", accountResponse.data);
                                    window.location = "index.html";
                                    console.info(accountResponse);
                                });
                        } else {
                            alert("That was an incorrect username");
                        }
                    })
                    .catch(error => {
                        axios.get("api/accounts/usernames")
                            .then(response => {
                                var usernames = response.data;
                                if (usernames.includes(this.account.username)) {
                                    dataStore.commit("error", ["Password is incorrect"]);
                                } else {
                                    dataStore.commit("error", ["Username is incorrect"]);
                                }
                            });
                    });
            }
        }
    }
});

// import the navigation menu
app.component('navmenu', navigationMenu);
app.use(dataStore);
// attach the controller to the <main> tag
app.mount("main");
