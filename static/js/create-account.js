var registerApi = "/api/register";
const app = Vue.createApp({
    mounted() {
    },
    data() {
        return {
            customer: new Object()
        };
    },
    methods: {
        register() {
            if (this.customer !== null) {
                axios.post(registerApi, this.customer)
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