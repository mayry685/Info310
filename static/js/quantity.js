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
            quantity: 1
        };
    },
    computed: Vuex.mapState({
        product: 'selectedProduct'
    }),
    mounted() {

    },
    methods: {
        buyProduct() {
            dataStore.commit("addItem", new SaleItem(this.product, this.quantity));
            window.location = "cart.html";
        }
    }
});

import { BasicAccessAuthentication } from './authentication.js';
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js';
app.use(dataStore);
app.mount("main");
