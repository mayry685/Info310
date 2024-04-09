const app = Vue.createApp({
    mixins: [BasicAccessAuthentication],
    
});

import {BasicAccessAuthentication} from './authentication.js';
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);
import { dataStore } from './data-store.js'
        app.use(dataStore);
app.mount("main");