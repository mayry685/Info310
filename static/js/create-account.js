var registerApi = "/api/accounts";
const app = Vue.createApp({
    mounted() {
    },
    data() {
        return {
            account: new Object()
        };
    },
    methods: {
        register() {
            if (this.customer !== null) {
                axios.post(registerApi, this.account)
                        .then(() => {
                            window.location = "sign-in.html";
                        })
                        .catch(error => {
                            alert("Please enter a different username");
                        });
            }
        }
    }
});
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js'
        app.use(dataStore);
app.mount("main");