"use strict";

export const navigationMenu = {

    computed: {
        signedIn() {
            return this.account != null;
        },
        ...Vuex.mapState({
                account: 'account'
        })
    },

    template:
            `
	<nav>
		<div v-if="signedIn">Welcome {{account.firstName}}</div>
		<a href=".">Home</a>
		<a href="#" v-if="signedIn" @click="signOut()">Sign Out</a>
		<a href="sign-in.html" v-if="!signedIn">Sign In</a>
	</nav>
	`,

    methods: {
        signOut() {
            sessionStorage.clear();
            window.location = '.';
        }
    }
};	