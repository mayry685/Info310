/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


var productApi = '/api/products';
var categoryApi = '/api/categories';
var customerApi = '/api/customers/';

// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    data() {
        return {
            customer: new Object()
        };
    },

    mounted() {

    },

    methods: {
        signIn(){
        if (this.customer !== null) {
            this.createToken(this.customer.username, this.customer.password);
            axios.get(customerApi + this.customer.username)
                .then(response => {
                    if (response.data !== null) {
                        dataStore.commit("signIn", response.data);
                        window.location = 'product-view.html';
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
