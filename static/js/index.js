// create the Vue controller
const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    mounted() {

    },
    data() {
        return {
            signedIn: this.account!==null
        };
    },
    computed: Vuex.mapState({
        account: 'account'
    })
});

import { BasicAccessAuthentication } from './authentication.js';
// import the navigation menu
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js';
app.use(dataStore);

// attach the controller to the <main> tag
app.mount("main");