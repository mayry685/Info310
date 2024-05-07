"use strict";

export const navigationMenu = {

    computed: {
        signedInUser() {
            return this.signedInUser != null;
        },
        ...Vuex.mapState({
                account: 'account',
                signedInUser: 'signedInUser'
        })
    },

    template:
            `
	<nav>
        <div v-if="signedInUser">
            <a class="nav-link" href=".">Home</a>
            <a class="nav-link" href="#" @click="signOut()">Sign Out</a> 
            <a class="nav-link" href="student-homepage.html">Student View</a>
            <a class="nav-link" href="calendar.html">Calendar</a>
            <a class="nav-link" href="paper-selection.html">Paper Selection</a>
        </div>

        <div v-if="!signedInUser">
            <a class="nav-link" href="sign-in.html">Sign In</a>
            <a class="nav-link" href="create-account.html">Create Account</a>
        </div>
	</nav>
	`,

    methods: {
        signOut() {
            sessionStorage.clear();
            window.location = '.';
        }
    }
};	