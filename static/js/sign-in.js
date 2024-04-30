/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


var accountApi = '/api/accounts/';


// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    data() {
        return {
            account: new Object()
        };
    },

    mounted() {

    },

    methods: {
        signIn() {
            if (this.account !== null) {
                this.createToken(this.account.username, this.account.password);
                console.log(accountApi + "validate?" + "username=" + this.account.username + "&password=" + this.account.password);
                console.log(axios.defaults.headers.common)
                axios.get(accountApi + "validate?" + "username=" + this.account.username + "&password=" + this.account.password)
                    .then(response => {
                        if (response.data !== null) {
                           
                            axios.get(accountApi + "searchByUsername?" + "username=" + this.account.username)
                                .then(accountResponse => {
                                    dataStore.commit("signIn", accountResponse.data);
                                    window.location = "index.html";
                                    console.info(accountResponse);

                                })
                        }
                    })
                    .catch(error => {
                        console.error(error);
                        alert("Incorrect Login Details");
                    });
            }
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
