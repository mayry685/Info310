/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


var productApi = '/api/products';
var categoryApi = '/api/categories';
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
        signIn(){
        if (this.account !== null) {
            this.createToken(this.account.username, this.account.password);
            axios.get(accountApi + this.account.username)
                .then(response => {
                    if (response.data !== null) {
                        dataStore.commit("signIn", response.data);
                        window.location = 'index.html';
                    } else {
                        alert("That was an incorrect username");
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
