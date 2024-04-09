"use strict";
class SaleItem {
    constructor(product, quantityPurchased) {
        this.product = product;
        this.quantityPurchased = quantityPurchased;
        this.salePrice = product.listPrice;
    }
}
class Sale {
    constructor(customer, items) {
        this.customer = customer;
        this.items = items;
    }
}
var saleApi = "/api/sales";
var productApi = "/api/products";
const app = Vue.createApp({
    mixins: [NumberFormatter, BasicAccessAuthentication],
    mounted() {
    },
    data() {
        return {
            //item: new Object()
            products: new Array()
        };

    },
    computed: Vuex.mapState({
        items: 'items',
        customer: 'customer'
    }),
    methods: {
        checkOut() {
            let sale = new Sale(this.customer, this.items);
            axios.get(productApi)
                    .then(response => {
                        this.products = response.data;
                        let stock = true; //tells us we have valid stock levels
                        for (const cartItem of this.items) { //go through each cart item and compare stock levels
                            const product = this.products.find(product => product.productId === cartItem.product.productId);
                            if (product) {
                                if (cartItem.quantityPurchased > product.quantityInStock) {
                                    alert(`Not enough stock for ${product.productName}`);
                                    stock = false;
                                    break; 
                                }
                            }
                        }
                        if (stock) { //we have enouugh stock for everythingd
                            axios.post(saleApi, sale)
                                    .then(() => {
                                        dataStore.commit("clearItems");
                                        window.location = "order.html";
                                    })
                                    .catch(error => {
                                        alert(error.response.data.message);
                                    });
                        }
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });
        },
        getTotal() {
            let total = 0;
            for (const item of this.items) {
                total += item.salePrice * item.quantityPurchased;
            }
            return total;
        },
        increaseQuantity(item) {
            dataStore.commit("increaseQuantity", item);
        },
        decreaseQuantity(item) {
            dataStore.commit("decreaseQuantity", item);
        }

    }
});

import { BasicAccessAuthentication } from './authentication.js';
import { NumberFormatter } from './number-formatter.js';
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js';
app.use(dataStore);

app.mount("main");

