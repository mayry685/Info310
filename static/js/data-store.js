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
        },
        // store basic access token
        authToken(state, token) {
            state.authToken = token;
        }

    },

    // add session storage persistence
    plugins: [window.createPersistedState({storage: window.sessionStorage})]

});