/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


export const dataStore = Vuex.createStore({

    state() {
        account: null;
        authToken: null;
    },

    mutations: {

        // user signs in
        signIn(state, account) {
            state.account = account;
            state.items = new Array();
        },
        // store basic access token
        authToken(state, token) {
            state.authToken = token;
        },
        addItem(state, item) {
            const existingItem = state.items.find((cartItem) => cartItem.product.productId === item.product.productId);

            if (existingItem) {
                existingItem.quantityPurchased += item.quantityPurchased;
            } else {
                state.items.push(item);
            }
        },
        increaseQuantity(state, item) {
            const existingItem = state.items.find((cartItem) => cartItem.product.productId === item.product.productId);
            existingItem.quantityPurchased++;
        },
        decreaseQuantity(state, item) {
            const existingItemIndex = state.items.findIndex((cartItem) => cartItem.product.productId === item.product.productId);

            if (existingItemIndex !== -1) {
                if (state.items[existingItemIndex].quantityPurchased === 1) {
                    state.items.splice(existingItemIndex, 1);
                } else {
                    state.items[existingItemIndex].quantityPurchased--;
                }
            }
        },

        selectProduct(state, product) {
            state.selectedProduct = product;
        },
        clearItems(state) {
            state.items = new Array();
        }

    },

    // add session storage persistence
    plugins: [window.createPersistedState({storage: window.sessionStorage})]

});