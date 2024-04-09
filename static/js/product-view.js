var productApi = '/api/products';
var categoryApi = '/api/categories';
// create the Vue controller

"use strict";

class SaleItem {
    constructor(product, quantityPurchased) {
        this.product = product;
        this.quantityPurchased = quantityPurchased;
        this.salePrice = product.listPrice;
    }
}

const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    data() {
        return {
            // models map (comma separated key/value pairs)
            products: new Array(),
            categories: new Array(),
            product: new Object()

        };
    },

    mounted() {
        // semicolon separated statements
        this.getProducts();
        this.getCategories();
    },

    methods: {
        getProducts() {
            axios.get(productApi)
                    .then(response => {
                        this.products = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });
        }
        ,
        getCategories() {
            axios.get(categoryApi)
                    .then(response => {
                        this.categories = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });
        },
        filterCategory(category){
            if(category === "all"){
                this.getProducts();
            } else {
                axios.get(categoryApi + "/" + category)
                        .then(response => {
                            this.products = response.data;
                })
                        .catch(error => {
                            console.error(error);
                    alert("An error occurred - check the console for details.");
                });
            }
        },
        buyProduct(item) {
            dataStore.commit("selectProduct", item);
            window.location = "quantity.html";
        }
    }
});

// import the navigation menu
import { navigationMenu } from './navigation-menu.js';
import { BasicAccessAuthentication } from './authentication.js';
// register the navigation menu under the <navmenu> tag
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js';
app.use(dataStore);
// attach the controller to the <main> tag
app.mount("main");
