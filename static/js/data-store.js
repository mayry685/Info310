/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


export const dataStore = Vuex.createStore({

    state() {
        account: null;
        signedInUser: null;
        authToken: null;
        error: [];
    },

    mutations: {

        // user signs in
        signIn(state, account) {
            state.account = account;
        },

        user(state, signedInUser) {
            state.signedInUser = signedInUser;
        },

        // store basic access token
        authToken(state, token) {
            state.authToken = token;
        },
        
        error(state, error) {
            state.error = error;
        }

    },

    // add session storage persistence
    plugins: [window.createPersistedState({storage: window.sessionStorage})]

});