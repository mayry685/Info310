import { dataStore } from './data-store.js'

export const BasicAccessAuthentication = {

	computed: Vuex.mapState({
		authToken: 'authToken'
	}),

	mounted() {
		// add default header to axios
		axios.defaults.headers.common = {'Authorization': `Basic ${this.authToken}`};
	},

	methods: {
		createToken(username, password) {
			let authToken = window.btoa(username + ":" + password);
			dataStore.commit("authToken", authToken);

			// add header to current axios instance since mounted has probably already been executed
			axios.defaults.headers.common = {'Authorization': `Basic ${authToken}`};
		}
	}
};